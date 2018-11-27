package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;

public interface ReplayReader {
    ReplayHeader readHeader();

    ReplayFrame readFrame();
}
