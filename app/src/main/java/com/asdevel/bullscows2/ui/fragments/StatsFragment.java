package com.asdevel.bullscows2.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;
import android.widget.TextView;

import com.asdevel.bullscows2.BullsAndCows;
import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.game.Ranking;
import com.asdevel.bullscows2.ui.Screen;
import com.asdevel.bullscows2.ui.adapters.AdapterRanking;
import com.asdevel.bullscows2.utils.LOG;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 *
 * Created by Fredy on 24/12/2014.
 */
public class StatsFragment extends Fragment {
    public ListView stats_list;
    TextView tv;
    AdapterRanking adapter;
    Context context;
    View empty_box;

    @SuppressLint("ValidFragment")
    public StatsFragment(Context context) {
        this.context = context;
        adapter = new AdapterRanking(context);
    }

    public StatsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stats_fragment, container, false);

        tv = (TextView) rootView.findViewById(R.id.stats_label);
        stats_list = (ListView) rootView.findViewById(R.id.stats_list);
        empty_box = rootView.findViewById(R.id.empty_box);

        stats_list.setAdapter(adapter);
        stats_list.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(BullsAndCows.app.getBaseContext(), R.anim.abc_slide_in_bottom)));
        stats_list.setItemsCanFocus(false);

        if (android.os.Build.VERSION.SDK_INT >= 11)
            tv.setRotation(-6f);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LOG.e(this, "onResume");
        if (adapter != null)
            adapter.setData(BullsAndCows.app.rankingManager.getRankings());

        if (adapter != null && adapter.getCount() > 0) {
            empty_box.setVisibility(View.GONE);
        } else {
            empty_box.setVisibility(View.VISIBLE);
        }

        try {
            ((Screen) getActivity()).rating_text.setText(getString(R.string.rating));
            ((TextView) ((Screen) getActivity()).rating_box.findViewById(R.id.rating_time)).setText(BullsAndCows.app.rankingManager.getMyRatingTime());
            ((TextView) ((Screen) getActivity()).rating_box.findViewById(R.id.rating_steps)).setText(BullsAndCows.app.rankingManager.getMyRatingSteps());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStatsData(ArrayList<Ranking> rank) {
        adapter.setData(rank);
        if (this.adapter != null && this.adapter.getCount() > 0 && stats_list != null) {
            empty_box.setVisibility(View.GONE);
            stats_list.setVisibility(View.VISIBLE);
        }
    }


}
