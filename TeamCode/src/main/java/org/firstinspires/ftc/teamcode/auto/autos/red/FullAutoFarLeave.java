package org.firstinspires.ftc.teamcode.auto.autos.red;

import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.misc.utils.Alliance;
import org.firstinspires.ftc.teamcode.auto.TeamAuto;

@Autonomous(name = "Full Far Red leave", group = "Red")
public class FullAutoFarLeave extends TeamAuto {

    @Override
    public void postInit() {
        Alliance.set(Alliance.RED);
//        isFar = true;
    }

    @Override
    public Command autoRoutine() {
        return sequential(

        );
    }
}
