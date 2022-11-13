package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;

public class ClawClass {
    //servo for the claw
    Servo servoMount;
    Servo claw;

    //Variables to control claw

    //Enumerations for control of claw
    public enum ClawRotationPositions {
        FRONT, BACK
    }

    ClawRotationPositions currentPosition;

    //constructor
    public ClawClass(Servo servoMount, Servo servoClaw, ClawRotationPositions startingRotation){
        this.servoMount = servoMount;
        this.claw = servoClaw;
        this.currentPosition = startingRotation;
    }

    /**
     * Method: clawControl()
     * - Allows control of linear slides in teleop
     * @param gamepad - the controller to be used in teleop
     */
    public void clawControl(Gamepad gamepad) {
        if(gamepad.right_bumper) {
            claw.setPosition(RobotConstants.CLAW_CLOSED_POSITION);
        } else if(gamepad.left_bumper) {
            claw.setPosition(RobotConstants.CLAW_OPEN_POSITION);
        }

        if(gamepad.right_trigger != 0) {
            setClawDirection(ClawRotationPositions.FRONT);
        } else if(gamepad.left_trigger != 0) {
            setClawDirection(ClawRotationPositions.BACK);
        }
    }

    /**
     * Method: openClaw()
     * - Opens the claw
     */
    public void openClaw() {
        claw.setPosition(RobotConstants.CLAW_OPEN_POSITION);
    }

    /**
     * Method: closeClaw()
     * - Closes the claw
     */
    public void closeClaw() {
        claw.setPosition(RobotConstants.CLAW_CLOSED_POSITION);
    }

    /**
     * Method: setClawDirection()
     * - moves the claw to either the front or back of the robot
     * @param desiredPosition - enumeration used to set position
     */
    public void setClawDirection(ClawRotationPositions desiredPosition) {
        currentPosition = desiredPosition;

        switch(currentPosition) {
            case FRONT:
                servoMount.setPosition(RobotConstants.MOUNT_FRONT_POSITION);
                break;
            case BACK:
                servoMount.setPosition(RobotConstants.MOUNT_BACK_POSITION);
                break;
        }
    }
}