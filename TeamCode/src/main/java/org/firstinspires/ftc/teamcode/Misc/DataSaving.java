package org.firstinspires.ftc.teamcode.Misc;

import com.pedropathing.math.Pose;

public class DataSaving {
    private static Pose endPos = null;
    public static void setEndPos(Pose pose){
        endPos = pose;
    }

    public static com.pedropathing.math.Pose getEndPos() throws NullPointerException{
        if(endPos != null)
            return endPos;

        throw new NullPointerException("No position set from auto");
    }
}
