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
import frc.robot.CustomMathUtil;
import frc.robot.Vision;
 
public class Shooter extends SubsystemBase{

    private Servo pitchMotor;
    private Servo turretMotor;
    private SparkFlex flywheelMotorRotate;
    private SparkMax intake1;
    private SparkMax intake2;
    private PIDController shooterSpeedPID;
    private double angle;


    public Shooter(){
        pitchMotor = new Servo(1);
        turretMotor = new Servo(2);
        flywheelMotorRotate = new SparkFlex(4, MotorType.kBrushless);
        intake1 = new SparkMax(2, MotorType.kBrushless);
        intake2 = new SparkMax(3, MotorType.kBrushless);
        shooterSpeedPID  = new PIDController(1,0,0);
    }


    /****                   angles                **********/
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

    /********                    rotate                  *********/
    public void turretAngle(double wantedAngle){
        double workingAngle = CustomMathUtil.map(wantedAngle, -765, 765, 0, 1);
        turretMotor.set(workingAngle);
        System.out.println("is this working");
    }

    public void angleZero(){
        angle = .5;
        turretMotor.set(angle);
    }

    public void angleNinedy(double wantedAngle){
        angle = 135;
        turretMotor.set((((wantedAngle/360)*.22*3)+.5));
        // turretMotor.set(1);
        System.out.println("down");
    }

    public double getTurretAngle(){
        return turretMotor.getPosition();
    }

    public void angleStop(){
       // pitchMotor.set(0);
    }

    /***************                    flywheel                   ****************/
    public void setShootingSpeed(double speed){
        flywheelMotorRotate.set(speed);
    }

    public void stopFlywheel(){
        flywheelMotorRotate.set(0);
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
