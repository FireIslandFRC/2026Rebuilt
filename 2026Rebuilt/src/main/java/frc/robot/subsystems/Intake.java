package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants.IntakeConstants;

import frc.robot.Configs;
 
public class Intake extends SubsystemBase{
    private SparkMax intakeMotorLeft;
    private SparkMax intakeMotorRight;
    private SparkFlex intakeMotor;
    private SparkClosedLoopController intakeMotorLeftController;
    private SparkClosedLoopController intakeMotorRightController;

    public Intake(){
        intakeMotorLeft = new SparkMax(IntakeConstants.kIntakeArmL, MotorType.kBrushless);
        intakeMotorRight = new SparkMax(IntakeConstants.kIntakeArmR, MotorType.kBrushless);
        intakeMotor = new SparkFlex(IntakeConstants.kIntakeArmRollers, MotorType.kBrushless);

        intakeMotorLeftController = intakeMotorLeft.getClosedLoopController();
        intakeMotorRightController = intakeMotorRight.getClosedLoopController();

        intakeMotorLeft.configure(Configs.SwerveModuleConfig.intakeConfigL, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        intakeMotorRight.configure(Configs.SwerveModuleConfig.intakeConfigR, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        intakeMotor.configure(Configs.SwerveModuleConfig.intakeConfigR, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);        
    }

    public void setAngle(double distance){
        //pitchPosition.setSetpoint(distance, ControlType.kPosition);
        System.out.println(distance);
    }

    public void intakeUp(){
        intakeMotorLeftController.setSetpoint(IntakeConstants.kIntakeUpPos, ControlType.kPosition);
        intakeMotor.set(0);
    }

    public void intakeDown(){
        intakeMotorLeftController.setSetpoint(IntakeConstants.kIntakeDownPos, ControlType.kPosition);
        intakeMotor.set(.3);
    }

    public void intakeIn(){
        intakeMotor.set(.3);
    }

    public void intakeOut(){
        intakeMotor.set(-.3);
    }

    public void stop(){
        intakeMotorLeft.set(0);
        intakeMotorRight.set(0);
        intakeMotor.set(0);
    }

    public void stopArms(){
        intakeMotorLeft.set(0);
        intakeMotorRight.set(0);
    }
    
    public double getIntakeAngle(){
        return intakeMotorLeft.getEncoder().getPosition();    
    }
}
