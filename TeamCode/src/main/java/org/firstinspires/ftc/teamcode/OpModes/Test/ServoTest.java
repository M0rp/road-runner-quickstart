package org.firstinspires.ftc.teamcode.OpModes.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;

@Autonomous
public class ServoTest extends LinearOpMode {
    Servo clawMount;

    @Override
    public void runOpMode() {
        clawMount = hardwareMap.get(Servo.class, RobotConstants.CLAW_MOUNT);

        waitForStart();

        clawMount.setPosition(1);

        while(opModeIsActive()) {

        }
    }
}
