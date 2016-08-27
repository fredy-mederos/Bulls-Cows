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

import com.asdevel.bullscows2.BullsAndCows;
import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.game.NumeroJugado;
import com.asdevel.bullscows2.ui.adapters.AdapterResults;
import com.asdevel.bullscows2.ui.widgets.TextViewHand;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 *
 * Created by Fredy on 24/12/2014.
 */
public class GameFragment extends Fragment {

    TextViewHand tv;
    ListView game_list;
    View empty_box;

    AdapterResults adapter;
    Context context;
    ArrayList<NumeroJugado> results;


    @SuppressLint("ValidFragment")
    public GameFragment(Context contex) {
        this.context = contex;
        adapter = new AdapterResults(contex);
    }

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game_fragment, container, false);

        tv = (TextViewHand) rootView.findViewById(R.id.game_label);
        empty_box = rootView.findViewById(R.id.empty_box);

        game_list = (ListView) rootView.findViewById(R.id.game_list);

        game_list.setAdapter(adapter);
        game_list.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(BullsAndCows.app.getBaseContext(), R.anim.abc_slide_in_bottom)));
        game_list.setItemsCanFocus(false);

        if (android.os.Build.VERSION.SDK_INT >= 11)
            tv.setRotation(-6f);


        return rootView;
    }

    public void refreshList() {
        if (results != null && game_list != null)
            game_list.setSelectionFromTop(results.size(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.results != null && this.results.size() > 0) {
            empty_box.setVisibility(View.GONE);
        } else {
            empty_box.setVisibility(View.VISIBLE);
        }
    }

    public void setGameData(ArrayList<NumeroJugado> results) {
        this.results = results;
        adapter.setData(results);

        refreshList();

        if (this.results != null && this.results.size() > 0 && game_list != null) {
            empty_box.setVisibility(View.GONE);
            game_list.setVisibility(View.VISIBLE);
        }
    }
}
