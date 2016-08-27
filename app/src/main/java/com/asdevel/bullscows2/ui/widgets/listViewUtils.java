package com.asdevel.bullscows2.ui.widgets;

import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Clase de ayuda para trabajar con {@link android.widget.ListView}
 *
 * @author Fredy
 */
public class listViewUtils {
    /**
     * Cambia la altura del listview segun el tamanno especificado y la cantidad de elementos del listview
     *
     * @param listView
     * @param dimen    conjunto de dimensiones
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, int... dimens) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int dimen = 0;
        for (int i : dimens) {
            dimen += i;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            totalHeight += dimen;
        }
        //Se cambia la altura del listview
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listView.getCount() - 1));
        listView.setLayoutParams(params);
    }
}