package com.asdevel.bullscows2.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

import com.asdevel.bullscows2.BullsAndCows;

/**
 * Created by Fredy on 25/12/2014.
 */
public class CronometerClock extends Chronometer {
    public CronometerClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CronometerClock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CronometerClock(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            setTypeface(BullsAndCows.app.font_number);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
