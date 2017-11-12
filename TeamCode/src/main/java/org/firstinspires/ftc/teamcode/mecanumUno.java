package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Cristian on 10/22/17.
 */

@TeleOp(name="TeleOp", group="Iterative Opmode")

public class mecanumUno extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftOne = null;
    private DcMotor rightOne = null;
    private DcMotor leftTwo = null;
    private DcMotor rightTwo = null;
    double v1 = 0;
    double v2 = 0;
    double v3 = 0;
    double v4 = 0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        leftOne  = hardwareMap.get(DcMotor.class, "lf");
        rightOne = hardwareMap.get(DcMotor.class, "rf");
        rightTwo = hardwareMap.get(DcMotor.class, "rb");
        leftTwo = hardwareMap.get(DcMotor.class, "lb");
    }
    @Override
    public void loop() {
        sg();
        go();

        telemetry.addData("Run Time", ":" + runtime.toString());
    }

    public void sg(){
        /*For Getting Direction Of Robot In TeleOp*/
        rightOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightOne.setDirection(DcMotor.Direction.REVERSE);
        rightTwo.setDirection(DcMotor.Direction.REVERSE);

        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        v1 = r * Math.cos(robotAngle) + rightX;
        v2 = r * Math.sin(robotAngle) - rightX;
        v3 = r * Math.sin(robotAngle) + rightX;
        v4 = r * Math.cos(robotAngle) - rightX;
    }

    public void go(){
        /*Set Motor Power*/
        if(gamepad1.left_bumper){
            leftOne.setPower(-1);
            rightOne.setPower(0);
            leftTwo.setPower(-1);
            rightTwo.setPower(1);
            telemetry.addData("Concerning: ","Around Front Left Wheel");
        }else if(gamepad1.right_bumper){
            leftOne.setPower(0);
            rightOne.setPower(-1);
            leftTwo.setPower(1);
            rightTwo.setPower(-1);
            telemetry.addData("Concerning: ","Around Front Right Wheel");
        }else if(gamepad1.left_trigger>0){
            leftOne.setPower(1);
            rightOne.setPower(-1);
            leftTwo.setPower(1);
            rightTwo.setPower(0);
            telemetry.addData("Concerning: ","Around Rear Left Wheel");
        }else if(gamepad1.right_trigger>0){
            leftOne.setPower(-1);
            rightOne.setPower(1);
            leftTwo.setPower(0);
            rightTwo.setPower(1);
            telemetry.addData("Concerning: ","Around Rear Right Wheel");
        }else if(gamepad1.dpad_down){
            leftOne.setPower(1);
            rightOne.setPower(-1);
            leftTwo.setPower(0);
            rightTwo.setPower(0);
            telemetry.addData("Turning: ","Rear-Right");
        }else if(gamepad1.dpad_up){
            leftOne.setPower(-1);
            rightOne.setPower(1);
            leftTwo.setPower(0);
            rightTwo.setPower(0);
            telemetry.addData("Turning: ","Rear-Left");
        }else if(gamepad1.dpad_left){
            leftOne.setPower(-1);
            rightOne.setPower(0);
            leftTwo.setPower(0);
            rightTwo.setPower(-1);
            telemetry.addData("Diagonal: ","Forward-Left");
        }else if(gamepad1.dpad_right){
            leftOne.setPower(0);
            rightOne.setPower(-1);
            leftTwo.setPower(-1);
            rightTwo.setPower(0);
            telemetry.addData("Diagonal: ","Forward-Right");
        }else if(gamepad1.x){
            leftOne.setPower(0);
            rightOne.setPower(1);
            leftTwo.setPower(1);
            rightTwo.setPower(0);
            telemetry.addData("Diagonal: ","Backwards-Left");
        }else if(gamepad1.b){
            leftOne.setPower(1);
            rightOne.setPower(0);
            leftTwo.setPower(0);
            rightTwo.setPower(1);
            telemetry.addData("Diagonal: ","Backwards-Right");
        }else{
                leftOne.setPower(v1);
                rightOne.setPower(v2);
                leftTwo.setPower(v3);
                rightTwo.setPower(v4);
        }
    }


}
