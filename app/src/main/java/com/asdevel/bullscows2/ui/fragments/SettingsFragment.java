package com.asdevel.bullscows2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.asdevel.bullscows2.BullsAndCows;
import com.asdevel.bullscows2.R;
import com.asdevel.bullscows2.ui.Screen;
import com.asdevel.bullscows2.utils.OnCleanListener;
import com.asdevel.bullscows2.utils.OnRestartRankingListener;
import com.asdevel.bullscows2.utils.LOG;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Fredy on 24/12/2014.
 */
public class SettingsFragment extends Fragment {

    TextView usados_size;

    View limpiar_cache;

    View limpiar_estadisticas;

    View see_about;

    ToggleButton toggle_vib;

    ToggleButton toggle_sound;

    ImageView lang_flag;

    View set_lang;

    View rootView;

    public SettingsFragment() {
    }

    public void restartView() {
        lang_flag.setBackgroundResource(BullsAndCows.app.settingsManager.getLocaleFlag());
        ((TextView) rootView.findViewById(R.id.text_sound)).setText(getString(R.string.settings_sonido));
        ((TextView) rootView.findViewById(R.id.text_vibr)).setText(getString(R.string.settings_vibracion));
        ((TextView) rootView.findViewById(R.id.text_lang)).setText(getString(R.string.languaje));
        ((TextView) rootView.findViewById(R.id.text_restart)).setText(getString(R.string.settings_restart));
        ((TextView) rootView.findViewById(R.id.text_restart_msg)).setText(getString(R.string.settings_restart_msg));
        ((TextView) rootView.findViewById(R.id.limpiar_text)).setText(getString(R.string.settings_limpiar));
        ((TextView) rootView.findViewById(R.id.usados_text)).setText(getString(R.string.settings_usados));
        ((TextView) rootView.findViewById(R.id.text_about)).setText(getString(R.string.menu_about));
        ((Screen) getActivity()).updateTitle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        usados_size = (TextView) rootView.findViewById(R.id.usados_size);
        limpiar_cache = rootView.findViewById(R.id.limpiar_cache);
        limpiar_estadisticas = rootView.findViewById(R.id.limpiar_estadisticas);

        toggle_vib = (ToggleButton) rootView.findViewById(R.id.toggle_vib);
        toggle_sound = (ToggleButton) rootView.findViewById(R.id.toggle_sound);
        lang_flag = (ImageView) rootView.findViewById(R.id.lang_flag);
        set_lang = rootView.findViewById(R.id.set_lang);
        see_about = rootView.findViewById(R.id.action_about);

        see_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Screen) getActivity()).setFragmentWithAnimation(BullsAndCows.STATE_INFO, true);
            }
        });

        set_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BullsAndCows.app.setLocale(BullsAndCows.app.settingsManager.getNextLocale());
                restartView();
            }
        });

        toggle_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BullsAndCows.app.settingsManager.setSound(isChecked);
                if (!isChecked) {
                    ((Screen) getActivity()).liberarReproductor();
                }
            }
        });
        toggle_vib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BullsAndCows.app.settingsManager.setVibration(isChecked);
            }
        });

        limpiar_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getCacheSize().equals("0B")) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    DialogCleanFragment dr = new DialogCleanFragment();
                    dr.cant_a_borrar = getCacheSize();
                    dr.setListener(new OnCleanListener() {
                        @Override
                        protected void OnClean() {
                            if (clearAll())
                                Toast.makeText(getActivity(), getString(R.string.settings_limpiar_msg), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            usados_size.setText(getCacheSize());
                        }
                    });
                    dr.show(ft, "dialog_clean");
                } else {
                    usados_size.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake_horizontal));
                    Toast.makeText(getActivity(), getString(R.string.settings_limpiar_msg_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        limpiar_estadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                DialogRestartFragment dr = new DialogRestartFragment();
                dr.setListener(new OnRestartRankingListener() {
                    @Override
                    protected void OnRestartRanking() {
                        BullsAndCows.app.rankingManager.clearRankings();
                        try {
                            ((TextView) ((Screen) getActivity()).rating_box.findViewById(R.id.rating_time)).setText(BullsAndCows.app.rankingManager.getMyRatingTime());
                            ((TextView) ((Screen) getActivity()).rating_box.findViewById(R.id.rating_steps)).setText(BullsAndCows.app.rankingManager.getMyRatingSteps());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                dr.show(ft, "dialog3");
            }
        });

        usados_size.setText(getCacheSize());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        toggle_vib.setChecked(BullsAndCows.app.settingsManager.isVibrationOn());
        toggle_sound.setChecked(BullsAndCows.app.settingsManager.isSoundOn());
        LOG.e(this, BullsAndCows.app.settingsManager.getLocale());
        lang_flag.setBackgroundResource(BullsAndCows.app.settingsManager.getLocaleFlag());
    }

    public boolean clearAll() {

        String rutaSD = BullsAndCows.app.getRutaImagenes();
        File directorioApp = new File(rutaSD);

        if (!directorioApp.exists())
            return false;

        File[] files = directorioApp.listFiles();
        if (files == null)
            return false;
        for (File f : files)
            f.delete();

        return true;

    }

    public String getCacheSize() {
        String rutaSD = BullsAndCows.app.getRutaImagenes();
        File directorioApp = new File(rutaSD);

        if (!directorioApp.exists())
            return "0B";

        float size = 0;
        File[] files = directorioApp.listFiles();
        if (files != null)
            for (File f : files)
                size += f.length();

        if (size == 0)
            return "0B";

        if (size < 1024) {
            return Float.toString(size) + "B";
        }
        size = size / 1024;
        size = (float) Math.round(size * 100) / 100;
        if (size < 1024) {
            return Float.toString(size) + "KB";
        }
        size = size / 1024;
        size = (float) Math.round(size * 100) / 100;
        if (size < 1024) {
            return Float.toString(size) + "MB";
        }
        size = size / 1024;
        size = (float) Math.round(size * 100) / 100;
        return Float.toString(size) + "GB";
    }

}
