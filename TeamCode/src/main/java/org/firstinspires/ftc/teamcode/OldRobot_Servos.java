package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Cristian on 10/22/17.
 */

@TeleOp(name="servos", group="Iterative Opmode")
@Disabled
public class OldRobot_Servos extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    Servo servoOne = null;
    //Servo servoTwo = null;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        servoOne = hardwareMap.servo.get("servo1");
        //servoTwo = hardwareMap.servo.get("servo2");
        servoOne.setPosition(0);
        //servoTwo.setPosition(0);
    }
    @Override
    public void loop() {
        if(gamepad1.right_bumper){
            servoOne.setPosition(1);
            //servoTwo.setPosition(1);
        }else{
            servoOne.setPosition(0);
        }

        telemetry.addData("Run Time", ":" + runtime.toString());
    }


}
