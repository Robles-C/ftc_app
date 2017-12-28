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
    Servo extend = null;
    Servo grabber = null;
    Servo color_arm = null;

    double v1 = 0;
    double v2 = 0;
    double v3 = 0;
    double v4 = 0;
    float liftPower = 0;

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
        extend = hardwareMap.get(Servo.class, "servo1");
        grabber = hardwareMap.get(Servo.class, "servo2");
        color_arm = hardwareMap.get(Servo.class,"servo3");
        extend.setPosition(.2);
        grabber.setPosition(.41);
        color_arm.setPosition(.90);

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
        if (gamepad1.left_bumper) {
            rightOne.setPower(-1);
            leftOne.setPower(0);
            rightTwo.setPower(-1);
            leftTwo.setPower(1);
            telemetry.addData("Concerning: ", "Around Front Left Wheel");
        } else if (gamepad1.right_bumper) {
            rightOne.setPower(0);
            leftOne.setPower(-1);
            rightTwo.setPower(1);
            leftTwo.setPower(-1);
            telemetry.addData("Concerning: ", "Around Front Right Wheel");
        } else if (gamepad1.left_trigger > 0) {
            rightOne.setPower(1);
            leftOne.setPower(-1);
            rightTwo.setPower(1);
            leftTwo.setPower(0);
            telemetry.addData("Concerning: ", "Around Rear Left Wheel");
        } else if (gamepad1.right_trigger > 0) {
            rightOne.setPower(-1);
            leftOne.setPower(1);
            rightTwo.setPower(0);
            leftTwo.setPower(1);
            telemetry.addData("Concerning: ", "Around Rear Right Wheel");
        } else if (gamepad1.dpad_down) {
            rightOne.setPower(1);
            leftOne.setPower(-1);
            rightTwo.setPower(0);
            leftTwo.setPower(0);
            telemetry.addData("Turning: ", "Rear Going Right");
        } else if (gamepad1.dpad_up) {
            rightOne.setPower(-1);
            leftOne.setPower(1);
            rightTwo.setPower(0);
            leftTwo.setPower(0);
            telemetry.addData("Turning: ", "Rear Going Left");
        }else if (gamepad1.y) {
            rightOne.setPower(0);
            leftOne.setPower(0);
            rightTwo.setPower(1);
            leftTwo.setPower(-1);
            telemetry.addData("Turning: ", "Front Going Right");
        } else if (gamepad1.a) {
            rightOne.setPower(0);
            leftOne.setPower(0);
            rightTwo.setPower(-1);
            leftTwo.setPower(1);
            telemetry.addData("Turning: ", "Front Going Left");
        } else {
            rightOne.setPower(v1);
            leftOne.setPower(v2);
            rightTwo.setPower(v3);
            leftTwo.setPower(v4);
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
        if (grabber.getPosition() == .41) {
            if (gamepad2.a) {
                grabber.setPosition(.15);
            }
        }
        if (grabber.getPosition() == .15) {
            if (gamepad2.y) {
                grabber.setPosition(.41);
            }
        }
        //Cs
        if (color_arm.getPosition() == .90) {
            if (gamepad2.dpad_up) {
                color_arm.setPosition(.17);
            }
        }
        if (color_arm.getPosition() == .17) {
            if (gamepad2.dpad_down) {
                color_arm.setPosition(.90);
            }
        }

        /*Lift Motor*/
        liftPower = gamepad2.left_stick_y;
        lift.setPower(liftPower/2.5);

    }
}
