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
import edu.wpi.first.wpilibj.Servo;

import frc.robot.Configs;
import frc.robot.Vision;
 
public class Shooter extends SubsystemBase{

    private Servo pitchMotor;
    private Servo turretMotor;
    private SparkMax flywheelMotor;
    private SparkMax intake1;
    private SparkMax intake2;


    public Shooter(){
        pitchMotor = new Servo(1);
        turretMotor = new Servo(2);
        flywheelMotor = new SparkMax(4, MotorType.kBrushless);
        intake1 = new SparkMax(2, MotorType.kBrushless);
        intake2 = new SparkMax(3, MotorType.kBrushless);
    }

    public void setShootingAngle(double angle){
        pitchMotor.set(angle);
        System.out.println(angle);
    }

    public void angleUp(){
        pitchMotor.set(pitchMotor.getPosition()+.01);
    }

    public void angleDown(){
        pitchMotor.set(pitchMotor.getPosition()-.01);
        System.out.println("down");
    }

    public void turretAngle(double angle){
        turretMotor.set(angle);
        System.out.println(angle);
    }

    public void angleLeft(){
        turretMotor.set(turretMotor.getPosition()+.05);
    }

    public void angleRight(){
        turretMotor.set(turretMotor.getPosition()-0.05);
        System.out.println("down");
    }

    public void angleStop(){
       // pitchMotor.set(0);
    }

    public void setShootingSpeed(double speed){
        flywheelMotor.set(speed);
    }

    public void stopFlywheel(){
        flywheelMotor.set(0);
    }

    public void setIntake(){
        intake1.set(1);
        intake2.set(-1);
    }

    public void stopIntake(){
        intake1.set(0);
        intake2.set(0);
    }

}
