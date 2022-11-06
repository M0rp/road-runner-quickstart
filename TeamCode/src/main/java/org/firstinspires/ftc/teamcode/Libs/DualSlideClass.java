package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;

public class DualSlideClass{
    DcMotor backSlide,frontSlide;
    
    int direction;
    double speedCapper;
    double speedLimtier;
    

    public DualSlideClass(DcMotor backSlideMotor, DcMotor frontSlideMotor) {
        this.backSlide = backSlideMotor;
        this.frontSlide = frontSlideMotor;

    }

    public void viperSlideControl(Gamepad gamepad) {
        frontSlide.setPower(-gamepad.left_stick_y);
        backSlide.setPower(gamepad.left_stick_y);
    }
}

