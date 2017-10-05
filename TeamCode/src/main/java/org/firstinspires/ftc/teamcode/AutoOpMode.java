package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by raymo on 9/24/2017.
 */

public abstract class AutoOpMode extends LinearOpMode {
    double velocity = 0;
    double rotation = 0;
    double strafe = 0;
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    BNO055IMU imu;
    ColorSensor colorSensor;
    //ColorSensor colorSensor2;
    int recCount = 0;

    public void initialize() {
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
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
        colorSensor = hardwareMap.colorSensor.get("color");
        //colorSensor2 = hardwareMap.colorSensor.get("color2");

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

    public double getGyroYaw(double turn) throws InterruptedException{
        double turnAbs = Math.abs(turn);
        Orientation angles = imu.getAngularOrientation();
        if (turnAbs > 270 && Math.abs(angles.firstAngle) < 90)
            return (Math.abs(angles.firstAngle) - (turnAbs - 360));
        else if (turnAbs < 90 && Math.abs(angles.firstAngle) > 270)
            return ((Math.abs(angles.firstAngle) - 360) - turnAbs);
        return (Math.abs(angles.firstAngle) - turnAbs);
    }

    public int getRed(ColorSensor color) {
        return colorSensor.red();
    }

    public int getBlue(ColorSensor color) {
        return colorSensor.blue();
    }

    //true is right
    //false is left
    public String chooseColor(ColorSensor colorLeft, ColorSensor colorRight, char c) {
        recCount++;
        //hitting blue
        if(c == 98) {
            if(getBlue(colorRight) > getBlue(colorLeft)) {
                telemetry.addData("hit", "right");
                telemetry.update();
                return "right";
            }
            else if(getBlue(colorLeft) > getBlue(colorRight)) {
                telemetry.addData("hit", "left");
                telemetry.update();
                return "left";
            }
            else {
                sleep(1000);
                if (recCount < 2)
                    chooseColor(colorLeft, colorRight, c);
                return "broken";
            }
        }
        //hitting red
        if(c == 114) {
            if(getRed(colorRight) > getRed(colorLeft)) {
                telemetry.addData("hit", "right");
                telemetry.update();
                return "right";
            }
            else if(getRed(colorLeft) > getRed(colorRight)) {
                telemetry.addData("hit", "left");
                telemetry.update();
                return "left";
            }
            else {
                sleep(1000);
                if (recCount < 2)
                    chooseColor(colorLeft, colorRight, c);
                return "broken";
            }
        }
        return "broken";
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


}


