package org.firstinspires.ftc.teamcode.Tests.TeleOp;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.pinpoint;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.Misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.TeamOpMode;
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
        Turret turret = new Turret();

        while (opModeIsActive()){
            turret.turnTowardsPoint(0, 0);
            telemetry.addData("turret curr deg", turret.getCurrDeg());
            telemetry.addData("turret wanted deg", turret.getWantedDeg(0, 0));
            telemetry.addData("turret wanted deg", pinpoint.getPosition());
            telemetry.update();
            pinpoint.update();
        }
    }

    @Override
    protected void end() {

    }
}
