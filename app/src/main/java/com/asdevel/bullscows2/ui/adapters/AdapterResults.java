package com.asdevel.bullscows2.ui.adapters;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.game.NumeroJugado;

import java.util.ArrayList;

/**
 * Created by Fredy on 24/12/2014.
 */
public class AdapterResults extends BaseAdapter {

    public LayoutInflater mInflater;
    Context context;
    ArrayList<NumeroJugado> results;

    public AdapterResults(Context contex) {
        context = contex;
        mInflater = LayoutInflater.from(context);
        results = new ArrayList<>();
    }

    public void setData(ArrayList<NumeroJugado> resultss) {
        results = resultss;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View ConvertView, ViewGroup parent) {
        final ViewHolder holder;

        NumeroJugado res = results.get(position);
        if (ConvertView == null) {

            ConvertView = mInflater.inflate(R.layout.adapter_result, null);

            holder = new ViewHolder();
            holder.count = (TextView) ConvertView.findViewById(R.id.count);
            holder.gess_text = (TextView) ConvertView.findViewById(R.id.gess_text);
            holder.count_bull = (TextView) ConvertView.findViewById(R.id.count_bull);
            holder.count_cow = (TextView) ConvertView.findViewById(R.id.count_cow);
            holder.img_bull = (ImageView) ConvertView.findViewById(R.id.img_bull);
            holder.img_cow = (ImageView) ConvertView.findViewById(R.id.img_cow);
            ConvertView.setTag(holder);
        } else {
            holder = (ViewHolder) ConvertView.getTag();
        }

        holder.count.setText(position + 1 + "");
        holder.gess_text.setText(res.numero + "");
        holder.count_bull.setText(res.toros + "");
        holder.count_cow.setText(res.vacas + "");

        if (res.toros != 0) {
            AnimationDrawable bAnimation = (AnimationDrawable) context.getResources().getDrawable(R.drawable.bull);
            holder.img_bull.setImageDrawable(bAnimation);
            bAnimation.start();
        } else
            holder.img_bull.setImageResource(R.drawable.bull1);

        if (res.vacas != 0) {
            AnimationDrawable bAnimation = (AnimationDrawable) context.getResources().getDrawable(R.drawable.cow);
            holder.img_cow.setImageDrawable(bAnimation);
            bAnimation.start();
        } else
            holder.img_cow.setImageResource(R.drawable.cow_s3);


        return ConvertView;
    }

    class ViewHolder {
        TextView count;
        TextView gess_text;
        TextView count_bull;
        TextView count_cow;
        ImageView img_bull;
        ImageView img_cow;
    }
}
