package org.firstinspires.ftc.teamcode.tests.teleop;

import static org.firstinspires.ftc.teamcode.misc.InitComponents.ll;
import static org.firstinspires.ftc.teamcode.misc.InitComponents.pinpoint;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.misc.utils.Alliance;
import org.firstinspires.ftc.teamcode.misc.utils.DashboardCanvas;
import org.firstinspires.ftc.teamcode.misc.utils.Extras;
import org.firstinspires.ftc.teamcode.misc.utils.GameElement;
import org.firstinspires.ftc.teamcode.misc.utils.Point;
import org.firstinspires.ftc.teamcode.misc.utils.PoseFunctions;
import org.firstinspires.ftc.teamcode.misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.camera.Limelight;

import java.util.List;

@Config
@TeleOp(group = "teleop tests")
public class AprilTagLLTest extends TeamOpMode {
    @Override
    public void postInit(){
        Alliance.set(Alliance.RED);
        Follower follower = Constants.create(hardwareMap); // this line and the next line initialises the drivetrain motors
        follower.update();
        pinpoint.resetPosAndIMU();
    }

    @Override
    protected void run() {
        Limelight limelight = new Limelight();
        ll.start();
        while (opModeIsActive()){
            List<Pose2D> detectionsResult;
            try{
                detectionsResult = limelight.getAbsoluteDetectionResult(pinpoint.getPosition());
                List<Point> detectionPoints = Extras.I_To_T_ForList(detectionsResult,
                        PoseFunctions::pose2DToPoint);
                List<GameElement> detectionElements = limelight.getDetectionElements();
                new DashboardCanvas()
                        .addGameElements(detectionElements, detectionPoints)
                        .addRobotAsCircle(pinpoint.getPosition())
                        .draw();
            }
            catch (NullPointerException ignored){
            }
            telemetry.update();
            pinpoint.update();

        }
    }

    @Override
    protected void end() {

    }
}
