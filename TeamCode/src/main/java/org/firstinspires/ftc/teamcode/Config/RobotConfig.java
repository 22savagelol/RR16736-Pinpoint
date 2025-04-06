package org.firstinspires.ftc.teamcode.Config;

public class RobotConfig {
    public double[] config = new double[19];
    public static int selection = 1;

     public double setConfig(int pick){
        if(selection == 1){
            //Second Robot
            config[0] = 1; //inPerTick
            config[1] = 0.7028368265746822; //lateralInPerTick
            config[2] = 15.5; //trackWidthTicks
            config[3] = 1.789541669118318; //kS
            config[4] = 0.1224587608268715; //kV
            config[5] = 0.001; //kA
            config[6] = Pose.driveVelocity; //maxWheelVel
            config[7] = Pose.driveMinAccel; //minProfileAccel
            config[8] = Pose.driveMaxAccel; //maxProfileAccel
            config[9] = Pose.driveMinAngle/2; //maxAngVel
            config[10] = Pose.driveMaxAngle; //maxAngAccel
            config[11] = 1; //axialGain
            config[12] = 1; //lateralGain
            config[13] = 1; //headingGain
            config[14] = 0.0; //axialVelGain
            config[15] = 0.0; //lateralVelGain
            config[16] = 0.0; //headingVelGain
            config[17] = .375; //parYTicks
            config[18] = -.0675; //perpXTicks
            return config[pick];
        } else {
            //First Robot
            config[0] = .001978956002; //inPerTick
            config[1] = 0.7665571658951872; //lateralInPerTick
            config[2] = 12.909184083483952; //trackWidthTicks
            config[3] = 2.41630800809041; //kS
            config[4] = 0.12578307198549346; //kV
            config[5] = 0.001; //kA
            config[6] = Pose.driveVelocity; //maxWheelVel
            config[7] = Pose.driveMinAccel; //minProfileAccel
            config[8] = Pose.driveMaxAccel; //maxProfileAccel
            config[9] = Pose.driveMinAngle; //maxAngVel
            config[10] = Pose.driveMaxAngle; //maxAngAccel
            config[11] = 1.5; //axialGain
            config[12] = 8; //lateralGain
            config[13] = 2; //headingGain
            config[14] = 0.0; //axialVelGain
            config[15] = 0.0; //lateralVelGain
            config[16] = 0.0; //headingVelGain
            config[17] = 80/25.4; //parYTicks todo run angular ramp logger to figure this out, it not based on inches
            config[18] = 81.0/25.4; //perpXTicks
            return config[pick];
        }
    }

}
