package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.maps.Map;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public strictfp class Game implements Hashable {
    public UUID id;
    public long timestamp;
    public Map map;
    public float health = 1.0f;
    public boolean bonusRound;
    public int bonusRoundSeconds;
    public boolean timeLord;
    public boolean over;

    private boolean winter;
    private boolean winterCalculated;

    @Override
    public void hash(Hash hash) {
        hash.add(id);
        // ignore timestamp
        // ignore map
        hash.add(health);
        hash.add(bonusRound);
        hash.add(bonusRoundSeconds);
    }

    public boolean isOver() {
        return over || isLost();
    }

    public boolean isLost() {
        return health <= 0.0f;
    }

    public boolean isWinter() {
        if (!winterCalculated) {
            winter = calculateIfWinter();
            winterCalculated = true;
        }
        return winter;
    }

    private boolean calculateIfWinter() {
        if (timestamp == 0) {
            return false;
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(timestamp);
        int month = calendar.get(Calendar.MONTH);
        return month == 11 || month == 0 || month == 1;
    }
}
