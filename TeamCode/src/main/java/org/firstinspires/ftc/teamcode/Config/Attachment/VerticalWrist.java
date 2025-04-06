package org.firstinspires.ftc.teamcode.Config.Attachment;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Config.Port;

public class VerticalWrist {
    ServoImplEx verticalWristLeft, verticalWristRight;
    AnalogInput leftSensor, RightSensor; double distance;
    public VerticalWrist(HardwareMap hardwareMap){
        verticalWristLeft = hardwareMap.get(ServoImplEx.class, Port.VERTICAL_WRIST_LEFT);
        verticalWristRight = hardwareMap.get(ServoImplEx.class, Port.VERTICAL_WRIST_RIGHT);

        leftSensor = hardwareMap.get(AnalogInput.class, Port.VERTICAL_SENSOR_LEFT);
        verticalWristPose(.5);
    }
    private class VerticalWristAction implements Action{
        double pose, targetPose, distance;
        public VerticalWristAction(double pose, double targetPose){
            this.pose = pose;
            this.targetPose = targetPose;
        }
        public VerticalWristAction(double pose){
            this.pose = pose;
            this.targetPose = 0;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            verticalWristPose(pose);
            distance = leftSensor.getVoltage() / 3.3 * 360;
            if(targetPose == 0){
                return false;
            } else {
                return (int) distance != targetPose;
            }
        }
    }
    public Action verticalWristAction(double pose, double targetPose){return new VerticalWristAction(pose, targetPose);}
    public Action verticalWristAction(double pose){return new VerticalWristAction(pose);}
    public void verticalWristPose(double pose){
        verticalWristLeft.setPosition(pose);
        verticalWristRight.setPosition(1-pose);
    }
    public double verticalWristSensor(){
        distance = leftSensor.getVoltage() / 3.3 * 360;
        return distance;
    }
}
