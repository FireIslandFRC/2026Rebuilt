package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.IntakeConstants;

import frc.robot.Configs;
 
public class Indexer extends SubsystemBase{
    private SparkFlex centralizerLeft;
    private SparkFlex centralizerRight;
    private SparkFlex spindexer;

    public Indexer(){
        centralizerLeft = new SparkFlex(IndexerConstants.kCentralizerL, MotorType.kBrushless);
        centralizerRight = new SparkFlex(IndexerConstants.kCentralizerR, MotorType.kBrushless);
        spindexer = new SparkFlex(IndexerConstants.KSpindexer, MotorType.kBrushless);

        centralizerLeft.configure(Configs.SwerveModuleConfig.indexerConfigL, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        centralizerRight.configure(Configs.SwerveModuleConfig.indexerConfigR, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        spindexer.configure(Configs.SwerveModuleConfig.spindexerConfig, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);        
    }

    public void runCentralizer(){
        centralizerLeft.set(-.1);
        centralizerRight.set(-.1);
    }

    public void runCentralizer(double speed){
        centralizerLeft.set(speed); 
        centralizerRight.set(speed);
    }

    public void stopCentralizer(){
        centralizerLeft.set(0); 
        centralizerRight.set(0);
    }

    public void runSpindexer(){
        spindexer.set(.2);
    }

    public void runSpindexer(double speed){
        spindexer.set(speed);
    }

    public void stopSpindexer(){
        spindexer.set(0);

    }
}
