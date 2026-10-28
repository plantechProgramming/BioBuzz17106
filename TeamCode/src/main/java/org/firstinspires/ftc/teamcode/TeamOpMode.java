package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Misc.Components;
import org.firstinspires.ftc.teamcode.Misc.RobotPose;
import org.firstinspires.ftc.teamcode.Misc.pedro.Constants;


public abstract class TeamOpMode extends LinearOpMode {
    protected Components components;
    protected Follower follower;
    protected RobotPose robotPose;
    private void initAll(){
        components.initDriveTrain();
//        components.initIntake();
//        components.initInBetween();
//        components.initShooter();
        components.initDashboard();
        components.initLL();
        components.initLogiCam();
    }

    private void initThings(){ // random things that need to be initialized here
        telemetry = new MultipleTelemetry(telemetry, components.dashboardTelemetry);
        follower = Constants.create(hardwareMap);
        Scheduler.reset();
        follower.update();
        components.robotPose = new RobotPose(follower);
        this.robotPose = components.robotPose;
    }
    @Override
    public void runOpMode() throws InterruptedException  {
        components = new Components(hardwareMap);
        initAll();
        initThings();
        postInit();
        waitForStart();

        if (opModeIsActive()) {
            run();
        }

        end();
    }

    protected void postInit() {

    }
    protected abstract void run();

    protected abstract void end();
}

