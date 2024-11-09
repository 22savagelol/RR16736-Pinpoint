package org.firstinspires.ftc.teamcode.robotverticalslides;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotverticalslides.HorizontalSlide.HorizontalIntakeActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.HorizontalSlide.HorizontalSlideActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.HorizontalSlide.HorizontalWristActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.VerticalSlide.VerticalGrabberActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.VerticalSlide.VerticalSlideActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.VerticalSlide.VerticalWristActions;

public abstract class HelperActions extends LinearOpMode {
    public final double SPEED = 0.5;

    public static int LEFT = 1;
    public static int RIGHT = 2;
    public static int FORWARDS = 3;
    public static int BACKWARDS = 4;
    public static int LOW = 5;
    public static int MEDIUM = 6;
    public static int HIGH = 7;

    private int speeding = 0;
    private double speed = 0.6;

    /**drive speed configuration**/
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public void changeSpeed(DriveActions driveActions, boolean upOne, boolean downOne, boolean upTwo, boolean downTwo, double scalarMultiple) {
        if (upOne) {
            speeding++;
            if (speeding == 1) {
                speed = speed + 0.1;
            }
        }
        if (downOne) {
            speeding++;
            if (speeding == 1) {
                speed = speed - 0.1;
            }
        }
        if (upTwo) {
            speeding++;
            if (speeding == 1) {
                speed = speed + 0.2;
            }
        }
        if (downTwo) {
            speeding++;
            if (speeding == 1) {
                speed = speed - 0.2;
            }
        }
        if (!upOne && !downOne && !upTwo && !downTwo) {
            speeding = 0;
        }
        if (speed < 0) {
            speed = 0;
        }
        if (speed > 1.0) {
            speed = 1.0;
        }
        double speedLeft = 1.0 - speed;
        driveActions.setSpeed(speed + (scalarMultiple * speedLeft));
        telemetry.addData("speed: ", speed);
    }

    double prevSpeed;
    boolean low = false;
    boolean prevToggle = false;
    double lowSpeed = 0.35;
    public void toggleSpeed(boolean toggle) {
        if (toggle && !prevToggle) {
            low = !low;
            if (low) {
                prevSpeed = speed;
                speed = lowSpeed;
            } else {
                lowSpeed = speed;
                speed = prevSpeed;
            }
        }
         prevToggle = toggle;
    }

     /**slide configuration?**/
    boolean wasOverrideSlide = true;
    boolean overrideSlide = true;
    public void updateExchangeAssembly(VerticalGrabberActions grabber, VerticalWristActions verticalWrist, HorizontalWristActions horizontalWrist, HorizontalSlideActions horizontalSlide, VerticalSlideActions verticalSlide) {
        //tells the vertical wrist when the grabber is closed
        verticalWrist.setGrabberClosed(grabber.isClose());
        //tells the vertical wrist when the slide is up
        verticalWrist.setSlideUp(verticalSlide.getSlidePosition() < -1000);
        //tells the horizontal slide to stop and let the horizontal wrist flip up or flip down when going in or out
        overrideSlide = horizontalSlide.getSlidePosition() < 325;
        horizontalWrist.setIsSlideIn(overrideSlide);
        overrideSlide(horizontalSlide);
        wasOverrideSlide = overrideSlide;
    }
    double startTime = 0;
    public void overrideSlide(HorizontalSlideActions horizontalSlide) {
        if (overrideSlide && !wasOverrideSlide) {
            startTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() < startTime + 420) {
            horizontalSlide.setOverride(true);
        } else {
            horizontalSlide.setOverride(false);
        }
    }

    public void close(VerticalGrabberActions verticalGrabber, VerticalWristActions verticalWrist, VerticalSlideActions verticalSlide, HorizontalWristActions horizontalWrist, HorizontalSlideActions horizontalSlide) {
        verticalGrabber.close();
        verticalWrist.backward();
        verticalSlide.setSlidePosition(0, 1000);

        horizontalWrist.backward();
        horizontalSlide.setSlidePosition(0, 1000);
        sleep(500);
    }
    double placeState = 0;
    double startTimePlace = 0;
    public void placeSample(VerticalGrabberActions verticalGrabber, VerticalWristActions verticalWrist, VerticalSlideActions verticalSlide) {
        if (placeState == 0) {
            verticalGrabber.close();
            startTimePlace = System.currentTimeMillis();
            placeState = 1;
        } else if (placeState == 1 && System.currentTimeMillis() > startTimePlace + 420) {
            placeState = 2;
            verticalWrist.autoFlipForwardDown();
            verticalSlide.setSlidePosition(-700, 2000);
        }
    }
}