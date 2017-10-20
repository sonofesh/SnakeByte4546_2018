package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by raymo on 10/4/17.
 */

@TeleOp (name = "ShouldersAreMyTeleOp", group = "TeleOp")
public class ShoulderControlsTeleOp extends OpMode {
    double rightMotion = 0;
    double leftMotion = 0;
    boolean halfSpeed = false;
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    DcMotor leftLift;
    DcMotor rightLift;
    Servo leftMani;
    Servo rightMani;
    boolean liftOut = false;

    @Override
    public void init() {
        //FR is port 3, FL is port 2, BR is port 1, BL is port 0
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        leftMani = hardwareMap.servo.get("LMani");
        rightMani = hardwareMap.servo.get("RMani");
    }

    @Override
    public void loop() {
        toggleHalfSpeed();
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

    public void toggleHalfSpeed() {
        if (gamepad1.x){
            if (halfSpeed)
                halfSpeed = false;
            else if (!halfSpeed)
                halfSpeed = true;
        }
    }



    public double getRightShoulder()
    {
        if (gamepad1.right_bumper)
            return 1.0;
        return 0;
    }

    public double getHalfSpeed(){
        if (halfSpeed)
            return 0.5;
        return 1;
    }

   
    public void setPower() {
        FL.setPower(getHalfSpeed()*(getLeftVelocity() + getLeftShoulder() - getRightShoulder()));
        FR.setPower(getHalfSpeed()*(-getRightVelocity() + getLeftShoulder() - getRightShoulder()));
        BL.setPower(getHalfSpeed()*(getLeftVelocity() - getLeftShoulder() + getRightShoulder()));
        BR.setPower(getHalfSpeed()*(-getRightVelocity() - getLeftShoulder() + getRightShoulder()));
    }
}