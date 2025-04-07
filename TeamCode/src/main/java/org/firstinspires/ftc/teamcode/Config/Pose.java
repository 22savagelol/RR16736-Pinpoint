package org.firstinspires.ftc.teamcode.Config;

public class Pose {
    public static double adriveMultiple = 1; //drive default movement
    public static double driveVelocity = (50* adriveMultiple); //default is 50
    public static double driveMinAccel =  -30; //default is -30
    public static double driveMaxAccel = (50* adriveMultiple); //default is 50
    public static double driveMinAngle = Math.PI * adriveMultiple; //default is Math.PI
    public static double driveMaxAngle = Math.PI * adriveMultiple; //default is Math.PI

    //vertical slide
    public static double verticalSlideHighBasket = 788;
    public static double verticalSlideBottom = 0;
    public static double verticalSlideBar = 210;
    public static double verticalVelocity = 5000;

    //vertical tilt
    public static double verticalTiltTransfer = .27;
    public static double verticalTiltWall = .52;
    public static double verticalTiltBasket = .56;
    public static double verticalTiltBar = .82;

    //vertical wrist
    public static double verticalWristTransfer = .71;
    public static double verticalWristHover = .65;
    public static double verticalWristWall = .69;
    public static double verticalWristBasket = .17;
    public static double verticalWristBar = .35;

    //vertical grabber
    public static double verticalGrabberClose = .41;
    public static double verticalGrabberCloseHard = .39;
    public static double verticalGrabberOpen = .66;

    //horizontal slide
    public static double horizontalSlideRetract = .23;
    public static double horizontalSlideExtend = .7; //17  inches
    public static double horizontalSlideTransfer = .35;  // 7.5 inches

    //horizontal wrist
    public static double horizontalWristIntake = .95;
    public static double horizontalWristHover = .82;
    public static double horizontalWristUp = .54;
    public static double horizontalWristTuck = .32;


    //global variable
    public static boolean blue = false;
    public static boolean red = false;

    public static double[] yellowThreshold = {1000, 200, 1000};
    public static double[] redThreshold = {700, 170, 300};
    public static double[] blueThreshold = {1200, 2900, 1900};
    public static double[] nonThreshold = {1300, 2100, 1800};
}
