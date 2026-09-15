package org.firstinspires.ftc.teamcode.Tests.Auto;

import static com.pedropathing.ivy.groups.Groups.*;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.subsystems.AutoCommands;

@Autonomous(group = "autonomous tests")
public class testSubsystems extends TeamOpMode {

    AutoCommands command;

    public Command autoRoutine(){
        return sequential(
            command.shooter.setPowerAsCommand(0.1)
        );
    }

    @Override
    public void postInit() {
        command = new AutoCommands();
    }

    @Override
    protected void run() {
        Scheduler.schedule(autoRoutine());
        while (opModeIsActive()) {
            command.periodic();
            Scheduler.execute();
        }
    }

    @Override
    protected void end() {

    }
}