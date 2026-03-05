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

        centralizerLeft.configure(Configs.IndexerConfig.indexerConfigL, ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        centralizerRight.configure(Configs.IndexerConfig.indexerConfigR, ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        spindexer.configure(Configs.IndexerConfig.spindexerConfig, ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);        
    }

    public void setCentralizer(){
        centralizerLeft.set(.8);
        centralizerRight.set(.8);
    }

    public void setCentralizer(double speed){
        centralizerLeft.set(speed); 
        centralizerRight.set(speed);
    }

    public void stopCentralizer(){
        centralizerLeft.set(0); 
        centralizerRight.set(0);
    }

    public void setSpindexer(){
        spindexer.set(.2);
    }

    public void setSpindexer(double speed){
        spindexer.set(speed);
    }

    public void stopSpindexer(){
        spindexer.set(0);
    }
}
