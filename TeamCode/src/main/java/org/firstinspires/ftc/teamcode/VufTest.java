package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by raymo on 9/30/2017.
 */
@Autonomous(name = "ColorSensor", group = "Autonomous")
public class VufTest extends AutoOpMode {
    VuforiaLocalizer vuforia;
    @Override
    public void runOpMode(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        //key
        parameters.vuforiaLicenseKey = "AQ1iIdT/////AAAAGZ0U6OKRfU8tpKf9LKl/7DM85y3Wp791rb6q3WwHfYaY53vqKSjAO8wU2FgulWnDt6gLqu9hB33z1reejMz/NyfL8u11QZlMIbimmnP/v4hvoXZWu0p62V9eMG3R2PQ3Z7rZ0qK8HwsQYE/0jmBhTy0D17M4fWpNW64QQnMJqFxq/N1BXm32PEInYDHBYs7WUrHL5oa9xeSSurxUq/TqDpeJwQM+1/GYppdAqzbcM1gi3yzU7JDLdNtOZ6+lbi5uXlU++GnFvQaEXL9uVcnTwMEgBhBng6oOEVoEDXiSUBuZHuMRGZmHfVXSNE3m1UXWyEdPTlMRI5vfEwfsBHmQTmvYr/jJjng3+tBpu85Q1ivo";
        //using front camera so it's easier to use the phone during competition
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        //init
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        relicTrackables.activate();
        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                if (vuMark == RelicRecoveryVuMark.LEFT){
                    telemetry.addData("Key","left");
                    telemetry.update();
                    cryptoboxKey = "left";
                }
                if (vuMark == RelicRecoveryVuMark.CENTER){
                    telemetry.addData("Key","center");
                    telemetry.update();
                    cryptoboxKey = "center";
                }
                if (vuMark == RelicRecoveryVuMark.RIGHT){
                    telemetry.addData("Key","right");
                    telemetry.update();
                    cryptoboxKey = "right";
                }

            }
            else {
                telemetry.addData("Key", "unknown");
                telemetry.update();
            }
            /*
            if ((colorSensor.red() > colorSensor.blue()) && (colorSensor.red() > 15)){
                telemetry.addData("red", colorSensor.red());
                telemetry.update();
            }
            else if ((colorSensor.red() < colorSensor.blue()) && ( colorSensor.blue() > 15)){
                telemetry.addData("blue", colorSensor.blue());
                telemetry.update();
            }
            else{
                telemetry.addData("Color", "None");
                telemetry.update();
            */
            }


    }
}
