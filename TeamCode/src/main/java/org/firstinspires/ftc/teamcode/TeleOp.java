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
        boolean isMovingAutomatically = false;
        int timesPressedA = 0;
        while(opModeIsActive()){
            if(!isMovingAutomatically){
                drivetrain.driveWithPedro(gamepad1, follower, elapsedTime);
            }
            if(gamepad1.aWasPressed()){
                timesPressedA++;
            }
            switch (timesPressedA){
                case 1:
                    isMovingAutomatically = true;
                    follower.follow(Paths.line(follower.pose(), new Pose(0, 0, 0)).linear(follower.pose().heading(), 0));
                    break;
                case 2:
                    isMovingAutomatically = false;
                    follower.stop();
                    timesPressedA = 0;
                    break;
            }
            if(isMovingAutomatically && !follower.isBusy()){
                isMovingAutomatically = false;
                timesPressedA = 0;
            }
            drivetrain.updateTelemetry(telemetry);
            new DashboardCanvas()
                    .addPreciseRobot(pinpoint.getPosition());
            telemetry.addData("is follower busy", follower.isBusy());
            telemetry.addData("times pressed a", timesPressedA);
            telemetry.update();
            follower.update();
        }
    }

    @Override
    protected void end() {

    }
}
