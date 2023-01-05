package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;
import org.firstinspires.ftc.teamcode.Libs.ClawClass;
import org.firstinspires.ftc.teamcode.Libs.DualSlideClass;
import org.firstinspires.ftc.teamcode.Libs.MecanumDriveClass;

public class PresetDrive extends OpMode {
    //Actuators that will be used throughout the program
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;

    public DcMotor leftSlide;
    public DcMotor rightSlide;

    public Servo clawMount;
    public Servo clawServo;

    //Classes that will be used throughout the program
    public MecanumDriveClass drive;
    public DualSlideClass slide;
    public ClawClass claw;

    @Override
    public void init() {
        //Initializes the actuators
        leftFront = hardwareMap.get(DcMotor.class, RobotConstants.LEFT_FRONT_MOTOR);
        rightFront = hardwareMap.get(DcMotor.class, RobotConstants.RIGHT_FRONT_MOTOR);
        leftBack = hardwareMap.get(DcMotor.class, RobotConstants.LEFT_BACK_MOTOR);
        rightBack = hardwareMap.get(DcMotor.class, RobotConstants.RIGHT_BACK_MOTOR);

        leftSlide = hardwareMap.get(DcMotor.class, RobotConstants.FRONT_SLIDE_MOTOR);
        rightSlide = hardwareMap.get(DcMotor.class, RobotConstants.BACK_SLIDE_MOTOR);

        clawMount = hardwareMap.get(Servo.class, RobotConstants.CLAW_MOUNT);
        clawServo = hardwareMap.get(Servo.class, RobotConstants.CLAW_SERVO);

        //Initializes the sensors

        //Reverses the direction of the left wheels
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //Sets motors to break mode
        leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Initialized the classes
        drive = new MecanumDriveClass(leftFront, rightFront, leftBack, rightBack);
        slide = new DualSlideClass(rightSlide, leftSlide);
        claw = new ClawClass(clawMount, clawServo, ClawClass.ClawRotationPositions.FRONT);
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }
}