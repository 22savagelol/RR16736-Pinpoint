package org.firstinspires.ftc.teamcode.Config.Attachment;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Config.Port;
import org.firstinspires.ftc.teamcode.Config.Pose;

public class VerticalSlide {
    DcMotorEx verticalSlide1, verticalSlide2;
    public VerticalSlide(HardwareMap hardwareMap){
        verticalSlide1 = hardwareMap.get(DcMotorEx.class, Port.VERTICAL_SLIDE1);
        verticalSlide1.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalSlide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlide1.setVelocity(Pose.verticalVelocity);

        verticalSlide2 = hardwareMap.get(DcMotorEx.class, Port.VERTICAL_SLIDE2);
        verticalSlide2.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalSlide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        verticalSlidePose(0);
        verticalSlide1.setVelocity(Pose.verticalVelocity);
        verticalSlide2.setVelocity(Pose.verticalVelocity);

        verticalSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        verticalSlidePose(0);
        verticalSlide1.setVelocity(Pose.verticalVelocity);
        verticalSlide2.setVelocity(Pose.verticalVelocity);

    }
    private class VerticalSlideAction implements Action{
        double pose;
        public VerticalSlideAction(double pose){
            this.pose = pose;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            verticalSlidePose(pose);
            return false;
        }
    }
    public Action verticalSlideAction(double pose){return new VerticalSlideAction(pose);}
    public void verticalSlidePose(double pose){
        if(pose == 0){
            verticalSlide1.setVelocity(1000);
            verticalSlide2.setVelocity(1000);
        } else {
            verticalSlide1.setVelocity(5000);
            verticalSlide2.setVelocity(5000);
        }
        verticalSlide1.setTargetPosition((int)pose);
        verticalSlide2.setTargetPosition((int)pose);
    }
}
