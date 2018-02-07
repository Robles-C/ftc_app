package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Cristian on 12/21/17.
 */

@Autonomous(name="AutoRedFar", group ="AutoRed")

public class AutoRedFar extends LinearOpMode {

    HardwareRobespierre         robot   = new HardwareRobespierre();
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1220;
    static final double     DRIVE_GEAR_REDUCTION    = 2;
    static final double     WHEEL_DIAMETER_INCHES   = 4.0;
    static final double     COUNTS_PER_INCH         = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415));
    static final double     DRIVE_SPEED             = 0.39;
    static final double     TURN_SPEED              = 0.5;
    static final double     STRAFE_SPEED            = 0.25;
    static final double     JEWEL_SPEED             = 0.20;

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    @Override public void runOpMode() {

        robot.init(hardwareMap);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.lf.getCurrentPosition(),
                robot.rf.getCurrentPosition());
        telemetry.update();


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AR/dGQz/////AAAAGefSPdahvk9xi29/5Ic0ZAIMHot8Hd/hCJSa/WffbXSv6wZnEcenvy8VMW0rLYr9rfocY8sa48bqBuPc6JjQhPIKeY7DBUhC2QTE+3uyNp0IkvCg4Y7qAuiVn+HUhfeLLtZpPlsmU23SbW7Ed/7G3VUGJeW35q8gLO/pl48dCAbAHQzer40Lwa8BXprp717Pe8r30KQuT+NzSiaxF9zdrdLNXukLrgi57Y4T68UyI72a1IypYIV3Ug31ICLnQU/AN3okoNLIVEd0v2+Qm2T/AgD17aPytGWoAcVthsfDumgSBt+cvigvAH4DkzUQ+faQfaGJjeKv2c16NQ3B4NX1kJ08jsxxawOolkqOPMjfl1QW";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData(">", "Press Play to start");
        telemetry.update();


        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()) {

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                telemetry.addData("VuMark", "%s visible", vuMark);

                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));

                if (pose != null) {
                    telemetry.addData("VuMark", "%s visible", vuMark);
                    if(vuMark == RelicRecoveryVuMark.LEFT){
                        lowerArm();
                        sleep(3000);
                        jewel(2.5);
                        sleep(100);
                        encoderDrive(DRIVE_SPEED,20,20,4);
                        encoderStrafe(STRAFE_SPEED, -21.5,21.5,21.5,-21.5,6);
                        sleep(250);
                        robot.extend.setPosition(.9);
                        sleep(250);
                        encoderDrive(DRIVE_SPEED,2,2,2);
                        sleep(250);
                        robot.grabber.setPosition(.15);
                        sleep(250);
                        encoderDrive(DRIVE_SPEED,-5,-5,3);
                        sleep(250);
                        robot.grabber.setPosition(.41);
                        sleep(250);
                        robot.extend.setPosition(.2);
                        sleep(250);
                        encoderDrive(DRIVE_SPEED,6,6,4);
                        stop();
                    }else if(vuMark == RelicRecoveryVuMark.CENTER){
                        lowerArm();
                        sleep(3000);
                        jewel(2.5);
                        sleep(100);
                        encoderDrive(DRIVE_SPEED,20,20,4);
                        encoderStrafe(STRAFE_SPEED, -14,14,14,-14,5);
                        sleep(250);
                        robot.extend.setPosition(.9);
                        sleep(250);
                        encoderDrive(DRIVE_SPEED,2,2,2);
                        sleep(250);
                        robot.grabber.setPosition(.15);
                        sleep(250);
                        encoderDrive(DRIVE_SPEED,-5,-5,3);
                        sleep(250);
                        robot.grabber.setPosition(.41);
                        sleep(250);
                        robot.extend.setPosition(.2);
                        sleep(250);
                        encoderDrive(DRIVE_SPEED,6,6,4);
                        stop();
                    }else if(vuMark == RelicRecoveryVuMark.RIGHT){
                        lowerArm();
                        sleep(3000);
                        jewel(2.5);
                        sleep(100);
                        encoderDrive(DRIVE_SPEED,20,20,4);
                        encoderStrafe(STRAFE_SPEED, -6.5,6.5,6.5,-6.5,3);
                        sleep(250);
                        robot.extend.setPosition(.9);
                        sleep(250);
                        encoderDrive(DRIVE_SPEED,2,2,2);
                        sleep(250);
                        robot.grabber.setPosition(.15);
                        sleep(250);
                        encoderDrive(DRIVE_SPEED,-5,-5,3);
                        sleep(250);
                        robot.grabber.setPosition(.41);
                        sleep(250);
                        robot.extend.setPosition(.2);
                        sleep(250);
                        encoderDrive(DRIVE_SPEED,6,6,4);
                        stop();
                        stop();
                    }
                }
            }
            else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {

            robot.lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            newLeftTarget = robot.lf.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rf.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.lf.setTargetPosition(newLeftTarget);
            robot.lb.setTargetPosition(newLeftTarget);
            robot.rf.setTargetPosition(newRightTarget);
            robot.rb.setTargetPosition(newRightTarget);

            runtime.reset();
            robot.lf.setPower(Math.abs(speed));
            robot.rf.setPower(Math.abs(speed));
            robot.lb.setPower(Math.abs(speed));
            robot.rb.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.lf.isBusy() && robot.rf.isBusy()
                            && robot.lb.isBusy() && robot.rb.isBusy())) {

                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.lf.getCurrentPosition(),
                        robot.rf.getCurrentPosition());
                telemetry.update();
            }

            robot.lf.setPower(0);
            robot.rf.setPower(0);
            robot.lb.setPower(0);
            robot.rb.setPower(0);

            resetEnc();

            robot.lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
    public void encoderStrafe(double speed,
                              double lfInches,double lbInches,
                              double rfInches , double rbInches,
                              double timeoutS) {
        int newLfTarget;
        int newRfTarget;
        int newRbTarget;
        int newLbTarget;

        if (opModeIsActive()) {
            robot.lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            newLfTarget = robot.lf.getCurrentPosition() + (int)(lfInches * COUNTS_PER_INCH);
            newRfTarget = robot.rf.getCurrentPosition() + (int)(rfInches * COUNTS_PER_INCH);
            newLbTarget = robot.lb.getCurrentPosition() + (int)(lbInches * COUNTS_PER_INCH);
            newRbTarget = robot.rb.getCurrentPosition() + (int)(rbInches * COUNTS_PER_INCH);

            robot.lf.setTargetPosition(newLfTarget);
            robot.lb.setTargetPosition(newLbTarget);
            robot.rf.setTargetPosition(newRfTarget);
            robot.rb.setTargetPosition(newRbTarget);

            runtime.reset();
            robot.lf.setPower(Math.abs(speed));
            robot.rf.setPower(Math.abs(speed));
            robot.lb.setPower(Math.abs(speed));
            robot.rb.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.lf.isBusy() && robot.rf.isBusy()
                            && robot.lb.isBusy() && robot.rb.isBusy())) {

                telemetry.addData("Path1",  "Running to %7d :%7d", newLfTarget,  newRfTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.lf.getCurrentPosition(),
                        robot.rf.getCurrentPosition());
                telemetry.update();
            }

            robot.lf.setPower(0);
            robot.rf.setPower(0);
            robot.lb.setPower(0);
            robot.rb.setPower(0);

            robot.lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            resetEnc();

            robot.lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
    public void raiseArm(){
        robot.color_arm.setPosition(.35);
        sleep(150);
        robot.color_arm.setPosition(.45);
        sleep(150);
        robot.color_arm.setPosition(.55);
        sleep(150);
        robot.color_arm.setPosition(.65);
        sleep(150);
        robot.color_arm.setPosition(.85);
        sleep(150);
    }
    public void lowerArm(){
        robot.color_arm.setPosition(.9);
        sleep(150);
        robot.color_arm.setPosition(.7);
        sleep(1000);
        robot.color_arm.setPosition(.25);
        sleep(150);
    }
    public void resetEnc(){
        robot.lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(169);
    }
    public void jewel(double holdTime){
        ElapsedTime holdTimer = new ElapsedTime();
        holdTimer.reset();
        while (opModeIsActive() && holdTimer.time() < holdTime) {
            if (robot.colorSensor.blue() > 2) {
                encoderDrive(JEWEL_SPEED, -3, 3, 2.0);
                encoderDrive(JEWEL_SPEED, 3, -3, 2.0);
                raiseArm();
            } else {
                encoderDrive(JEWEL_SPEED, 3, -3, 2.0);
                encoderDrive(JEWEL_SPEED, -3, 3, 2.0);
                raiseArm();
            }
            robot.lb.setPower(0);
            robot.lf.setPower(0);
            robot.rf.setPower(0);
            robot.rb.setPower(0);
        }
    }
}
