package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class MecanumDriveClass {
    /**
     * This class assumes you have the basic mecanum wheel layout as seen below along with the
     * motors moving the robot forward when given positive power values.
     */
    //      __________
    //     |\\      //|
    //     |          |
    //     |          |
    //     |//______\\|

    //Motor objects to control the wheels
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    //Global variables used throughout the class
    private boolean reverseDirection = false;
    private double driveSpeedLimiter = 0.75;

    //Other classes being used throughout the class
    private ToggleClass directionToggle = new ToggleClass();

    /**
     * Constructor: MecanumDriveClass
     * - sets variables to motors being used in the program using this class
     * @param leftFrontMotor - the motor driving the left front wheel
     * @param rightFrontMotor - the motor driving the right front wheel
     * @param leftBackMotor - the motor driving the left back wheel
     * @param rightBackMotor - the motor driving the right back wheel
     */
    public MecanumDriveClass(DcMotor leftFrontMotor, DcMotor rightFrontMotor, DcMotor leftBackMotor, DcMotor rightBackMotor) {
        this.leftFront = leftFrontMotor;
        this.rightFront = rightFrontMotor;
        this.leftBack = leftBackMotor;
        this.rightBack = rightBackMotor;
    }

    /**
     * Method - mecanumDriveControl
     *  A way to drive the robot in Tele-Op
     * @param gamepad - the gamepad input that controls the wheels
     *      ___              ___
     *      ___ ____________ ___
     *  /    ^       (=)      y   \
     *  \ <    >   -    -  x    b  \
     *   \  v   _O_______O_  a    /
     *    \    /           \    /
     *     \_/              \_/
     *
     */
    public void mecanumDriveControl(Gamepad gamepad) {
        //Sets power to each of the wheels depending on the input from the controller
        if(!reverseDirection) {
            leftFront.setPower((-gamepad.left_stick_y + gamepad.left_stick_x + gamepad.right_stick_x) * driveSpeedLimiter);
            rightFront.setPower((-gamepad.left_stick_y - gamepad.left_stick_x + gamepad.right_stick_x)  * driveSpeedLimiter);
            leftBack.setPower((-gamepad.left_stick_y - gamepad.left_stick_x - gamepad.right_stick_x)  * driveSpeedLimiter);
            rightBack.setPower((-gamepad.left_stick_y + gamepad.left_stick_x - gamepad.right_stick_x) * driveSpeedLimiter);
        } else {
            leftFront.setPower((gamepad.left_stick_y - gamepad.left_stick_x + gamepad.right_stick_x) * driveSpeedLimiter);
            rightFront.setPower((gamepad.left_stick_y + gamepad.left_stick_x + gamepad.right_stick_x) * driveSpeedLimiter);
            leftBack.setPower((gamepad.left_stick_y + gamepad.left_stick_x - gamepad.right_stick_x) * driveSpeedLimiter);
            rightBack.setPower((gamepad.left_stick_y - gamepad.left_stick_x - gamepad.right_stick_x) * driveSpeedLimiter);
        }   //end of if(...) - else if(...)

        //Adjusts the variable driveSpeedLimiter to vary the speed of the robot
        if(gamepad.a) {
            driveSpeedLimiter = 1;
        } else if(gamepad.b) {
            driveSpeedLimiter = 0.75;
        } else if(gamepad.x) {
            driveSpeedLimiter = 0.5;
        } else if(gamepad.y) {
            driveSpeedLimiter = 0.25;
        }

        //Uses ToggleClass to flip the state of reverseDirection when down dpad is pressed
        reverseDirection = directionToggle.buttonReleaseToggle(gamepad.dpad_down, false);
    }

    /**
     * Method: stop()
     * - stops all motors from moving
     */
    public void stop() {
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }
}