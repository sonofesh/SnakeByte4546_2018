package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by raymo on 10/16/2017.
 */
@Autonomous(name = "Servo-urban")
public class ServoTest extends LinearOpMode {
    CRServo leftArm;
    CRServo rightArm;
    @Override
    public void runOpMode() throws InterruptedException {
        leftArm = hardwareMap.crservo.get("LRelicArm");
        rightArm = hardwareMap.crservo.get("RRelicArm");
        leftArm.setPower(0.4);
        rightArm.setPower(-0.4);

    }
}
