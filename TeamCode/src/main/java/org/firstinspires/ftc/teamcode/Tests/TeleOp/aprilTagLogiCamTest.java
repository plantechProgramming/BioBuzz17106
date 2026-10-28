package org.firstinspires.ftc.teamcode.Tests.TeleOp;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Camera.AprilTagLocalization;
import org.firstinspires.ftc.vision.VisionPortal;

/**
 * FILE IS NOT USED. here for "what if we need it for some reason in comp"
 * reasons. old logitech cam.
 */
@Config
@TeleOp(group = "teleop tests")
public class aprilTagLogiCamTest extends TeamOpMode {
    AprilTagLocalization tagLocalization = new AprilTagLocalization(telemetry, follower, components);

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
            follower.update();
        }
    }


    @Override
    protected void end() {

    }
}