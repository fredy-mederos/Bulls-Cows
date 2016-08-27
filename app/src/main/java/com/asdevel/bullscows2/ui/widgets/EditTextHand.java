package com.asdevel.bullscows2.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.asdevel.bullscows2.BullsAndCows;

/**
 * Usa las fuentes definidas en la aplicación.
 * Desde los xml puede ser referenciado como:
 * {@code <com.isofact.bullscows2.ui.widgets.EditTextHand }<br>
 * {@code <!--Parametros--> }<br>
 * {@code > }<br>
 *
 * @author Fredy
 */
public class EditTextHand extends EditText {


    public EditTextHand(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public EditTextHand(Context context) {
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
    }

}
