package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by raymo on 9/27/2017.
 */


@Autonomous(name = "ProteinIsMyAuto")
public class DriveTrainTest extends DriveTrain {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
       /* moveForward(1);
        sleep(800);
        setZero();
        turn(1);
        sleep(700);
        setZero();
        moveForward(1);
        sleep(400);
        setZero();
        moveForward(-1);
        sleep(400);
        setZero();*/
        setPower(0.5, 0, calculateStrafe(0.5, 45);
        sleep(1000);
        setZero();

       ))

    }
}
