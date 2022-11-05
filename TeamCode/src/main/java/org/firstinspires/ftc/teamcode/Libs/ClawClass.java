package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;


public class ClawClass {
    //servo for the claw
    Servo SERVO_MOUNT;
    Servo SERVO_CLAW;

    //Variables to control claw
    double currentDefaultPosition;
    double closePosition, openPosition;

    //constructor
    public ClawClass(Servo servoMount, Servo servoClaw, double closePosition, double openPosition){
        this.SERVO_MOUNT = servoMount;
        this.SERVO_CLAW = servoClaw;
        this.closePosition = closePosition;
        this.openPosition = openPosition;
    }
}

    public void clawControl(Gamepad gamepad2) {
        if(gamepad2.a) {
            SERVO_CLAW.setPosition(closePosition);
        } else if(gamepad2.b) {
            SERVO_CLAW.setPosition(openPosition);
        } else {
            SERVO_CLAW.setPosition(currentDefaultPosition)}
        }
    }