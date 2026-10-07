package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.pinpoint;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Misc.InitComponents;
import org.firstinspires.ftc.teamcode.Misc.RobotPose;
import org.firstinspires.ftc.teamcode.Misc.Utils.PoseFunctions;

public class Turret {
    DcMotorEx turret;

    private final double MOTOR_PPR = 384.5;
    private final double OUTER_GEAR_RATIO = 1; // eg 1/2 half as many at the turretMotor compared to the motor shaft

    private final double MIN_LIMIT = -160, MAX_LIMIT = 160;
    private final double STARTING_ROTATE_OFFSET = 90; // deg at the start of the match instead of pointing the turret in a strange direction you can set the offset so that you can place the turret in a normal direction
    PoseFunctions poseFunctions;
    public Turret() {
        turret = InitComponents.turretMotor;
        poseFunctions = new PoseFunctions(new RobotPose(pinpoint));
    }
    public void runToTick(int tick){
        if (!isInNoReachZone(tickToDeg(tick))) {
            turret.setTargetPosition(getRotatedTick(tick));
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setVelocity(turret.getMotorType().getAchieveableMaxTicksPerSecond());
        }
    }

    public void turnTowardsPoint(double x, double y){
        runToDeg(getWantedDeg(x, y));
    }
    public boolean isInNoReachZone(double deg){
        return deg < MIN_LIMIT || deg > MAX_LIMIT;
    }

    public void runToDeg(double deg){
        runToTick(degToTick(deg));
    }

    public int degToTick(double deg){
        return (int) ((deg/360) * MOTOR_PPR / OUTER_GEAR_RATIO);
    }
    public double tickToDeg(int tick){
        return (tick * 360) / MOTOR_PPR * OUTER_GEAR_RATIO;
    }

    public double getCurrDeg(){
        return tickToDeg(turret.getCurrentPosition());
    }
    public double getWantedDeg(double x, double y){
        double turretDeg = -poseFunctions.getAngleFromPoint(new Pose2D(DistanceUnit.CM, x, y, AngleUnit.DEGREES, 0));
        double absoluteDeg = turretDeg + pinpoint.getHeading(AngleUnit.DEGREES);
        return absoluteDeg;
    }

    public double getRotatedDeg(double deg){
        return AngleUnit.normalizeDegrees(deg + STARTING_ROTATE_OFFSET);
    }

    public int getRotatedTick(int tick){
        return degToTick(getRotatedDeg(tickToDeg(tick)));
    }
}
