package org.firstinspires.ftc.teamcode.auto.autos.paths;

import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;

public class Points {
    // blue points


    public void setPointsToRed() {
    }


    private Pose Mymirror(Pose pPose) {
        Pose k = pPose;
        return new Pose(141.5 - k.getX(), k.getY()-2, MathFunctions.normalizeAngle(Math.PI - k.getHeading()), PedroCoordinates.INSTANCE);
    }


}