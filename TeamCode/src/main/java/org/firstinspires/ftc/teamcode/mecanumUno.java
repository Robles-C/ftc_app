package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Autonomous.HardwareRobespierre;


/**
 * Created by Cristian on 10/22/17.
 */

@TeleOp(name="TeleOp", group="Iterative Opmode")

public class mecanumUno extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftOne = null;
    private DcMotor rightOne = null;
    private DcMotor leftTwo = null;
    private DcMotor rightTwo = null;
    private DcMotor lift = null;
    //private DcMotor extendR = null;
    Servo extend = null;
    Servo grabber = null;
    Servo color_arm = null;
    //Servo relic_grabber = null;
    //Servo relic_wrist = null;

    double v1 = 0;
    double v2 = 0;
    double v3 = 0;
    double v4 = 0;
    float liftPower = 0;
    float extendRPower = 0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        rightOne = hardwareMap.get(DcMotor.class, "rf");
        leftOne = hardwareMap.get(DcMotor.class, "lf");
        leftTwo = hardwareMap.get(DcMotor.class, "lb");
        rightTwo = hardwareMap.get(DcMotor.class, "rb");
        lift = hardwareMap.get(DcMotor.class, "lift");
        //extendR = hardwareMap.get(DcMotor.class, "extendM");
        extend = hardwareMap.get(Servo.class, "servo1");
        grabber = hardwareMap.get(Servo.class, "servo2");
        color_arm = hardwareMap.get(Servo.class,"servo3");
        //relic_wrist = hardwareMap.get(Servo.class, "servo4");
        //relic_grabber = hardwareMap.get(Servo.class, "servo5");
        extend.setPosition(.2);
        grabber.setPosition(.41);
        color_arm.setPosition(.85);
        //relic_grabber.setPosition(0);
        //relic_wrist.setPosition(0);

    }

    @Override
    public void loop() {
        sg();
        go();


        telemetry.addData("Run Time", ":" + runtime.toString());
    }

    public void sg() {
        leftOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftOne.setDirection(DcMotor.Direction.REVERSE);
        leftTwo.setDirection(DcMotor.Direction.REVERSE);

        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        v1 = r * Math.cos(robotAngle) + rightX;
        v2 = r * Math.sin(robotAngle) - rightX;
        v3 = r * Math.sin(robotAngle) + rightX;
        v4 = r * Math.cos(robotAngle) - rightX;
    }

    public void go() {
        /*Driving*/
        if (gamepad1.dpad_right) {
            rightOne.setPower(1);
            leftOne.setPower(-1);
            rightTwo.setPower(0);
            leftTwo.setPower(0);
            telemetry.addData("Turning: ", "Rear Going Right");
        } else if (gamepad1.dpad_left) {
            rightOne.setPower(-1);
            leftOne.setPower(1);
            rightTwo.setPower(0);
            leftTwo.setPower(0);
            telemetry.addData("Turning: ", "Rear Going Left");
        } else if (gamepad1.x) {
            rightOne.setPower(0);
            leftOne.setPower(0);
            rightTwo.setPower(1);
            leftTwo.setPower(-1);
            telemetry.addData("Turning: ", "Front Going Right");
        } else if (gamepad1.b) {
            rightOne.setPower(0);
            leftOne.setPower(0);
            rightTwo.setPower(-1);
            leftTwo.setPower(1);
            telemetry.addData("Turning: ", "Front Going Left");
        } else if(gamepad1.right_stick_x > 0){
            rightOne.setPower(-.5);
            leftOne.setPower(.5);
            rightTwo.setPower(-.5);
            leftTwo.setPower(.5);
        } else if(gamepad1.right_stick_x < 0){
            rightOne.setPower(.5);
            leftOne.setPower(-.5);
            rightTwo.setPower(.5);
            leftTwo.setPower(-.5);
        }else {
            rightOne.setPower(v1*.9);
            leftOne.setPower(v2*.9);
            rightTwo.setPower(v3*.9);
            leftTwo.setPower(v4*.9);
        }

        /*Extend Servos*/
        if (extend.getPosition() == .2) {
            if (gamepad2.left_bumper) {
                extend.setPosition(.90);
            }
        }
        if (extend.getPosition() == .90) {
            if (gamepad2.right_bumper) {
                extend.setPosition(.2);
            }
        }
        /*Grabbers*/
        if (gamepad2.x) {
            grabber.setPosition(.15);
        }
        if(gamepad2.a){
            grabber.setPosition(.22);
        }
        if (gamepad2.b) {
            grabber.setPosition(.34);
        }
        if (gamepad2.y){
            grabber.setPosition(.41);
        }
        //Cs
        //if (color_arm.getPosition() == 1) {
            //if (gamepad2.dpad_up) {
                //color_arm.setPosition(.25);
            //}
        //}
        //if (color_arm.getPosition() == .25) {
            //if (gamepad2.dpad_down) {
                //color_arm.setPosition(1);
            //}
        //}

        /*Lift Motor*/
        liftPower = gamepad2.left_stick_y;
        lift.setPower(liftPower/1.9);

        /*Extend Motor
        extendRPower = gamepad2.right_stick_y;
        extendR.setPower(extendRPower/3);

        //Wrist motion
        if (relic_wrist.getPosition() == 0) {
            if (gamepad2.dpad_up) {
                relic_wrist.setPosition(.5);
            }
        }
        if (relic_wrist.getPosition() == .5) {
            if (gamepad2.dpad_down) {
                relic_wrist.setPosition(0);
            }
        }

        //Relic Grabber
        if (relic_grabber.getPosition() == 0) {
            if (gamepad2.dpad_left) {
                relic_grabber.setPosition(.5);
            }
        }
        if (relic_grabber.getPosition() == .5) {
            if (gamepad2.dpad_right) {
                relic_grabber.setPosition(0);
            }
        }
        **/
    }
}
