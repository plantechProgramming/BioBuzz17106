package org.firstinspires.ftc.teamcode.tests.auto;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import static org.firstinspires.ftc.teamcode.misc.InitComponents.ll;

import android.view.WindowContentFrameStats;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.misc.utils.Alliance;
import org.firstinspires.ftc.teamcode.auto.TeamAuto;

@Autonomous(group="autonomous tests")
public class Test extends TeamAuto {

    @Override
    public void postInit(){
//        isFar = true;
        Alliance.set(Alliance.BLUE);
        ll.start();
    }

    @Override
    public Command autoRoutine() {
        return sequential(
                command.goToDetectedBlob()
//                command.scoreDetectedBlob()
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
