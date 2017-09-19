package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by sopa on 9/18/17.
 */

public abstract class DriveTrain extends LinearOpMode {
    //variables
    double velocity = 0;
    double rotation = 0;
    double strafe = 0;
    //motors
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;

    public void setPower() {
        FL.setPower(velocity - rotation - strafe);
        FR.setPower(velocity + rotation + strafe);
        BL.setPower(velocity - rotation + strafe);
        BR.setPower(velocity + rotation - strafe);
    }



}
