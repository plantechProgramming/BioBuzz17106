package org.firstinspires.ftc.teamcode.subsystems;

import static com.pedropathing.ivy.commands.Commands.infinite;
import static com.pedropathing.ivy.commands.Commands.lazy;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import static org.firstinspires.ftc.teamcode.misc.InitComponents.pinpoint;

import android.util.Pair;

import com.pedropathing.api.Paths;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.misc.RobotPose;
import org.firstinspires.ftc.teamcode.misc.utils.PoseFunctions;
import org.firstinspires.ftc.teamcode.subsystems.camera.Limelight;

public class AutoCommands{
    public Shooter shooter;
    public Intake intake;
    public InBetween inBetween;
    Follower follower;
    Limelight limelight;
    PoseFunctions poseFunctions;
    public AutoCommands(Follower follower, Limelight limelight) {
        poseFunctions = new PoseFunctions(new RobotPose(pinpoint));
//        shooter = new Shooter();
//        intake = new Intake();
//        inBetween = new InBetween();
        this.follower = follower;
        this.limelight = limelight;
    }

    public AutoCommands(){
        poseFunctions = new PoseFunctions(new RobotPose(pinpoint));
//        shooter = new Shooter();
//        intake = new Intake();
//        inBetween = new InBetween();
        limelight = new Limelight();
    }

    public Command periodic(){
        return parallel(shooter.periodic());
    }


    public Path getPathToBlob(){
        if(limelight.isDetectorResultGood()){
            Pose2D targetPose2D = limelight.getBestPoseForPickup(PoseFunctions.poseToPose2D(follower.pose()));
            Pair<Double, Double> deltaXY = poseFunctions.getXYDiffToPoint(targetPose2D);
            Pair<Double, Double> shortenedDeltaXY = PoseFunctions.shortenVector(deltaXY, 10);
            Pair<Double, Double> shortenedXY = new Pair<>(shortenedDeltaXY.first+deltaXY.first, shortenedDeltaXY.second+deltaXY.second);
            Pose targetPose = PoseFunctions.pose2DToPose(new Pose2D(DistanceUnit.CM, shortenedXY.first, shortenedXY.second,
                    AngleUnit.DEGREES, targetPose2D.getHeading(AngleUnit.DEGREES)));
            Path pathToBlob = Paths.line(follower.pose(), targetPose).tangent();
            return pathToBlob;
        }
        return null;
    }
    Path pathToBlob = null;
    public Command goToDetectedBlob(){
        pathToBlob = null;
        return sequential(
                lazy(() -> infinite(() -> pathToBlob = getPathToBlob()).until(() -> pathToBlob != null)),
                lazy(() -> follow(follower, pathToBlob))
        );
    }

    public Command scoreDetectedBlob(){ // far score
        return lazy(() -> score(Paths.line(follower.pose(), new Pose(0,0,0))));
    }

    public Command shoot(){
        return sequential(
                inBetween.inFull(),
                intake.take()
        );
    }

    public Command take(){
        return parallel(
                inBetween.inPart(),
                intake.take()
        );
    }

    public Command score(Path path){
        return sequential(
                follow(follower, path),
                shoot(),
                waitMs(900)
        );
    }

    public Command out(){ // including the shooter
        return parallel(
                partialOut(),
                shooter.out()
        );
    }

    public Command partialOut(){ // not including the shooter
        return parallel(
                intake.out(),
                inBetween.out()
        );
    }

    public Command stopAll(){ // not including the shooter
        return parallel(
                intake.stop(),
                inBetween.stop()
        );
    }
    public Command scoreWithDelay(Path path, double delay){
        return sequential(
                follow(follower, path),
                shoot(),
                waitMs(delay)
        );
    }

    public Command intake(Path grabPath){
        return sequential(
                take(),
                follow(follower, grabPath),
                stopAll()
        );
    }
    public Command intakeWithSpeed(Path grabPath, double speed){
        return sequential(
                take(),
//                follow(follower, grabPath, speed), // you cant do this in the new ivy version so you will have to put the limit on the path
                stopAll()
        );
    }

    public Command intakeAndShoot(Path grabAndShootPath){
        return sequential(
                take(),
                follow(follower, grabAndShootPath),
                shoot(),
                waitMs(1600)
        );
    }

    public Command startShooter(boolean far){
        int delay = 500;
        if(far){
            delay = 1200;
        }
        return sequential(
                shooter.naiveShooter(far),
                waitMs(delay)
//                intake.take(),
//                inBetween.inBetweenInPart()
        );
    }

}


