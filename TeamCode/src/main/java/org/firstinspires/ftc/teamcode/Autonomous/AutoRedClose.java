package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

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
 * Created by Cristian on 11/10/17.
 */

@Autonomous(name="AutoRedClose", group ="AutoRed")
@Disabled
public class AutoRedClose extends LinearOpMode {

    ColorSensor color_sensor;

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    @Override public void runOpMode() {
        color_sensor = hardwareMap.colorSensor.get("color");
        color_sensor.enableLed(true);

        color_sensor.red();   // Red channel value
        color_sensor.green(); // Green channel value
        color_sensor.blue();  // Blue channel value

        color_sensor.alpha(); // Total luminosity
        color_sensor.argb();  // Combined color value

        telemetry.addData("Red:", color_sensor.red());
        telemetry.addData("Blue:", color_sensor.green());
        telemetry.addData("Green:", color_sensor.blue());
        telemetry.addData("Alpha:", color_sensor.alpha());
        telemetry.addData("ARGB:", color_sensor.argb());


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AR/dGQz/////AAAAGefSPdahvk9xi29/5Ic0ZAIMHot8Hd/hCJSa/WffbXSv6wZnEcenvy8VMW0rLYr9rfocY8sa48bqBuPc6JjQhPIKeY7DBUhC2QTE+3uyNp0IkvCg4Y7qAuiVn+HUhfeLLtZpPlsmU23SbW7Ed/7G3VUGJeW35q8gLO/pl48dCAbAHQzer40Lwa8BXprp717Pe8r30KQuT+NzSiaxF9zdrdLNXukLrgi57Y4T68UyI72a1IypYIV3Ug31ICLnQU/AN3okoNLIVEd0v2+Qm2T/AgD17aPytGWoAcVthsfDumgSBt+cvigvAH4DkzUQ+faQfaGJjeKv2c16NQ3B4NX1kJ08jsxxawOolkqOPMjfl1QW";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

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

                    }else if(vuMark == RelicRecoveryVuMark.CENTER){

                    }else if(vuMark == RelicRecoveryVuMark.RIGHT){

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
}
