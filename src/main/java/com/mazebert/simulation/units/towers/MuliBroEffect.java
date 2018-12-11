package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.projectiles.OnProjectileImpact;
import com.mazebert.simulation.projectiles.Projectile;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.units.abilities.CooldownAbility;

public strictfp class MuliBroEffect extends CooldownAbility<Huli> implements OnProjectileImpact {

    private final ProjectileGateway projectileGateway = Sim.context().projectileGateway;

    @Override
    protected void initialize(Huli unit) {
        super.initialize(unit);
        unit.setInfluencedByMuli(true);
    }

    @Override
    public void dispose(Huli unit) {
        unit.setInfluencedByMuli(false);
        super.dispose(unit);
    }

    @Override
    protected boolean onCooldownReached() {
        Muli muli = (Muli)getOrigin();
        projectileGateway.shoot(Huli.PROJECTILE_VIEW_TYPE, getUnit(), muli, Huli.PROJECTILE_SPEED, this);

        return true;
    }

    @Override
    public void onProjectileImpact(Projectile projectile) {
        Muli muli = (Muli) projectile.target;
        if (muli.isDisposed()) {
            return;
        }
        muli.addBananas(1);
    }
}
