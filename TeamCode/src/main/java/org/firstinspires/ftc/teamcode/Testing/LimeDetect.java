package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.LimeSweet;

@TeleOp(name = "LimeDetect", group = "test")
public class LimeDetect extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        LimeSweet lime = new LimeSweet(hardwareMap, telemetry, 0);

        //wait for the start button to be press
        waitForStart();
        //if the stop button press then stop the robot
        if (isStopRequested()) return;

        while(opModeIsActive()){
            lime.scanButter();
        }
    }
}
