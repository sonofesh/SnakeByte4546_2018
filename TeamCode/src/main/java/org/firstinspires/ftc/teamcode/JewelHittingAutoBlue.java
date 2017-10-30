package org.firstinspires.ftc.teamcode;

/**
 * Created by sopa on 10/25/17.
 */

public class JewelHittingAutoBlue extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        setAlliance('b');
        lowerJewel();
        hitJewel();
    }
}
