package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.security.PublicKey;

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
import frc.robot.Vision;
 
public class ShooterPitch extends SubsystemBase{

    private SparkMax pitchMotor;
    private SparkClosedLoopController pitchPosition;

    public ShooterPitch(){
        pitchMotor = new SparkMax(49, MotorType.kBrushless);
        pitchMotor.configure(Configs.SwerveModuleConfig.pitchConfig, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        pitchPosition = pitchMotor.getClosedLoopController();
    }

    public void setAngle(double distance){
        //pitchPosition.setSetpoint(distance, ControlType.kPosition);
        System.out.println(distance);
    }

    public void angleUp(){
        pitchMotor.set(1);
    }

    public void angleDown(){
        pitchMotor.set(-1);
        System.out.println("down");
    }

    public void angleStop(){
        pitchMotor.set(0);
    }
    public double getAngle(){
return pitchMotor.getEncoder().getPosition();    }


}
