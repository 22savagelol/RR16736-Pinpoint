package org.firstinspires.ftc.teamcode.Config.Attachment;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Config.Port;
import org.firstinspires.ftc.teamcode.Config.Pose;

public class HorizontalIntake {
    DcMotorEx horizontalIntakeMotor; ColorSensor horizontalIntakeSensor;
    public HorizontalIntake(HardwareMap hardwareMap){
        horizontalIntakeMotor = hardwareMap.get(DcMotorEx.class, Port.HORIZONTAL_INTAKE);
        horizontalIntakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        horizontalIntakeSensor = hardwareMap.get(ColorSensor.class, Port.COLOR_SENSOR);
    }
    private class HorizontalIntakeAction implements Action {
        double pose;
        public HorizontalIntakeAction(double pose){
            this.pose = pose;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            horizontalIntakeMotor.setPower(pose);
            return false;
        }
    }
    public Action horizontalIntakeAction(double pose){return new HorizontalIntakeAction(pose);}
    public void horizontalIntakePose(double pose){
        horizontalIntakeMotor.setPower(pose);
    }
    public boolean horizontalColorYellow(){
        return horizontalIntakeSensor.red() >= Pose.yellowThreshold[0]
                && horizontalIntakeSensor.blue() >= Pose.yellowThreshold[1]
                && horizontalIntakeSensor.green() >= Pose.yellowThreshold[2];
    }
    public boolean horizontalColorBlue(){
        return horizontalIntakeSensor.red() >= Pose.blueThreshold[0]
                && horizontalIntakeSensor.blue() >= Pose.blueThreshold[1]
                && horizontalIntakeSensor.green() >= Pose.blueThreshold[2];
    }
    public double[] horizontalColor(){
        return new double[]{horizontalIntakeSensor.red(), horizontalIntakeSensor.blue(), horizontalIntakeSensor.green()};
    }
}
