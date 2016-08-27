package com.asdevel.bullscows2.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.asdevel.bullscows2.BullsAndCows;

/**
 * Usa las fuentes definidas en la aplicación.
 * Desde los xml puede ser referenciado como:
 * {@code <com.isofact.bullscows2.ui.widgets.TextViewHand }<br>
 * {@code <!--Parametros--> }<br>
 * {@code > }<br>
 *
 * @author Fredy
 */
public class ButtonHand extends Button {


    public ButtonHand(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonHand(Context context) {
        super(context);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //Cambia el typeface del componente usando las fuentes definidas en la clase application
        try {
            setTypeface(BullsAndCows.app.font_hand);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                //Se cambia el minimo de altura.
//                if (getHeight() < ((View) getParent()).getHeight()) {
//                    setMinimumHeight(((View) getParent()).getHeight());
//                }
//            }
//        });
    }

}
