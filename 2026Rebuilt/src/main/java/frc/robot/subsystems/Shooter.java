package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.PIDCommand;
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

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Servo;

import frc.robot.Configs;
import frc.robot.Constants;
import frc.robot.CustomMathUtil;
import frc.robot.Vision;
 
public class Shooter extends SubsystemBase{

    private Servo pitchServo;
    private SparkFlex flywheel;
    private SparkMax rotation;
    private SparkClosedLoopController flywheelPID;
    private SparkClosedLoopController rotationPID;

    public Shooter(){
        pitchServo = new Servo(Constants.TurretConstants.kPitchServo);

        pitchServo.set(0.06);

        rotation = new SparkMax(Constants.TurretConstants.kRotationMotor, MotorType.kBrushless);
        flywheel= new SparkFlex(Constants.TurretConstants.kFlywheel, MotorType.kBrushless);
        
        flywheel.configure(Configs.TurretConfig.flywheelConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);        
        rotation.configure(Configs.TurretConfig.rotationConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);        
        
        rotationPID = rotation.getClosedLoopController();
        flywheelPID = flywheel.getClosedLoopController();
    }


    /****                   angles                **********/
    public void setShootingAngle(double angle){

        pitchServo.set(angle);
        
    }

    public void setAngleUp(){

        if (pitchServo.getPosition() < .21){
            pitchServo.set(pitchServo.getPosition()+.01);
        }

    }

    public void setAngleDown(){

        if (pitchServo.getPosition() > .05){
            pitchServo.set(pitchServo.getPosition()-.01);
        }

    }

    public void setPitchZero()
    {
        pitchServo.set(0);

    }

    /********                    rotate                  *********/
    public void setRotationZero(){
        
        rotation.getEncoder().setPosition(0);

    }

    public void turretAngle(double wantedAngle){

        if (rotation.getEncoder().getPosition() > -.4 && rotation.getEncoder().getPosition() < .4){
            rotationPID.setSetpoint(wantedAngle, ControlType.kPosition);
        }

    }

    public void setTurretRight(){

        rotation.set(-.4);

    }

    public void setTurretLeft(){

        rotation.set(.4);

    }

    public void stopTurret(){
        rotation.stopMotor();
    }

    public double getTurretAngle(){
        return rotation.getEncoder().getPosition();
    }

    public double getPitchAngle(){
        return pitchServo.getPosition();
    }

    /***************                    flywheel                   ****************/
    public void setShootingSpeed(double speed){
        flywheelPID.setSetpoint(speed, ControlType.kDutyCycle);
    }

    public void stopFlywheel(){
        flywheel.set(0);
    }

}
