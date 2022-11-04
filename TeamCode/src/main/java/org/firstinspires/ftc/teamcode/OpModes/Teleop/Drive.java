package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;
import org.firstinspires.ftc.teamcode.Libs.MecanumDriveClass;

@TeleOp (name="Drive", group="DrivePrograms")
//@Disabled
public class Drive extends OpMode {
    //Actuators that will be used throughout the program
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;

    //Sensors that will be used throughout the program
    //<...>

    //Classes that will be used throughout the program
    public MecanumDriveClass drive;

    @Override
    public void init() {
        //Initializes the actuators
        leftFront = hardwareMap.get(DcMotor.class, RobotConstants.LEFT_FRONT_MOTOR);
        rightFront = hardwareMap.get(DcMotor.class, RobotConstants.RIGHT_FRONT_MOTOR);
        leftFront = hardwareMap.get(DcMotor.class, RobotConstants.LEFT_BACK_MOTOR);
        rightFront = hardwareMap.get(DcMotor.class, RobotConstants.RIGHT_BACK_MOTOR);

        //Initializes the sensors

        //Reverses the direction of the left wheels
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //Initialized the classes
        drive = new MecanumDriveClass(leftFront, rightFront, leftBack, rightBack);
    }

    @Override
    public void start() {
        drive.stop();
    }

    @Override
    public void loop() {
        drive.mecanumDriveControl(gamepad1);
    }

    @Override
    public void stop() {
        drive.stop();
    }
}
