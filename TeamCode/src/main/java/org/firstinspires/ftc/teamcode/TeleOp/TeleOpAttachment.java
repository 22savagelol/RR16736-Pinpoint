package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalIntake;
import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalSlide;
import org.firstinspires.ftc.teamcode.Config.Attachment.HorizontalWrist;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalGrabber;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalSlide;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalTilt;
import org.firstinspires.ftc.teamcode.Config.Attachment.VerticalWrist;
import org.firstinspires.ftc.teamcode.Config.Pose;
import org.firstinspires.ftc.teamcode.Config.Timing;
import org.firstinspires.ftc.teamcode.RR.MecanumDrive;

import java.util.ArrayList;
import java.util.List;

public class TeleOpAttachment {
    double[] time = {0.0,1.0,2.0,3.0,4.0,5.0,6.0};
    double[] loop = {0.0,1.0,2.0,3.0,4.0,5.0,6.0};
    int spot = 0; long currTime; double desiredLoopms;
    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();
    TelemetryPacket packet = new TelemetryPacket();
    MecanumDrive drive; Gamepad gamepad1, gamepad2; Gamepad.RumbleEffect singlePulseRumble, longPulseRumble;
    HorizontalIntake horizontalIntake; HorizontalSlide horizontalSlide; HorizontalWrist horizontalWrist;
    VerticalGrabber verticalGrabber; VerticalSlide verticalSlide; VerticalTilt verticalTilt; VerticalWrist verticalWrist;
    boolean VG, HS, VS, VH = false;
    public TeleOpAttachment(MecanumDrive drive, HorizontalIntake horizontalIntake, HorizontalSlide horizontalSlide, HorizontalWrist horizontalWrist,
                            VerticalGrabber verticalGrabber, VerticalSlide verticalSlide, VerticalTilt verticalTilt, VerticalWrist verticalWrist, Gamepad gamepad1, Gamepad gamepad2){
        this.horizontalIntake = horizontalIntake;
        this.horizontalSlide = horizontalSlide;
        this.horizontalWrist = horizontalWrist;
        this.verticalGrabber = verticalGrabber;
        this.verticalSlide = verticalSlide;
        this.verticalTilt = verticalTilt;
        this.verticalWrist = verticalWrist;

        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        singlePulseRumble = new Gamepad.RumbleEffect.Builder()
                .addStep(.5, .5, 250)
                .build();
        longPulseRumble = new Gamepad.RumbleEffect.Builder()
                .addStep(.5, .5, 125)
                .addStep(0, 0, 125)
                .addStep(.5, .5, 125)
                .build();

        currTime = System.currentTimeMillis();
        desiredLoopms = 250;
    }
    public void verticalSlide(boolean upInput, boolean downInput){
        loop[0] = currTime - time[0];
        if(upInput || downInput){
            if(loop[0] >= desiredLoopms){
                if(upInput){
                    spot += 1;
                    if(spot >= 2){
                        spot = 2;
                    }
                }
                if(downInput){
                    spot -= 1;
                    if(spot <= 0){
                        spot = 0;
                    }
                } if(spot == 0){
                    VS = true;
                    HS = false;
                    runningActions.clear();
                    runningActions.add(new SequentialAction(
                            new InstantAction(() -> horizontalWrist.horizontalWristPose(Pose.horizontalWristHover)),
                            new InstantAction(() -> horizontalSlide.horizontalSlidePose(Pose.horizontalSlideTransfer)),
                            new InstantAction(() -> verticalSlide.verticalSlidePose(Pose.verticalSlideBottom)),
                            new InstantAction(() -> verticalGrabber.verticalGrabberPose(Pose.verticalGrabberOpen)),
                            new InstantAction(() -> verticalWrist.verticalWristPose(Pose.verticalWristTransfer)),
                            new InstantAction(() -> verticalTilt.verticalTiltPose(Pose.verticalTiltTransfer))
                    ));
                } if(spot == 1){
                    runningActions.clear();
                    runningActions.add(new SequentialAction(
                            new InstantAction(() -> verticalWrist.verticalWristPose(Pose.verticalWristBar)),
                            new InstantAction(() -> verticalTilt.verticalTiltPose(Pose.verticalTiltBar))
                    ));
                } if(spot == 2){
                    runningActions.clear();
                    runningActions.add(new SequentialAction(
                            new InstantAction(() -> verticalSlide.verticalSlidePose(Pose.verticalSlideHighBasket)),
                            new InstantAction(() -> verticalWrist.verticalWristPose(Pose.verticalWristBasket)),
                            new InstantAction(() -> verticalTilt.verticalTiltPose(Pose.verticalTiltBasket))
                    ));
                }
                time[0] = currTime;
            }
        }
    }
    public void verticalGrabber(boolean input){
        loop[1] = currTime - time[1];
        if(input){
            if(loop[1] >= desiredLoopms){
                if(!VG){
                    runningActions.add(new SequentialAction(
//                            new InstantAction(() -> verticalGrabber.verticalGrabberPose(Pose.verticalGrabberOpen))
                            verticalGrabber.verticalGrabberAction(Pose.verticalGrabberOpen)
                    ));
                } else {
                    runningActions.add(new SequentialAction(
//                            new InstantAction(() -> verticalGrabber.verticalGrabberPose(Pose.verticalGrabberClose))
                            verticalGrabber.verticalGrabberAction(Pose.verticalGrabberCloseHard)
                    ));
                }
                time[1] = currTime;
                VG = !VG;
            }
        }
    }
    public void horizontalSlide(boolean input){
        loop[2] = currTime - time[2];
        if(input){
            if(loop[2] >= desiredLoopms){
                if(!HS){
                    runningActions.clear();
                    runningActions.add(new SequentialAction(
                            new InstantAction(() -> horizontalIntake.horizontalIntakePose(.75)),
                            new InstantAction(() -> verticalWrist.verticalWristPose(Pose.verticalWristHover)),
                            new InstantAction(() -> horizontalWrist.horizontalWristPose(Pose.horizontalWristHover)),
                            new InstantAction(() -> horizontalSlide.horizontalSlidePose(Pose.horizontalSlideExtend)),
                            new SleepAction(Timing.horizontalSlideExtend),
                            new InstantAction(() -> horizontalWrist.horizontalWristPose(Pose.horizontalWristIntake))
                    ));
                } else {
                    runningActions.clear();
                    runningActions.add(new SequentialAction(
                            new InstantAction(() -> horizontalIntake.horizontalIntakePose(0)),
                            new InstantAction(() -> horizontalWrist.horizontalWristPose(Pose.horizontalWristHover)),
                            new InstantAction(() -> verticalGrabber.verticalGrabberPose(Pose.verticalGrabberOpen)),
                            new InstantAction(() -> verticalTilt.verticalTiltPose(Pose.verticalTiltTransfer)),
                            new InstantAction(() -> horizontalSlide.horizontalSlidePose(Pose.horizontalSlideTransfer)),
                            new SleepAction(Timing.horizontalSlideRetract),
                            verticalWrist.verticalWristAction(Pose.verticalWristTransfer),
                            new SleepAction(.125),
                            new InstantAction(() -> verticalGrabber.verticalGrabberPose(Pose.verticalGrabberClose))
                    ));
                }
                time[2] = currTime;
                HS = !HS;
            }
        }
    }
    public void horizontalIntake(boolean input){
        if(input){
            runningActions.add(new SequentialAction(
                    new InstantAction(() -> horizontalIntake.horizontalIntakePose(-1)),
                    new SleepAction(.5),
                    new InstantAction(() -> horizontalIntake.horizontalIntakePose(0))
            ));
        }
    }
    public void verticalHang(boolean input){
        loop[3] = currTime - time[3];
        if(input){
            if(loop[3] >= desiredLoopms){
                if(!VH) {
                    spot = 0;
                    runningActions.clear();
                    runningActions.add(new SequentialAction(
                            new InstantAction(() -> verticalSlide.verticalSlidePose(100)),
                            new InstantAction(() -> verticalWrist.verticalWristPose(Pose.verticalWristHover)),
                            new InstantAction(() -> verticalTilt.verticalTiltPose(Pose.verticalTiltWall)),
                            new SleepAction(.125),
                            new InstantAction(() -> horizontalWrist.horizontalWristPose(Pose.horizontalWristTuck)),
                            new InstantAction(() -> horizontalSlide.horizontalSlidePose(Pose.horizontalSlideRetract)),
                            new SleepAction(.75),
                            new InstantAction(() -> verticalSlide.verticalSlidePose(Pose.verticalSlideBottom)),
                            new InstantAction(() -> verticalWrist.verticalWristPose(Pose.verticalWristWall)),
                            new InstantAction(() -> verticalGrabber.verticalGrabberPose(Pose.verticalGrabberOpen))
                    ));
                } else {
                    runningActions.clear();
                    runningActions.add(new SequentialAction(
                            new InstantAction(() -> verticalSlide.verticalSlidePose(Pose.verticalSlideBar)),
                            new SleepAction(.5),
                            new InstantAction(() -> verticalGrabber.verticalGrabberPose(Pose.verticalGrabberOpen))
                    ));
                }
                time[3] = currTime;
                VH = !VH;
            }
        }
    }
    public void updateFunction(){
        if(HS) {
            if (horizontalIntake.horizontalColorYellow()) {
                gamepad1.runRumbleEffect(singlePulseRumble);
                runningActions.add(new SequentialAction(
                        new SleepAction(.5),
                        new InstantAction(() -> horizontalIntake.horizontalIntakePose(0)),
                        new InstantAction(() -> horizontalWrist.horizontalWristPose(Pose.horizontalWristHover)),
                        new InstantAction(() -> verticalGrabber.verticalGrabberPose(Pose.verticalGrabberOpen)),
                        new InstantAction(() -> verticalTilt.verticalTiltPose(Pose.verticalTiltTransfer)),
                        new InstantAction(() -> horizontalSlide.horizontalSlidePose(Pose.horizontalSlideTransfer)),
                        new SleepAction(Timing.horizontalSlideRetract),
                        verticalWrist.verticalWristAction(Pose.verticalWristTransfer, Timing.verticalWristTransfer),
                        new InstantAction(() -> verticalGrabber.verticalGrabberPose(Pose.verticalGrabberClose))
                ));
                HS = !HS;
            } if(horizontalIntake.horizontalColorBlue()){
                runningActions.add(new SequentialAction(
                        new InstantAction(() -> horizontalIntake.horizontalIntakePose(-1)),
                        new SleepAction(.25),
                        new InstantAction(() -> horizontalIntake.horizontalIntakePose(.75))
                ));
            } if(horizontalIntake.horizontalColorBlue()) {
                runningActions.add(new SequentialAction(
                        new InstantAction(() -> horizontalIntake.horizontalIntakePose(-1)),
                        new SleepAction(.25),
                        new InstantAction(() -> horizontalIntake.horizontalIntakePose(.75))
                ));
            }
        }
    }
    public void updateAction(){
        List<Action> newActions = new ArrayList<>();
        for (Action action : runningActions) {
            action.preview(packet.fieldOverlay());
            if (action.run(packet)) {
                newActions.add(action);
            }
        }
        runningActions = newActions;
        dash.sendTelemetryPacket(packet);
        currTime = System.currentTimeMillis();
    }
}
