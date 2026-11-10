package org.firstinspires.ftc.teamcode.auto.autos.paths;


import android.util.Pair;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Points {
    protected PoseFactory poseFactory = PoseFactory.degrees(); // .degrees meaning that pose's angle will be in deg
    private Map<String, Pose> points = new HashMap<>();
    public void setPoints(){ // blue points
        points.put("start", poseFactory.of(55.9, 8.8, 90));
        points.put("garden", poseFactory.of(8.95, 8.63, 270));
        points.put("control 1", poseFactory.of(14.54, 23.95, 0));
        points.put("score", poseFactory.of(45.45, 16.2, 70));
    }

    public Pose get(String pointName){
        return points.get(pointName);
    }

}