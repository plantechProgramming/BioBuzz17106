package org.firstinspires.ftc.teamcode.Tests.Auto;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.ll;

import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.auto.TeamAuto;

@Autonomous(group="autonomous tests")
public class test extends TeamAuto {

    @Override
    public void postInit(){
//        isFar = true;
        Alliance.set(Alliance.BLUE);
        ll.start();
    }

    @Override
    public Command autoRoutine() {
        return sequential(
//                follow(follower,
//                        line(new Pose(72, 72, Math.toRadians(0)), new Pose(0,0,Math.toRadians(0))).constant(Math.toRadians(0)))
//                repeat(sequential(
//                        command.goToDetectedBlob(),
//                        command.scoreDetectedBlob(path),
//                        follow(follower, follower.pathBuilder().addPath(new BezierLine(path.points.scorePoseFar, path.points.startPoseFar))
//                                .setLinearHeadingInterpolation(path.points.scorePoseFar.getHeading(), path.points.startPoseFar.getHeading()).build())
//                ), 15)
        );
    }
}
