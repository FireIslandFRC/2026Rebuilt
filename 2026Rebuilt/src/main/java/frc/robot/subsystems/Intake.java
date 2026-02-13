package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Configs;
 
public class Intake extends SubsystemBase{

    private SparkFlex intakeMotorLeft;
    private SparkFlex intakeMotorRight;

    public Intake(){
        intakeMotorLeft = new SparkFlex(41, MotorType.kBrushless);
        intakeMotorRight = new SparkFlex(42, MotorType.kBrushless);
        intakeMotorLeft.configure(Configs.SwerveModuleConfig.intakeConfigL, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        intakeMotorRight.configure(Configs.SwerveModuleConfig.intakeConfigR, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
    }

    public void setAngle(double distance){
        //pitchPosition.setSetpoint(distance, ControlType.kPosition);
        System.out.println(distance);
    }

    public void IntakeUp(){
        System.out.println("up");
        intakeMotorLeft.set(-.5);
        intakeMotorRight.set(-.5);
        System.out.println(intakeMotorLeft.getEncoder().getPosition());
    }

    public void IntakeDown(){
        intakeMotorLeft.set(.5);
        //System.out.println("down");
    }

    public void stop(){
        intakeMotorLeft.set(0);
        System.out.println("Why");
    }
    
    public double getAngle(){
        return intakeMotorLeft.getEncoder().getPosition();    
    }
}
