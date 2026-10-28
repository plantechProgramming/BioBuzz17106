package org.firstinspires.ftc.teamcode.subsystems;

import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.groups.Groups.parallel;

import com.pedropathing.api.Paths;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PedroDriveTrain {
    Follower follower;
    public PedroDriveTrain(Follower follower){
        this.follower = follower;
    }
    public Command driveWithPedro(Gamepad gamepad1, ElapsedTime elapsedTime){
        if(elapsedTime.milliseconds() >= 200){
            DrivePowers powers = ManualDrive.fieldCentric(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x,
                    follower.pose().heading()
            );
            return instant(() -> ManualDrive.driveOrHold(follower, powers));
        }
        else {
            return instant(() -> follower.manual(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x
            ));

        }
    }

    boolean stopAutoMove = false;
    boolean prevInput = false;
    public void pedroGoToPose(boolean input){
        if(!prevInput && input){
            if(!follower.following()){
                follower.follow(Paths.line(follower.pose(), new Pose(0, 0, 0)).linear(follower.pose().heading(), 0));
                stopAutoMove = true;
            }
            else if(stopAutoMove){
                follower.stop();
                stopAutoMove = false;
            }
        }
        prevInput = input;
    }

}
