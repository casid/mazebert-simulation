package com.mazebert.simulation.units.towers;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.math.Frustum;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class SniperBurst extends Ability<Tower> implements OnAttackListener, Consumer<Creep> {

    public static final float SHOT_LENGTH = 1000;
    public static final float SPREAD_LENGTH = 500;

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final DamageSystem damageSystem = Sim.context().damageSystem;

    private final Frustum frustum = new Frustum(3);
    private double currentDamage;
    private int currentShotCount;
    private Creep currentTarget;


    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAttack.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        currentTarget = target;
        currentDamage = damageSystem.dealDamage(this, getUnit(), target);
        if (currentDamage <= 0) {
            return;
        }

        currentShotCount = calculateShotCount();
        updateFrustum(target);

        unitGateway.forEach(Creep.class, this);

        currentTarget = null;
    }

    private void updateFrustum(Creep target) {
        // Tower point in iso space (only x and y are relevant).
        float tx = getUnit().getX();
        float ty = getUnit().getY();

        // Creep point in iso space (only x and y are relevant).
        float cx = target.getX();
        float cy = target.getY();

        // Vector facing creep
        float dx = cx - tx;
        float dy = cy - ty;

        // Normalize this vector
        float length = (float) StrictMath.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;

        // Cross product to find orthogonal vector
        float ox = dy;
        float oy = -dx;

        // Find end point colinear to the effect in screen space.
        float ex = tx + dx * SHOT_LENGTH;
        float ey = ty + dy * SHOT_LENGTH;

        // Find end points of the frustum.
        float ex0 = ex + SPREAD_LENGTH * ox;
        float ey0 = ey + SPREAD_LENGTH * oy;
        float ex1 = ex - SPREAD_LENGTH * ox;
        float ey1 = ey - SPREAD_LENGTH * oy;

        // Find normal vector for the right frustum plane.
        float nx0 = ex0 - tx;
        float ny0 = ey0 - ty;
        length = (float) StrictMath.sqrt(nx0 * nx0 + ny0 * ny0);
        nx0 /= length;
        ny0 /= length;

        // Find normal vector for the left frustum plane.
        float nx1 = ex1 - tx;
        float ny1 = ey1 - ty;
        length = (float) StrictMath.sqrt(nx1 * nx1 + ny1 * ny1);
        nx1 /= length;
        ny1 /= length;

        // Set-up frustum
        frustum.planes[0].setPointAndDirection(tx, ty, 0, -dx, -dy, 0);
        frustum.planes[1].setPointAndDirection(tx, ty, 0, ny0, -nx0, 0);
        frustum.planes[2].setPointAndDirection(tx, ty, 0, -ny1, nx1, 0);
    }

    private int calculateShotCount() {
        Tower tower = getUnit();

        int shots = 1;
        float chance = SniperAttack.CHANCE + tower.getLevel() * SniperAttack.CHANCE_PER_LEVEL;

        for (int i = 0; i < SniperAttack.MAX_SHOTS; ++i) {
            if (tower.isAbilityTriggered(chance)) {
                ++shots;
            } else {
                break;
            }
        }

        return shots;
    }

    @Override
    public void accept(Creep creep) {
        double shotCount = calculateShotCount(creep);
        if (shotCount > 0) {
            damageSystem.dealDamage(this, getUnit(), creep, currentDamage * shotCount, 0);
        }
    }

    private double calculateShotCount(Creep creep) {
        if (creep == currentTarget) {
            return currentShotCount - 1;
        } else if (frustum.containsPoint(creep.getX(), creep.getY(), 0)) {
            return currentShotCount;
        }
        return 0;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Ka-boom";
    }

    @Override
    public String getDescription() {
        return "Bullets split into several pieces spreading in a small cone over the map, dealing 100% damage to all creeps they hurt.";
    }

    @Override
    public String getIconFile() {
        return "bullet_512";
    }
}
