package com.mazebert.simulation;

import com.mazebert.simulation.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SimulationListenersTrainer extends SimulationListeners {
    private final Map<Unit, List<String>> notifications = new HashMap<>();

    @Override
    public boolean areNotificationsEnabled() {
        return true;
    }

    @Override
    public void showNotification(Unit unit, String text, int color) {
        notifications.computeIfAbsent(unit, k -> new ArrayList<>()).add(text);
    }

    public void thenNotificationsAre(Unit unit, String... notifications) {
        assertThat(this.notifications.get(unit)).containsExactly(notifications);
    }

    public void thenNoNotificationsWereShown(Unit unit) {
        assertThat(this.notifications.get(unit)).isNull();
    }
}