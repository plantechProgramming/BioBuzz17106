package org.firstinspires.ftc.teamcode.Tests.TeleOp;

import static com.pedropathing.ivy.Scheduler.execute;
import static com.pedropathing.ivy.Scheduler.schedule;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.dashboardTelemetry;
import static org.firstinspires.ftc.teamcode.Misc.InitComponents.pinpoint;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.Misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.AutoCommands;
import org.firstinspires.ftc.teamcode.subsystems.Camera.AprilTagLocalization;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.vision.VisionPortal;

/**
 * FILE IS NOT USED. here for "what if we need it for some reason in comp"
 * reasons. old logitech cam.
 */
@Config
@TeleOp(group = "teleop tests")
public class aprilTagLogiCamTest extends TeamOpMode {
    AprilTagLocalization tagLocalization = new AprilTagLocalization(telemetry);

    @Override
    public void postInit(){
        Alliance.set(Alliance.BLUE);
        Follower follower = Constants.create(hardwareMap);
    }
    @Override
    public void run() {
        DriveTrain driveTrain = new DriveTrain();
        tagLocalization.initProcessor();
        while (tagLocalization.visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING){
            sleep(20);
        }
        tagLocalization.applySettings();
        while (opModeIsActive()) {
            tagLocalization.detectTags();
            telemetry.update();
            pinpoint.update();
        }
    }


    @Override
    protected void end() {

    }
}