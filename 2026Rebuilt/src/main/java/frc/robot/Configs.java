package frc.robot;

import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Constants.SwerveConstants;

public final class Configs {
        public static final class SwerveModuleConfig {
                public static final SparkFlexConfig drivingConfig = new SparkFlexConfig();
                public static final SparkFlexConfig turningConfig = new SparkFlexConfig();
                public static final SparkMaxConfig pitchConfig = new SparkMaxConfig();
                public static final SparkMaxConfig intakeConfigL = new SparkMaxConfig();
                public static final SparkMaxConfig intakeConfigR = new SparkMaxConfig();
                public static final SparkFlexConfig intakeConfig = new SparkFlexConfig();

                static {

                        drivingConfig 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(true);
                        drivingConfig.encoder
                                        .positionConversionFactor(SwerveConstants.DRIVE_ENCODER_POSITION_CONVERSION) // meters   CHECKME make sure right conversion
                                        .velocityConversionFactor(SwerveConstants.DRIVE_ENCODER_VELOCITY_CONVERSION); // meters per second

                        intakeConfigL 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(true);

                        intakeConfigR 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(false);
                                        //.follow(41);
                        intakeConfigL.closedLoop
                                        .pid(0.6, 0, 0);

                        intakeConfigR.closedLoop
                                        .pid(0.6, 0, 0);

                        intakeConfig
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(false);

                        turningConfig
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(20)
                                        .inverted(false); //NOTE: DONT FORGET

                        pitchConfig
                                        .idleMode(IdleMode.kCoast)
                                        .smartCurrentLimit(40)
                                        .inverted(false);
                        pitchConfig.closedLoop
                                        .pid(0.6, 0, 0);
                                        
                }
        }

}
