package com.asdevel.bullscows2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.asdevel.bullscows2.game.Ranking;

import java.util.ArrayList;

/**
 * Gestor para las {@link SharedPreferences}. *
 *
 * @author Fredy
 */
public class RankingManager {

    //Constantes
    public static final String PREFERENCES_NAME = "BullsCowsPref";
    public static final String PREF_RANKING = "ranking";
    public static final String PREF_LASTPERSON = "last_person";
    private static final int CANT_MAX = 10;
    Gson gson;
    private SharedPreferences shared_preferences;
    private SharedPreferences.Editor shared_preferencesEditor;
    private Context context;


    /**
     * Constructor del Gestor de Preferencias.
     *
     * @param cntx Contexto de la aplicacion.
     */
    public RankingManager(Context cntx) {
        context = cntx;
        shared_preferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
        gson = new Gson();
    }

    public static int to_real_time(String time) {
        String time_str = time;
        String[] minS = time_str.split(":");
        int timeS = Integer.parseInt(minS[1]) + (Integer.parseInt(minS[0]) * 60);
        LOG.e("to_real_time", timeS + " secs");
        return timeS;
    }

    public static String to_string_time(int time) {
        int mins = time / 60;
        int secs = time % 60;

        String minsStr = Integer.toString(mins);
        String secsStr = Integer.toString(secs);

        if (minsStr.length() == 1)
            minsStr = "0" + minsStr;

        if (secsStr.length() == 1)
            secsStr = "0" + secsStr;

        return minsStr + ":" + secsStr;
    }

    public ArrayList<Ranking> getRankings() {
        String ranking = shared_preferences.getString(PREF_RANKING, "");
        ArrayList<Ranking> rankings_list = new ArrayList<>();

        if (ranking.equals(""))
            return rankings_list;

        Ranking[] rankings = gson.fromJson(ranking, Ranking[].class);
        for (Ranking r : rankings) {
            if (r != null)
                rankings_list.add(r);
        }
        return rankings_list;
    }

    public Ranking[] getMyRankings() {
        ArrayList<Ranking> my_rankings = new ArrayList<>();

        ArrayList<Ranking> rankings = getRankings();
        for (Ranking r : rankings) {
            if (r.isMine())
                my_rankings.add(r);
        }

        Ranking[] my_rankings_array = new Ranking[my_rankings.size()];
        for (int i = 0; i < my_rankings.size(); i++) {
            my_rankings_array[i] = my_rankings.get(i);
        }

        return my_rankings_array;
    }

    public String getLastRankingName() {
        return shared_preferences.getString(PREF_LASTPERSON, "");
    }

    public void saveLastRankingName(String name) {
        shared_preferencesEditor = shared_preferences.edit();
        shared_preferencesEditor.putString(PREF_LASTPERSON, name);
        shared_preferencesEditor.commit();
    }

    public boolean canAddRanking(Ranking ranking) {
        ArrayList<Ranking> ranks = getRankings();
        if (ranks.size() < CANT_MAX)
            return true;
        return ranks.get(ranks.size() - 1).time > ranking.time || (ranks.get(ranks.size() - 1).time == ranking.time && ranks.get(ranks.size() - 1).steps > ranking.steps);

    }

    public void addRanking(Ranking r) {
        ArrayList<Ranking> rankings = getRankings();
        rankings.add(r);
        saveLastRankingName(r.name);
        saveRankings(rankings);
    }

    public ArrayList<Ranking> sort(ArrayList<Ranking> rankings) {
        ArrayList<Ranking> ranks_sorted = new ArrayList<>();

        int index;
        boolean exist;
        for (Ranking r : rankings) {
            exist = false;
            index = -1;
            for (int i = 0; i < ranks_sorted.size(); i++) {
                if (r.isEquals(ranks_sorted.get(i))) {
                    exist = true;
                    break;
                }
                if (r.time < ranks_sorted.get(i).time || (r.time == ranks_sorted.get(i).time && r.steps < ranks_sorted.get(i).steps)) {
                    index = i;
                    break;
                }
            }
            if (!exist) {
                if (index != -1)
                    ranks_sorted.add(index, r);
                else
                    ranks_sorted.add(r);
            }
        }
        LOG.e(this, gson.toJson(ranks_sorted));
        return ranks_sorted;
    }

    public void clearRankings() {
        shared_preferencesEditor = shared_preferences.edit();
        shared_preferencesEditor.putString(PREF_RANKING, "");
        shared_preferencesEditor.commit();
    }

    public boolean saveRankingsFromServer(Ranking[] rankss) {
        ArrayList<Ranking> rankings = new ArrayList<>();
        if (rankss != null) {
            for (Ranking r : rankss) {
                if (r != null)
                    rankings.add(r);
            }
        }
        rankings.addAll(getRankings());
        return saveRankings(rankings);
    }

    public boolean saveRankings(ArrayList<Ranking> rankss) {
        shared_preferencesEditor = shared_preferences.edit();
        ArrayList<Ranking> rankings = sort(rankss);

        Ranking[] ranks = new Ranking[rankings.size()];
        for (int i = 0; i < rankings.size(); i++) {
            if (rankings.get(i) != null && i < CANT_MAX)
                ranks[i] = rankings.get(i);
        }
        shared_preferencesEditor.putString(PREF_RANKING, gson.toJson(ranks));
        return shared_preferencesEditor.commit();
    }

    public String getMyRatingTime() {
        Ranking[] myRankings = getMyRankings();
        int time = 0;
        int cant = 0;
        for (Ranking r : myRankings) {
            if (r != null) {
                time += r.time;
                cant++;
            }
        }
        return to_string_time(time / cant);
    }

    public String getMyRatingSteps() {
        Ranking[] myRankings = getMyRankings();
        float steps = 0;
        float cant = 0;
        for (Ranking r : myRankings) {
            if (r != null) {
                steps += r.steps;
                cant++;
            }
        }
        return Integer.toString(Math.round(steps / cant));
    }


}

