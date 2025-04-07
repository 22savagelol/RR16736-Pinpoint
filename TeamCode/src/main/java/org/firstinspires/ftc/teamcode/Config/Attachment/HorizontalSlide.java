package org.firstinspires.ftc.teamcode.Config.Attachment;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Config.Port;
import org.firstinspires.ftc.teamcode.Config.Pose;

public class HorizontalSlide {
    ServoImplEx horizontalSlideLeft, horizontalSlideRight;
    AnalogInput leftSensor, rightSensor; double distance;
    public HorizontalSlide(HardwareMap hardwareMap){
        horizontalSlideLeft = hardwareMap.get(ServoImplEx.class, Port.HORIZONTAL_SLIDE_LEFT);
        horizontalSlideRight = hardwareMap.get(ServoImplEx.class, Port.HORIZONTAL_SLIDE_RIGHT);
//        leftSensor = hardwareMap.get(AnalogInput.class, Port.HORIZONTAL_SENSOR_LEFT);
//        rightSensor = hardwareMap.get(AnalogInput.class, Port.HORIZONTAL_SENSOR_RIGHT);
        horizontalSlidePose(Pose.horizontalSlideTransfer);
    }
    private class HorizontalSlideAction implements Action{
        double pose, targetPose, distance;
        public HorizontalSlideAction(double pose){
            this.pose = pose;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            horizontalSlidePose(pose);
            distance = rightSensor.getVoltage() / 3.3 * 360;
            return (int)distance != targetPose;
        }
    }
    private class HorizontalSlideDistanceAction implements Action{
        double pose;
        public HorizontalSlideDistanceAction(double pose){
            this.pose = pose;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setDistance(pose);
            return false;
        }
    }
    public Action horizontalSlideAction(double pose){return new HorizontalSlideAction(pose);}
    public Action horizontalSlideDistanceAction(double pose){return new HorizontalSlideDistanceAction(pose);}
    public void horizontalSlidePose(double pose){
        horizontalSlideLeft.setPosition(pose);
        horizontalSlideRight.setPosition(1-pose);
    }
    public void setDistance(double distance){
        double linkageLength = 27.5;
        //convert from distance to radians
        double theta = Math.acos(1 - (Math.pow(distance, 2) / (2 * Math.pow(linkageLength, 2))));
        //convert from radians to servo position
        double pos = (0.75 / (2.23954 - 0.32875)) * (theta - 2.23954) + 1;
        horizontalSlideLeft.setPosition(pos);
        horizontalSlideRight.setPosition(1-(pos));
    }
//    public double horizontalSlideSensor(){
//        distance = rightSensor.getVoltage() / 3.3 * 360;
//        return distance;
//    }
}
