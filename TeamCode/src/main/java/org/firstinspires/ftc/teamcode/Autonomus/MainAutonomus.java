package org.firstinspires.ftc.teamcode.Autonomus;

// RR-specific imports
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Rotation;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;

//Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//Team code imports
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.PinpointDrive;
import org.firstinspires.ftc.teamcode.robotverticalslides.DriveActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.HorizontalSlide.HorizontalSlideActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.HorizontalSlide.HorizontalWristActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.HorizontalSlide.HorizontalIntakeActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.VerticalSlide.VerticalGrabberActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.VerticalSlide.VerticalSlideActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.VerticalSlide.VerticalWristActions;
import org.firstinspires.ftc.teamcode.robotverticalslides.constants.ConfigConstants;
import org.firstinspires.ftc.teamcode.Autonomus.Configuration;

//Java code imports
import java.util.Arrays;


@Config
@Autonomous(name = "MainAuto", group = "Autonomous")
public class MainAutonomus extends LinearOpMode {
    private VerticalSlideRR verticalSlideRR = null;
    private HorizontalSlideRR horizontalSlideRR = null;
    private VerticalGrabberRR verticalGrabberRR = null;
    private VerticalWristRR verticalWristRR = null;
    private HorizontalWristRR horizontalWristRR = null;

    public class VerticalSlideRR{
        public DcMotorEx verticalSlide1 = null;
        public DcMotorEx verticalSlide2 = null;

