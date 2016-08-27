package com.asdevel.bullscows2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.game.Ranking;

import java.util.ArrayList;

/**
 *
 * Created by Fredy on 24/12/2014.
 */
public class AdapterRanking extends BaseAdapter {

    public LayoutInflater mInflater;
    Context context;
    ArrayList<Ranking> ranking;

    public AdapterRanking(Context contex) {
        context = contex;
        mInflater = LayoutInflater.from(context);
        ranking = new ArrayList<>();
    }

    public void setData(ArrayList<Ranking> rankingss) {
        ranking = rankingss;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ranking.size();
    }

    @Override
    public Object getItem(int position) {
        return ranking.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View ConvertView, ViewGroup parent) {
        final ViewHolder holder;

        Ranking res = ranking.get(position);
        if (ConvertView == null) {

            ConvertView = mInflater.inflate(R.layout.adapter_stats, null);

            holder = new ViewHolder();
            holder.ranking = (TextView) ConvertView.findViewById(R.id.ranking);
            holder.name_text = (TextView) ConvertView.findViewById(R.id.name_text);
            holder.time_text = (TextView) ConvertView.findViewById(R.id.time_text);
            holder.steps_text = (TextView) ConvertView.findViewById(R.id.steps_text);

            ConvertView.setTag(holder);
        } else {
            holder = (ViewHolder) ConvertView.getTag();
        }

        holder.ranking.setText(position + 1 + "");
        holder.name_text.setText(res.name);
        holder.time_text.setText(res.time_string);
        holder.steps_text.setText(res.steps + "");

        holder.ranking.setBackgroundResource(position % 2 == 0 ? R.drawable.circle_red_fill : R.drawable.circle_gray_fill);
        holder.name_text.setTextColor(context.getResources().getColor(position % 2 == 0 ? R.color._red : R.color._gray_darker));


        return ConvertView;
    }

    class ViewHolder {
        TextView ranking;
        TextView name_text;
        TextView time_text;
        TextView steps_text;
    }
}
