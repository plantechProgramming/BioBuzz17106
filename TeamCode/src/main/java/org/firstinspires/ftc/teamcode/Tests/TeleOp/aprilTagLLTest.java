package org.firstinspires.ftc.teamcode.Tests.TeleOp;


import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.Misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Camera.Limelight;

@Config
@TeleOp(group = "teleop tests")
public class aprilTagLLTest extends TeamOpMode {
    @Override
    public void postInit(){
        Alliance.set(Alliance.RED);
    }

    @Override
    protected void run() {
//        AutoCommands commands = new AutoCommands();
        Limelight limelight = new Limelight(components);
        components.ll.start();
        Pose3D latestLLPos = null;
        Pose2D latestFilteredLLPos = null;
        while (opModeIsActive()){
//            commands.shooter.variableShoot(gamepad1.dpad_up, gamepad1.dpad_down, 0.005);
            try{
//                latestLLPos = limelight.getLatestBotpose();
//                latestFilteredLLPos = limelight.getFilteredBotPose();
//                telemetry.addData("XY field coords(moved)", limelight.getAbsoluteDetectionResult(pinpoint.getPosition()));
//                telemetry.addData("XY field coords(not moved)", limelight.getRotatedDetectionResult(pinpoint.getHeading(AngleUnit.DEGREES)));
//                telemetry.addData("XY robot coords", limelight.getDetectionResultAsPose2D());
                telemetry.addData("odo heading", robotPose.headingFTC());
                telemetry.addData("odo x", robotPose.xFTC());
                telemetry.addData("odo y", robotPose.yFTC());
//                new DashboardCan vas()
//                        .addRobotAsCircle(latestFilteredLLPos)
//                        .addRobotAsCircle(PoseFunctions.pose3DToPose2D(latestLLPos, AngleUnit.DEGREES), "#00FF00")
//                        .draw();
            }
            catch (NullPointerException e){
//                telemetry.addLine("No apriltag found");
            }
//            telemetry.addData("robotPose", latestLLPos);
            telemetry.update();
//            schedule(commands.periodic());
            Scheduler.execute();
            follower.update();

        }
    }

    @Override
    protected void end() {

    }
}
