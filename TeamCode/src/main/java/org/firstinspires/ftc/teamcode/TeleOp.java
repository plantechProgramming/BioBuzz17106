package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.Scheduler.schedule;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.pinpoint;
import static org.firstinspires.ftc.teamcode.Misc.InitComponents.pinpoint;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Misc.DataSaving;
import org.firstinspires.ftc.teamcode.Misc.RobotPose;
import org.firstinspires.ftc.teamcode.Misc.Utils.DashboardCanvas;
import org.firstinspires.ftc.teamcode.Misc.Utils.PoseFunctions;
import org.firstinspires.ftc.teamcode.Misc.Utils.TelemetryUtils;
import org.firstinspires.ftc.teamcode.Misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.AutoCommands;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "teleOp")
public class TeleOp extends TeamOpMode {
    Follower follower;

    @Override
    public void postInit() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
    }

    @Override
    public void run(){

        while (opModeIsActive()) {

            DrivePowers powers = ManualDrive.fieldCentric(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    follower.pose().heading()
            );
            ManualDrive.driveOrHold(follower, powers);

            Scheduler.execute();
            follower.update();
        }

    }

    @Override
    protected void end() {

    }
}
