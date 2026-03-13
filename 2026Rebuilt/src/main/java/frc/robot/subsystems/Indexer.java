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
import frc.robot.Constants.IntakeConstants;

import frc.robot.Configs;
 
@Logged
public class Indexer extends SubsystemBase{
    private SparkFlex centralizerLeft;
    private SparkFlex centralizerRight;
    private SparkFlex spindexer;
    private SparkClosedLoopController centralizerLeftPID, centralizerRightPID, spindexerPID;

    public Indexer(){
        centralizerLeft  = new SparkFlex(IndexerConstants.kCentralizerL, MotorType.kBrushless);
        centralizerRight = new SparkFlex(IndexerConstants.kCentralizerR, MotorType.kBrushless);
        spindexer        = new SparkFlex(IndexerConstants.KSpindexer,    MotorType.kBrushless);

        centralizerLeft .configure(Configs.IndexerConfig.indexerConfigL,  ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        centralizerRight.configure(Configs.IndexerConfig.indexerConfigR,  ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        spindexer       .configure(Configs.IndexerConfig.spindexerConfig, ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);        
        
        centralizerLeftPID  = centralizerLeft .getClosedLoopController();
        centralizerRightPID = centralizerRight.getClosedLoopController();
        spindexerPID        = spindexer       .getClosedLoopController();
    
    }
    

    public void setCentralizer(){
        centralizerLeft.set(.8);
        centralizerRight.set(.8);
    }

    public void setCentralizer(double speed){
        centralizerLeftPID.setSetpoint(speed * .75, ControlType.kDutyCycle);
        centralizerRightPID.setSetpoint(speed, ControlType.kDutyCycle);
    }

    public void stopCentralizer(){
        centralizerLeft.set(0); 
        centralizerRight.set(0);
    }

    public void setSpindexer(){
        spindexer.set(.1);
    }

    public void setSpindexer(double speed){
        spindexerPID.setSetpoint(speed, ControlType.kDutyCycle);
    }

    public void stopSpindexer(){
        spindexer.set(0);
    }

     public double getCentralizerRPM(){
        return centralizerLeft.getEncoder().getVelocity();
    }

}
