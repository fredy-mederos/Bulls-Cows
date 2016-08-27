package com.asdevel.bullscows2.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.game.NumeroJugado;
import com.asdevel.bullscows2.ui.adapters.AdapterResults;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Fredy on 24/12/2014.
 */
public class HelpFragment extends Fragment {

    TextView tv;

    ListView game_list_help;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.help_fragment, container, false);
        tv = (TextView) rootView.findViewById(R.id.game_label);
        game_list_help = (ListView) rootView.findViewById(R.id.game_list_help);
        context = getActivity();

        AdapterResults adapter = new AdapterResults(context);
        ArrayList<NumeroJugado> rs = new ArrayList<>();
        NumeroJugado nj = new NumeroJugado();
        rs.add(nj);
        nj.numero = "0329";
        nj.toros = 2;
        nj.vacas = 1;
        adapter.setData(rs);
        game_list_help.setAdapter(adapter);
        return rootView;
    }
}
