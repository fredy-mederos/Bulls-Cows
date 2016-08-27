package com.asdevel.bullscows2;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Environment;
import android.provider.Settings;

import com.asdevel.bullscows2.game.Ranking;
import com.asdevel.bullscows2.game.TorosVacasVsPc;
import com.asdevel.bullscows2.utils.RankingManager;
import com.asdevel.bullscows2.utils.SettingsManager;

import java.io.File;
import java.util.Locale;

/**
 *
 * Created by Fredy on 24/12/2014.
 */
public class BullsAndCows extends Application {

    public static final int CANTIDAD_DIGITOS_NUMERO_ADIVINAR = 4;

    public final static int STATE_HOME = 1;
    public static int state = STATE_HOME;
    public final static int STATE_HELP = 2;
    public final static int STATE_STATS = 3;
    public final static int STATE_INFO = 4;
    public final static int STATE_SETTHINGS = 5;
    public static final String DIRECTORIO_JUEGO = ".BullsCows";
    public static BullsAndCows app;
    public static boolean isPlaying = false;
    private final static String FONT_HAND_NAME = "mvboli.ttf";
    private final static String FONT_NUMBER_NAME = "Clockopia.ttf";
    public Typeface font_hand;
    public Typeface font_number;
    public TorosVacasVsPc juegoVSPC;
    public RankingManager rankingManager;
    public Ranking current_ranking;
    public SettingsManager settingsManager;

    @Override
    public void onCreate() {
        super.onCreate();
        font_hand = Typeface.createFromAsset(getAssets(), FONT_HAND_NAME);
        font_number = Typeface.createFromAsset(getAssets(), FONT_NUMBER_NAME);
        rankingManager = new RankingManager(getApplicationContext());
        settingsManager = new SettingsManager(getApplicationContext());
        updateLocale();
        app = this;

    }

    public void initGame() {
        juegoVSPC = new TorosVacasVsPc(CANTIDAD_DIGITOS_NUMERO_ADIVINAR);
        isPlaying = true;
        current_ranking = null;
    }

    public void endGame() {
        isPlaying = false;
    }

    /**
     * @return Id unico del dispositivo
     */
    public String getID() {
        return Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getRutaImagenes() {
        String rutaSD = Environment.getExternalStorageDirectory() + File.separator;
        rutaSD += DIRECTORIO_JUEGO;
        return rutaSD;
    }

    public void updateLocale() {
        Resources res = getApplicationContext().getResources();
        Locale loc = new Locale(settingsManager.getLocale());
        Locale.setDefault(loc);
        Configuration config = new Configuration();
        config.locale = loc;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public void setLocale(String locale) {
        settingsManager.setLocale(locale);
        updateLocale();
    }
}
