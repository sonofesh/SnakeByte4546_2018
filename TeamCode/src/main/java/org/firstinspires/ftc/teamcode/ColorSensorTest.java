package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by raymo on 9/30/2017.
 */
@Autonomous(name = "ColorSensor", group = "Autonomous")
public class ColorSensorTest extends AutoOpMode {
    @Override
    public void runOpMode(){
        initialize();
        while (opModeIsActive()) {
            scanImage();
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
