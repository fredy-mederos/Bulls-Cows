package com.asdevel.bullscows2.ui.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.asdevel.bullscows2.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * Created by Fredy on 04/01/2015.
 */
public class Confeti extends ImageView {

    ArrayList<ConfetiParticle> citems = new ArrayList<>();

    private static final long mPeriod = 50;
    private Timer mUpdateTimer;
    private float aceleration_min = 20;
    private float y_min = 40;

    boolean closing = false;
    public static final int CANT_CONFETIS = 100;

    public Confeti(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Confeti(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Confeti(Context context) {
        super(context);
    }

    public void init() {
        citems.clear();
        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.confetti11);
        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.confetti12);

        Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.confetti21);
        Bitmap b4 = BitmapFactory.decodeResource(getResources(), R.drawable.confetti22);

        Bitmap b5 = BitmapFactory.decodeResource(getResources(), R.drawable.confetti31);
        Bitmap b6 = BitmapFactory.decodeResource(getResources(), R.drawable.confetti32);


        Random r = new Random();
        for (int i = 0; i < CANT_CONFETIS; i++) {
            int rand = r.nextInt(3);
            if (rand == 0)
                citems.add(new ConfetiParticle(b1, b2));
            else if (rand == 1)
                citems.add(new ConfetiParticle(b3, b4));
            else
                citems.add(new ConfetiParticle(b5, b6));
        }
    }


    public void StartViewing() {
        StopViewing();
        init();

        closing = false;

        setVisibility(View.VISIBLE);

        mUpdateTimer = new Timer();
        mUpdateTimer.schedule(new UpdateTask(), 0, mPeriod);
    }

    public void StopViewing() {
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
        }

        for (ConfetiParticle ci : citems) {
            ci.destroy();
        }
        citems.clear();
        setVisibility(View.GONE);
    }

    class UpdateTask extends TimerTask {
        @Override
        public void run() {

            updateconfeti();
            postInvalidate();

            if (closing) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        StopViewing();
                    }
                });
            }
        }
    }

    private void updateconfeti() {
        for (ConfetiParticle ci : citems) {
            ci.update();
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (ConfetiParticle ci : citems) {
            canvas.drawBitmap(ci.getBitmap(), ci.m, null);
        }
    }

    class ConfetiParticle {

        static final int STATE_1 = 1;
        static final int STATE_2 = 2;

        Bitmap b1;
        Bitmap b2;
        Matrix m;
        float y = 0;
        float x = 0;
        float aceleration = 0;
        final int velocity_base = 5;
        final int aceleration_base = 5;
        int steps = 0;
        int state = STATE_1;
        float rotation = 0;
        float rotation_base = 0;
        float scale_factor = 0.8f;
        final int[] Colors = {0xFF0099cc,0xFF9933cc,0xFF669900,0xFFff8800,0xFFcc0000};

        public ConfetiParticle(Bitmap b1, Bitmap b2) {
            Random r = new Random();
            this.b1 = b1.copy(b1.getConfig(), true);
            this.b2 = b2.copy(b2.getConfig(), true);

            int color = Colors[r.nextInt(Colors.length)];
            updateColor(this.b1, color);
            updateColor(this.b2, color);

            m = new Matrix();
            x = r.nextInt(getWidth());
            y = r.nextInt(100) * -1;
            aceleration = r.nextFloat();
            rotation_base = (float) r.nextInt(10);
            scale_factor = (aceleration * 0.65f)+scale_factor;

            if (aceleration < aceleration_min || aceleration == aceleration_min && y < y_min) {
                aceleration_min = aceleration;
                y_min = y;
            }
        }

        private void updateColor(Bitmap myBitmap, int color) {
            for (int i = 0; i < myBitmap.getHeight(); i++)
                for (int j = 0; j < myBitmap.getWidth(); j++) {
                    int pixelcolor = myBitmap.getPixel(i, j);
                    int newpixelcolor = Color.argb(Color.alpha(pixelcolor), Color.red(color), Color.green(color), Color.blue(color));
                    myBitmap.setPixel(i, j, newpixelcolor);
                }
        }

        public Bitmap getBitmap() {
            return state == STATE_1 ? b1 : b2;
        }


        public void update() {
            y += (velocity_base * aceleration)+aceleration_base;
            steps++;
            rotation += rotation_base;


            m.setTranslate(x, y);
            m.preRotate(rotation,5f,1f);
            m.preScale(scale_factor,scale_factor);

            if (steps > ((3*aceleration)+3)) {
                steps = 0;
                toggleState();
            }
            if (aceleration == aceleration_min && y >= getHeight() + 100) {
                closing = true;
            }
        }

        private void toggleState() {
            state = state == STATE_1 ? STATE_2 : STATE_1;
        }

        public void destroy() {
            b1.recycle();
            b2.recycle();
        }
    }
}
