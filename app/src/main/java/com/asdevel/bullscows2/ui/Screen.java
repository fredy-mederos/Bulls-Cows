package com.asdevel.bullscows2.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.asdevel.bullscows2.BullsAndCows;
import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.game.NumeroJugado;
import com.asdevel.bullscows2.game.Ranking;
import com.asdevel.bullscows2.game.TorosVacas;
import com.asdevel.bullscows2.ui.fragments.DialogGanadorFragment;
import com.asdevel.bullscows2.ui.fragments.DialogPerdedorFragment;
import com.asdevel.bullscows2.ui.fragments.DialogRankingFragment;
import com.asdevel.bullscows2.ui.fragments.GameFragment;
import com.asdevel.bullscows2.ui.fragments.HelpFragment;
import com.asdevel.bullscows2.ui.fragments.InfoFragment;
import com.asdevel.bullscows2.ui.fragments.SettingsFragment;
import com.asdevel.bullscows2.ui.fragments.StatsFragment;
import com.asdevel.bullscows2.ui.widgets.Confeti;
import com.asdevel.bullscows2.ui.widgets.Parallax;
import com.asdevel.bullscows2.utils.OnCreateRankingListener;
import com.asdevel.bullscows2.utils.RankingManager;
import com.asdevel.bullscows2.utils.LOG;
import com.asdevel.bullscows2.ui.widgets.base.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 *
 * Created by Fredy on 23/12/2014.
 */
public class Screen extends AppCompatActivity {

    //Views
    public FrameLayout screen_frame;
    public View rating_box;
    public android.os.Bundle savedInstance;
    protected TextView title;
    Context context;
    FloatingActionButton fabbutton;
    Toolbar toolbar_actionbar;
    GameFragment f_game;
    HelpFragment f_help;
    InfoFragment f_info;
    StatsFragment f_stats;
    SettingsFragment f_sett;
    EditText gess_text;
    Chronometer clock;
    public TextView rating_text;
    ArrayList<NumeroJugado> results = new ArrayList<>();
    InputMethodManager administradorEntrada;
    //    AudioManager administradorAudio;
    Vibrator vibratorService;
    MediaPlayer mMediaPlayer;
    long baseTime = SystemClock.elapsedRealtime();
    Parallax parallax;
    Confeti confeti;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        savedInstance = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen);

        context = this;
        BullsAndCows.app.updateLocale();

        rating_text = (TextView) findViewById(R.id.rating_text);
        toolbar_actionbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        screen_frame = (FrameLayout) findViewById(R.id.screen_frame);
        fabbutton = (FloatingActionButton) findViewById(R.id.fabbutton);
        parallax = (Parallax) findViewById(R.id.parallax);
        setSupportActionBar(toolbar_actionbar);


        getSupportActionBar().setCustomView(R.layout.action_bar_title);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        gess_text = (EditText) findViewById(R.id.gess_text);
        clock = (Chronometer) findViewById(R.id.clock);
        rating_box = findViewById(R.id.rating_box);
        confeti = (Confeti) findViewById(R.id.confeti);


        try {
            baseTime = savedInstanceState.getLong("baseTime");

        } catch (Exception e) {
            baseTime = SystemClock.elapsedRealtime();
        }
        boolean playing;
        try {
            playing = savedInstanceState.getBoolean("isPlaying");
        } catch (Exception e) {
            playing = false;
        }
        if (!playing) {
            finishGame();
        }


        f_game = new GameFragment(context);
        f_help = new HelpFragment();
        f_info = new InfoFragment();
        f_stats = new StatsFragment(context);
        f_sett = new SettingsFragment();

        activarFiltrosEditText();
        setupFloatingButton();

