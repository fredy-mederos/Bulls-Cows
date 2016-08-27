package com.asdevel.bullscows2.game;

import com.asdevel.bullscows2.BullsAndCows;

import java.io.Serializable;

/**
 *
 * Created by Fredy on 26/12/2014.
 */
public class Ranking implements Serializable {
    public String name;
    public String time_string;
    public long time;
    public int steps;
    public String device_id;
    public long timestamp;

    public Ranking(String name, String time_string, long time, int steps, String device_id, long timestamp) {
        this.name = name;
        this.time_string = time_string;
        this.time = time;
        this.steps = steps;
        this.device_id = device_id;
        this.timestamp = timestamp;
    }


    public boolean isEquals(Ranking o) {
        return o.name.equals(this.name) && o.time == this.time && o.steps == this.steps && o.time_string.equals(this.time_string) && o.device_id.equals(this.device_id) && o.timestamp == this.timestamp;
    }

    public boolean isMine() {
        return device_id.equals(BullsAndCows.app.getID());
    }
}
