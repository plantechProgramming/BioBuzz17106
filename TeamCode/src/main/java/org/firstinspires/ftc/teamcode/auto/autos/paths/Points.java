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
//        poses.put("start", poseFactory.of(20, 20, 180));
    }

    public Pose get(String pointName){
        return points.get(pointName);
    }

}