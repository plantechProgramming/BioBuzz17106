package org.firstinspires.ftc.teamcode.tests.teleop;


import static org.firstinspires.ftc.teamcode.misc.InitComponents.dashboard;
import static org.firstinspires.ftc.teamcode.misc.InitComponents.ll;
import static org.firstinspires.ftc.teamcode.misc.InitComponents.pinpoint;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.commands.Commands;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.misc.utils.Alliance;
import org.firstinspires.ftc.teamcode.misc.utils.Color;
import org.firstinspires.ftc.teamcode.misc.utils.DashboardCanvas;
import org.firstinspires.ftc.teamcode.misc.utils.Extras;
import org.firstinspires.ftc.teamcode.misc.utils.GameElement;
import org.firstinspires.ftc.teamcode.misc.utils.Point;
import org.firstinspires.ftc.teamcode.misc.utils.PoseFunctions;
import org.firstinspires.ftc.teamcode.misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.AutoCommands;
import org.firstinspires.ftc.teamcode.subsystems.camera.Limelight;

import java.util.List;

@Config
@TeleOp(group = "teleop tests")
public class AprilTagLLTest extends TeamOpMode {
    @Override
    public void postInit(){
        Alliance.set(Alliance.RED);
        Constants.create(hardwareMap).update(); // this line and the next line initialises the drivetrain motors
    }

    @Override
    protected void run() {
        Limelight limelight = new Limelight();
        ll.start();
        while (opModeIsActive()){
            List<Pose2D> detectionsResult;
            if(limelight.isDetectorResultGood()){
                detectionsResult = limelight.getAbsoluteDetectorResult(pinpoint.getPosition());
                List<Point> detectionPoints = Extras.I_To_T_ForList(detectionsResult,
                        PoseFunctions::pose2DToPoint);
                List<GameElement> detectionElements = limelight.getDetectorResultElements();
                Point bestPointToGo = limelight.getBestPointForPickUp(pinpoint.getPosition());
                telemetry.addData("best point to go", bestPointToGo);
                telemetry.addData("detections", detectionPoints);
                telemetry.addData("detections elements", detectionElements);
                DashboardCanvas dashboardCanvas = new DashboardCanvas();
                if(bestPointToGo != null){
                    double bestAngle = AngleUnit.normalizeDegrees(90-(Math.toDegrees(Math.atan2(bestPointToGo.getX(), bestPointToGo.getY()))-pinpoint.getHeading(AngleUnit.DEGREES)));
                    Pose2D bestPoseToGo = new Pose2D(DistanceUnit.CM, bestPointToGo.getX(), bestPointToGo.getY(), AngleUnit.DEGREES, bestAngle);
                    dashboardCanvas.addRobotAsCircle(bestPoseToGo, Color.BLACK.getColorHex());
                }
                dashboardCanvas.addRobotAsCircle(pinpoint.getPosition())
                        .addGameElements(detectionElements, detectionPoints)
                        .draw();
            }
            telemetry.update();
            limelight.updateDetectorResult();
            pinpoint.update();
        }
    }

    @Override
    protected void end() {

    }
}
