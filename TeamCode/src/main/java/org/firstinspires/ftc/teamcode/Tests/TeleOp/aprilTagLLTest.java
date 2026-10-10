package org.firstinspires.ftc.teamcode.Tests.TeleOp;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.ll;
import static org.firstinspires.ftc.teamcode.Misc.InitComponents.pinpoint;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.Misc.Utils.DashboardCanvas;
import org.firstinspires.ftc.teamcode.Misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Camera.Limelight;

import java.util.List;

@Config
@TeleOp(group = "teleop tests")
public class aprilTagLLTest extends TeamOpMode {
    @Override
    public void postInit(){
        Alliance.set(Alliance.RED);
        Follower follower = Constants.create(hardwareMap); // this line and the next line initialises the drivetrain motors
        follower.update();
        pinpoint.resetPosAndIMU();
    }

    @Override
    protected void run() {
//        AutoCommands commands = new AutoCommands();
        Limelight limelight = new Limelight();
        ll.start();
        while (opModeIsActive()){
//            commands.shooter.variableShoot(gamepad1.dpad_up, gamepad1.dpad_down, 0.005);
            try{
                List<Pose2D> pollenAbsPoses = limelight.getAbsoluteDetectionResult(pinpoint.getPosition());
                telemetry.addData("odo heading", pinpoint.getHeading(AngleUnit.DEGREES));
                telemetry.addData("odo x", pinpoint.getPosX(DistanceUnit.CM));
                telemetry.addData("odo y", pinpoint.getPosY(DistanceUnit.CM));
                new DashboardCanvas()
                        .addPreciseRobot(pinpoint.getPosition())
                        .addDot(pollenAbsPoses.get())
                        .draw();
            }
            catch (NullPointerException e){
//                telemetry.addLine("No apriltag found");
            }
//            telemetry.addData("robotPose", latestLLPos);
            telemetry.update();
//            schedule(commands.periodic());
            Scheduler.execute();
            pinpoint.update();

        }
    }

    @Override
    protected void end() {

    }
}
