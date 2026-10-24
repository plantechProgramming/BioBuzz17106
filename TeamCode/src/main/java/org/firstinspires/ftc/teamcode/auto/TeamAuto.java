package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.ivy.Scheduler.schedule;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.misc.DataSaving;
import org.firstinspires.ftc.teamcode.misc.utils.TelemetryUtils;
import org.firstinspires.ftc.teamcode.misc.pedro.Constants;
import org.firstinspires.ftc.teamcode.TeamOpMode;
import org.firstinspires.ftc.teamcode.auto.autos.paths.Paths;
import org.firstinspires.ftc.teamcode.subsystems.AutoCommands;
import org.firstinspires.ftc.teamcode.subsystems.camera.Limelight;

public abstract class TeamAuto extends TeamOpMode {
    protected AutoCommands command;
    protected Paths path;
    protected Follower follower;
    public Limelight limelight;
//    protected Boolean isFar;

    @Override
    public void run(){
        Scheduler.reset();
        path = new Paths();
        follower = Constants.create(hardwareMap);
        follower.setPose(new Pose(9, 8.5, Math.toRadians(0)));
        limelight = new Limelight();
//        if(isFar){
////            follower.setPose(path.points.get("startFar"));
//        }
//        else{
////            follower.setPose(path.points.get("start"));
//        }
        follower.update();
        command = new AutoCommands(follower, limelight);
        schedule(autoRoutine());
        while (opModeIsActive()) {
            TelemetryUtils.updateCertainTelemtries(telemetry, follower);
            DataSaving.setEndPos(follower.pose());
//            schedule(command.periodic());
            Path path = command.getPathToBlob();
            if(path != null)
                telemetry.addData("pos", path.endPose());
            Scheduler.execute();
            follower.update();
            telemetry.update();
            limelight.updateDetectorResult();
        }
    }

    public abstract Command autoRoutine();
    @Override
    protected void end() {
//        System.out.println("auto loop time: " + Arrays.toString(extras.getHistogram()));
    }
}
