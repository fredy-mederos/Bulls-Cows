package com.asdevel.bullscows2.ui.widgets.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asdevel.bullscows2.R;

/**
 * Widget que tiene apariencia de paginador. Se de definen cuantas páginas contiene la pantalla y en cual estamos en este momento.
 * Desde los xml puede ser referenciado como: <br>
 * {@code <com.isofact.bullscows2.ui.widgets.base.PaginatorView }<br>
 * {@code <!--Parametros--> }<br>
 * {@code > }<br>
 * <br>
 * Los parametros pueden ser:<br>
 * <li>{@code total } Cantidad de páginas.
 * <li>{@code current } Página actual.
 * <li>{@code resON } Recurso a mostrar en el elento que se encuentre en current.
 * <li>{@code resOff } Recurso a mostrar para los demas elementos.
 * <br>Funciones principales:<br>
 * <li>{@link #setPaginator_ISOF_current(int)}
 *
 * @author Fredy
 */
public class PaginatorView extends LinearLayout {

    // parametros xml y atributos
    int Paginator_ISOF_total = 0;
    int Paginator_ISOF_current = 0;
    int max = 0;
    int resOn;
    int resOff;
    int size;
    Context cntx;

    public PaginatorView(Context context) {
        super(context);
        cntx = context;
    }

    public PaginatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttrs(context, attrs);
    }

    public void loadAttrs(Context context, AttributeSet attrs) {
        cntx = context;

        if (attrs == null)
            return;
        //Se cargan los valores de los parametros desde el xml

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Iso_Paginator);

        Paginator_ISOF_total = a.getInteger(R.styleable.Iso_Paginator_total, 1);
        Paginator_ISOF_current = a.getInteger(R.styleable.Iso_Paginator_current, 0);
        max = a.getInteger(R.styleable.Iso_Paginator_max, 20);

        resOn = a.getResourceId(R.styleable.Iso_Paginator_resON, 0);
        resOff = a.getResourceId(R.styleable.Iso_Paginator_resOff, 0);

        if (resOn == 0 || resOff == 0) {
            throw new IllegalArgumentException(
                    "Please set \"resOn\" and \"resOff\" attributes.");
        }

//		initViews();
        a.recycle();
    }

    private void fixCurrentMax() {
//		LOG.e(this, "Paginator_ISOF_total "+Paginator_ISOF_total+ " Paginator_ISOF_current "+Paginator_ISOF_current+" max "+max);
        float div = (float) Paginator_ISOF_total / max * 1f;
        if (div < 1)
            div = 1;
        Paginator_ISOF_current = Math.round(Paginator_ISOF_current / div);
//		LOG.e(this, "Paginator_ISOF_total "+Paginator_ISOF_total+ " Paginator_ISOF_current "+Paginator_ISOF_current+" max "+max + " div " + div);
    }

    /**
     * Genera la vista del componente
     */
    private void initViews() {
        fixCurrentMax();

        int size = getResources().getDimensionPixelSize(R.dimen.paginator_size);
        int margin = getResources().getDimensionPixelSize(R.dimen.paginator_margin);
//		int size =10;
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(size, size);
        //los margenes entre elementos son de 12px
        params.setMargins(margin, 0, margin, 0);

        if (Paginator_ISOF_current <= Paginator_ISOF_total) {
            for (int i = 1; i <= Paginator_ISOF_total; i++) {
                ImageView img = new ImageView(getContext());
                if (i == Paginator_ISOF_current)
                    img.setBackgroundResource(resOn);
                else
                    img.setBackgroundResource(resOff);

                addView(img, params);
            }
        }
    }

    //Get Y Sets
    public int getPaginator_ISOF_total() {
        return Paginator_ISOF_total;
    }

    public void setPaginator_ISOF_total(int paginator_ISOF_total) {
        Paginator_ISOF_total = paginator_ISOF_total;
    }

    public int getPaginator_ISOF_current() {
        return Paginator_ISOF_current;
    }

    /**
     * Cambia el elemento actual y actualiza la vista
     *
     * @param paginator_ISOF_current
     */
    public void setPaginator_ISOF_current(int paginator_ISOF_current) {
        Paginator_ISOF_current = paginator_ISOF_current;
        removeAllViews();
        initViews();
    }

    public int getResOn() {
        return resOn;
    }

    public void setResOn(int resOn) {
        this.resOn = resOn;
    }

    public int getResOff() {
        return resOff;
    }

    public void setResOff(int resOff) {
        this.resOff = resOff;
    }

}
