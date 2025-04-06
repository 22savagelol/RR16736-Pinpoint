package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalIntake;
import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalSlide;
import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalWrist;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalGrabber;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalSlide;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalTilt;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalWrist;
import org.firstinspires.ftc.teamcode.Config.Pose;
import org.firstinspires.ftc.teamcode.RR.MecanumDrive;
import org.firstinspires.ftc.teamcode.RR.PinpointDrive;

@TeleOp(name = "MainTeleOp", group = "main")
public class MainTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HorizontalIntake horizontalIntake = new HorizontalIntake(hardwareMap);
        HorizontalSlide horizontalSlide = new HorizontalSlide(hardwareMap);
        HorizontalWrist horizontalWrist = new HorizontalWrist(hardwareMap);
        VerticalGrabber verticalGrabber = new VerticalGrabber(hardwareMap);
        VerticalSlide verticalSlide = new VerticalSlide(hardwareMap);
        VerticalTilt verticalTilt = new VerticalTilt(hardwareMap);
        VerticalWrist verticalWrist = new VerticalWrist(hardwareMap);

        Pose2d pose = new Pose2d(0,0,Math.toRadians(90));
        PinpointDrive drive = new PinpointDrive(hardwareMap, pose);

        DriveTrain driveTrain = new DriveTrain(drive.leftFront, drive.rightFront, drive.leftBack, drive.rightBack, drive, false);
        TeleOpAttachment attachment = new TeleOpAttachment(drive, horizontalIntake, horizontalSlide, horizontalWrist, verticalGrabber,
                verticalSlide, verticalTilt, verticalWrist, gamepad1, gamepad2);

        //wait for the start button to be press
        waitForStart();
        //if the stop button press then stop the robot
        if (isStopRequested()) return;
        horizontalWrist.horizontalWristPose(Pose.horizontalWristHover);
        while(opModeIsActive()){
            attachment.verticalSlide(gamepad1.left_bumper, gamepad1.left_trigger >= .5);
            attachment.verticalGrabber(gamepad1.right_bumper);
            attachment.horizontalSlide(gamepad1.triangle);
            attachment.horizontalIntake(gamepad1.cross);

            driveTrain.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_trigger, .6);

//            telemetry.addData("HorizontalSlideSensor: ", horizontalSlide.horizontalSlideSensor());
            telemetry.addData("VerticalWristSensor", verticalWrist.verticalWristSensor());
            telemetry.addData("Color Red: ", horizontalIntake.horizontalColor()[0]);
            telemetry.addData("Color Blue: ", horizontalIntake.horizontalColor()[1]);
            telemetry.addData("Color Green: ", horizontalIntake.horizontalColor()[2]);
            telemetry.update();
//            attachment.updateFunction();
            attachment.updateAction();
        }

    }
}
