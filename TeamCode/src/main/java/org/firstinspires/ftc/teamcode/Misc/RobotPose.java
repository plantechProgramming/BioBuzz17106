package org.firstinspires.ftc.teamcode.Misc;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Misc.Utils.PoseFunctions;

public class RobotPose {

    Follower follower;

    public RobotPose(Follower follower){
        this.follower = follower;
    }
    public Pose2D ftc(){
        if(follower != null){
            return PoseFunctions.poseToPose2D(follower.pose());
        }
        else{
            throw new IllegalArgumentException("No pos given");
        }
    }

    public double xPedro(){
        if(follower != null){
            return follower.pose().x();
        }
        else{
            throw new IllegalArgumentException("No X pos given");
        }
    }

    public double yPedro(){
        if(follower != null){
            return follower.pose().y();
        }
        else{
            throw new IllegalArgumentException("No Y pos given");
        }
    }

    public double headingPedro(){
        if(follower != null){
            return follower.pose().heading();
        }
        else{
            throw new IllegalArgumentException("No heading given");
        }
    }

    public double xFTC(){
        if(follower != null) {
            return ftc().getX(DistanceUnit.CM);
        }
        else{
            throw new IllegalArgumentException("No X pos given");
        }
    }
    public double yFTC(){
        if(follower != null) {
            return ftc().getY(DistanceUnit.CM);
        }
        else{
            throw new IllegalArgumentException("No Y pos given");
        }
    }
    public double headingFTC(){
        if(follower != null) {
            return ftc().getHeading(AngleUnit.DEGREES);
        }
        else{
            throw new IllegalArgumentException("No heading given");
        }
    }

}
