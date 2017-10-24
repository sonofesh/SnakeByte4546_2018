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
    final int delayToggle = 500;
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    DcMotor leftLiftSlide;
    DcMotor rightLiftSlide;
    Servo leftMani;
    Servo rightMani;
    boolean liftOut = false;
    Servo leftArm;
    Servo rightArm;
    long currentTime;
    long lastTime;

    @Override
    public void init() {
        //Expansion Hub 1: Motor 0 is BR, Motor 1 is FR, Motor 2 is BL, Motor 3 is FL
        //Expansion Hub 1: Servo 0 is RRelicArm, Servo 1 is LRelioArm
        //Expansion Hub 2:
        //Expansion Hub 2: Servo 4 is LMani, Servo 5 is RMani
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        leftArm = hardwareMap.servo.get("LRelicArm");
        rightArm = hardwareMap.servo.get("RRelicArm");
        leftMani = hardwareMap.servo.get("LMani");
        rightMani = hardwareMap.servo.get("RMani");
        leftLiftSlide = hardwareMap.dcMotor.get("LSlide");
        rightLiftSlide = hardwareMap.dcMotor.get("RSlide");
        liftMani = hardwareMap.dcMotor.get("liftMani");
        telemetry.addData("Initialization", "done");
        telemetry.update();
    }

    @Override
    public void loop() {
        setPower();
        setArmPower();
        setManiPower();
        setLiftSlide();
        toggleHalfSpeed();
        raiseMani();
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
        currentTime = System.currentTimeMillis();
        if (gamepad1.x && (currentTime - lastTime) < delayToggle) {
            if (halfSpeed) {
                halfSpeed = false;
                telemetry.addData("halfspeed", "disabled");
                telemetry.update();
            }
            else if (!halfSpeed) {
                halfSpeed = true;
                telemetry.addData("halfspeed", "enabled");
                telemetry.update();
            }
            lastTime = System.currentTimeMillis();
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

    public void setArmPower() {
        if (gamepad2.right_stick_y > 0.1) {
            leftArm.setPosition(1);
            rightArm.setPosition(0);
        }
        else if (gamepad2.right_stick_y < -0.1){
            leftArm.setPosition(0);
            rightArm.setPosition(1);
        }
        else{
            leftArm.setPosition(0.5);
            rightArm.setPosition(0.5);
        }
    }


    public void setPower() {
        FL.setPower(getHalfSpeed()*(getLeftVelocity() + getLeftShoulder() - getRightShoulder()));
        FR.setPower(getHalfSpeed()*(-getRightVelocity() + getLeftShoulder() - getRightShoulder()));
        BL.setPower(getHalfSpeed()*(getLeftVelocity() - getLeftShoulder() + getRightShoulder()));
        BR.setPower(getHalfSpeed()*(-getRightVelocity() - getLeftShoulder() + getRightShoulder()));
    }

    public void setLiftSlide(){
        if (gamepad2.left_stick_y > 0.1){
            leftLiftSlide.setPower(-1);
        }
        else if (gamepad2.left_stick_y < -0.1){
            rightLiftSlide.setPower(1);
        }
        else{
            rightLiftSlide.setPower(0);
            leftLiftSlide.setPower(0);
        }
    }

    public void setManiPower(){
        if (gamepad2.a){
            leftMani.setPosition(1);
            rightMani.setPosition(0);
            telemetry.addData("mani", "open");
            telemetry.update();
        }
        else if (gamepad2.b){
            leftMani.setPosition(0);
            rightMani.setPosition(1);
            telemetry.addData("mani", "closed");
            telemetry.update();
        }
        else{
            leftMani.setPosition(0.7);
            rightMani.setPosition(0.7);
        }
    }

    public void raiseMani(){
        if (gamepad2.right_trigger > 0.1){
            liftMani.setPower(-1);
        }
        else if (gamepad2.left_trigger > 0.1){
            liftMani.setPower(1);
        }
        else{
            liftMani.setPower(0);
        }
    }
}