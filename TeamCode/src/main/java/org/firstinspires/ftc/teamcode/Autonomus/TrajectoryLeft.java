package org.firstinspires.ftc.teamcode.Autonomus;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalIntake;
import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalSlide;
import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalWrist;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalGrabber;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalSlide;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalTilt;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalWrist;
import org.firstinspires.ftc.teamcode.Config.Pose;
import org.firstinspires.ftc.teamcode.RR.MecanumDrive;

public class TrajectoryLeft {
    HorizontalIntake horizontalIntake; HorizontalSlide horizontalSlide; HorizontalWrist horizontalWrist;
    VerticalGrabber verticalGrabber; VerticalSlide verticalSlide; VerticalTilt verticalTilt; VerticalWrist verticalWrist;
    MecanumDrive drive; TrajectoryActionBuilder currentTrajectory;
    public TrajectoryLeft(MecanumDrive drive, Pose2d pose, HorizontalIntake horizontalIntake, HorizontalSlide horizontalSlide, HorizontalWrist horizontalWrist,
                          VerticalGrabber verticalGrabber, VerticalSlide verticalSlide, VerticalTilt verticalTilt, VerticalWrist verticalWrist) {
        this.horizontalIntake = horizontalIntake;
        this.horizontalSlide = horizontalSlide;
        this.horizontalWrist = horizontalWrist;
        this.verticalGrabber = verticalGrabber;
        this.verticalSlide = verticalSlide;
        this.verticalTilt = verticalTilt;
        this.verticalWrist = verticalWrist;

        currentTrajectory = drive.actionBuilder(pose);
    }
    double basket, butter = 0;
    double BX = -56; double BY = -50; double BH = Math.toRadians(220); double BT = Math.toRadians(220);
    double FBX = -50; double FBY = -40; double FBH = Math.toRadians(-90); double FBT = Math.toRadians(-90);
    double SBX = -55; double SBY = -40; double SBH = Math.toRadians(-90); double SBT = Math.toRadians(-90);

    public Action getBasket(){
        TrajectoryActionBuilder basketTrajectory = currentTrajectory
                .afterTime(0, verticalSlide.verticalSlideAction(Pose.verticalSlideHighBasket))
                .afterTime(0, verticalWrist.verticalWristAction(Pose.verticalWristBasket))
                .afterTime(0, verticalTilt.verticalTiltAction(Pose.verticalTiltBasket))
                .afterTime(0, horizontalWrist.horizontalWristAction(Pose.horizontalWristHover))
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(BX, BY, BH), BT)
                ;
        basket++;
        currentTrajectory = basketTrajectory.fresh();
        return basketTrajectory.build();
    }
    public Action getButter(){
        if(butter == 0){
            TrajectoryActionBuilder firstButter = currentTrajectory
                    .afterTime(.5, verticalSlide.verticalSlideAction(Pose.verticalSlideBottom))
                    .stopAndAdd(verticalGrabber.verticalGrabberAction(Pose.verticalGrabberOpen))
                    .stopAndAdd(verticalWrist.verticalWristAction(Pose.verticalWristTransfer))
                    .stopAndAdd(verticalTilt.verticalTiltAction(Pose.verticalTiltTransfer))

                    .setTangent(Math.toRadians(45))
                    .strafeToLinearHeading(new Vector2d(FBX, FBY), FBH)
                    ;
            butter++;
            currentTrajectory = firstButter.fresh();
            return firstButter.build();
        } else {
            TrajectoryActionBuilder secondButter = currentTrajectory
                    .afterTime(.5, verticalSlide.verticalSlideAction(Pose.verticalSlideBottom))
                    .stopAndAdd(verticalGrabber.verticalGrabberAction(Pose.verticalGrabberOpen))
                    .stopAndAdd(verticalWrist.verticalWristAction(Pose.verticalWristTransfer))
                    .stopAndAdd(verticalTilt.verticalTiltAction(Pose.verticalTiltTransfer))

                    .setTangent(Math.toRadians(45))
                    .splineToLinearHeading(new Pose2d(SBX, SBY, SBH), SBT)
                    ;
            butter++;
            currentTrajectory = secondButter.fresh();
            return secondButter.build();
        }
    }

    public Action getSpline(){
        TrajectoryActionBuilder Spline = currentTrajectory
                .splineTo(new Vector2d(-30, 15), Math.toRadians(180))
                .splineTo(new Vector2d(-60, 0), Math.toRadians(-90));
        return Spline.build();
    }
}
