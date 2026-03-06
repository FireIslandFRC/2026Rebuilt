package frc.robot;

import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.SwerveConstants;

public final class Configs {
        public static final class SwerveConfig {
                public static final SparkFlexConfig drivingConfig = new SparkFlexConfig();
                public static final SparkFlexConfig turningConfig = new SparkFlexConfig();

                static {

                        drivingConfig 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(false);
                        drivingConfig.encoder
                                        .positionConversionFactor(SwerveConstants.DRIVE_ENCODER_POSITION_CONVERSION) // meters   CHECKME make sure right conversion
                                        .velocityConversionFactor(SwerveConstants.DRIVE_ENCODER_VELOCITY_CONVERSION); // meters per second

                        turningConfig
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(20)
                                        .inverted(false); //NOTE: DONT FORGET
                        
                }
        }

        public static final class IntakeConfig {
                
                public static final SparkFlexConfig intakeConfigL = new SparkFlexConfig();
                public static final SparkFlexConfig intakeConfigR = new SparkFlexConfig();
                public static final SparkFlexConfig intakeRollersConfig = new SparkFlexConfig();

                static {

                        intakeConfigL 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(true);

                        intakeConfigR 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(false);


                        intakeConfigL.closedLoop
                                        .pid(0.6, 0, 0);

                        intakeConfigR.closedLoop
                                        .pid(0.6, 0, 0);


                        intakeConfigL.encoder
                                        .positionConversionFactor(16);

                        intakeConfigR.encoder
                                        .positionConversionFactor(16);


                        intakeRollersConfig
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(true);
                                        
                }
        }

        public static final class IndexerConfig {

                public static final SparkFlexConfig indexerConfigL = new SparkFlexConfig();
                public static final SparkFlexConfig indexerConfigR = new SparkFlexConfig();
                public static final SparkFlexConfig spindexerConfig = new SparkFlexConfig();

                static {
                        
                        indexerConfigL 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(false);

                        indexerConfigR 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(true);

                        spindexerConfig
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(true);

                }
        }

        public static final class TurretConfig {

                public static final SparkFlexConfig flywheelConfig = new SparkFlexConfig();
                public static final SparkFlexConfig rotationConfig = new SparkFlexConfig();

                static {

                        flywheelConfig 
                                        .idleMode(IdleMode.kCoast)
                                        .smartCurrentLimit(40)
                                        .inverted(true);

                        rotationConfig 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(true);
                        rotationConfig.encoder
                                        .positionConversionFactor(81*3);
                                        
                }
        }

}
