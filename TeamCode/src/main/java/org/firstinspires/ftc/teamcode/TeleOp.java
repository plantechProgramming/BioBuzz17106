package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.pinpoint;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.api.Paths;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Misc.Utils.DashboardCanvas;
import org.firstinspires.ftc.teamcode.Misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.PedroDriveTrain;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "teleOp")
public class TeleOp extends TeamOpMode {
    Follower follower;

    @Override
    public void postInit() {
        follower = Constants.create(hardwareMap);
    }

    @Override
    protected void run() {
        ElapsedTime elapsedTime = new ElapsedTime();
        DriveTrain drivetrain = new DriveTrain();
        PedroDriveTrain pedroDriveTrain = new PedroDriveTrain(follower);
        while(opModeIsActive()){
            if(!follower.following()){
                pedroDriveTrain.driveWithPedro(gamepad1, elapsedTime);
            }
            pedroDriveTrain.pedroGoToPose(gamepad1.a);

            drivetrain.updateTelemetry(telemetry);
            new DashboardCanvas()
                    .addPreciseRobot(pinpoint.getPosition());
            telemetry.addData("drive state", follower.mode());
            telemetry.update();
            follower.update();
        }
    }

    @Override
    protected void end() {

    }
}
