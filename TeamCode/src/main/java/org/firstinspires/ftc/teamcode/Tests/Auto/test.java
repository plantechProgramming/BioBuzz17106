package org.firstinspires.ftc.teamcode.Tests.Auto;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.ll;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.auto.TeamAuto;

@Autonomous(group="autonomous tests")
public class test extends TeamAuto {

    @Override
    public void postInit(){
        isFar = true;
        Alliance.set(Alliance.BLUE);
        ll.start();
    }

    @Override
    public Command autoRoutine() {
        return sequential(
                command.startShooter(true),
                waitMs(3000)
//                repeat(sequential(
//                        command.goToDetectedBlob(),
//                        command.scoreDetectedBlob(path),
//                        follow(follower, follower.pathBuilder().addPath(new BezierLine(path.points.scorePoseFar, path.points.startPoseFar))
//                                .setLinearHeadingInterpolation(path.points.scorePoseFar.getHeading(), path.points.startPoseFar.getHeading()).build())
//                ), 15)
        );
    }
}
