package org.firstinspires.ftc.teamcode.auto.autos.paths;

import static com.pedropathing.api.Paths.line;

import android.util.Pair;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.Misc.Utils.PoseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Paths {
    public Points points = new Points();
    private Map<String, Path> paths = new HashMap<>();

    public Paths(){
        if(Alliance.get() == Alliance.RED) {
            points.poseFactory = points.poseFactory.mirrorX(
                    (DistanceUnit.INCH.fromCm(PoseFunctions.LEN_FIELD))/2); // mirror to the points to the red side
        }
        points.setPoints();
        setPaths();
    }
    private void setPaths(){
//        paths.put("start to score", line(points.get("start"), points.get("score")));
    }

    public Path get(String pathName){
        return paths.get(pathName);
    }

}
