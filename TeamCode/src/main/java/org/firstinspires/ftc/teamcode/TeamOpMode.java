package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.dashboardTelemetry;
import static org.firstinspires.ftc.teamcode.Misc.InitComponents.pinpoint;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Misc.InitComponents;
import org.firstinspires.ftc.teamcode.subsystems.Turret;


public abstract class TeamOpMode extends LinearOpMode {
    InitComponents initMotors;

    private void initAll(){
        initMotors.initDriveTrain();
        initMotors.initTurret();
//        initMotors.initIntake();
//        initMotors.initInBetween();
//        initMotors.initShooter();
        initMotors.initPinpoint();
        initMotors.initDashboard();
        initMotors.initLL();
        initMotors.initLogiCam();

    }

    private void initThings(){ // random things that need to be initialized here
        telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);
    }
    @Override
    public void runOpMode() throws InterruptedException  {
        initMotors = new InitComponents(hardwareMap);
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

