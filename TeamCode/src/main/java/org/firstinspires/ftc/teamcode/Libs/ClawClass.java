package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;


public class ClawClass {
    //servo for the claw
    Servo servoMount;
    Servo servoClaw;

    //Variables to control claw

    //constructor
    public ClawClass(Servo servoMount, Servo servoClaw){
        this.servoMount = servoMount;
        this.servoClaw = servoClaw;
    }

    public void clawControl(Gamepad gamepad2) {
        if(gamepad2.a) {
            servoClaw.setPosition(RobotConstants.CLAW_CLOSED_POSITION);
        } else if(gamepad2.b) {
            servoClaw.setPosition(RobotConstants.CLAW_OPEN_POSITION);
        }

        if(gamepad2.x) {
            servoMount.setPosition(RobotConstants.MOUNT_FRONT_POSITION);
        } else if(gamepad2.y) {
            servoMount.setPosition(RobotConstants.MOUNT_BACK_POSITION);
        }
    }
}