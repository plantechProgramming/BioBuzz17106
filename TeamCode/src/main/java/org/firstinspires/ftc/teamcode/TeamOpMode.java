package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Misc.InitComponents.dashboardTelemetry;
import static org.firstinspires.ftc.teamcode.Misc.InitComponents.ll;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Misc.InitComponents;


public abstract class TeamOpMode extends LinearOpMode {
    InitComponents initMotors;

    private void initAll(){
        initMotors.initDriveTrain();
//        initMotors.initIntake();
//        initMotors.initInBetween();
//        initMotors.initShooter();
        initMotors.initPinpoint();
        initMotors.initDashboard();
        initMotors.initLL();
    }

    private void initThings(){ // random things that need to be initialized here
        telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);
        InitComponents.dashboard.startCameraStream(ll, 60);
    }
    @Override
    public void runOpMode() throws InterruptedException  {
        initMotors = new InitComponents(hardwareMap);
        initAll();
        initThings();
        init();
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

