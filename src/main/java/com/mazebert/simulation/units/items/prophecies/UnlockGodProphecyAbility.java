package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.systems.ProphecySystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.quests.Quest;
import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.quests.UnlockGodQuest;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class UnlockGodProphecyAbility extends ProphecyAbility implements OnUnitRemovedListener {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final ProphecySystem prophecySystem = Sim.context().prophecySystem;
    private final LootSystem lootSystem = Sim.context().lootSystem;

    private final ItemType prophecy;
    private final TowerType god;
    private final QuestType quest;

    public UnlockGodProphecyAbility(ItemType prophecy, TowerType god, QuestType quest) {
        this.prophecy = prophecy;
        this.god = god;
        this.quest = quest;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        simulationListeners.onUnitRemoved.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        simulationListeners.onUnitRemoved.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Creep) {
            Creep creep = (Creep)unit;
            if (!creep.isDead()) {
                return;
            }

            if (creep.getWave().type != WaveType.Challenge) {
                return;
            }

            Wizard wizard = getUnit().getWizard();

            Quest quest = getQuest(wizard);
            if (quest != null) {
                quest.addAmount(1);

                if (quest.isComplete()) {
                    prophecySystem.fulfillProphecy(wizard, prophecy);

                    wizard.foilTowers.add(god);
                    if (god == TowerType.Thor) {
                        wizard.foilItems.add(ItemType.Mjoelnir);
                    }

                    addGodCardToPlayerHand(wizard);
                }
            } else {
                prophecySystem.fulfillProphecy(wizard, prophecy);
                addGodCardToPlayerHand(wizard);
            }
        }
    }

    private Quest getQuest(Wizard wizard) {
        return wizard.getAbility(this.quest.questClass);
    }

    private void addGodCardToPlayerHand(Wizard wizard) {
        lootSystem.dropCard(wizard, null, wizard.towerStash, god, Integer.MAX_VALUE);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getDescription() {
        return "You will sacrifice " + UnlockGodQuest.REQUIRED_AMOUNT + " challenges and win " + format.card(god) + "'s favor. If you've already completed this quest, " + format.card(god) + " will drop after the next sacrificed challenge.";
    }

    @Override
    public String getLevelBonus() {
        if (getUnit() != null) {
            Wizard wizard = getUnit().getWizard();
            Quest quest = getQuest(wizard);
            if (quest != null) {
                return quest.getCurrentAmount() + "/" + quest.getRequiredAmount();
            }
        }

        return super.getLevelBonus();
    }
}
