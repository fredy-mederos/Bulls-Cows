package com.asdevel.bullscows2.utils;

import android.os.Handler;
import android.view.View;

import com.asdevel.bullscows2.ui.fragments.DialogCleanFragment;

/**
 * Created by Fredy on 26/12/2014.
 */
public abstract class OnCleanListener implements View.OnClickListener {
    public DialogCleanFragment dialog;
    Handler h;

    public OnCleanListener() {
        h = new Handler();
    }

    @Override
    public void onClick(View v) {
        notifyOnClean();
    }

    public void notifyOnClean() {
        h.post(new Runnable() {
            @Override
            public void run() {
                OnClean();
            }
        });
    }

    protected abstract void OnClean();
}
