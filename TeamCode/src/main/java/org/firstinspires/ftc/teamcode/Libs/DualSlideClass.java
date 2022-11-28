package org.firstinspires.ftc.teamcode.Libs;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;

public class DualSlideClass{
    DcMotor backSlide, frontSlide;

    //variables to control the slide
    int direction;
    double speedCapper;
    double speedLimiter;
    int position1, position2, position3, position4;

    //Enumerations for each state
    public enum SlidePositions {
        LOADING, LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, IDLE, LOWER
    }
    SlidePositions currentPos = SlidePositions.IDLE;
    }
    //constructor for Dual slide class
    public DualSlideClass(DcMotor backSlideMotor, DcMotor frontSlideMotor) {
        this.backSlide = backSlideMotor;
        this.frontSlide = frontSlideMotor;
        this.direction = direction;
        this.speedCapper = speedCapper;
    }
    //Method: viperSlideUpdate(), this isn't right mark please check this part.
    public void viperSlideUpdate(SlidePositions position) {
        switch(position) {
            case LOADING:
                frontSlide(0.5, 1);
                backSlide(0.5, 1);
                break;
            case LEVEL_ONE:
                frontSlide(0.5, 2);
                backSlide(0.5, 2);
                break;
            case LEVEL_TWO:
                frontSlide(0.5,3);
                backSlide(0.5,3);
                break;
            case LEVEL_THREE:
                frontSlide(0.5, 4):
                backSlide(0.5,4);
                break;
            case IDLE:
                break;
        }
    }

    /**
     * Method: viperSlideControl(...)
     * - the original slide control for teleop without the state machines
     * */
    public void viperSlideControl(Gamepad gamepad) {
        frontSlide.setPower(-gamepad.left_stick_y);
        backSlide.setPower(gamepad.left_stick_y);
        if(gamepad2.left_stick_y <= 0) {
            frontSlide.setPower(gamepad2.left_stick_y * direction * speedCapper);
            backSlide.setPower(gamepad2.left_stick_y * direction * speedCapper);
        } else if (gamepad2.left_stick_y >= 0) {
            frontSlide.setPower(gamepad2.left_stick_y * direction * speedLimiter);
            backSlide.setPower(gamepad2.left_stick_y * direction * speedLimiter);
        } else {
            frontSlide.setPower(0);
            backSlide.setPower(0);
        }
    }

    /**
    Method: viperSlideControl()
     another version of viperSlide control that is meant to use the state machines to control the viper slides
     Mark baby please check if my code is right lmao, and we need to accurately define height presets in person
     */
    public void viperSlideControl(Gamepad gamepad, SlidePositions startPosition) {
        if(gamepad.a) {
            currentPos = SlidePositions.LOADING;
        } else if(gamepad.b) {
            currentPos = SlidePositions.LEVEL_ONE;
        } else if(gamepad.x) {
            currentPos = SlidePositions.LEVEL_TWO;
        } else if(gamepad.y) {
            currentPos = SlidePositions.LEVEL_THREE;
        }

        if(currentPos == SlidePositions.IDLE) {
            if(gamepad.left_stick_y <= 0) {
                frontSlide.setPower(gamepad.left_stick_y * direction * speedCapper);
                backSlide.setPower(gamepad.left_stick_y * direction * speedCapper);
            } else if (gamepad.left_stick_y >= 0) {
                frontSlide.setPower(gamepad.left_stick_y * direction * speedLimiter);
                backSlide.setPower(gamepad.left_stick_y * direction * speedLimiter);
            } else {
                frontSlide.setPower(0);
                backSlide.setPower(0);
            }

            if(frontSlide.getCurrentPosition() >= RobotConstants.LOWEST_DISTANCE,backSlide.getCurrentPosition() >= RobotConstants.LOWERST_DISTANCE) {
                speedLimiter = 0;
            } else {
                speedLimiter = speedCapper;
            }
        }

        viperSlideUpdate(currentPos);
    }


    /**
     * Method: extendSlide()
     * - control
     * @param power - the speed at which the slide extends
     * @param ticks - the distance for the slide to go
     */
    public void extendSlide(double power, int ticks) {
        frontSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontSlide.setTargetPosition(-ticks);
        backSlide.setTargetPosition(ticks);

        frontSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontSlide.setPower(power);
        backSlide.setPower(power);

        while(frontSlide.isBusy() && backSlide.isBusy());

        frontSlide.setPower(0);
        backSlide.setPower(0);
    }

    /**
     * Method: retractSlide()
     * @param power - the speed at which the slide extends
     * @param ticks - the distance for the slide to go
     */
    public void retractSlide(double power, int ticks) {
        frontSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontSlide.setTargetPosition(ticks);
        backSlide.setTargetPosition(-ticks);

        frontSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontSlide.setPower(power);
        backSlide.setPower(power);

        while(frontSlide.isBusy() && backSlide.isBusy());

        frontSlide.setPower(0);
        backSlide.setPower(0);
    }

    public void goToPosition(double power, int position) {
        int goalTicks;

        switch (position) {
            case 1:
                goalTicks = position1;
                break;
            case 2:
                goalTicks = position2;
                break;
            case 3:
                goalTicks = position3;
                break;
            case 4:
                goalTicks = position4;
                break;
            default:
                goalTicks = 0;
        }
        frontSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontSlide.setTargetPosition(goalTicks);
        backSlide.setTargetPosition(goalTicks);

        frontSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontSlide.setPower(power);
        backSlide.setPower(power);

        if(!frontSlide.isBusy(), !backSlide.isBusy()) {
            frontSlide.setPower(0);
            backSlide.setPower(0);
            frontSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            currentPos = SlidePositions.IDLE;
        }
    }
    /*Method: slideSpeedChanger
    I dont know it this one is necessary but i saw it in last year's repository and added it just in case
    - adjusts the power provided to the slide by the current position to get a nice bell curve
     */
    public double slideSpeedChanger(int position) {
        return -0.5 * (Math.cos(6.3 * position)) + 0.5;
    }
}
