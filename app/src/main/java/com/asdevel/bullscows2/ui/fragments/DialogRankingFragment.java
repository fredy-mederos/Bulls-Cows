package com.asdevel.bullscows2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.asdevel.bullscows2.BullsAndCows;
import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.utils.OnCreateRankingListener;

import androidx.fragment.app.DialogFragment;

/**
 *
 * Created by Fredy on 26/12/2014.
 */
public class DialogRankingFragment extends DialogFragment {
    public EditText name;
    public String lastName = "";
    OnCreateRankingListener listener;
    OnCreateRankingListener cancel_listener;


    public DialogRankingFragment() {
    }


    public void setListener(OnCreateRankingListener positiveAction, OnCreateRankingListener negativeAction) {
        listener = positiveAction;
        cancel_listener = negativeAction;
        listener.dialog = this;
        cancel_listener.dialog = this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme_Panel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialog = inflater.inflate(R.layout.dialog_ranking, null);
        name = (EditText) dialog.findViewById(R.id.name);
        name.setText(lastName);


        Button button_ok = (Button) dialog.findViewById(R.id.button_ok);
        Button button_cancel = (Button) dialog.findViewById(R.id.button_cancel);

        ((TextView) dialog.findViewById(R.id.time_text)).setText(BullsAndCows.app.current_ranking.time_string);
        ((TextView) dialog.findViewById(R.id.steps_text)).setText(BullsAndCows.app.current_ranking.steps + "");

        button_ok.setOnClickListener(listener);
        button_cancel.setOnClickListener(cancel_listener);

        return dialog;
    }

}