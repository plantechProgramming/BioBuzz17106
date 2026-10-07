package org.firstinspires.ftc.teamcode.Misc;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RobotPose { // everything is in CM

    GoBildaPinpointDriver pinpoint;

    public RobotPose(GoBildaPinpointDriver pinpoint){
        this.pinpoint = pinpoint;
    }

    public double getX(){
        if(pinpoint != null){
            return pinpoint.getPosX(DistanceUnit.CM);
        }
        else{
            throw new IllegalArgumentException("No X pos given");
        }
    }

    public double getY(){
        if(pinpoint != null){
            return pinpoint.getPosY(DistanceUnit.CM);
        }
        else{
            throw new IllegalArgumentException("No Y pos given");
        }
    }

    public double getHeading(){
        if(pinpoint != null){
            return pinpoint.getHeading(AngleUnit.DEGREES);
        }
        else{
            throw new IllegalArgumentException("No heading given");
        }
    }
}