        public VerticalSlideRR(HardwareMap hardwareMap){
            verticalSlide1 = hardwareMap.get(DcMotorEx.class, ConfigConstants.VERTICAL_SLIDE1);
            verticalSlide1.setDirection(DcMotorSimple.Direction.REVERSE);
            verticalSlide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            verticalSlide1.setTargetPosition(0);
            verticalSlide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            verticalSlide2 = hardwareMap.get(DcMotorEx.class, ConfigConstants.VERTICAL_SLIDE2);
            verticalSlide2.setDirection(DcMotorSimple.Direction.FORWARD);
            verticalSlide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            verticalSlide2.setTargetPosition(0);
            verticalSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        public class Liftup implements Action{
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                int position = -480;
                double velocity = 1800;
                if(!initialized){
                        verticalSlide1.setTargetPosition(position);
                        verticalSlide1.setVelocity(velocity);
                        verticalSlide2.setTargetPosition(-position);
                        verticalSlide2.setVelocity(-velocity);
                    initialized = true;
                }
                double currentPosition = verticalSlide1.getCurrentPosition();
                if (currentPosition > position){
                    return true;
                } else {
                    return false;

                }
            }
        }
        public Action liftUp(){
            return new Liftup();
        }
        public class SetDown implements Action{
            private boolean initialized = false;
            public boolean run(@NonNull TelemetryPacket packet){
                int position = 0;
                double velocity = 1800;
                if(!initialized){
                    verticalSlide1.setTargetPosition(position);
                    verticalSlide1.setVelocity(velocity);
                    verticalSlide2.setTargetPosition(-position);
                    verticalSlide2.setVelocity(-velocity);
                    initialized = true;
                }
                double currentPosition = verticalSlide1.getCurrentPosition();
                if (currentPosition > position){
                    return true;
                } else {
                    return false;

                }
            }
        }
        public Action setDown(){
            return new SetDown();
        }
    }
    //make a class for horizontal Slide
    public class HorizontalSlideRR{
        public DcMotorEx HorizontalSlide2 = null;
        private Telemetry telemetry;
        public HorizontalSlideRR(HardwareMap hardwareMap, Telemetry telemetry) {
            this.telemetry = telemetry;
            HorizontalSlide2 = hardwareMap.get(DcMotorEx.class, ConfigConstants.HORIZONTAL_SLIDE2);
            HorizontalSlide2.setDirection(DcMotorSimple.Direction.FORWARD);
            HorizontalSlide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            HorizontalSlide2.setTargetPosition(0);
            HorizontalSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        private boolean initialized = false;
        public class RetractSlide implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                int position = 0;
                double velocity = 0;
                if(!initialized){
                    HorizontalSlide2.setTargetPosition(position);
                    HorizontalSlide2.setVelocity(velocity);
                    initialized = true;
                }
                double currentPosition = HorizontalSlide2.getCurrentPosition();
                if(currentPosition > position){
                    return true;
                }else {
                    return false;
                }
            }
        }
        public Action retractSlide(){
            return new RetractSlide();
        }
    }
    //add a class for the vertical grabber
    public class VerticalGrabberRR{
        public Servo verticalGrabberServo;
        private Telemetry telemetry;
        private HardwareMap hardwareMap;
        private double open = Configuration.open;
        private double close = Configuration.close;
        public VerticalGrabberRR(Telemetry opModeTelemetry, HardwareMap opModeHardware) {
            this.telemetry = opModeTelemetry;
            this.hardwareMap = opModeHardware;
            verticalGrabberServo = hardwareMap.get(Servo.class, ConfigConstants.VERTICAL_GRABBER);
            verticalGrabberServo.setPosition(1.0);
        }
        public class CloseGrabber implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                verticalGrabberServo.setPosition(close);
                return false;
            }
        }
        public Action closeGrabber(){
            return new CloseGrabber();
        }
        public class OpenGrabber implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                verticalGrabberServo.setPosition(open);
                return false;
            }
        }
        public Action openGrabber(){
            return new OpenGrabber();
        }
    }
    //add Vertical Wrist Class
    public class VerticalWristRR{
        public Servo verticalWristServo;
        private Telemetry telemetry;
        private HardwareMap hardwareMap;
        double forwardUp = Configuration.forwardUp;
        double forwardDown = Configuration.forwardDown;
        double backwardPos = Configuration.backwardPos;
        public VerticalWristRR(Telemetry opModeTelemetry, HardwareMap opModeHardware) {
            this.telemetry = opModeTelemetry;
            this.hardwareMap = opModeHardware;
            verticalWristServo = hardwareMap.get(Servo.class, ConfigConstants.VERTICAL_WRIST);
            verticalWristServo.setPosition(backwardPos);
        }
        public class PlaceBasket implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                verticalWristServo.setPosition(forwardUp);
                return false;
            }
        }
        public Action placeBasket(){
            return new PlaceBasket();
        }
        public class WallButter implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                verticalWristServo.setPosition(forwardDown);
                return false;
            }
        }
        public Action wallButter(){
            return new WallButter();
        }
        public class TakeButter implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                verticalWristServo.setPosition(backwardPos);
                return false;
            }
        }
        public Action takeButter(){
            return new TakeButter();
        }
    }
    public class HorizontalWristRR{
        public Servo horizontalWristServo;
        private Telemetry telemetry;
        private HardwareMap hardwareMap;
        private double backwardPosIn = Configuration.backwardPosIn;
        private double backwardPosOut = Configuration.backwardPosOut;
        private double forwardPosOut = Configuration.forwardPosOut;
        public HorizontalWristRR(Telemetry opModeTelemetry, HardwareMap opModeHardware) {
            this.telemetry = opModeTelemetry;
            this.hardwareMap = opModeHardware;
            horizontalWristServo = hardwareMap.get(Servo.class, ConfigConstants.HORIZONTAL_WRIST);
            horizontalWristServo.setPosition(backwardPosIn);
        }
        public class GrabButter implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                horizontalWristServo.setPosition(forwardPosOut);
                return false;
            }
        }
        public Action grabButter(){
            return new GrabButter();
        }
        public class InRobot implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                horizontalWristServo.setPosition(backwardPosIn);
                return false;
            }
        }
        public Action inRobot(){
            return new InRobot();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //instantiate the robot to a particular pose.
        verticalSlideRR = new VerticalSlideRR(hardwareMap);
        horizontalSlideRR = new HorizontalSlideRR(hardwareMap, telemetry);
        verticalGrabberRR = new VerticalGrabberRR(telemetry, hardwareMap);
        verticalWristRR = new VerticalWristRR(telemetry, hardwareMap);
        horizontalWristRR = new HorizontalWristRR(telemetry, hardwareMap);


        //todo find the correct initial position and put it below
        Pose2d initialPose = new Pose2d(0,0, Math.toRadians(90));
        Vector2d vector2d = new Vector2d(0,0);
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);
        VelConstraint pushBlockVelOverride = new TranslationalVelConstraint(30);
        AccelConstraint pushBlockAccelOverride = new ProfileAccelConstraint(-10, 25);

        VelConstraint parkVelOverride = new TranslationalVelConstraint(60);
        VelConstraint parkAngularOverride = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(60),
                new AngularVelConstraint(Math.toRadians(90))
        ));
        AccelConstraint parkAccelOverride = new ProfileAccelConstraint(-10, 10);



        TrajectoryActionBuilder park = drive.actionBuilder(initialPose)
                .waitSeconds(.25)
                .strafeTo(new Vector2d(-10, 29), pushBlockVelOverride, pushBlockAccelOverride)
                .afterDisp(0, verticalGrabberRR.openGrabber())
                .waitSeconds(.2)
                .afterDisp(2, verticalSlideRR.setDown())
                .afterDisp(2, verticalWristRR.takeButter())
                .afterDisp(4, verticalGrabberRR.closeGrabber())
                .strafeTo(new Vector2d(26, 25), parkVelOverride, parkAccelOverride)
                .strafeTo(new Vector2d(27, 50), parkVelOverride, parkAccelOverride)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(38, 65,Math.toRadians(-90)),(-1)*Math.toRadians(90), parkAngularOverride, parkAccelOverride)
                .strafeTo(new Vector2d(38, 10), parkVelOverride, parkAccelOverride)
                .strafeTo(new Vector2d(38, 65), parkVelOverride, parkAccelOverride)
                .strafeTo(new Vector2d( 48, 65), parkVelOverride, parkAccelOverride)
                .strafeTo(new Vector2d(48, 10), parkVelOverride, parkAccelOverride)
                .strafeTo(new Vector2d(48, 65), parkVelOverride, parkAccelOverride)
                .strafeTo(new Vector2d(58, 65), parkVelOverride, parkAccelOverride)
                .strafeTo(new Vector2d(58, 10), parkVelOverride, parkAccelOverride);


        TrajectoryActionBuilder basket = drive.actionBuilder(initialPose)
                .waitSeconds(.25)
                .strafeTo(new Vector2d(10, 29), pushBlockVelOverride, pushBlockAccelOverride)
                .afterDisp(0, verticalGrabberRR.openGrabber())
                .waitSeconds(.2)
                .afterDisp(2, verticalSlideRR.setDown())
                .afterDisp(2, verticalWristRR.takeButter())
                .afterDisp(4, verticalGrabberRR.closeGrabber())
                .strafeTo(new Vector2d(0, 5));

        //initialize the robot
        Actions.runBlocking(
                new SequentialAction(
                        verticalGrabberRR.closeGrabber(),
                        verticalWristRR.takeButter(),
                        horizontalSlideRR.retractSlide(),
                        horizontalWristRR.inRobot()
                )
        );

        //wait for the start button to be press
        waitForStart();
        //if stop button is press, automatically stop
        if (isStopRequested()) return;

        //run the chosen action blocking
        Actions.runBlocking(
            new SequentialAction(
                    verticalSlideRR.liftUp(),
                    verticalWristRR.wallButter(),
                    park.build()
            )
        );
    }
}



