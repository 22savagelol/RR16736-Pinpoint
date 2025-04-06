package org.firstinspires.ftc.teamcode.RR;

import com.acmerobotics.roadrunner.Pose2d;

public class GlobalVariables {

    /*
        annotation feb 10 2025 wyatt evans
        Set up variables to be updated at the end of Roadrunner to allow TeleOp to maintain its position
        autoStarted is a failsafe for in case if robot crashes between auton and TeleOp
     */

    public static Pose2d currentPose = new Pose2d(0,0,0);
    public static boolean autoStarted = false;
    public static double X = 0;
    public static double Y = 0;
    public static double Roll = 0;
    public static boolean driveDisable = false;
    public static boolean cancel = false;

}
