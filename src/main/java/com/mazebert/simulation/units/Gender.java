package com.mazebert.simulation.units;

public strictfp enum Gender {
    Unknown,
    Female,
    Male
    ;

    public String getName() {
        switch (this) {
            case Female:
                return "Female";
            case Male:
                return "Male";
        }
        return "None";
    }
}
