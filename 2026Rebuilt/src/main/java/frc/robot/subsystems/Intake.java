package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants.IntakeConstants;

import frc.robot.Configs;
 
public class Intake extends SubsystemBase{
    private SparkFlex intakeMotorLeft;
    private SparkFlex intakeMotorRight;
    private SparkFlex intakeRoller;
    private SparkClosedLoopController intakeMotorLeftController;
    private SparkClosedLoopController intakeMotorRightController;

    public Intake(){
        intakeMotorLeft = new SparkFlex(IntakeConstants.kIntakeArmL, MotorType.kBrushless);
        intakeMotorRight = new SparkFlex(IntakeConstants.kIntakeArmR, MotorType.kBrushless);
        intakeRoller = new SparkFlex(IntakeConstants.kIntakeArmRollers, MotorType.kBrushless);

        intakeMotorLeftController = intakeMotorLeft.getClosedLoopController();
        intakeMotorRightController = intakeMotorRight.getClosedLoopController();

        intakeMotorLeft.configure(Configs.IntakeConfig.intakeConfigL, ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        intakeMotorRight.configure(Configs.IntakeConfig.intakeConfigR, ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        intakeRoller.configure(Configs.IntakeConfig.intakeRollersConfig, ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);        
    }

    public void setAngle(double distance){

        intakeMotorLeftController.setSetpoint(distance, ControlType.kPosition);
        intakeMotorRightController.setSetpoint(distance, ControlType.kPosition);
        // System.out.println(distance);

    }

    public void setIntakeUp(){
        intakeMotorLeftController.setSetpoint(IntakeConstants.kIntakeUpPos, ControlType.kPosition);
        intakeMotorRightController.setSetpoint(IntakeConstants.kIntakeUpPos, ControlType.kPosition);
    }

    public void setIntakeDown(){
        intakeMotorLeftController.setSetpoint(IntakeConstants.kIntakeDownPos, ControlType.kPosition);
        intakeMotorRightController.setSetpoint(IntakeConstants.kIntakeDownPos, ControlType.kPosition);
    }

    public void setIntakeMid(){
        intakeMotorLeftController.setSetpoint(IntakeConstants.kIntakeStowedPos, ControlType.kPosition);
        intakeMotorRightController.setSetpoint(IntakeConstants.kIntakeStowedPos, ControlType.kPosition);
    }

    public void setIntakeUp(double speed){
        intakeMotorLeft.set(speed);
        intakeMotorRight.set(speed);
    }

    public void setIntakeDown(double speed){
        intakeMotorLeft.set(-speed);
        intakeMotorRight.set(-speed);
    }

    public void setIntakeIn(){
        intakeRoller.set(1);
    }
    
    public void setIntakeIn(double speed){
        intakeRoller.set(speed);
    }

    public void setintakeOut(){
        intakeRoller.set(-.3);
    }

    public void setintakeOut(double speed){
        intakeRoller.set(-speed);
    }

    public void stopRollers(){
        intakeRoller.set(0);
    }

    public void stopArms(){
        intakeMotorLeft.set(0);
        intakeMotorRight.set(0);
    }
    
    public double getIntakeLAngle(){
        return intakeMotorLeft.getEncoder().getPosition();
    }

    public double getIntakeRAngle(){
        return intakeMotorRight.getEncoder().getPosition();
    }
}
