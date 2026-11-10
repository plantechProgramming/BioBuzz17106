package org.firstinspires.ftc.teamcode.auto.autos.paths;

import static com.pedropathing.api.Paths.curve;
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
    public Points p = new Points();
    private Map<String, Path> paths = new HashMap<>();

    public Paths(){
        if(Alliance.get() == Alliance.BLUE) {
            p.poseFactory = p.poseFactory.mirrorX(
                    (DistanceUnit.INCH.fromCm(PoseFunctions.LEN_FIELD))/2); // mirror to the points to the blue side
        }
        p.setPoints();
        setPaths();
    }

    private void setPaths(){
        paths.put("start to garden", curve(p.get("start"), p.get("control 1"), p.get("garden")).linear(p.get("start").heading(), p.get("garden").heading()));

    }

    public Path get(String pathName){
        return paths.get(pathName);
    }
}
