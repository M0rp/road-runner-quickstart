package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;

public class DualSlideClass{
    DcMotor backSlide, frontSlide;

    public DualSlideClass(DcMotor backSlideMotor, DcMotor frontSlideMotor) {
        this.backSlide = backSlideMotor;
        this.frontSlide = frontSlideMotor;

    }

    public void viperSlideControl(Gamepad gamepad) {
        frontSlide.setPower(-gamepad.left_stick_y);
        backSlide.setPower(gamepad.left_stick_y);
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

    public void goToPosition() {

    }
}

