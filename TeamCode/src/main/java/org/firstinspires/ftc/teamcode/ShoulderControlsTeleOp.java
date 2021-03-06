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
    DcMotor liftMani;
    Servo leftMani;
    Servo rightMani;
    Servo jewelHitter;
    boolean liftOut = false;
    Servo leftArm;
    Servo rightArm;
    Servo leftRelic;
    Servo rightRelic;
    double leftRelicPosition;
    double rightRelicPosition;
    long currentTime;
    long lastTime;
    long closeTime;
    //jewelstate 0 is upright, 1 is near upright, 2 is position to hit jewel
    String jewelState = "down";

    @Override
    public void init() {
        /*
        Expansion Hub 1: Motor 0 is BR, Motor 1 is FR, Motor 2 is BL, Motor 3 is FL
        Expansion Hub 1: Servo 0 is RRelicArm, Servo 1 is LRelioArm, Servo 2 is LRelio, Servo 3 is RRelic
        Expansion Hub 1: Servo 4 is LMani, Servo 5 is RMani
        Expansion Hub 2: Motor 0 is LSlide, Motor 1 is liftMani, Motor 2 is RSlide
        Expansion Hub 2: Servo 0 is JewelHitter
        */
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        leftArm = hardwareMap.servo.get("LRelicArm");
        rightArm = hardwareMap.servo.get("RRelicArm");
        leftRelic = hardwareMap.servo.get("LRelic");
        rightRelic = hardwareMap.servo.get("RRelic");
        leftMani = hardwareMap.servo.get("LMani");
        rightMani = hardwareMap.servo.get("RMani");
        rightMani.setDirection(Servo.Direction.FORWARD);
        leftMani.setDirection(Servo.Direction.REVERSE);
        leftLiftSlide = hardwareMap.dcMotor.get("LSlide");
        rightLiftSlide = hardwareMap.dcMotor.get("RSlide");
        liftMani = hardwareMap.dcMotor.get("liftMani");
        jewelHitter = hardwareMap.servo.get("jewelhitter");
        jewelHitter.setDirection(Servo.Direction.REVERSE);
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
        //grapRelic();
        pickRelic();
        useJewel();
        //jewelHitter.setPosition(0.54);
        //telemetry.addData("JewelHitter", jewelHitter.getPosition());
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

    public void useJewel(){
        if (gamepad1.dpad_left){
            jewelHitter.setPosition(0);
        }
        if (gamepad1.dpad_right
                ){
            jewelHitter.setPosition(0.57);
        }
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

    //Relic
    public void setArmPower() {
        if (gamepad2.right_stick_y < -0.2) {
            leftArm.setPosition(1);
            rightArm.setPosition(0);
        }
        else if (gamepad2.right_stick_y > 0.2){
            leftArm.setPosition(0);
            rightArm.setPosition(1);
        }
        else{
            leftArm.setPosition(0.5);
            rightArm.setPosition(0.5);
        }
    }


    //Jewel Hitter (have not tested positions)



    public void setPower() {
        FL.setPower(getHalfSpeed()*(getLeftVelocity() + getLeftShoulder() - getRightShoulder()));
        FR.setPower(getHalfSpeed()*(-getRightVelocity() + getLeftShoulder() - getRightShoulder()));
        BL.setPower(getHalfSpeed()*(getLeftVelocity() - getLeftShoulder() + getRightShoulder()));
        BR.setPower(getHalfSpeed()*(-getRightVelocity() - getLeftShoulder() + getRightShoulder()));
    }

    //relic lift
    public void setLiftSlide(){
        if (gamepad2.left_stick_y < -0.1){
            leftLiftSlide.setPower(-1);
            rightLiftSlide.setPower(1);
        }
        else if (gamepad2.left_stick_y > 0.1){
            rightLiftSlide.setPower(-1);
            leftLiftSlide.setPower(1);
        }
        else{
            rightLiftSlide.setPower(0);
            leftLiftSlide.setPower(0);
        }
    }

    //clamp for glyphs
    public void setManiPower(){
        if (gamepad2.b){
            leftMani.setPosition(1);
            rightMani.setPosition(1);
            closeTime = System.currentTimeMillis();
        }
        else if (System.currentTimeMillis() - closeTime < 1000){
            leftMani.setPosition(0.3);
            rightMani.setPosition(0.3);
        }
        else{
            leftMani.setPosition(0.5);
            rightMani.setPosition(0.5);
        }
    }

    public void grapRelic() {
        if(gamepad2.x) {
            leftRelicPosition += .005;
            rightRelicPosition -= .005;
            leftRelic.setPosition(leftRelicPosition);
            rightRelic.setPosition(rightRelicPosition);
        }
        if(gamepad2.y) {
            leftRelicPosition += .005;
            leftRelicPosition -= .005;
            leftRelic.setPosition(leftRelicPosition);
            rightRelic.setPosition(rightRelicPosition);
        }
    }

    public void pickRelic(){
        if(gamepad2.x) {
            leftRelic.setPosition(1);
            rightRelic.setPosition(0);
        }
        else if (gamepad2.y){
            leftRelic.setPosition(0);
            rightRelic.setPosition(1);
        }
    }

    public void raiseMani() {
        if (gamepad2.dpad_down){
            liftMani.setPower(1);
        }
        else if (gamepad2.dpad_up){
            liftMani.setPower(-1);
        }
        else{
            liftMani.setPower(0);
        }
    }
}