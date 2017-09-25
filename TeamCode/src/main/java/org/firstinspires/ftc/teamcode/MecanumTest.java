package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by sopa on 9/12/17.
 */

public class MecanumTest extends OpMode {
    double velocity = 0;
    double rotation = 0;
    double strafe = 0;
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;

    @Override
    public void init() {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
    }

    @Override
    public void loop() {
        setPower();
    }

    public double getVelocity() {
        if(Math.abs(gamepad1.right_stick_y) > .05)
            return gamepad1.right_stick_y;
        return 0;
    }

    public double getRotation() {
        if(Math.abs(gamepad1.right_stick_x) > .05)
            return gamepad1.right_stick_x;
        return 0;
    }

    public double getStrafe() {
        if(Math.abs(gamepad1.left_stick_y) > .05)
            return gamepad1.left_stick_y;
        return 0;
    }

    public void setPower() {
        velocity = getVelocity();
        rotation = getRotation();
        strafe = getStrafe();
        FL.setPower(velocity - rotation - strafe);
        FR.setPower(velocity + rotation + strafe);
        BL.setPower(velocity - rotation + strafe);
        BR.setPower(velocity + rotation - strafe);
    }
}
