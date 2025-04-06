package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Config.Port;

@TeleOp(name = "ServoTest", group = "test")
public class ServoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ServoImplEx leftServo = hardwareMap.get(ServoImplEx.class, Port.HORIZONTAL_SLIDE_LEFT);
        ServoImplEx rightServo = hardwareMap.get(ServoImplEx.class, Port.HORIZONTAL_SLIDE_RIGHT);

        AnalogInput leftSensor = hardwareMap.get(AnalogInput.class, Port.VERTICAL_SENSOR_LEFT);
        AnalogInput rightSensor = hardwareMap.get(AnalogInput.class, Port.VERTICAL_SENSOR_RIGHT);

        double pose = .5; double desireMs = 250;
        long currentTime = System.currentTimeMillis();
        double[] loop = new double[2];
        double[] time = new double[2];

        //wait for the start button to be press
        waitForStart();
        //if the stop button press then stop the robot
        if (isStopRequested()) return;

        while(opModeIsActive()){
            currentTime = System.currentTimeMillis();
            loop[0] = currentTime - time[0];
            loop[1] = currentTime - time[1];
            leftServo.setPosition(pose);
            rightServo.setPosition(1-pose);

            if(gamepad1.dpad_up && loop[0] >= desireMs){
                pose += .01;
                time[0] = currentTime;
                if(pose >= 1){
                    pose = 1;
                }
            }
            if(gamepad1.dpad_down && loop[1] >= desireMs){
                pose -=.01;
                time[1] = currentTime;
                if(pose <= 0){
                    pose = 0;
                }
            }
            double HorizontalLeftPosition = leftSensor.getVoltage() / 3.3 * 360;
            double HorizontalRightPosition = rightSensor.getVoltage() / 3.3 * 360;
            telemetry.addData("Horizontal Left", HorizontalLeftPosition);
            telemetry.addData("Horizontal Right", HorizontalRightPosition);
            telemetry.addData("Pose", pose);
            telemetry.update();
        }
    }
}
