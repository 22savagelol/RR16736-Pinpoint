package org.firstinspires.ftc.teamcode.Autonomus.secondRobot;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;

import org.firstinspires.ftc.teamcode.Autonomus.secondRobot.Vision.LimeSweet;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.ConfigurationSecondRobot;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.HorizontalGrabberRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.HorizontalRollRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.HorizontalSlideRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.HorizontalWristRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.RobotSensor;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.VerticalGrabberRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.VerticalHangerRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.VerticalSlideRR;
import org.firstinspires.ftc.teamcode.Configuration.secondRobot.VerticalWristRR;
import org.firstinspires.ftc.teamcode.GlobalVariables;
import org.firstinspires.ftc.teamcode.PinpointDrive;

import java.util.Arrays;

public class TrajectoryLeft {
    VerticalSlideRR verticalSlideRR; VerticalWristRR verticalWristRR; VerticalGrabberRR verticalGrabberRR; VerticalHangerRR verticalHangerRR;
    HorizontalSlideRR horizontalSlideRR; HorizontalRollRR horizontalRollRR; HorizontalGrabberRR horizontalGrabberRR;
    HorizontalWristRR horizontalWristRR; PinpointDrive drive; Pose2d pose; TrajectoryActionBuilder currentTrajectory;
    public TrajectoryLeft(PinpointDrive drive, Pose2d pose, VerticalSlideRR verticalSlideRR,
                      VerticalWristRR verticalWristRR, VerticalGrabberRR verticalGrabberRR,
                      VerticalHangerRR verticalHangerRR, HorizontalSlideRR horizontalSlideRR, HorizontalRollRR horizontalRollRR,
                      HorizontalGrabberRR horizontalGrabberRR, HorizontalWristRR horizontalWristRR){
        this.drive = drive;
        this.pose = pose;
        this.verticalSlideRR = verticalSlideRR;
        this.verticalWristRR = verticalWristRR;
        this.verticalGrabberRR = verticalGrabberRR;
        this.verticalHangerRR = verticalHangerRR;
        this.horizontalSlideRR = horizontalSlideRR;
        this.horizontalRollRR = horizontalRollRR;
        this.horizontalGrabberRR = horizontalGrabberRR;
        this.horizontalWristRR = horizontalWristRR;

        currentTrajectory = drive.actionBuilder(pose);
    }
    double basketX = -56; double basketY = -55; int basket = 0;
    int butter = 0; int attachment = 0; int park = 0;int guess = 0;
    double firstButterX = -48.5; double firstButterY = -36.5;
    double secondButterX = -58.75; double secondButterY = -50;
    double thirdButterX = -61.25; double thirdButterY = -25;
    double parkX = -20; double parkY = 0;
    double humanX = 20, humanY = -63;
    public Action getBasket() {
        if (basket == 0) {
            TrajectoryActionBuilder Basket = currentTrajectory
                    .afterTime(0, verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.topBasket))
                    .afterTime(0, verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristBasket))
                    .setTangent(Math.toRadians(90))
                    .splineToLinearHeading(new Pose2d(basketX, basketY, Math.toRadians(221)), Math.toRadians(221));
            basket++;
            currentTrajectory = Basket.endTrajectory().fresh();
            return Basket.build();
        }
        if (basket == 1 || basket == 3) {
            TrajectoryActionBuilder Basket = currentTrajectory
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime / 1000)
                    .waitSeconds(1.25)
                    .setReversed(true)
                    .splineToLinearHeading(new Pose2d(basketX, basketY, Math.toRadians(227)), Math.toRadians(227));
            basket++;
            currentTrajectory = Basket.endTrajectory().fresh();
            return Basket.build();
        }
        if (basket == 2) {
            TrajectoryActionBuilder Basket = currentTrajectory
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime / 1000)
                    .waitSeconds(2.5)
                    .setReversed(true)
                    .splineToLinearHeading(new Pose2d(-56, -55, Math.toRadians(227)), Math.toRadians(227));
            basket++;
            currentTrajectory = Basket.endTrajectory().fresh();
            return Basket.build();
        } else {
            if (park == 1) {
                TrajectoryActionBuilder Basket = currentTrajectory
                        .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime / 1000)
                        .setTangent(Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(basketX + 20, basketY, Math.toRadians(180)), Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(basketX, basketY, Math.toRadians(227)), Math.toRadians(227));
                basket++;
                currentTrajectory = Basket.endTrajectory().fresh();
                return Basket.build();
            } else {
                TrajectoryActionBuilder Basket = currentTrajectory
                        .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime / 1000)
                        .setTangent(Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(basketX, -10, Math.toRadians(-90)), Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(basketX, basketY, Math.toRadians(227)), Math.toRadians(227));
                basket++;
                currentTrajectory = Basket.endTrajectory().fresh();
                return Basket.build();
            }
        }
    }
    public Action getButter(){
        if(butter == 0){
            TrajectoryActionBuilder firstButter = currentTrajectory
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .waitSeconds(ConfigurationSecondRobot.verticalCloseTime/1000)
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristIntake))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.bottom))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberWide))
                    .setReversed(true)
                    .splineToLinearHeading(new Pose2d(firstButterX, firstButterY, Math.toRadians(-90)), Math.toRadians(90))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberClose))
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime/1000);
            butter++;
            currentTrajectory = firstButter.endTrajectory().fresh();
            return firstButter.build();
        } if(butter ==1){
            TrajectoryActionBuilder secondButter = currentTrajectory
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .waitSeconds(ConfigurationSecondRobot.verticalCloseTime/1000)
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristIntake))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.bottom))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberWide))
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ConfigurationSecondRobot.horizontalSlideExtend))
                    .setTangent(Math.toRadians(45))
                    .splineToLinearHeading(new Pose2d(secondButterX, secondButterY, Math.toRadians(-90)), Math.toRadians(90))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberClose))
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime/1000);
            butter++;
            currentTrajectory = secondButter.endTrajectory().fresh();
            return secondButter.build();
        } else {
            TrajectoryActionBuilder thirdButter = currentTrajectory
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .waitSeconds(ConfigurationSecondRobot.verticalCloseTime/1000)
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristIntake))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.bottom))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberWide))
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(350))
                    .stopAndAdd(horizontalRollRR.horizontalRollAction(ConfigurationSecondRobot.sideway))
                    .setReversed(true)
                    .splineToLinearHeading(new Pose2d(thirdButterX, thirdButterY, Math.toRadians(0)), Math.toRadians(90))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberClose))
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime/1000);
            butter++;
            currentTrajectory = thirdButter.endTrajectory().fresh();
            return thirdButter.build();
        }
    }
    public Action getAttachment(){
        if(attachment == 0) {
            TrajectoryActionBuilder Attachment = currentTrajectory
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristTransfer))
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristIntake))
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .stopAndAdd(horizontalRollRR.horizontalRollAction(ConfigurationSecondRobot.flat))
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ConfigurationSecondRobot.horizontalSlideRetract))
                    .waitSeconds(ConfigurationSecondRobot.horizontalWristIntaketoTransfer / 1000)
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalClose))
                    .waitSeconds(ConfigurationSecondRobot.verticalCloseTime / 1000)
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberOpen))
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberCloseTime / 1000)
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristBasket))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.topBasket));
            attachment++;
            currentTrajectory = Attachment.endTrajectory().fresh();
            return Attachment.build();
        } else {
            TrajectoryActionBuilder Attachment = currentTrajectory
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristTransfer))
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristIntake))
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .stopAndAdd(horizontalRollRR.horizontalRollAction(ConfigurationSecondRobot.flat))
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ConfigurationSecondRobot.horizontalSlideRetract))
                    .waitSeconds(ConfigurationSecondRobot.horizontalWristIntaketoTransfer / 1000 + .5)
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalClose))
                    .waitSeconds(ConfigurationSecondRobot.verticalCloseTime / 1000)
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberOpen))
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberCloseTime / 1000)
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristBasket))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.topBasket));
            attachment++;
            currentTrajectory = Attachment.endTrajectory().fresh();
            return Attachment.build();
        }
    }
    public Action getPark() {
        if (park == 0) {
            TrajectoryActionBuilder Park = currentTrajectory
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .waitSeconds(ConfigurationSecondRobot.verticalCloseTime / 1000)
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristIntake))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.bottom))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberWide))
                    .setTangent(Math.toRadians(45))
                    .splineToLinearHeading(new Pose2d(basketX + 10, humanY, Math.toRadians(180)), Math.toRadians(0))
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ConfigurationSecondRobot.horizontalSlideExtend))
                    .splineToLinearHeading(new Pose2d(humanX, humanY, Math.toRadians(180)), Math.toRadians(0))
                    .afterDisp(0, horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberClose))
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime / 1000);
            park++;
            currentTrajectory = Park.endTrajectory().fresh();
            return Park.build();
        } else {
            TrajectoryActionBuilder Park = currentTrajectory
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .waitSeconds(ConfigurationSecondRobot.verticalCloseTime / 1000)
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristUp))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.bottom))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberWide))
                    .setTangent(Math.toRadians(70))
                    .splineToLinearHeading(new Pose2d(basketX + 10, parkY, Math.toRadians(0)), Math.toRadians(0))
                    .splineToLinearHeading(new Pose2d(parkX, parkY, Math.toRadians(0)), Math.toRadians(0))
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristWall))
                    .waitSeconds(1);
            currentTrajectory = Park.endTrajectory().fresh();
            return Park.build();
        }
    }
    public Action getGuess(){
        if(guess == 0) {
            TrajectoryActionBuilder Guess = currentTrajectory
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .waitSeconds(ConfigurationSecondRobot.verticalCloseTime / 1000)
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristIntake))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.bottom))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristHover))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberWide))
                    .setTangent(Math.toRadians(0))
                    .splineToLinearHeading(new Pose2d(-25, -10, Math.toRadians(180)), Math.toRadians(0))
                    .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ConfigurationSecondRobot.horizontalSlideExtend))
                    .splineToLinearHeading(new Pose2d(parkX, -10, Math.toRadians(180)), Math.toRadians(0))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                    .waitSeconds(.25)
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberClose))
                    .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime / 1000);
            guess++;
            currentTrajectory = Guess.endTrajectory().fresh();
            return Guess.build();
        } else {
            TrajectoryActionBuilder Park = currentTrajectory
                    .stopAndAdd(verticalGrabberRR.verticalGrabberAction(ConfigurationSecondRobot.verticalOpen))
                    .waitSeconds(ConfigurationSecondRobot.verticalCloseTime / 1000)
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristUp))
                    .stopAndAdd(verticalSlideRR.verticalSlideAction(ConfigurationSecondRobot.bottom))
                    .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                    .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberWide))
                    .setTangent(Math.toRadians(70))
                    .splineToLinearHeading(new Pose2d(basketX + 10, -10, Math.toRadians(0)), Math.toRadians(0))
                    .splineToLinearHeading(new Pose2d(parkX, -10, Math.toRadians(0)), Math.toRadians(0))
                    .stopAndAdd(verticalWristRR.VerticalWristAction(ConfigurationSecondRobot.verticalWristWall))
                    .waitSeconds(1);
            currentTrajectory = Park.endTrajectory().fresh();
            return Park.build();
        }
    }
    public Action getYellow(){
        double y = GlobalVariables.X;
        double x = drive.getLastPinpointPose().position.y;
        int ticks = horizontalSlideRR.getSlideTick();
        
        TrajectoryActionBuilder Yellow = currentTrajectory
                .stopAndAdd(horizontalSlideRR.horizontalSlideActions(ticks))
                .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristHover))
                .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberWide))
                .strafeToConstantHeading(new Vector2d(x, y))
                .stopAndAdd(horizontalWristRR.horizontalWristAction(ConfigurationSecondRobot.horizontalWristIntake))
                .waitSeconds(.125)
                .stopAndAdd(horizontalGrabberRR.horizontalGrabberAction(ConfigurationSecondRobot.horizontalGrabberClose))
                .waitSeconds(ConfigurationSecondRobot.horizontalGrabberWideTime/1000);
        currentTrajectory = Yellow.endTrajectory().fresh();
        return Yellow.build();
    }
}
