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
        Alliance.set(Alliance.RED);
    }

    @Override
    public Command autoRoutine() {
        return sequential(
                follow(follower, path.get("start to garden"))
        );
    }
}
