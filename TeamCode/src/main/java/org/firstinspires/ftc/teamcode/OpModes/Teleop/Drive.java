package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;
import org.firstinspires.ftc.teamcode.Libs.ClawClass;
import org.firstinspires.ftc.teamcode.Libs.DualSlideClass;
import org.firstinspires.ftc.teamcode.Libs.MecanumDriveClass;

@TeleOp (name="Drive", group="DrivePrograms")
//@Disabled
public class Drive extends OpMode {
    //Actuators that will be used throughout the program
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;

    public DcMotor frontSlide;
    public DcMotor backSlide;

    public Servo clawMount;
    public Servo clawServo;

    //Sensors that will be used throughout the program
    //<...>

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

        frontSlide = hardwareMap.get(DcMotor.class, RobotConstants.FRONT_SLIDE_MOTOR);
        backSlide = hardwareMap.get(DcMotor.class, RobotConstants.BACK_SLIDE_MOTOR);

        clawMount = hardwareMap.get(Servo.class, RobotConstants.CLAW_MOUNT);
        clawServo = hardwareMap.get(Servo.class, RobotConstants.CLAW_SERVO);

        //Initializes the sensors

        //Reverses the direction of the left wheels
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //Sets motors to break mode
        frontSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Initialized the classes
        drive = new MecanumDriveClass(leftFront, rightFront, leftBack, rightBack);
        slide = new DualSlideClass(backSlide, frontSlide);
        claw = new ClawClass(clawMount, clawServo);
    }

    @Override
    public void start() {
        drive.stop();
        clawMount.setPosition(1);
    }

    @Override
    public void loop() {
        drive.mecanumDriveControl(gamepad1);
        slide.viperSlideControl(gamepad2);
        claw.clawControl(gamepad2);
        clawMount.setPosition(1);
    }

    @Override
    public void stop() {
        drive.stop();
    }
}
