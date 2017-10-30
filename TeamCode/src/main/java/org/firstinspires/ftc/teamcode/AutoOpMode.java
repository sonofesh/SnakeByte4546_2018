package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

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
 * Created by raymo on 9/24/2017.
 */

public abstract class AutoOpMode extends LinearOpMode {
    double velocity = 0;
    double rotation = 0;
    double strafe = 0;
    VuforiaLocalizer vuforia;
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
    Servo leftArm;
    Servo rightArm;
    Servo leftRelic;
    Servo rightRelic;
    BNO055IMU imu;
    ColorSensor colorFront;
    ColorSensor colorBack;
    int recCount = 0;
    int cameraMonitorViewId;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;
    String cryptoboxKey;
    VuforiaLocalizer.Parameters parameters;
    char alliance;
    long closeTime;


    public void initialize() {
        //FL is 0, BL is 1, FR is 2, BR is 3
        //Jewel is 0
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
        //gyro init
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        //color sensor init
        colorFront = hardwareMap.colorSensor.get("color");
        colorBack = hardwareMap.colorSensor.get("color2");
    }

    public void prepareVuforia(){
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
    }

    public void setPower(double velocity, double rotation, double strafe){
        FL.setPower(velocity - rotation + strafe);
        FR.setPower(-velocity - rotation + strafe);
        BL.setPower(velocity - rotation - strafe);
        BR.setPower(-velocity - rotation - strafe);
    }

    public void setZero() {
        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    public double calculateStrafe (double velocity, double angle) {
        return Math.tan(angle)/velocity;
    }

    public double getGyroYaw() throws InterruptedException{
        Orientation angles = imu.getAngularOrientation();
        return (angles.firstAngle * -1);
    }

    public double getGyroPitch() throws InterruptedException {
        Orientation angles = imu.getAngularOrientation();
        return (angles.thirdAngle * -1);
    }

    public double getGyroYaw(double turn) throws InterruptedException{
        double turnAbs = Math.abs(turn);
        Orientation angles = imu.getAngularOrientation();
        return angles.firstAngle;
//        if (turnAbs > 270 && Math.abs(angles.firstAngle) < 90)
//            return (Math.abs(angles.firstAngle) - (turnAbs - 360));
//        else if (turnAbs < 90 && Math.abs(angles.firstAngle) > 270)
//            return ((Math.abs(angles.firstAngle) - 360) - turnAbs);
//        return (Math.abs(angles.firstAngle) - turnAbs);
    }

    public int getRed(ColorSensor color) {
        return color.red();
    }

    public int getBlue(ColorSensor color) {
        return color.blue();
    }

    public void lowerJewel() {
        jewelHitter.setPosition(1);
    }

    public void raiseJewel() {jewelHitter.setPosition(0);
    }

    public void setAlliance(char c) {
        alliance = c;
    }

    //if we're on the blue alliance we want to hit the red ball
    public String chooseColor(char c) {
        //hitting blue
        if(c == 114) {
            if(getBlue(colorFront) < getBlue(colorBack)) {
                telemetry.addData("hit", "forwards");
                telemetry.update();
                return "forwards";
            }
            else if(getBlue(colorFront) > getBlue(colorBack)) {
                telemetry.addData("hit", "backwards");
                telemetry.update();
                return "backwards";
            }
            else {
                sleep(1000);
                if (recCount < 2)
                    recCount++;
                    chooseColor(c);
                telemetry.addData("ColorSensors", "broken");
                telemetry.update();
                return "broken";

            }
        }
        //hitting red
        if(c == 98) {
            if(getRed(colorFront) < getRed(colorBack)) {
                telemetry.addData("hit", "forwards");
                telemetry.update();
                return "forwards";
            }
            else if(getRed(colorFront) > getRed(colorBack)) {
                telemetry.addData("hit", "backwards");
                telemetry.update();
                return "backwards";
            }
            else {
                sleep(1000);
                if (recCount < 2)
                    recCount++;
                    chooseColor(c);
                telemetry.addData("ColorSensors", "broken");
                telemetry.update();
                return "broken";
            }
        }
        return "broken";
    }

    public void hitJewel() throws InterruptedException {
        if (chooseColor(alliance).equals("forwards")) {
            //park in safe zone
            moveForward(0.25, 3000);
        }
        else if (chooseColor(alliance).equals("backwards")) {
            moveForward(-0.25, 500);
            moveForward(.25, 4000);
        }

    }

    public int getAvgEncoder(){
        return (Math.abs(FL.getCurrentPosition()) + Math.abs(FR.getCurrentPosition()))/2;
    }
    public void moveForward(double velocity) {
        setPower(velocity, 0, 0);
    }

    public void moveAtAngle(double velocity, double angle){
        setPower(velocity, 0, calculateStrafe(velocity, angle));
    }

    public void turn(double rotation){
        setPower(0,rotation,0);
    }

    public void moveForward(double velocity, int distance) throws InterruptedException{
        int startPos = getAvgEncoder();
        while(Math.abs(getAvgEncoder() - startPos) < distance) {
            moveForward(velocity);
            idle();
        }
        setZero();
        if (Math.abs(getAvgEncoder() - startPos) > distance + 50){
            telemetry.addData("overshoot", "fix");
            telemetry.update();
        }
    }

    public void turn(double rotation, double angle) throws InterruptedException{
        double first = getGyroYaw();
        while (Math.abs(getGyroYaw() - first) < angle){
            turn(rotation);
            idle();
        }
        setZero();
    }

    public void turnPID(double angle) throws InterruptedException {
        double p = 0.005;
        double i = 0.00001;
        double startTime = System.currentTimeMillis();
        double power = 0;
        double integral = 0;
        double proportion = 0;
        double start = getGyroYaw();
        double current = getGyroYaw();
        while (Math.abs(current - start) < angle && Math.abs(System.currentTimeMillis() - start) < 500){
            integral += Math.abs(current - start) * Math.abs(System.currentTimeMillis() - start) * i;
            proportion = p * Math.abs(current - start);
            power = proportion + integral;
            turn(power);
            idle();
        }
        setZero();
    }

    public void scanImage()
    {
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
    }

    public void moveToDropBlock(String place) throws InterruptedException {
        scanImage();
        if (place.equals("left")) {
            moveForward(.2, 1000);
        }
        else if (place.equals("middle")) {
            moveForward(.2,2000);
        }
        else
            moveForward(.2,2000);
    }

    public void grabGlyph(){
        closeTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - closeTime < 1000) {
            leftMani.setPosition(1);
            rightMani.setPosition(1);
        }
        leftMani.setPosition(0.5);
        rightMani.setPosition(0.5);
    }

    public void releaseGlyph() {
        closeTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - closeTime < 1000) {
            leftMani.setPosition(0.3);
            rightMani.setPosition(0.3);
            leftMani.setPosition(0.5);
            rightMani.setPosition(0.5);
        }
    }

}


