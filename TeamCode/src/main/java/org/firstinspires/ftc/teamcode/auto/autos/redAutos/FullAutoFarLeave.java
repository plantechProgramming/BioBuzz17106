package org.firstinspires.ftc.teamcode.auto.autos.redAutos;

import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Misc.Utils.Alliance;
import org.firstinspires.ftc.teamcode.auto.TeamAuto;

@Autonomous(name = "Full Far Red leave", group = "Red")
public class FullAutoFarLeave extends TeamAuto {

    @Override
    public void postInit() {
        Alliance.set(Alliance.RED);
        isFar = true;
    }

    @Override
    public Command autoRoutine() {
        return sequential(

        );
    }
}
