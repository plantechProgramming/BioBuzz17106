package org.firstinspires.ftc.teamcode.Misc.Utils;

public enum TurretState {
    NORMAL,
    INITIALISING,
    WAITING_FOR_WARP;

    private static TurretState turretState = TurretState.NORMAL;

    public static TurretState get(){
        return turretState;
    }

    public static void set(TurretState state){
        turretState = state;
    }
}
