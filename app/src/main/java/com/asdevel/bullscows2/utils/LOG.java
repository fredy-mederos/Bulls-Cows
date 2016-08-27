package com.asdevel.bullscows2.utils;

import android.util.Log;

import com.asdevel.bullscows2.BuildConfig;

/**
 * Clase de ayuda que permite mostrar logs en el depurador.<br>
 * Los logs pueden dejar de mostrarse si es necesario.
 * 
 * @author Fredy
 */
public class LOG {

//	private static final String TAG = "IsoApplication";
	/**
	 * Muestra en el LogCat el texto en rojo
	 * 
	 * @param error
	 *            Texto a mostrar
	 */
	public static void e(Object obj,String error) {
		if (BuildConfig.DEBUG)
			Log.e(obj.getClass().getName(), error);
	}
	/**
	 * Muestra en el LogCat el texto en rojo
	 * 
	 * @param error
	 *            Texto a mostrar
	 */
	public static void e(String TAG, String error) {
        if (BuildConfig.DEBUG)
			Log.e(TAG, error);
	}

	/**
	 * Muestra en el LogCat el texto en
	 * 
	 * @param info
	 *            Texto a mostrar
	 */
	public static void i(Object obj,String info) {
        if (BuildConfig.DEBUG)
			Log.i(obj.getClass().getName(), info);
	}

	/**
	 * Muestra en el LogCat el texto en negro
	 * 
	 * @param vervose
	 *            Texto a mostrar
	 */
	public static void v(Object obj,String vervose) {
        if (BuildConfig.DEBUG)
			Log.v(obj.getClass().getName(), vervose);
	}

	/**
	 * Muestra en el LogCat el texto en negro
	 * 
	 * @param vervose
	 *            Texto a mostrar
	 */
	public static void w(Object obj,String vervose) {
        if (BuildConfig.DEBUG)
			Log.w(obj.getClass().getName(), vervose);
	}
}
