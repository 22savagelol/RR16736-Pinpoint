package org.firstinspires.ftc.teamcode.Config.Attachment;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Config.Port;
import org.firstinspires.ftc.teamcode.Config.Pose;

public class VerticalTilt {
    ServoImplEx verticalTiltServo;
    public VerticalTilt(HardwareMap hardwareMap){
        verticalTiltServo = hardwareMap.get(ServoImplEx.class, Port.VERTICAL_TILT);
        verticalTiltPose(Pose.verticalTiltTransfer);
    }
    private class VerticalTiltAction implements Action{
        double pose;
        public VerticalTiltAction(double pose){
            this.pose = pose;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            verticalTiltPose(pose);
            return false;
        }
    }
    public Action verticalTiltAction(double pose){return new VerticalTiltAction(pose);}
    public void verticalTiltPose(double pose){
        verticalTiltServo.setPosition(pose);
    }
}
