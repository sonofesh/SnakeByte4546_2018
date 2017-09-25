
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by raymo on 9/20/2017.
 */
@TeleOp
public class MecanumThing extends OpMode{
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
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
    }

    @Override
    public void loop() {
        setPower();
    }

    public double getVelocity() {
        if(Math.abs(gamepad1.right_stick_x)>0.05)

            return gamepad1.right_stick_x;
        return 0;
    }

    public double getRotation() {
        if(Math.abs(gamepad1.right_stick_y)>0.05)
            return gamepad1.right_stick_y;
        return 0;
    }
    public double getStrafe() {
        if(Math.abs(gamepad1.left_stick_y)>0.05)
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

