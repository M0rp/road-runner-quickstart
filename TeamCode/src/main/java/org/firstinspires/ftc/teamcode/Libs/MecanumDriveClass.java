package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

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
            leftFront.setPower((gamepad.left_stick_y - gamepad.left_stick_x - gamepad.right_stick_x) * driveSpeedLimiter);
            rightFront.setPower((gamepad.left_stick_y + gamepad.left_stick_x + gamepad.right_stick_x)  * driveSpeedLimiter);
            leftBack.setPower((gamepad.left_stick_y + gamepad.left_stick_x - gamepad.right_stick_x)  * driveSpeedLimiter);
            rightBack.setPower((gamepad.left_stick_y - gamepad.left_stick_x + gamepad.right_stick_x) * driveSpeedLimiter);
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

    public void forwardsEncoder(double power, int ticks) {

            double correctedPower = ((Range.clip(power, 0, 100))/100);

            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            leftFront.setTargetPosition(ticks);
            rightFront.setTargetPosition(ticks);
            leftBack.setTargetPosition(ticks);
            rightBack.setTargetPosition(ticks);

            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftFront.setPower(correctedPower);
            rightFront.setPower(correctedPower);
            leftBack.setPower(correctedPower);
            rightBack.setPower(correctedPower);

            while(leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy());

            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
    }
    public void backwardsEncoder(double power, int ticks)
    {
        double correctedPower = ((Range.clip(power, 0, 100))/100);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront.setTargetPosition(-ticks);
        rightFront.setTargetPosition(-ticks);
        leftBack.setTargetPosition(-ticks);
        rightBack.setTargetPosition(-ticks);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(correctedPower);
        rightFront.setPower(correctedPower);
        leftBack.setPower(correctedPower);
        rightBack.setPower(correctedPower);

        while(leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy());

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }
    public void rightEncoder(double power, int ticks){
        double correctedPower = ((Range.clip(power, 0, 100))/100);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront.setTargetPosition(ticks);
        rightFront.setTargetPosition(-ticks);
        leftBack.setTargetPosition(-ticks);
        rightBack.setTargetPosition(ticks);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(correctedPower);
        rightFront.setPower(correctedPower);
        leftBack.setPower(correctedPower);
        rightBack.setPower(correctedPower);

        while(leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy());

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void leftEncoder(double power, int ticks){
        double correctedPower = ((Range.clip(power, 0, 100))/100);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront.setTargetPosition(-ticks);
        rightFront.setTargetPosition(ticks);
        leftBack.setTargetPosition(ticks);
        rightBack.setTargetPosition(-ticks);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(correctedPower);
        rightFront.setPower(correctedPower);
        leftBack.setPower(correctedPower);
        rightBack.setPower(correctedPower);

        while(leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy());

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    //Turn Methods

    /**
     * Method: turnRightEncoder(...)
     * Turns the robot right using the drive encoders
     * @param power - the power that the robot drives at from 0 to 100
     * @param ticks - how many ticks the robot should turn right
     */
    public void turnRightEncoder(double power, int ticks) {
        double correctedPower = ((Range.clip(power, 0, 100))/100);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront.setTargetPosition(ticks);
        rightFront.setTargetPosition(-ticks);
        leftBack.setTargetPosition(ticks);
        rightBack.setTargetPosition(-ticks);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(correctedPower);
        rightFront.setPower(correctedPower);
        leftBack.setPower(correctedPower);
        rightBack.setPower(correctedPower);

        while(leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy());

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }//turnRight function ends here


    /**
     * Method: turnLeftEncoder(...)
     * Turns the robot left using the drive encoders
     * @param power - the power that the robot drives at from 0 to 100
     * @param ticks - how many ticks the robot should turn left
     */
    public void turnLeftEncoder(double power, int ticks) {
        double correctedPower = ((Range.clip(power, 0, 100))/100);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront.setTargetPosition(-ticks);
        rightFront.setTargetPosition(ticks);
        leftBack.setTargetPosition(-ticks);
        rightBack.setTargetPosition(ticks);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(correctedPower);
        rightFront.setPower(correctedPower);
        leftBack.setPower(correctedPower);
        rightBack.setPower(correctedPower);

        while(leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy());

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }//turnLeft function ends here

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