package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.maps.Map;
import com.mazebert.simulation.maps.MapGrid;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class CandleAttractionEffect extends Ability<Creep> {
    private final Candle candle;

    public CandleAttractionEffect(Candle candle) {
        this.candle = candle;
        setOrigin(candle);
    }

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);

        Map map = Sim.context().gameGateway.getMap();

        Path p1 = map.getGrid().findPath(StrictMath.round(unit.getX()), StrictMath.round(unit.getY()), StrictMath.round(candle.getX()), StrictMath.round(candle.getY()), MapGrid.FLYABLE);

        Path p2 = map.getGrid().findPath(StrictMath.round(candle.getX()), StrictMath.round(candle.getY()), map.getEndWaypoint().x, map.getEndWaypoint().y, MapGrid.FLYABLE);

        unit.setPath(new Path(p1, p2));
    }
}
