package com.asdevel.bullscows2.utils;

import android.os.Handler;
import android.view.View;

import com.asdevel.bullscows2.ui.fragments.DialogRestartFragment;

/**
 * Created by Fredy on 26/12/2014.
 */
public abstract class OnRestartRankingListener implements View.OnClickListener {
    public DialogRestartFragment dialog;
    Handler h;

    public OnRestartRankingListener() {
        h = new Handler();
    }

    @Override
    public void onClick(View v) {
        notifyOnRestartRanking();
    }

    public void notifyOnRestartRanking() {
        h.post(new Runnable() {
            @Override
            public void run() {
                OnRestartRanking();
            }
        });
    }

    protected abstract void OnRestartRanking();
}
