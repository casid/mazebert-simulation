package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.maps.Map;
import com.mazebert.simulation.units.heroes.Azathoth;

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
    public boolean tutorial;
    public boolean timeLord;
    public boolean over;
    public boolean autoNextWave;

    private boolean winter;
    private boolean winterCalculated;
    private boolean halloween;
    private boolean halloweenCalculated;
    private boolean timeLordAllowed;
    private boolean timeLordAllowedCalculated;
    private boolean aprilFoolsGame;
    private boolean aprilFoolsGameCalculated;

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

    public boolean isTimeLordAllowed() {
        if (!timeLordAllowedCalculated) {
            timeLordAllowed = Sim.isDoLSeasonContent() && !Sim.context().unitGateway.containsUnit(Azathoth.class);
            timeLordAllowedCalculated = true;
        }
        return timeLordAllowed;
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
        return month == 10 || month == 11 || month == 0 || month == 1;
    }

    public boolean isHalloween() {
        if (!halloweenCalculated) {
            halloween = calculateIfHalloween();
            halloweenCalculated = true;
        }
        return halloween;
    }

    private boolean calculateIfHalloween() {
        if (timestamp == 0) {
            return false;
        }

        if (Sim.context().version < Sim.vHalloween) {
            return false;
        }

        if (tutorial) {
            return false;
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(timestamp);
        int month = calendar.get(Calendar.MONTH);
        return month == 9;
    }

    public boolean isAprilFoolsGame() {
        if (!aprilFoolsGameCalculated) {
            aprilFoolsGame = calculateIfAprilFoolsGame();
            aprilFoolsGameCalculated = true;
        }
        return aprilFoolsGame;
    }

    private boolean calculateIfAprilFoolsGame() {
        if (timestamp == 0) {
            return false;
        }

        if (Sim.context().version < Sim.v24) {
            return false;
        }

        if (tutorial) {
            return false;
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(timestamp);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        return month == 3 && day == 1;
    }
}
