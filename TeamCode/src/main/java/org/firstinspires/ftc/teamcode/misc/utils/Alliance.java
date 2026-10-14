package org.firstinspires.ftc.teamcode.misc.utils;

public enum Alliance {
    UNKNOWN,
    BLUE,
    RED;

    private static Alliance currentAlliance = UNKNOWN;

    public static Alliance get(){
        if(currentAlliance == Alliance.UNKNOWN){
            throw new NullPointerException("alliance is unknown");
        }
        return currentAlliance;
    }

    public static void set(Alliance alliance){
        currentAlliance = alliance;
    }
}
