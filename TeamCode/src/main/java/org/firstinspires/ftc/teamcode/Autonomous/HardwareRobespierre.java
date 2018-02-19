package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot.MID_SERVO;

public class HardwareRobespierre
{
    /* Public OpMode members. */
    public DcMotor lf = null;
    public DcMotor rf = null;
    public DcMotor lb = null;
    public DcMotor rb = null;
    //public DcMotor lift = null;

    public Servo extend = null;
    public Servo grabber = null;
    public Servo color_arm = null;

    public ColorSensor colorSensor = null;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareRobespierre(){
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        lf  = hwMap.get(DcMotor.class, "lf");
        rf = hwMap.get(DcMotor.class, "rf");
        lb = hwMap.get(DcMotor.class, "lb");
        rb = hwMap.get(DcMotor.class, "rb");
        //lift = hwMap.get(DcMotor.class, "lift");


        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);
        //lift.setPower(0);


        // May want to use RUN_USING_ENCODERS if encoders are installed.
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        extend  = hwMap.get(Servo.class, "servo1");
        grabber = hwMap.get(Servo.class, "servo2");
        color_arm = hwMap.get(Servo.class, "servo3");

        colorSensor = hwMap.get(ColorSensor.class, "color1");
        extend.setPosition(.34);
        grabber.setPosition(.46);
        color_arm.setPosition(.95);
    }
 }

