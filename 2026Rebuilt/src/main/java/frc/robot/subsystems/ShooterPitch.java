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
import frc.robot.Vision;

public class ShooterPitch extends SubsystemBase{

    private SparkMax pitchMotor;
    private SparkClosedLoopController pitchPosition;

    public ShooterPitch(){
        pitchMotor = new SparkMax(1, MotorType.kBrushless);
        pitchMotor.configure(Configs.SwerveModuleConfig.drivingConfig, ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        pitchPosition = pitchMotor.getClosedLoopController();

    }

    public void setAngle(double pitchAngle){
        // pitchPosition.setSetpoint(pitchAngle, ControlType.kPosition);
        System.out.println(pitchAngle);
    }


}
