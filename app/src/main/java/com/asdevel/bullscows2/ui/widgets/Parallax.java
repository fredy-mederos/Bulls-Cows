package com.asdevel.bullscows2.ui.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.utils.LOG;

/**
 *
 * Created by Fredy on 04/01/2015.
 */
public class Parallax extends HorizontalScrollView {

    boolean wasResised = false;
    ImageView backg;
    private static final String back_id = "88";
    private int BackgroundWidth = 0;

    public Parallax(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public Parallax(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public Parallax(Context context) {
        super(context);
        init();
    }
    private void init(){
        setEnabled(false);
        removeAllViews();

        backg = new ImageView(getContext());
        backg.setBackgroundResource(R.drawable.screen_bg2);
        backg.setId(Integer.parseInt(back_id));
        addView(backg);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    public void doParallax(int position, boolean animated){

        int part = getBackgroundWidth()/8;

        final int from = getScrollX();
        final int to = part*position;

        LOG.e(this,"doParallax from:"+from+" to:"+to);

        if(animated) {
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                ObjectAnimator anim = ObjectAnimator.ofInt(this, "ScrollX", from, to);
                anim.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime) * 2);
                anim.start();
            } else {
                smoothScrollTo(to, 0);
            }
        }
        else{
            scrollTo(to,0);
        }

    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Se cambia el mínimo de altura.
                if (getHeight() > 0 && !wasResised) {
                    wasResised = true;
                    backg.setMinimumHeight(getHeight());
                    backg.setMinimumWidth(getBackgroundWidth());
                }
                else
                    wasResised = false;
            }
        });
    }
    private int getBackgroundWidth(){
        BackgroundWidth = (getHeight()*480)/320;
        LOG.e(this,"BackgroundWidth "+BackgroundWidth);
        return BackgroundWidth;
    }
}
