package org.firstinspires.ftc.teamcode.Tests.TeleOp;

import static com.pedropathing.ivy.Scheduler.schedule;

import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Misc.Utils.DashboardCanvas;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.PedroDriveTrain;

@TeleOp(group = "teleop tests")
public class DriveTest extends TeamOpMode {

    @Override
    protected void run() {
        follower.setPose(new Pose(72, 72, Math.toRadians(0)));
        DriveTrain driveTrain = new DriveTrain(components);
        PedroDriveTrain pedroDriveTrain = new PedroDriveTrain(follower);
        ElapsedTime elapsedTime = new ElapsedTime();

        while(opModeIsActive()){
            schedule(pedroDriveTrain.driveWithPedro(gamepad1, elapsedTime));

            driveTrain.updateTelemetry(telemetry);
            new DashboardCanvas()
                    .addRobotAsCircle(robotPose.ftc())
                    .draw();
            telemetry.update();
            Scheduler.execute();
            follower.update();
        }
    }

    @Override
    protected void end() {

    }
}
