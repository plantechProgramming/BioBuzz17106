package org.firstinspires.ftc.teamcode;


import static com.pedropathing.ivy.Scheduler.schedule;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Misc.Utils.DashboardCanvas;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.PedroDriveTrain;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "teleOp")
public class TeleOp extends TeamOpMode {

    @Override
    protected void run() {
        follower.setPose(new Pose(72, 72, Math.toRadians(0)));
        ElapsedTime elapsedTime = new ElapsedTime();
        DriveTrain drivetrain = new DriveTrain(components);
        PedroDriveTrain pedroDriveTrain = new PedroDriveTrain(follower);
        while(opModeIsActive()){
            schedule(pedroDriveTrain.driveWithPedro(gamepad1, elapsedTime));


            drivetrain.updateTelemetry(telemetry);
            new DashboardCanvas()
                    .addPreciseRobot(components.robotPose.ftc());
            telemetry.addData("drive state", follower.mode());
            telemetry.addData("pedroPose", follower.pose());
            telemetry.update();
            Scheduler.execute();
            follower.update();
        }
    }

    @Override
    protected void end() {

    }
}
