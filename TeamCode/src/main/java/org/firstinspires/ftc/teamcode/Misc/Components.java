package org.firstinspires.ftc.teamcode.Misc;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;

public class Components {
    HardwareMap hardwareMap;

    public CRServo SL,SR;
    public DcMotorEx FL, FR, BL, BR; //odometry is for testing purposes
    public DcMotorEx inBetweenMotor, shootMotor, shootMotorOp,intakeMotor; //odometry is for testing purposes
    public Telemetry dashboardTelemetry;
    public Limelight3A ll;
    public CameraName logiCam;
    public FtcDashboard dashboard;

    public RobotPose robotPose;
    public Components(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }
    public void initDriveTrain(){ // the initialisation of the drivetrain motor is in Pedro
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        BR = hardwareMap.get(DcMotorEx.class, "BR");
    }
    
    public void initIntake(){
        intakeMotor = hardwareMap.get(DcMotorEx.class,"Intake");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    
    public void initInBetween(){
        SL = hardwareMap.get(CRServo.class,"SL");
        SL.setDirection(CRServo.Direction.REVERSE);
        SR = hardwareMap.get(CRServo.class,"SR");
        SR.setDirection(CRServo.Direction.REVERSE);

        inBetweenMotor = hardwareMap.get(DcMotorEx.class, "inbetween");
        inBetweenMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    
    public void initShooter(){
        shootMotorOp = hardwareMap.get(DcMotorEx.class, "ShooterFar");
        shootMotorOp.setDirection(DcMotorSimple.Direction.FORWARD);
        shootMotorOp.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        shootMotor = hardwareMap.get(DcMotorEx.class, "ShooterClose");
        shootMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shootMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

    
    public void initDashboard(){
        dashboard = FtcDashboard.getInstance();
        dashboardTelemetry = dashboard.getTelemetry();
    }

    public void initLL() {
        ll = hardwareMap.get(Limelight3A.class, "LimeLight");
        ll.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        ll.pipelineSwitch(2);
    }

    public void initLogiCam(){
        logiCam = hardwareMap.get(CameraName.class, "webcam");
    }
}
