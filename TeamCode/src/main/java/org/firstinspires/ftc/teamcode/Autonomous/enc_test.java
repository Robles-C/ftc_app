package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Cristian on 11/10/17.
 */

@Autonomous(name="Encoder_Test", group ="Autonomous")
@Disabled
public class enc_test extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftOne = null;
    private DcMotor rightOne = null;
    private DcMotor leftTwo = null;
    private DcMotor rightTwo = null;

    @Override
    public void runOpMode() throws InterruptedException{

        leftOne  = hardwareMap.get(DcMotor.class, "lf");
        rightOne = hardwareMap.get(DcMotor.class, "rf");
        rightTwo = hardwareMap.get(DcMotor.class, "rb");
        leftTwo = hardwareMap.get(DcMotor.class, "lb");

        resetEnc();

        waitForStart();

        leftOne.setTargetPosition(2240);
        leftTwo.setTargetPosition(2240);
        rightOne.setTargetPosition(2240);
        rightTwo.setTargetPosition(2240);

        leftOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        leftOne.setPower(1);
        leftTwo.setPower(1);
        rightOne.setPower(1);
        rightTwo.setPower(1);

        while (leftOne.isBusy()){

        }leftOne.setPower(0);
        leftTwo.setPower(0);
        rightOne.setPower(0);
        rightTwo.setPower(0);


        telemetry.addData("Run Time", ":" + runtime.toString());
    }
    public void resetEnc(){
        leftOne.setMode(DcMotor.RunMode.RESET_ENCODERS);
        leftTwo.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightOne.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightTwo.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }
    public void sg(){
        rightOne.setDirection(DcMotor.Direction.REVERSE);
        rightTwo.setDirection(DcMotor.Direction.REVERSE);
        leftOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
}
