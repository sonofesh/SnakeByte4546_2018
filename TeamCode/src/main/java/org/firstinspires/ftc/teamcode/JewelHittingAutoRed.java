package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by sopa on 10/25/17.
 */
@Autonomous (name = "Blood Diamonds")
public class JewelHittingAutoRed extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        setAlliance('r');
        lowerJewel();
        hitJewel();
    }
}