//        administradorAudio = (AudioManager) getSystemService(AUDIO_SERVICE);
        vibratorService = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (BullsAndCows.isPlaying) {
            clock.setBase(baseTime);
            clock.start();
            fabbutton.setDrawable(getResources().getDrawable(R.drawable.ic_edit));
            reloadList();
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("baseTime", clock.getBase());
        outState.putBoolean("isPlaying", BullsAndCows.isPlaying);
        savedInstance = outState;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LOG.e(this, "onStart");
        setFragmentWithAnimation(BullsAndCows.state, false);
        if (BullsAndCows.state == BullsAndCows.STATE_HOME) {
            reloadList();
        }
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onStop() {
        super.onStop();
        setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
        liberarReproductor();

//        BullsAndCows.app.endGame();
    }

    private void hideTextView() {
        if (gess_text.getVisibility() == View.VISIBLE) {
            Animation.AnimationListener al = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    gess_text.setVisibility(View.GONE);
                    gess_text.setText("");
                    try {
                        administradorEntrada.hideSoftInputFromWindow(gess_text.getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            };
            Animation an = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
            an.setAnimationListener(al);
            gess_text.startAnimation(an);
        } else {
            gess_text.setVisibility(View.GONE);
            gess_text.setText("");
            try {
                administradorEntrada.hideSoftInputFromWindow(gess_text.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void activarFiltrosEditText() {
        InputFilter filtroNumeroUnico = new InputFilter() {
            public CharSequence filter(CharSequence aNuevoTexto, int aInicioNuevoTexto, int aFinNuevoTexto, Spanned aTextoDestino, int aInicioTextoDestino, int aFinTextoDestino) {
                for (int i = aInicioNuevoTexto; i < aFinNuevoTexto; i++) {
                    if (aTextoDestino.toString().contains(String.valueOf(aNuevoTexto.charAt(i)))) {
                        return "";
                    }
                }
                return null;
            }
        };
        //Adicionando filtros para que no se repitan los números en el edit  y para establecer la longitud máxima que acepta el EditText
        gess_text.setFilters(new InputFilter[]{filtroNumeroUnico, new InputFilter.LengthFilter(BullsAndCows.CANTIDAD_DIGITOS_NUMERO_ADIVINAR)});
    }

    private void setupFloatingButton() {
        administradorEntrada = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        gess_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 10)
                    hideTextView();
                else if (actionId == 11) {
                    submitNumber();
                    hideTextView();
                }
                return false;
            }
        });

        fabbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!BullsAndCows.isPlaying) {
                    clock.setBase(SystemClock.elapsedRealtime());
                    clock.start();
                    BullsAndCows.app.initGame();
                    reloadList();
                }

                fabbutton.setDrawable(getResources().getDrawable(R.drawable.ic_edit));

                if (gess_text.getVisibility() == View.GONE) {
                    gess_text.setVisibility(View.VISIBLE);

                    Animation.AnimationListener al = new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            gess_text.requestFocus();
                            administradorEntrada.showSoftInput(gess_text, 0);
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    };
                    Animation an = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
                    an.setAnimationListener(al);
                    gess_text.startAnimation(an);
                } else {
                    gess_text.requestFocus();
                    administradorEntrada.showSoftInput(gess_text, 0);
                }

                submitNumber();

            }
        });

    }

    private void submitNumber() {

        if (gess_text.getText().toString().length() == BullsAndCows.CANTIDAD_DIGITOS_NUMERO_ADIVINAR) {
            BullsAndCows.app.juegoVSPC.Jugada(gess_text.getText().toString());
            NumeroJugado nj = BullsAndCows.app.juegoVSPC.getArregloNumerosJugados()[BullsAndCows.app.juegoVSPC.getCantidadJugadasRealizadas() - 1].copy();
            results.add(nj);

            f_game.setGameData(results);
            gess_text.setText("");

            switch (BullsAndCows.app.juegoVSPC.resultadosJuego()) {
                case TorosVacas.ES_GANADOR:
                    accionesGanador();
                    break;

                case TorosVacas.ES_PERDEDOR:
                    accionesPerdedor();
                    break;

                case TorosVacas.CONTINUA_JUEGO:
                    if (BullsAndCows.app.juegoVSPC.getCantidadJugadasRealizadas() == 9) {
                        // Reproduciendo continuamente sonido de la penúltima jugada
                        reproducirPenultimaJugada();
                    }
                    break;
            }
        }
    }

    public void liberarReproductor() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void MediaPlayerPlay(int res, boolean looping) {
        if (BullsAndCows.app.settingsManager.isSoundOn()) {
            mMediaPlayer = MediaPlayer.create(this, res);
            if (looping)
                mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        }
    }

    private void Vibrate() {
        if (BullsAndCows.app.settingsManager.isVibrationOn())
            vibratorService.vibrate(500);
    }

    private void reproducirPenultimaJugada() {
        MediaPlayerPlay(R.raw.sonido_penultima_jugada, true);
    }

    private void accionesPerdedor() {
        finishGame();
        fabbutton.setDrawable(getResources().getDrawable(R.drawable.ic_floating_restart));
        liberarReproductor();

        MediaPlayerPlay(R.raw.sonido_perdedor, false);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogPerdedorFragment dr = new DialogPerdedorFragment();
        dr.show(ft, "dialog2");
    }

    private void accionesGanador() {
        confeti.StartViewing();

        finishGame();
        fabbutton.setDrawable(getResources().getDrawable(R.drawable.ic_floating_restart));

        BullsAndCows.app.current_ranking = new Ranking("", clock.getText().toString(), RankingManager.to_real_time(clock.getText().toString()), BullsAndCows.app.juegoVSPC.getCantidadJugadasRealizadas(), BullsAndCows.app.getID(), new Date().getTime());

        Vibrate();

        liberarReproductor();

        MediaPlayerPlay(R.raw.sonido_ganador, false);

        if (!BullsAndCows.app.rankingManager.canAddRanking(BullsAndCows.app.current_ranking)) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DialogGanadorFragment dr = new DialogGanadorFragment();
            dr.show(ft, "dialog4");
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogRankingFragment dr = new DialogRankingFragment();
        dr.lastName = BullsAndCows.app.rankingManager.getLastRankingName();

        dr.setListener(new OnCreateRankingListener() {
            @Override
            protected void OnCreateRanking(String name) {
                if (name.equals(""))
                    return;
                BullsAndCows.app.current_ranking.name = name;
                BullsAndCows.app.rankingManager.addRanking(BullsAndCows.app.current_ranking);
                dialog.dismiss();
                setFragmentWithAnimation(BullsAndCows.STATE_STATS, true);

            }
        }, new OnCreateRankingListener() {
            @Override
            protected void OnCreateRanking(String mail) {
                BullsAndCows.app.current_ranking = null;
                dialog.dismiss();
            }
        });
        dr.show(ft, "dialog");
    }

    public void finishGame() {
        BullsAndCows.app.endGame();
        clock.stop();
        hideTextView();
        fabbutton.setDrawable(getResources().getDrawable(R.drawable.ic_play));
    }

    private void reloadList() {
        if (results != null && BullsAndCows.isPlaying) {
            LOG.e(this, "reloadList " + new Gson().toJson(results));
            results.clear();
            for (int i = 0; i < BullsAndCows.app.juegoVSPC.getCantidadJugadasRealizadas(); i++) {
                NumeroJugado nj = BullsAndCows.app.juegoVSPC.getArregloNumerosJugados()[i].copy();
                results.add(nj);
            }
            f_game.setGameData(results);
        }
    }

    public void updateTitle() {
        String logo = "<font color='#78767A'>" + getString(R.string.bulls) + "</font><font color='#202020'> " + getString(R.string.title_and) + " </font><font color='#F4E1DE'>" + getString(R.string.cows) + "</font>";
        switch (BullsAndCows.state) {
            case BullsAndCows.STATE_HOME:
                title.setText(Html.fromHtml(logo));
                break;
            case BullsAndCows.STATE_HELP:
                title.setText(getString(R.string.menu_help));
                break;
            case BullsAndCows.STATE_INFO:
                title.setText(getString(R.string.menu_about));
                break;
            case BullsAndCows.STATE_STATS:
                title.setText(getString(R.string.menu_stats));
                break;
            case BullsAndCows.STATE_SETTHINGS:
                title.setText(getString(R.string.menu_settings));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menur;
        switch (BullsAndCows.state) {
            case BullsAndCows.STATE_HOME:
                menur = R.menu.main;
                break;
            case BullsAndCows.STATE_STATS:
                menur = R.menu.stats;
                break;
            default:
                return super.onCreateOptionsMenu(menu);

        }
        getMenuInflater().inflate(menur, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void setFragment(final int st, Fragment f) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.screen_frame, f);
        fragmentTransaction.commit();
        BullsAndCows.state = st;
        if (st != BullsAndCows.STATE_HOME) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        invalidateOptionsMenu();
        updateTitle();

    }



    public void setFragmentWithAnimation(final int st, boolean anim) {

        hideTextView();

        Fragment ftemp = new Fragment();
        switch (st) {
            case BullsAndCows.STATE_HOME:
                ftemp = f_game;
                break;
            case BullsAndCows.STATE_HELP:
                ftemp = f_help;
                break;
            case BullsAndCows.STATE_INFO:
                ftemp = f_info;
                break;
            case BullsAndCows.STATE_STATS:
                ftemp = f_stats;
                break;
            case BullsAndCows.STATE_SETTHINGS:
                ftemp = f_sett;
                break;
        }
        final Fragment f = ftemp;

        if (!anim) {
            fabbutton.hide(st != BullsAndCows.STATE_HOME);
            clock.setVisibility(st == BullsAndCows.STATE_HOME ? View.VISIBLE : View.GONE);
            rating_box.setVisibility(st == BullsAndCows.STATE_STATS ? View.VISIBLE : View.GONE);
            setFragment(st, f);
            doParallax(st,false);
            return;
        }
        int anim1 = R.anim.slide_in_right;
        int anim2 = R.anim.slide_out_left;

        if (st == BullsAndCows.STATE_HOME || (st == BullsAndCows.STATE_SETTHINGS && BullsAndCows.state == BullsAndCows.STATE_INFO)) {
            anim1 = R.anim.slide_in_left;
            anim2 = R.anim.slide_out_right;
        }

        final int anim_in = anim1;
        final int anim_out = anim2;


        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fabbutton.hide(st != BullsAndCows.STATE_HOME);
                clock.setVisibility(View.GONE);
                rating_box.setVisibility(View.GONE);

                doParallax(st,true);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setFragment(st, f);

                Animation an = AnimationUtils.loadAnimation(context, anim_in);
                Animation.AnimationListener al = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        clock.setVisibility(st == BullsAndCows.STATE_HOME ? View.VISIBLE : View.GONE);
                        rating_box.setVisibility(st == BullsAndCows.STATE_STATS ? View.VISIBLE : View.GONE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                };
                an.setAnimationListener(al);
                screen_frame.startAnimation(an);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
        Animation an = AnimationUtils.loadAnimation(context, anim_out);
        an.setAnimationListener(al);
        screen_frame.startAnimation(an);
    }

    private void doParallax(final int st, final boolean b) {
        if(parallax==null)
            return;
        if(st == BullsAndCows.STATE_HOME)
            parallax.doParallax(0,b);
        else if(st == BullsAndCows.STATE_INFO)
            parallax.doParallax(2,b);
        else parallax.doParallax(1,b);
    }

    @Override
    public void onBackPressed() {
        if (BullsAndCows.state == BullsAndCows.STATE_HOME) {
            hideTextView();
            super.onBackPressed();
        } else if (BullsAndCows.state == BullsAndCows.STATE_INFO) {
            setFragmentWithAnimation(BullsAndCows.STATE_SETTHINGS, true);
        } else {
            setFragmentWithAnimation(BullsAndCows.STATE_HOME, true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_help) {
            setFragmentWithAnimation(BullsAndCows.STATE_HELP, true);
        }
        if (item.getItemId() == R.id.menu_stats) {
            setFragmentWithAnimation(BullsAndCows.STATE_STATS, true);
        }
        if (item.getItemId() == android.R.id.home) {
            if (BullsAndCows.state == BullsAndCows.STATE_INFO)
                setFragmentWithAnimation(BullsAndCows.STATE_SETTHINGS, true);
            else
                setFragmentWithAnimation(BullsAndCows.STATE_HOME, true);
        }
        if (item.getItemId() == R.id.menu_settings) {
            setFragmentWithAnimation(BullsAndCows.STATE_SETTHINGS, true);
        }
        if (item.getItemId() == R.id.menu_share) {
            f_stats.stats_list.setSelectionFromTop(0, 0);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            final Handler handlerCompartir = new Handler();
            handlerCompartir.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String nombreFichero = generarNombreFichero();
                    if (capturarPantalla(nombreFichero)) {
                        String rutaFichero = BullsAndCows.app.getRutaImagenes() + File.separator + nombreFichero;
                        compartirImagen(rutaFichero);
                    }
                    //mostrando la barra de estado
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }, 1500);
        }
        if (item.getItemId() == R.id.menu_reload) {
            liberarReproductor();
            BullsAndCows.app.endGame();
            clock.stop();
            clock.setBase(SystemClock.elapsedRealtime());
            results.clear();
            f_game.setGameData(results);
            f_game.onResume();

            gess_text.setText("");
            fabbutton.performClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BullsAndCows.app.updateLocale();
    }

    public String generarNombreFichero() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss", Locale.US);
        return formatoFecha.format(new GregorianCalendar().getTime()) + ".png";
    }

    public boolean capturarPantalla(final String aNombreCaptura) {
        // liberando el reproductor en caso de estar reproduciendo algún sonido
        liberarReproductor();
        // reproduciendo sonido del obturador
        MediaPlayerPlay(R.raw.sonido_obturador_camara, false);


        if (estaMontadaSdCard()) {
            View vista = getWindow().getDecorView().getRootView();
            vista.setDrawingCacheEnabled(true);
            //capturando la pantalla
            Bitmap imagenPantalla = vista.getDrawingCache();
            return guardarImagen(imagenPantalla, aNombreCaptura);

        } else {
            Toast.makeText(this, R.string.mensaje_error_no_existe_sd, Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public void compartirImagen(String aRutaImagen) {
        Intent intentCompartir = new Intent(Intent.ACTION_SEND);
        String asuntoCorreo = getString(R.string.asunto_correo) + " " + getString(R.string.app_name);
        //Colocando datos para correo
        intentCompartir.putExtra(Intent.EXTRA_SUBJECT, asuntoCorreo);
        intentCompartir.putExtra(Intent.EXTRA_TEXT, getString(R.string.cuerpo_correo));
        //Colocando datos para MMS
        intentCompartir.putExtra("subject", asuntoCorreo);
        intentCompartir.putExtra("sms_body", getString(R.string.cuerpo_correo));

        intentCompartir.setType("image/png");
        intentCompartir.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + aRutaImagen));

        // Verificando que el Intent pueda resolver al menos una actividad para compartir
        if (intentCompartir.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intentCompartir, getString(R.string.titulo_intent_compartir)), 5);
        } else {
            Toast.makeText(this, R.string.mensaje_no_existen_app_para_compartir, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOG.e(this, requestCode + " requestCode");
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean guardarImagen(Bitmap aBitmap, String aNombreCaptura) {
        String rutaSD = BullsAndCows.app.getRutaImagenes();
        File directorioApp = new File(rutaSD);

        if (directorioApp.mkdirs() || directorioApp.isDirectory()) {
            String rutaImagen = rutaSD + File.separator + aNombreCaptura;
            File ficheroImagen = new File(rutaImagen);
            FileOutputStream fileStream;
            try {
                fileStream = new FileOutputStream(ficheroImagen);
                aBitmap.compress(Bitmap.CompressFormat.PNG, 85, fileStream);
                fileStream.flush();
                fileStream.close();

                String mensaje = getString(R.string.mensaje_captura_correcta) + " " + rutaSD;
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                LOG.e("Error: ", e.getMessage());
                Toast.makeText(this, R.string.mensaje_error_guardando_captura, Toast.LENGTH_LONG).show();
                return false;
            } catch (IOException e) {
                LOG.e("Error: ", e.getMessage());
                Toast.makeText(this, R.string.mensaje_error_guardando_captura, Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(this, R.string.mensaje_error_creando_directorio, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean estaMontadaSdCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
