package org.firstinspires.ftc.teamcode.tests.teleop;

import static com.pedropathing.ivy.Scheduler.schedule;

import static org.firstinspires.ftc.teamcode.misc.InitComponents.pinpoint;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.misc.utils.Alliance;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.camera.AprilTagLocalization;
import org.firstinspires.ftc.vision.VisionPortal;

/**
 * FILE IS NOT USED. here for "what if we need it for some reason in comp"
 * reasons. old logitech cam.
 */
@Config
@TeleOp(group = "teleop tests")
public class AprilTagLogiCamTest extends TeamOpMode {
    AprilTagLocalization tagLocalization = new AprilTagLocalization(telemetry);

    @Override
    public void postInit(){
        Alliance.set(Alliance.BLUE);
    }
    @Override
    public void run() {
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