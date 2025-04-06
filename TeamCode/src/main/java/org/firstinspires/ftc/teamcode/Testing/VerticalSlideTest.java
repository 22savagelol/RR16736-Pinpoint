package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Config.Port;

@TeleOp(name = "VerticalSlideTest", group = "test")
public class VerticalSlideTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx verticalSlideMotor1 = hardwareMap.get(DcMotorEx.class, Port.VERTICAL_SLIDE1);
        DcMotorEx verticalSlideMotor2 = hardwareMap.get(DcMotorEx.class, Port.VERTICAL_SLIDE2);

        verticalSlideMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlideMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        verticalSlideMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalSlideMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //wait for the start button to be press
        waitForStart();
        //if the stop button press then stop the robot
        if (isStopRequested()) return;

        while(opModeIsActive()){
            telemetry.addData("verticalSlideMotor1", verticalSlideMotor1.getCurrentPosition());
            telemetry.addData("verticalSlideMotor2", verticalSlideMotor2.getCurrentPosition());
            telemetry.update();
        }

    }
}
