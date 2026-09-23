package org.firstinspires.ftc.teamcode.Misc.Utils;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class TelemetryUtils {

    public static void addTitle(Telemetry telemetry, String title){
        String dashes = "------------";
        telemetry.addLine(dashes + title + dashes);
    }

    public static void updateCertainTelemtries(Telemetry telemetry, Follower follower, Shooter shooter){
        updatePedroTelemetry(telemetry, follower);
        shooter.updateTelemetry(telemetry);
    }
    public static void updateCertainTelemtries(Telemetry telemetry, DriveTrain drivetrain, Shooter shooter, PoseFunctions poseFunctions){
        drivetrain.updateTelemetry(telemetry);
        shooter.updateTelemetry(telemetry);
        poseFunctions.updateTelemetry(telemetry);
    }

    public static void updateCertainTelemtries(Telemetry telemetry, DriveTrain drivetrain, Shooter shooter, PoseFunctions poseFunctions, Follower follower){
        drivetrain.updateTelemetry(telemetry);
        shooter.updateTelemetry(telemetry);
        poseFunctions.updateTelemetry(telemetry);
        updatePedroTelemetry(telemetry, follower);
    }

    public static void updatePedroTelemetry(Telemetry telemetry, Follower follower){
        TelemetryUtils.addTitle(telemetry, "starting Pedro telemetry");
        telemetry.addData("robot x", follower.pose().x()); // in inches
        telemetry.addData("robot y", follower.pose().y()); // in inches
        telemetry.addData("robot heading(degrees)", Math.toDegrees(follower.pose().heading())); // in deg
        TelemetryUtils.addTitle(telemetry, "ending Pedro telemetry");
    }
}
