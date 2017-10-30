package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by raymo on 10/25/17.
 */
@Autonomous
public class ColorTest extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        //lowerJewel();
        moveForward(0.5, 500);
        grabGlyph();

    }
}
