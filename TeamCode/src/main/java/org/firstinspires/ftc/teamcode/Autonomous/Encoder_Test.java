package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Cristian on 11/10/17.
 */

@Autonomous(name="Encoder Test", group="Autonomous")

public class Encoder_Test extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftOne = null;
    private DcMotor rightOne = null;
    private DcMotor leftTwo = null;
    private DcMotor rightTwo = null;

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
        telemetry.addData("Run Time", ":" + runtime.toString());
    }
    public void sg(){
        rightOne.setDirection(DcMotor.Direction.REVERSE);
        rightTwo.setDirection(DcMotor.Direction.REVERSE);
        rightOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}
