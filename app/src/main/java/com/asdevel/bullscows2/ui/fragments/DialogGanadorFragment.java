package com.asdevel.bullscows2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.asdevel.bullscows2.BullsAndCows;
import com.asdevel.bullscows2.R;

import androidx.fragment.app.DialogFragment;

/**
 *
 * Created by Fredy on 26/12/2014.
 */
public class DialogGanadorFragment extends DialogFragment {

    public DialogGanadorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme_Panel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialog = inflater.inflate(R.layout.dialog_ranking_normal, null);


        Button button_ok = (Button) dialog.findViewById(R.id.button_ok);

        ((TextView) dialog.findViewById(R.id.time_text)).setText(BullsAndCows.app.current_ranking.time_string);
        ((TextView) dialog.findViewById(R.id.steps_text)).setText(BullsAndCows.app.current_ranking.steps + "");

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return dialog;
    }

}