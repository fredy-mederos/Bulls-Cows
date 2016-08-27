package com.asdevel.bullscows2.utils;

import android.os.Handler;
import android.view.View;

import com.asdevel.bullscows2.ui.fragments.DialogRankingFragment;

/**
 * Created by Fredy on 26/12/2014.
 */
public abstract class OnCreateRankingListener implements View.OnClickListener {
    public DialogRankingFragment dialog;
    Handler h;

    public OnCreateRankingListener() {
        h = new Handler();
    }

    @Override
    public void onClick(View v) {
        notifyOnCreateRanking(dialog.name.getText().toString());
    }

    public void notifyOnCreateRanking(final String name) {
        h.post(new Runnable() {
            @Override
            public void run() {
                OnCreateRanking(name);
            }
        });
    }

    protected abstract void OnCreateRanking(String name);
}
