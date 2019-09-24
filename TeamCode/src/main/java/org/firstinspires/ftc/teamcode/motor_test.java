package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Cristian on 10/22/17.
 */

@TeleOp(name="Motor Test", group="Iterative Opmode")

public class motor_test extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftOne = null;
    private DcMotor rightOne = null;
    private DcMotor leftTwo = null;
    private DcMotor rightTwo = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Instructions", "Lift Robot");
        telemetry.addData("LF", "For Testing Left Front Press DPad Up");
        telemetry.addData("LB", "For Testing Left Back Press DPad Down");
        telemetry.addData("RF", "For Testing Right Front Press Y");
        telemetry.addData("RB", "For Testing Right Back Press A");
        telemetry.addData("Stopping", "Press Start To Stop Motors");

        leftOne  = hardwareMap.get(DcMotor.class, "lf");
        rightOne = hardwareMap.get(DcMotor.class, "rf");
        rightTwo = hardwareMap.get(DcMotor.class, "rb");
        leftTwo = hardwareMap.get(DcMotor.class, "lb");
        //extend = hardwareMap.get(Servo.class, "servo1");
    }
    @Override
    public void loop() {
        sg();
        jt();


        telemetry.addData("Run Time", runtime.toString());
    }
    public void jt(){
        if (gamepad1.dpad_up) {
            rightOne.setPower(1);
            telemetry.addData("Press Start To Stop","");
        }else if(gamepad1.dpad_down){
            rightTwo.setPower(1);
            telemetry.addData("Press Start To Stop","");
        }else if(gamepad1.y) {
            leftOne.setPower(1);
            telemetry.addData("Press Start To Stop","");
        }else if(gamepad1.a) {
            leftTwo.setPower(1);
            telemetry.addData("Press Start To Stop","");
        }else if(gamepad1.start){
            rightOne.setPower(0);
            rightTwo.setPower(0);
            leftOne.setPower(0);
            leftTwo.setPower(0);
        }
    }
    public void sg(){
        /*For Getting Direction Of Robot In TeleOp*/
        rightOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightOne.setDirection(DcMotor.Direction.FORWARD);
        rightTwo.setDirection(DcMotor.Direction.FORWARD);
        leftOne.setDirection(DcMotor.Direction.REVERSE);
        leftTwo.setDirection(DcMotor.Direction.REVERSE);
    }


}
