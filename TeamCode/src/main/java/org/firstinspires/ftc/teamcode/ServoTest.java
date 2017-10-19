package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by raymo on 10/16/2017.
 */
@Autonomous(name = "Servo-urban")
public class ServoTest extends LinearOpMode {
    Servo testServo;
    @Override
    public void runOpMode() throws InterruptedException {
        testServo = hardwareMap.servo.get("jHitter");
        testServo.setPosition(0.5);
        sleep(500);
        testServo.setPosition(0);
        sleep(500);
        testServo.setPosition(1);

    }
}
