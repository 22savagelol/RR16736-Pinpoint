package org.firstinspires.ftc.teamcode.Autonomus.secondRobot;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;

import org.firstinspires.ftc.teamcode.Configuration.secondRobot.ConfigurationSecondRobot;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.HorizontalGrabberRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.HorizontalRollRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.HorizontalSlideRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.HorizontalWristRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.VerticalGrabberRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.VerticalSlideRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.VerticalWristRR;
import org.firstinspires.ftc.teamcode.PinpointDrive;

import java.util.Arrays;

@Config
public class Trajectory{
    VerticalSlideRR verticalSlideRR;
    VerticalWristRR verticalWristRR;
    VerticalGrabberRR verticalGrabberRR;

    HorizontalSlideRR horizontalSlideRR;
    HorizontalRollRR horizontalRollRR;
    HorizontalGrabberRR horizontalGrabberRR;
    HorizontalWristRR horizontalWristRR;
    PinpointDrive drive;
    Pose2d pose;
    TrajectoryActionBuilder currentTrajectory;
    boolean side = false;
    public void setTrajectory(PinpointDrive drive, Pose2d pose, VerticalSlideRR verticalSlideRR,
                              VerticalWristRR verticalWristRR, VerticalGrabberRR verticalGrabberRR,
                              HorizontalSlideRR horizontalSlideRR, HorizontalRollRR horizontalRollRR,
                              HorizontalGrabberRR horizontalGrabberRR, HorizontalWristRR horizontalWristRR,
                              boolean side) {
        this.drive = drive;
        this.pose = pose;
        this.verticalSlideRR = verticalSlideRR;
        this.verticalWristRR = verticalWristRR;
        this.verticalGrabberRR = verticalGrabberRR;
        this.horizontalSlideRR = horizontalSlideRR;
        this.horizontalRollRR = horizontalRollRR;
        this.horizontalGrabberRR = horizontalGrabberRR;
        this.horizontalWristRR = horizontalWristRR;
        this.side = side;
        if(side){
            hangX = hangX*-1;
            butterX = butterX*-1;
            parkX = parkX*-1;
        }
    }
    public void setStartTrajectory(){
        currentTrajectory = drive.actionBuilder(pose);
    }
    public TrajectoryActionBuilder setSplineTestTrajectory(){
        return drive.actionBuilder(pose)
                .splineTo(new Vector2d(30, 30), Math.PI / 2)
                .splineTo(new Vector2d(0, 60), Math.PI);
    }
    VelConstraint baseVel = new MinVelConstraint(Arrays.asList(
            new TranslationalVelConstraint(35),
            new AngularVelConstraint(Math.PI*.9)
    ));
    VelConstraint speed = new MinVelConstraint(Arrays.asList(
            new TranslationalVelConstraint(120),
            new AngularVelConstraint(Math.PI)
    ));

    public static double butterY = 16.75;
    public static double butterX = 37;
    public static double hangX = -12;
    public static double parkX = 49;
    public static double basketX = -47;
    public static double basketY = 7;
    public static double basketHeading = 200;
    public TrajectoryActionBuilder getHangTrajectory() {
        TrajectoryActionBuilder HangTrajectory = currentTrajectory
                .afterTime(0, verticalSlideRR.verticalSlideAction((ConfigurationSecondRobot.highBar+185)))
                .afterTime(0, verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristBar))
                .afterTime(0, verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalClose))
                .strafeToSplineHeading(new Vector2d(hangX, 19),Math.toRadians(90))
                .strafeToSplineHeading(new Vector2d(hangX, 29),Math.toRadians(90))
                .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                .strafeTo(new Vector2d(hangX, butterY))
                .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.bottom+5))
                .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristBasket));
        currentTrajectory = HangTrajectory.endTrajectory().fresh();
        if(side) {
            hangX = hangX - 3;
        } else {
            hangX = hangX + 3;
        }
        return HangTrajectory;
    }
    public TrajectoryActionBuilder getButterPickUpTrajectory(){
        TrajectoryActionBuilder ButterPickUpTrajectory = currentTrajectory
                .strafeToSplineHeading(new Vector2d(butterX, butterY), Math.toRadians(-90), baseVel);
        currentTrajectory = ButterPickUpTrajectory.endTrajectory().fresh();
        return ButterPickUpTrajectory;
    }
    public TrajectoryActionBuilder getSecondButterPickUpTrajectory(){
        TrajectoryActionBuilder SecondButterPickUpTrajectory;
        if(side){
            SecondButterPickUpTrajectory = currentTrajectory
                    .waitSeconds(1)
                    .strafeToSplineHeading(new Vector2d(basketX, basketY), Math.toRadians(basketHeading))
                    .strafeToSplineHeading(new Vector2d(butterX - 12, butterY), Math.toRadians(90));
        }else {
            SecondButterPickUpTrajectory = currentTrajectory
                    .waitSeconds(1)
                    .strafeTo(new Vector2d(butterX + 12, butterY));
        }
        currentTrajectory = SecondButterPickUpTrajectory.endTrajectory().fresh();
        return SecondButterPickUpTrajectory;
    }

    public TrajectoryActionBuilder getButterPickUpAttachment(){
        if(side){
            return currentTrajectory
                    //priming the horizontal to grab the butter
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ConfigurationSecondRobot.horizontalSlideExtend))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberOpen))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristIntake))
                    .waitSeconds(1)
                    //close the horizontal grabber
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberClose))
                    //retract the horizontal
                    .waitSeconds(.3)
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ConfigurationSecondRobot.horizontalSlideRetract))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristTransfer))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberSoftClose))
                    .waitSeconds(1.25)
                    //horizontal grabber let go and vertical grabber close
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalClose))
                    .waitSeconds(.25)
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberOpen))
                    .waitSeconds(.25)
                    //vertical wrist move to the basket position and extend the vertical slide
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristBasket))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.topBasket));
        }else{
            return currentTrajectory
                    //priming the horizontal to grab the butter
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ConfigurationSecondRobot.horizontalSlideExtend))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberOpen))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristIntake))
                    .waitSeconds(ConfigurationSecondRobot.verticalWristWalltoIntake)
                    //close the horizontal grabber
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberClose))
                    //retract the horizontal
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberCloseTime)
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ConfigurationSecondRobot.horizontalSlideRetract))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristTransfer))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberSoftClose))
                    .waitSeconds(1.25)
                    //horizontal grabber let go and vertical grabber close
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalClose))
                    .waitSeconds(.25)
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberOpen))
                    .waitSeconds(.25)
                    //vertical wrist move to the other side
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristWall))
                    .waitSeconds(ConfigurationSecondRobot.verticalWristWalltoIntake)
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .waitSeconds(.25);
        }
    }
    public TrajectoryActionBuilder getPostHangLocationTrajectory(){
        TrajectoryActionBuilder PostHangLocationTrajectory = currentTrajectory
                //strafe to human pick up
                .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristWall))
                .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                .strafeToSplineHeading(new Vector2d(49, 12), Math.toRadians(-90))
                .strafeToSplineHeading(new Vector2d(49, 8.25), Math.toRadians(-90))
                .waitSeconds(.5)
                .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalClose));
        currentTrajectory = PostHangLocationTrajectory.endTrajectory().fresh();
        return  PostHangLocationTrajectory;
    }

    public TrajectoryActionBuilder getPark(){
        return currentTrajectory
                .strafeTo(new Vector2d(parkX, 4), speed);
    }

    public TrajectoryActionBuilder getPostHangAttachment(){
        return currentTrajectory
                .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristWall))
                .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalClose))
                .waitSeconds(.5)
                .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristBar));
    }
}
