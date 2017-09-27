package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by raymo on 9/27/2017.
 */
@Autonomous(name = "ProteinAuto")
public class DriveTrainTest extends DriveTrain {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        long currentTime = System.currentTimeMillis();
        while (currentTime < 2000){
            currentTime = System.currentTimeMillis();
            moveForward(1);
        }
        setZero();
        while (currentTime < 4000){
            currentTime = System.currentTimeMillis();
            turn(1);
        }
        setZero();
        while (currentTime < 6000){
            currentTime = System.currentTimeMillis();
            turn(-1);
        }
        setZero();
        while (currentTime < 8000){
            currentTime = System.currentTimeMillis();
            moveForward(-1);
        }
        setZero();
    }
}
