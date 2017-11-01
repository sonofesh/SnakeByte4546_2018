package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by sopa on 10/25/17.
 */
@Autonomous (name = "Blue is the warmest color")
public class JewelHittingAutoBlue extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.addData("Blue", "Ready");
        waitForStart();
        setAlliance('b');
        lowerJewel();
        sleep(2500);
        hitJewel();
        moveForward(-.2,2000);
        sleep(1000);
    }
}
