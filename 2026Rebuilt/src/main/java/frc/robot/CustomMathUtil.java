/*
 * MIT License
 *
 * Copyright (c) PhotonVision
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.SwerveSubsystem;

public class CustomMathUtil {

    private Pose2d targetPose;
    private Rotation2d currentRotation;

    public CustomMathUtil() {
        targetPose = new Pose2d(8, 8, new Rotation2d(0)); // Example target pose
    }

    public double distanceToTarget(Pose2d robotPose) {
        return robotPose.getTranslation().getDistance(targetPose.getTranslation());
    }

    public Rotation2d angleToTarget(Pose2d robotPose) {
        double xDist = targetPose.getX() - robotPose.getX();
        double yDist = targetPose.getY() - robotPose.getY();
        System.out.println(Math.atan2(yDist, xDist));
        return new Rotation2d(Math.atan2(yDist, xDist));
    }

    public static double map(double value, double inMin, double inMax, double outMin, double outMax){
        return(value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

}

    
