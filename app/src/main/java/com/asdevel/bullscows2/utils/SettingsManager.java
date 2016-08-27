package com.asdevel.bullscows2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.asdevel.bullscows2.R;

import java.util.Locale;

/**
 * Gestor para las {@link android.content.SharedPreferences}. *
 *
 * @author Fredy
 */
public class SettingsManager {

    //Constantes
    public static final String PREFERENCES_NAME = "BullsCowsPref";
    public static final String PREF_SOUND = "sound";
    public static final String PREF_VIBRATION = "vibration";
    public static final String PREF_LOCALE = "locale";
    public static final String LOCALE_ES = "ES";
    public static final String LOCALE_EN = "EN";
    public static final String LOCALE_FR = "FR";
    private SharedPreferences shared_preferences;
    private SharedPreferences.Editor shared_preferencesEditor;
    private Context context;


    /**
     * Constructor del Gestor de Preferencias.
     *
     * @param cntx Contexto de la aplicacion.
     */
    public SettingsManager(Context cntx) {
        context = cntx;
        shared_preferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
        if (getLocale().equals(""))
            setLocaleCustom();
    }

    public boolean isSoundOn() {
        return shared_preferences.getBoolean(PREF_SOUND, true);
    }

    public boolean isVibrationOn() {
        return shared_preferences.getBoolean(PREF_VIBRATION, true);
    }

    public String getLocale() {
        return shared_preferences.getString(PREF_LOCALE, "");
    }

    public int getLocaleFlag() {
        String locale = getLocale();
        int locale_flag = R.drawable.flag_en;

        if (locale.equals(LOCALE_EN))
            locale_flag = R.drawable.flag_en;
        if (locale.equals(LOCALE_ES))
            locale_flag = R.drawable.flag_es;
        if (locale.equals(LOCALE_FR))
            locale_flag = R.drawable.flag_fr;

        return locale_flag;
    }

    public String getNextLocale() {

        String locale = getLocale();

        if (locale.equals(LOCALE_EN))
            return LOCALE_ES;
        if (locale.equals(LOCALE_ES))
            return LOCALE_FR;
        if (locale.equals(LOCALE_FR))
            return LOCALE_EN;
        return LOCALE_ES;
    }

    public String setLocale(String locale) {
        shared_preferencesEditor = shared_preferences.edit();
        shared_preferencesEditor.putString(PREF_LOCALE, locale);
        shared_preferencesEditor.commit();
        return locale;
    }

    public void setSound(boolean bo) {
        shared_preferencesEditor = shared_preferences.edit();
        shared_preferencesEditor.putBoolean(PREF_SOUND, bo);
        shared_preferencesEditor.commit();
    }

    public void setVibration(boolean bo) {
        shared_preferencesEditor = shared_preferences.edit();
        shared_preferencesEditor.putBoolean(PREF_VIBRATION, bo);
        shared_preferencesEditor.commit();
    }

    public void setLocaleCustom() {
        String locale = Locale.getDefault().getLanguage();
        if (locale.toUpperCase().equals(LOCALE_ES))
            setLocale(LOCALE_ES);
        else if (locale.toUpperCase().equals(LOCALE_FR))
            setLocale(LOCALE_FR);
        else setLocale(LOCALE_EN);
    }
}

