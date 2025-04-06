package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.RR.MecanumDrive;
import org.firstinspires.ftc.teamcode.RR.PinpointDrive;

public class DriveTrain {
    DcMotorEx leftFront, rightFront, leftBack, rightBack; PinpointDrive drive;
    double botHeading, slow; double THROTTLE = .8;
    boolean field;
    public DriveTrain(DcMotorEx leftFront, DcMotorEx rightFront, DcMotorEx leftBack, DcMotorEx rightBack, PinpointDrive drive, boolean field) {
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.leftBack = leftBack;
        this.rightBack = rightBack;
        this.drive = drive;
        this.field = field;
    }
    public void drive(double leftStickY, double leftStickX, double rightStickX, double precise, double startSpeed) {
        if(field) {
            double y = -leftStickY; // Remember, Y stick value is reversed
            double x = leftStickX;
            double rx = rightStickX;

            drive.localizer.update();
            double botHeading = drive.getLastPinpointPose().heading.toDouble();

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            slow = precise * (1 - startSpeed) + startSpeed;

            leftFront.setPower(frontLeftPower * slow);
            leftBack.setPower(backLeftPower * slow);
            rightFront.setPower(frontRightPower * slow);
            rightBack.setPower(backRightPower * slow);
        } else {
            double y = leftStickY;
            double x = -leftStickX;
            double rx = -rightStickX;

            slow = precise * (1 - startSpeed) + startSpeed;

            x = x * THROTTLE;
            y = y * THROTTLE;
            rx = rx * THROTTLE;

            double frontLeft = x + y - rx;
            double frontRight = -x + y + rx;
            double backLeft = -x + y - rx;
            double backRight = x + y + rx;

            rightFront.setPower(frontRight * slow);
            leftFront.setPower(frontLeft * slow);
            rightBack.setPower(backRight * slow);
            leftBack.setPower(backLeft * slow);
        }
    }
}
