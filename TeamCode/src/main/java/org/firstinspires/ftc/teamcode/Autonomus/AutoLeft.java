package org.firstinspires.ftc.teamcode.Autonomus;

import androidx.annotation.AnimatorRes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalIntake;
import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalSlide;
import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalWrist;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalGrabber;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalSlide;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalTilt;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalWrist;
import org.firstinspires.ftc.teamcode.RR.PinpointDrive;

@Autonomous(name = "AutoLeft", group = "auto")
public class AutoLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HorizontalIntake horizontalIntake = new HorizontalIntake(hardwareMap);
        HorizontalSlide horizontalSlide = new HorizontalSlide(hardwareMap);
        HorizontalWrist horizontalWrist = new HorizontalWrist(hardwareMap);
        VerticalGrabber verticalGrabber = new VerticalGrabber(hardwareMap);
        VerticalSlide verticalSlide = new VerticalSlide(hardwareMap);
        VerticalTilt verticalTilt = new VerticalTilt(hardwareMap);
        VerticalWrist verticalWrist = new VerticalWrist(hardwareMap);

        Pose2d pose = new Pose2d(-29.5,-62.25,Math.toRadians(180));
//        Pose2d pose = new Pose2d(0,0,Math.toRadians(90));
        PinpointDrive drive = new PinpointDrive(hardwareMap, pose);

        TrajectoryLeft trajectory = new TrajectoryLeft(drive, pose, horizontalIntake, horizontalSlide, horizontalWrist,
                verticalGrabber, verticalSlide, verticalTilt, verticalWrist);

        //wait for the start button to be press
        waitForStart();
        //if the stop button press then stop the robot
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(
                trajectory.getBasket(),
                trajectory.getButter(),
                new SleepAction(2000)
        ));
    }
}
