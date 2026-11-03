package org.firstinspires.ftc.teamcode.Tests.Auto;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.repeat;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.groups.Groups;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.auto.TeamAuto;

@Autonomous(group="autonomous tests")
public class test extends TeamAuto {

    @Override
    public void postInit(){
//        isFar = true;
        Alliance.set(Alliance.BLUE);

        components.ll.start();
    }
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose path1 = poseFactory.of(70.8477, 37.2522, -90);
    private final Pose point2Start = poseFactory.of(70.8477, 37.2522, -90);
    private final Pose point2 = poseFactory.of(110.4708, 37.7463, -270);
    private final Pose point3Start = poseFactory.of(110.4708, 37.7463, 0);
    private final Pose point3 = poseFactory.of(98.8404, 112.2293, 0);
    private final Pose point3Control1 = poseFactory.of(141.5, 87.0591, 0);
    private final Pose point4 = poseFactory.of(54.8504, 112.4505, -0.2881);
    private final Pose point5Start = poseFactory.of(54.8504, 112.4505, 0);
    private final Pose start = poseFactory.of(71.75, 71.75, -90);
    private final Pose point5Control1 = poseFactory.of(73.889, 137.6995, 0);
    private final Pose bigloopThrough1 = poseFactory.of(54.365, 111.8008, 0);
    private final Pose bigloopThrough2 = poseFactory.of(25.5582, 90.3208, 0);
    private final Pose bigloopThrough3 = poseFactory.of(28.0195, 47.0238, 0);
    private final Pose bigloopThrough4 = poseFactory.of(65.4179, 24.9909, 0);
    private final Pose bigloopThrough5 = poseFactory.of(107.7485, 37.8276, 0);
    private final Pose bigloopThrough6 = poseFactory.of(119.8017, 78.7433, 0);
    private final Pose bigloopThrough7 = poseFactory.of(94.4307, 109.4791, 0);

    public Path path1() {
        return Paths.line(start, path1).constant(path1);
    }

    public Path path2() {
        return Paths.line(point2Start, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point3Start, point3Control1, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.line(point3, point4).reverseTangent();
    }

    public Path path5() {
        return Paths.curve(point5Start, point5Control1, start).linear(point5Start, start);
    }

    public Path bigLoopThrough() {
        return Paths.through(start, bigloopThrough1, bigloopThrough2, bigloopThrough3, bigloopThrough4, bigloopThrough5, bigloopThrough6, bigloopThrough7).facingPoint(start);
    }
    @Override
    public Command autoRoutine() {
        return sequential(
                repeat(
                        sequential(
                                follow(follower, path1()),
                                follow(follower, path2()),
                                follow(follower, path3()),
                                follow(follower, path4()),
                                follow(follower, path5()),
                                repeat(
                                        follow(follower, bigLoopThrough()),
                                2)
                        ),
                100)




//                repeat(sequential(
//                        command.goToDetectedBlob(),
//                        command.scoreDetectedBlob(path),
//                        follow(follower, follower.pathBuilder().addPath(new BezierLine(path.points.scorePoseFar, path.points.startPoseFar))
//                                .setLinearHeadingInterpolation(path.points.scorePoseFar.getHeading(), path.points.startPoseFar.getHeading()).build())
//                ), 15)
        );
    }
}
