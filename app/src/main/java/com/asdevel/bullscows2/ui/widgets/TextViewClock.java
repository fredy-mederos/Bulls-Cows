package com.asdevel.bullscows2.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.asdevel.bullscows2.BullsAndCows;

/**
 * Usa las fuentes definidas en la aplicación.
 * Desde los xml puede ser referenciado como:
 * {@code <com.isofact.bullscows2.ui.widgets.TextViewClock }<br>
 * {@code <!--Parametros--> }<br>
 * {@code > }<br>
 *
 * @author Fredy
 */
public class TextViewClock extends android.widget.TextView {


    public TextViewClock(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public TextViewClock(Context context) {
        super(context);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //Cambia el typeface del componente usando las fuentes definidas en la clase application
        try {
            setTypeface(BullsAndCows.app.font_number);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
