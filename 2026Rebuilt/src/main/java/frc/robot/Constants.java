// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {

  public static final int PhID = 15;

  public static class ControllerConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
  }

  public static class Vision {
    public static final String kCameraName = "YOUR CAMERA NAME";
    // Cam mounted facing forward, half a meter forward of center, half a meter up from center.
    public static final Transform3d kRobotToCam =
            new Transform3d(new Translation3d(0, 0.0, 0), new Rotation3d(0, 0, 0)); //NOTE: Camera translation
    // The layout of the AprilTags on the field
    public static final AprilTagFieldLayout kTagLayout =
            AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);
    // The standard deviations of our vision estimated poses, which affect correction rate
    // (Fake values. Experiment and determine estimation noise on an actual robot.)
    public static final Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(4, 4, 8);
    public static final Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.5, 0.5, 1);
  }


  public static class SwerveConstants {
    //SDS L2 
    public static final boolean ROTATION_ENCODER_DIRECTION = false; //CHECKME not sure how works?

    public static final int PIGEON_ID = 30;

    /* * * MEASUREMENTS * * */
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double TRACK_WIDTH = Units.inchesToMeters(25);
    public static final double WHEEL_BASE = Units.inchesToMeters(25); //FIXME update
    
    public static final double DRIVE_GEAR_RATIO = 5.27 / 1;
    public static final double ROTATION_GEAR_RATIO = 26 / 1;
    
    public static final double VOLTAGE = 7.2;

    /* * * SWERVE DRIVE KINEMATICS * * */
    // ORDER IS ALWAYS FL, BL, FR, BR 
    //pos x is out in front, pos y is to the left CHECKME
    public static final SwerveDriveKinematics DRIVE_KINEMATICS = new SwerveDriveKinematics(
      
    //  // front left
    //   new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2),
    //   // back left
    //   new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2),
    //   // front right
    //   new Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2),
    //   // back right
    //   new Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2)

      // front right
      new Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2),
      // back left
      new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2),
      // front left
      new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2),
      // back right
      new Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2)
    );

    /* * * FRONT LEFT * * */
    public static class FrontLeft {
      public static final int DRIVE_PORT = 22;
      public static final int ROTATION_PORT = 21;
      public static final int ABSOLUTE_ENCODER_PORT = 31;
      public static final double OFFSET = 107.57;
      public static final boolean DRIVE_INVERTED = false;
      public static final boolean ROTATION_INVERTED = true;

      public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_PORT, ROTATION_PORT, ABSOLUTE_ENCODER_PORT, OFFSET, DRIVE_INVERTED, ROTATION_INVERTED);
    }

    /* * * FRONT RIGHT * * */
    public static class FrontRight {
      public static final int DRIVE_PORT = 24;
      public static final int ROTATION_PORT = 23;
      public static final int ABSOLUTE_ENCODER_PORT = 32;
      public static final double OFFSET = 41.2;
      public static final boolean DRIVE_INVERTED = false; 
      public static final boolean ROTATION_INVERTED = true; 

      public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_PORT, ROTATION_PORT, ABSOLUTE_ENCODER_PORT, OFFSET, DRIVE_INVERTED, ROTATION_INVERTED);
    }

    /* * * BACK LEFT * * */
    public static class BackLeft {
      public static final int DRIVE_PORT = 28;
      public static final int ROTATION_PORT = 27;
      public static final int ABSOLUTE_ENCODER_PORT = 34;
      public static final double OFFSET = 75.95;
      public static final boolean DRIVE_INVERTED = false;
      public static final boolean ROTATION_INVERTED = true; 

      public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_PORT, ROTATION_PORT, ABSOLUTE_ENCODER_PORT, OFFSET, DRIVE_INVERTED, ROTATION_INVERTED);
    }

    /* * * BACK RIGHT * * */
    public static class BackRight {
      public static final int DRIVE_PORT = 26;
      public static final int ROTATION_PORT = 25;
      public static final int ABSOLUTE_ENCODER_PORT = 33;
      public static final double OFFSET = -85.7;
      public static final boolean DRIVE_INVERTED = false; 
      public static final boolean ROTATION_INVERTED = true; 

      public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_PORT, ROTATION_PORT, ABSOLUTE_ENCODER_PORT, OFFSET, DRIVE_INVERTED, ROTATION_INVERTED);
    }

    /* * * CONVERSIONS FOR ENCODERS * * */
    //velocity in meters per sec instead of RPM 
    public static final double DRIVE_ENCODER_POSITION_CONVERSION = ((2 * Math.PI * (WHEEL_DIAMETER/2))) / DRIVE_GEAR_RATIO; //drive enc rotation
    //velocity in meters instead of rotations 
    public static final double DRIVE_ENCODER_VELOCITY_CONVERSION = DRIVE_ENCODER_POSITION_CONVERSION / 60; //drive enc speed 

    /* * * PID VALUES FOR TURNING MOTOR PID * * */
    public static final double KP_TURNING = 0.01;
    public static final double KI_TURNING = 0.00;
    public static final double KD_TURNING = 0.00;

    /* * * MAX * * */
    public static final double MAX_SPEED = 5.5; //12.0 ft/s CHECKME
    public static final double MAX_ROTATION = MAX_SPEED / Math.hypot(TRACK_WIDTH / 2.0, WHEEL_BASE / 2.0);

  }
}