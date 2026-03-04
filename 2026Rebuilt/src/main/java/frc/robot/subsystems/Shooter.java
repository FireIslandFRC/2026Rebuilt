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

    private Servo pitchMotor;
    private Servo turretMotor;
    private SparkFlex flywheelMotorRotate;
    private SparkMax rotationMotor;
    private SparkMax intake1;
    private SparkMax intake2;
    private PIDController shooterSpeedPID;
    private SparkClosedLoopController flyWheelPID;
    private SparkClosedLoopController rotationPID;
    private double angle;


    public Shooter(){
        pitchMotor = new Servo(Constants.TurretConstants.kPitchServo);
        // turretMotor = new Servo(Constants.TurretConstants.kRotationServo);
        // turretMotor.set(0.4);
        // intake1 = new SparkMax(2, MotorType.kBrushless);
        // intake2 = new SparkMax(3, MotorType.kBrushless);

        pitchMotor.set(0.06);

        rotationMotor = new SparkMax(Constants.TurretConstants.kRotationMotor, MotorType.kBrushless);
        // rotationMotor.configure(Configs.EEConfig.wristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);        
        rotationPID = rotationMotor.getClosedLoopController();
        flywheelMotorRotate = new SparkFlex(Constants.TurretConstants.kFlywheel, MotorType.kBrushless);
        flyWheelPID = flywheelMotorRotate.getClosedLoopController();
        shooterSpeedPID  = new PIDController(1,0,0);
    }


    /****                   angles                **********/
    public void setShootingAngle(double angle){

        pitchMotor.set(angle);
        System.out.println(angle);
    }

    public void angleUp(){
        if (pitchMotor.getPosition() < .21){
            pitchMotor.set(pitchMotor.getPosition()+.01);
        }
    }

    public void angleDown(){
        if (pitchMotor.getPosition() > .05){
            pitchMotor.set(pitchMotor.getPosition()-.01);
        }
    }

    public void pitchZero(){
        pitchMotor.set(0);
    }

    /********                    rotate                  *********/
    public void angleZero(){
        turretMotor.set(.5);
    }

    public void turretAngle(double wantedAngle){
        if (turretMotor.getPosition() > .3 && turretMotor.getPosition() < .7){
            turretMotor.set(-1*(((wantedAngle/360)*.22*3)+.5));
        }
        // turretMotor.set(-1*(((wantedAngle/360)*.22*3)+.5));
        System.out.println(wantedAngle);
    }

    public void turretRight(){
        rotationMotor.set(-.4);

        // if (turretMotor.getPosition() > .3){
        // if (turretMotor.getPosition() > .3 && turretMotor.getPosition() < .7){
            // turretMotor.set(turretMotor.getPosition() - .05);
            // System.out.println(turretMotor.getPosition());
        // }
    }

    public void turretLeft(){
        rotationMotor.set(.4);

        // if (turretMotor.getPosition() > .3 && turretMotor.getPosition() < .7){
        // if (turretMotor.getPosition() < .7){
            // turretMotor.set(turretMotor.getPosition() + .05);
            // System.out.println(turretMotor.getPosition());
        // } 
    }

    public void turretStop(){
        rotationMotor.stopMotor();
    }

    public double getTurretAngle(){
        return turretMotor.getPosition();
    }

    public double getPitchAngle(){
        return pitchMotor.getPosition();
    }

    public void angleStop(){
       // pitchMotor.set(0);
    }

    /***************                    flywheel                   ****************/
    public void setShootingSpeed(double speed){
        flyWheelPID.setSetpoint(speed, ControlType.kDutyCycle);
    }

    public void stopFlywheel(){
        flywheelMotorRotate.set(0);
    }

}
