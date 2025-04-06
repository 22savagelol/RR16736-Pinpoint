package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Config.Port;

@TeleOp(name = "ColorTest", group = "test")
public class ColorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ColorSensor colorSensor = hardwareMap.get(ColorSensor.class, Port.COLOR_SENSOR);

        //wait for the start button to be press
        waitForStart();
        //if the stop button press then stop the robot
        if (isStopRequested()) return;

        while(opModeIsActive()){
            telemetry.addData("Red", colorSensor.red());
            telemetry.addData("Blue", colorSensor.blue());
            telemetry.addData("Green", colorSensor.green());
            telemetry.update();
        }

    }
}
