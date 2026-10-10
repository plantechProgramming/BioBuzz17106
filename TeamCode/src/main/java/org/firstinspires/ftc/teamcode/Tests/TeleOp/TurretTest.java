package org.firstinspires.ftc.teamcode.Tests.TeleOp;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.pinpoint;
import static org.firstinspires.ftc.teamcode.Misc.InitComponents.turretMotor;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.Misc.Utils.TurretState;
import org.firstinspires.ftc.teamcode.Misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Turret;

@TeleOp(group = "teleop tests")
public class TurretTest extends TeamOpMode {

    @Override
    protected void postInit() {
        Alliance.set(Alliance.RED);
        Constants.create(hardwareMap);
    }
    @Override
    protected void run() {
        pinpoint.resetPosAndIMU();
        Turret turret = new Turret(telemetry);
//        DriveTrain driveTrain = new DriveTrain();
//
//        double gamepadForward; //-1 to 1
//        double gamepadTurn;
//        double gamepadDrift;
//        double botHeading;
//
//        DriveTrain.setDriveToBrakeMode();

        while (opModeIsActive()){
//            gamepadForward = -gamepad1.left_stick_y;
//            gamepadTurn = gamepad1.right_stick_x;
//            gamepadDrift = gamepad1.left_stick_x;
//
//            botHeading = pinpoint.getHeading(AngleUnit.DEGREES);
//
//            driveTrain.drive(gamepadForward, gamepadDrift, gamepadTurn, botHeading, 1);
            turret.turnTowardsPoint(0, 0);
            telemetry.addData("turret curr deg", turret.getCurrDeg());
            telemetry.addData("turret wanted deg", turret.getWantedDeg(0, 0));
            telemetry.addData("rPose", pinpoint.getPosition());
            telemetry.addData("turret state", TurretState.get());
            telemetry.update();
            pinpoint.update();
        }
    }

    @Override
    protected void end() {

    }
}
