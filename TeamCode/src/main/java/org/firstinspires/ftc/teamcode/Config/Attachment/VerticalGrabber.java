package org.firstinspires.ftc.teamcode.Config.Attachment;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Config.Port;
import org.firstinspires.ftc.teamcode.Config.Pose;

public class VerticalGrabber {
    ServoImplEx verticalGrabber;
    public VerticalGrabber(HardwareMap hardwareMap){
        verticalGrabber = hardwareMap.get(ServoImplEx.class, Port.VERTICAL_GRABBER);
        verticalGrabberPose(Pose.verticalGrabberClose);
    }
    private class VerticalGrabberAction implements Action{
        double pose;
        public VerticalGrabberAction(double pose){
            this.pose = pose;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            verticalGrabberPose(pose);
            return false;
        }
    }
    public Action verticalGrabberAction(double pose){return new VerticalGrabberAction(pose);}
    public void verticalGrabberPose(double pose){
        verticalGrabber.setPosition(pose);
    }
}
