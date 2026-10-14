package org.firstinspires.ftc.teamcode.auto.autos.blue; // make sure this aligns with class location
import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.misc.utils.Alliance;
import org.firstinspires.ftc.teamcode.auto.TeamAuto;

@Autonomous(name = "Full Far Blue leave", group = "Blue")
public class AutoFullFarLeave extends TeamAuto {

    @Override
    public void postInit() {
        Alliance.set(Alliance.BLUE);
//        isFar = true;
    }

    @Override
    public Command autoRoutine() {
        return sequential(

        );
    }
}