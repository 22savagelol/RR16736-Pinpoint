package org.firstinspires.ftc.teamcode.Testing;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
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
import org.firstinspires.ftc.teamcode.Config.LimeSweet;
import org.firstinspires.ftc.teamcode.Config.Pose;
import org.firstinspires.ftc.teamcode.Config.RobotConfig;
import org.firstinspires.ftc.teamcode.RR.MecanumDrive;

@Autonomous(name = "LimeDetection", group = "test")
public class LimeDetection extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotConfig.selection = 1;
        HorizontalIntake horizontalIntake = new HorizontalIntake(hardwareMap);
        HorizontalSlide horizontalSlide = new HorizontalSlide(hardwareMap);
        HorizontalWrist horizontalWrist = new HorizontalWrist(hardwareMap);
        VerticalGrabber verticalGrabber = new VerticalGrabber(hardwareMap);
        VerticalSlide verticalSlide = new VerticalSlide(hardwareMap);
        VerticalTilt verticalTilt = new VerticalTilt(hardwareMap);
        VerticalWrist verticalWrist = new VerticalWrist(hardwareMap);

        Pose2d pose = new Pose2d(0,0,Math.toRadians(90));
        Pose2d staticPose = new Pose2d(0,0,Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, pose);
        LimeSweet lime = new LimeSweet(hardwareMap, telemetry, 0);

        TrajectoryActionBuilder prime = drive.actionBuilder(pose)
                .strafeTo(new Vector2d(0, -2));


        //wait for the start button to be press
        waitForStart();
        //if the stop button press then stop the robot
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(
                        prime.build()
        ));
        double[] point = {1,5};
        TrajectoryActionBuilder grabButter = drive.actionBuilder(staticPose)
                .stopAndAdd(horizontalSlide.horizontalSlideDistanceAction(point[1]))
                .stopAndAdd(horizontalWrist.horizontalWristAction(Pose.horizontalWristHover))
                .stopAndAdd(horizontalIntake.horizontalIntakeAction(1))
                .waitSeconds(.125)
                .stopAndAdd(horizontalWrist.horizontalWristAction(Pose.horizontalWristIntake))
                .strafeTo(new Vector2d(0, point[0]));
        Actions.runBlocking(new SequentialAction(
                grabButter.build(),
                new SleepAction(20000)
        ));

    }
}
