package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by raymo on 10/4/17.
 */

@TeleOp (name = "ShouldersAreMyTeleOp", group = "TeleOp")
public class ShoulderControlsTeleOp extends OpMode {
    double rightMotion = 0;
    double leftMotion = 0;
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;

    @Override
    public void init() {
        //FR is port 3, FL is port 2, BR is port 1, BL is port 0
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
    }

    @Override
    public void loop() {
        setPower();
    }

    public double getRightVelocity()
    {
        if (Math.abs(gamepad1.right_stick_y) > 0.05)
            return gamepad1.right_stick_y;
        return 0;
    }

    public double getLeftVelocity()
    {
        if (Math.abs(gamepad1.left_stick_y) > 0.05)
            return gamepad1.left_stick_y;
        return 0;
    }

    public double getLeftShoulder()
    {
        if (gamepad1.left_bumper)
            return 1.0;
        return 0;
    }

    public double getRightShoulder()
    {
        if (gamepad1.right_bumper)
            return 1.0;
        return 0;
    }

    public void setPower() {
        rightMotion = getRightVelocity();
        leftMotion = getLeftVelocity();
        FL.setPower(getLeftVelocity() - getLeftShoulder() + getRightShoulder());
        FR.setPower(-getRightVelocity() - getLeftShoulder() + getRightShoulder());
        BL.setPower(getLeftVelocity() + getLeftShoulder() - getRightShoulder());
        BR.setPower(-getRightVelocity() + getLeftShoulder() - getRightShoulder());
    }
}