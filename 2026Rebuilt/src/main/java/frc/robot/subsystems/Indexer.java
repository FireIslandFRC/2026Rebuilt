package frc.robot.subsystems;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants.IndexerConstants;

import frc.robot.Configs;
 
@Logged
public class Indexer extends SubsystemBase{
    private SparkMax centralizerLeft, centralizerRight, centralizerMid, spindexerMax;
    private SparkFlex spindexer;
    private SparkClosedLoopController centralizerLeftPID, centralizerRightPID, centralizerMidPID, spindexerPID;

    public Indexer(){
        centralizerLeft  = new SparkMax (IndexerConstants.kCentralizerL, MotorType.kBrushless);
        centralizerRight = new SparkMax (IndexerConstants.kCentralizerR, MotorType.kBrushless);
        centralizerMid   = new SparkMax (IndexerConstants.kCentralizerM, MotorType.kBrushless);
        // spindexer        = new SparkFlex(IndexerConstants.KSpindexer,    MotorType.kBrushless);
        spindexerMax        = new SparkMax(IndexerConstants.  KSpindexer,    MotorType.kBrushed);

        centralizerLeft .configure(Configs.IndexerConfig.indexerConfigL,  ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        centralizerRight.configure(Configs.IndexerConfig.indexerConfigR,  ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        centralizerMid  .configure(Configs.IndexerConfig.indexerConfigM,  ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        // spindexer       .configure(Configs.IndexerConfig.spindexerConfig, ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);        
        
        centralizerLeftPID  = centralizerLeft .getClosedLoopController();
        centralizerRightPID = centralizerRight.getClosedLoopController();
        centralizerMidPID   = centralizerMid  .getClosedLoopController();
        // spindexerPID        = spindexer       .getClosedLoopController();
    
    }
    

    public void setCentralizer(){
        centralizerLeft.set(.8);
        centralizerRight.set(.8);
        centralizerMid.set(.8);
    }

    public void setCentralizer(double speed){
        centralizerLeftPID.setSetpoint(speed, ControlType.kDutyCycle);
        centralizerRightPID.setSetpoint(speed, ControlType.kDutyCycle);
        centralizerMidPID.setSetpoint(speed, ControlType.kDutyCycle);
    }

    public void stopCentralizer(){
        centralizerLeft.set(0); 
        centralizerRight.set(0);
        centralizerMid.set(0);
    }

    public void setSpindexer(){
        spindexerMax.set(.1);
    }

    public void setSpindexer(double speed){
        // spindexerPID.setSetpoint(speed, ControlType.kDutyCycle);4
        spindexerMax.set(speed);
    }

    public void stopSpindexer(){
        spindexerMax.set(0);
    }

     public double getCentralizerRPM(){
        return centralizerLeft.getEncoder().getVelocity();
    }

}
