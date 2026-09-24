package org.firstinspires.ftc.teamcode.Misc.pedro;

import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
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
                Controller primaryTranslationalForward = Controller.proportional(0.46366039276822046);
                Controller secondaryTranslationalForward = Controller.proportional(0.1713101258939869);
                Controller primaryTranslationalLateral = Controller.proportional(-0.284519616322902);
                Controller secondaryTranslationalLateral = Controller.proportional(-0.10512239572714682);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.015401496092736888));
                c.brake.set(Controller.proportionalFeedforward(0.013091271678826354));

                c.headingFeedback.set(Controller.proportional(3.128887700076632));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04610267623057011, 0.011340598566507442));

                c.linearBrakeCoefficients.set(Matrix.diag(0.03512411119106037, 0.06582539548756669));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.003605292162692686, 0.002129406095017298));

                c.maxAchievableForwardVelocity.set(67.16916047265144);
                c.maxAchievableStrafeVelocity.set(53.572021169975244);
                c.naturalForwardDeceleration.set(30.341429502043);
                c.naturalStrafeDeceleration.set(3354.3856332062437);
            }
    );
}