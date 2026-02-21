package frc.robot;

import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.Intake;

public final class Configs {
        public static final class SwerveModuleConfig {
                public static final SparkFlexConfig drivingConfig = new SparkFlexConfig();
                public static final SparkFlexConfig turningConfig = new SparkFlexConfig();
                
                public static final SparkMaxConfig intakeConfigL = new SparkMaxConfig();
                public static final SparkMaxConfig intakeConfigR = new SparkMaxConfig();
                public static final SparkFlexConfig intakeConfig = new SparkFlexConfig();

                public static final SparkFlexConfig indexerConfigL = new SparkFlexConfig();
                public static final SparkFlexConfig indexerConfigR = new SparkFlexConfig();
                public static final SparkFlexConfig spindexerConfig = new SparkFlexConfig();

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

                        
                        
                        indexerConfigL 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(false);

                        indexerConfigR 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(false)
                                        .follow(IntakeConstants.kIntakeArmL);

                        spindexerConfig
                                        .idleMode(IdleMode.kCoast)
                                        .smartCurrentLimit(40)
                                        .inverted(false);





                        intakeConfigL 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(true);

                        intakeConfigR 
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(false)
                                        .follow(IntakeConstants.kIntakeArmL);
                        intakeConfigL.closedLoop
                                        .pid(0.6, 0, 0);

                        intakeConfigR.closedLoop
                                        .pid(0.6, 0, 0);

                        intakeConfig
                                        .idleMode(IdleMode.kBrake)
                                        .smartCurrentLimit(40)
                                        .inverted(false);
                                        
                }
        }

}
