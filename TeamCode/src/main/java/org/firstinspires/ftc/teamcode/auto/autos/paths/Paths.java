package org.firstinspires.ftc.teamcode.auto.autos.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;

public class Paths {

    public Points points;

    public Paths(){
         points = new Points();
        if(Alliance.get() == Alliance.RED) {
            points.setPointsToRed();
        }
    }

    public void buildPaths(Follower follower) {
    }

}
