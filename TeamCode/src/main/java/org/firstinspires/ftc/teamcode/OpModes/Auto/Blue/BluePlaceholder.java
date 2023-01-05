package org.firstinspires.ftc.teamcode.OpModes.Auto.Blue;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.RobotConstants;
import org.firstinspires.ftc.teamcode.Libs.ClawClass;
import org.firstinspires.ftc.teamcode.Libs.DualSlideClass;
import org.firstinspires.ftc.teamcode.Libs.MecanumDriveClass;
import org.firstinspires.ftc.teamcode.Libs.ToggleClass;

@Autonomous (name="Blue Left", group = "Blue")
public class BluePlaceholder extends LinearOpMode{
    //Motors to run drive train
    DcMotor frontLeft, frontRight, backLeft, backRight;

    //Viper Slide Motors
    DcMotor frontSlideMotor, backSlideMotor;

    //Claw Servos
    Servo rotationServo, clawServo;

    //Initiations of drive classes
    ClawClass Claw;
    DualSlideClass DualSlide;
    MecanumDriveClass MecanumDrive;

    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag ID 1, 2, and 3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;
    @Override
    public void runOpMode() throws InterruptedException{
        frontLeft = hardwareMap.dcMotor.get(RobotConstants.LEFT_FRONT_MOTOR);
        frontRight = hardwareMap.dcMotor.get(RobotConstants.RIGHT_FRONT_MOTOR);
        backLeft = hardwareMap.dcMotor.get(RobotConstants.LEFT_BACK_MOTOR);
        backRight = hardwareMap.dcMotor.get(RobotConstants.RIGHT_BACK_MOTOR);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontSlideMotor = hardwareMap.dcMotor.get(RobotConstants.FRONT_SLIDE_MOTOR);
        frontSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backSlideMotor = hardwareMap.dcMotor.get(RobotConstants.BACK_SLIDE_MOTOR);
        backSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        clawServo = hardwareMap.servo.get(RobotConstants.CLAW_SERVO);
        rotationServo = hardwareMap.servo.get(RobotConstants.CLAW_MOUNT);

        MecanumDrive = new MecanumDriveClass(frontLeft, frontRight, backLeft, backRight);
        DualSlide = new DualSlideClass(frontSlideMotor,backSlideMotor);
        Claw = new ClawClass(rotationServo, clawServo, ClawClass.ClawRotationPositions.FRONT);

        {
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
            aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

            camera.setPipeline(aprilTagDetectionPipeline);
            camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
            {
                @Override
                public void onOpened()
                {
                    camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
                }

                @Override
                public void onError(int errorCode)
                {

                }
            });

            telemetry.setMsTransmissionInterval(50);

            /*
             * The INIT-loop:
             * This REPLACES waitForStart!
             */
            while (!isStarted() && !isStopRequested())
            {
                ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

                if(currentDetections.size() != 0)
                {
                    boolean tagFound = false;

                    for(AprilTagDetection tag : currentDetections)
                    {
                        if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT)
                        {
                            tagOfInterest = tag;
                            tagFound = true;
                            break;
                        }
                    }

                    if(tagFound)
                    {
                        telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                        tagToTelemetry(tagOfInterest);
                    }
                    else
                    {
                        telemetry.addLine("Don't see tag of interest :(");

                        if(tagOfInterest == null)
                        {
                            telemetry.addLine("(The tag has never been seen)");
                        }
                        else
                        {
                            telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                            tagToTelemetry(tagOfInterest);
                        }
                    }

                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }

                }

                telemetry.update();
                sleep(20);
            }

            /*
             * The START command just came in: now work off the latest snapshot acquired
             * during the init loop.
             */

            /* Update the telemetry */
            if(tagOfInterest != null)
            {
                telemetry.addLine("Tag snapshot:\n");
                tagToTelemetry(tagOfInterest);
                telemetry.update();
            }
            else
            {
                telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
                telemetry.update();
            }

            /* Actually do something useful */
            if(tagOfInterest == null)
            {
                /*
                 * Insert your autonomous code here, presumably running some default configuration
                 * since the tag was never sighted during INIT
                 */
            }
            else
            {
                //Insert your autonomous code here, probably using the tag pose to decide your configuration.
                 switch(tagOfInterest) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                }
            }
    }



}
