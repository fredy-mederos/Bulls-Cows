package com.asdevel.bullscows2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.utils.OnCleanListener;

import androidx.fragment.app.DialogFragment;

/**
 *
 * Created by Fredy on 26/12/2014.
 */
public class DialogCleanFragment extends DialogFragment {
    public EditText name;
    public String cant_a_borrar;
    OnCleanListener listener;


    public DialogCleanFragment() {
    }


    public void setListener(OnCleanListener positiveAction) {
        listener = positiveAction;
        listener.dialog = this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme_Panel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialog = inflater.inflate(R.layout.dialog_clean, null);

        Button button_ok = (Button) dialog.findViewById(R.id.button_ok);
        Button button_cancel = (Button) dialog.findViewById(R.id.button_cancel);

        TextView cant_borrar = (TextView) dialog.findViewById(R.id.cant_borrar);
        cant_borrar.setText(cant_a_borrar);

        button_ok.setOnClickListener(listener);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }


}