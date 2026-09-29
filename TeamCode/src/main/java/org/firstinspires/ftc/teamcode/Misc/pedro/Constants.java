package org.firstinspires.ftc.teamcode.Misc.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("FL");
        c.frontRightName.set("FR");
        c.backLeftName.set("BL");
        c.backRightName.set("BR");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.manualBrakeMode.set(true);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(4.593918867937224);
        c.yPodOffset.set(1.0105208900031144);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.22955209492074766);
                Controller secondaryTranslationalForward = Controller.proportional(0.08481336532827317);
                Controller primaryTranslationalLateral = Controller.proportional(0.4384386171319102);
                Controller secondaryTranslationalLateral = Controller.proportional(0.16199135373462736);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.015540310069697048));
                c.brake.set(Controller.proportionalFeedforward(0.013209263559242491));

                c.headingFeedback.set(Controller.proportional(3.377508859978219));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.0526933206034827, 0.009398871151449125));

                c.linearBrakeCoefficients.set(Matrix.diag(0.07937536242619594, 0.08542307618107225));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0022992195884228733, 0.0021808151535518175));

                c.maxAchievableForwardVelocity.set(68.94721285256807);
                c.maxAchievableStrafeVelocity.set(53.62102676783619);
                c.naturalForwardDeceleration.set(31.721375508520808);
                c.naturalStrafeDeceleration.set(48.07771521587994);
            }
    );
}