package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.ivy.Scheduler.schedule;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Misc.DataSaving;
import org.firstinspires.ftc.teamcode.Misc.Utils.Extras;
import org.firstinspires.ftc.teamcode.Misc.Utils.TelemetryUtils;
import org.firstinspires.ftc.teamcode.Misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.auto.autos.paths.Paths;
import org.firstinspires.ftc.teamcode.subsystems.AutoCommands;

public abstract class TeamAuto extends TeamOpMode {
    protected AutoCommands command;
    protected Paths path;
    protected Follower follower;

    @Override
    public void run(){
        Scheduler.reset();
        path = new Paths();
        follower = Constants.create(hardwareMap);
        follower.setPose(path.p.get("start"));
        follower.update();
        command = new AutoCommands(follower);
        schedule(autoRoutine());
        while (opModeIsActive()) {
            TelemetryUtils.updateCertainTelemtries(telemetry, follower);
            DataSaving.setEndPos(follower.pose());
            Scheduler.execute();
            follower.update();
            telemetry.update();
        }
    }

    public abstract Command autoRoutine();
    @Override
    protected void end() {
//        System.out.println("auto loop time: " + Arrays.toString(extras.getHistogram()));
    }
}
