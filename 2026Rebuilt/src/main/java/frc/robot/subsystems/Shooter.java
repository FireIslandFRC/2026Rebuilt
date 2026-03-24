package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants;
import frc.robot.CustomMathUtil;
import frc.robot.RobotStates;
import frc.robot.RobotStates.TurretState;
 
public class Shooter extends SubsystemBase{

    private SparkFlex flywheel;
    private SparkMax rotation, pitch;
    private SparkClosedLoopController flywheelPID;
    private SparkClosedLoopController rotationPID, pitchPID;
    private double pitchEncoder;

    public Shooter(){
        pitch = new SparkMax(Constants.TurretConstants.kPitchMotor, MotorType.kBrushed);

        rotation = new SparkMax(Constants.TurretConstants.kRotationMotor, MotorType.kBrushless);
        flywheel= new SparkFlex(Constants.TurretConstants.kFlywheel, MotorType.kBrushless);
        
        flywheel.configure(Configs.TurretConfig.flywheelConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);        
        rotation.configure(Configs.TurretConfig.rotationConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);        
        pitch.configure(Configs.TurretConfig.pitchConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);        
        
        rotationPID = rotation.getClosedLoopController();
        flywheelPID = flywheel.getClosedLoopController();
        pitchPID = pitch.getClosedLoopController();


        pitchEncoder = pitch.getAbsoluteEncoder().getPosition();
    }


    /****                   angles                **********/
    public void setShootingAngle(double angle){

        pitchPID.setSetpoint(angle, ControlType.kPosition);
        
    }

    public void setShootingAngleAndSpeed(double distance){
        switch(RobotStates.getTurretState()){
            case SHOOT_HUB:

                if (distance < 3.0){
                    pitchPID.setSetpoint(.157, ControlType.kPosition);
                    flywheelPID.setSetpoint(-(.45 + .05 * distance), ControlType.kDutyCycle);
                }else if(distance >= 3.0){
                    pitchPID.setSetpoint((0.0171429 * distance * distance) - 0.106571 * distance + 0.322429, ControlType.kPosition);
                    flywheelPID.setSetpoint(-.6, ControlType.kDutyCycle);
                }
                break;

            case SHOOT_BACKLEFT:

                if (distance < 12.0){
                    pitchPID.setSetpoint(.22, ControlType.kPosition);
                    flywheelPID.setSetpoint(-.6, ControlType.kDutyCycle);
                }else if(distance >= 12.0){
                    pitchPID.setSetpoint(.27, ControlType.kPosition);
                    flywheelPID.setSetpoint(-1, ControlType.kDutyCycle);
                }
                break;
                
            case SHOOT_BACKRIGHT:

                if (distance < 12.0){
                    pitchPID.setSetpoint(.22, ControlType.kPosition);
                    flywheelPID.setSetpoint(-.6, ControlType.kDutyCycle);
                }else if(distance >= 12.0){
                    pitchPID.setSetpoint(.27, ControlType.kPosition);
                    flywheelPID.setSetpoint(-1, ControlType.kDutyCycle);
                }
                break;
            case AIM_HUB:

                if (distance < 3.0){
                    pitchPID.setSetpoint(.157, ControlType.kPosition);
                    flywheelPID.setSetpoint(-(.45 + .05 * distance), ControlType.kDutyCycle);
                }else if(distance >= 3.0){
                    pitchPID.setSetpoint((0.0171429 * distance * distance) - 0.106571 * distance + 0.322429, ControlType.kPosition);
                    flywheelPID.setSetpoint(-.6, ControlType.kDutyCycle);
                }
                break;

            case AIM_BACKLEFT:

                if (distance < 12.0){
                    pitchPID.setSetpoint(.22, ControlType.kPosition);
                    flywheelPID.setSetpoint(-.6, ControlType.kDutyCycle);
                }else if(distance >= 12.0){
                    pitchPID.setSetpoint(.27, ControlType.kPosition);
                    flywheelPID.setSetpoint(-1, ControlType.kDutyCycle);
                }
                break;
                
            case AIM_BACKRIGHT:

                if (distance < 12.0){
                    pitchPID.setSetpoint(.22, ControlType.kPosition);
                    flywheelPID.setSetpoint(-.6, ControlType.kDutyCycle);
                }else if(distance >= 12.0){
                    pitchPID.setSetpoint(.27, ControlType.kPosition);
                    flywheelPID.setSetpoint(-1, ControlType.kDutyCycle);
                }
                break;
            default:

                break;
        }
    
        if (distance < 3.0){
            pitchPID.setSetpoint(.157, ControlType.kPosition);
            flywheelPID.setSetpoint(-(.45 + .05 * distance), ControlType.kDutyCycle);
        }else if(distance >= 3.0){
            pitchPID.setSetpoint((0.0171429 * distance * distance) - 0.106571 * distance + 0.322429, ControlType.kPosition);
            flywheelPID.setSetpoint(-.6, ControlType.kDutyCycle);
        }

        //TODO: logic for distance
        // pitchPID.setSetpoint(angle, ControlType.kPosition);
        
    }

    public void setShootingAngleUp(){
        if (pitch.getAbsoluteEncoder().getPosition() < .27)
            pitch.set(.4);
        else{
            pitch.set(0);
        }
    }

    public void setShootingAngleDown(){
        if (pitch.getAbsoluteEncoder().getPosition() > .16)
            pitch.set(-.2);
        else{
            pitch.set(0);
        }
    }

    public void stopPitch()
    {
        pitch.set(0);
    }

    /********                    rotate                  *********/
    public void setRotationZero(){
        
        rotation.getEncoder().setPosition(0);

    }

    public void turretAngle(double wantedAngle){

        if (rotation.getEncoder().getPosition() > -170 && rotation.getEncoder().getPosition() < 110){

            rotationPID.setSetpoint(wantedAngle, ControlType.kPosition);
            RobotStates.setSoftStop(false);

        }else if(wantedAngle > -170 && wantedAngle < 110){

            rotationPID.setSetpoint(wantedAngle, ControlType.kPosition);
            RobotStates.setSoftStop(false);

        }else{
            RobotStates.setSoftStop(true);
        }

    }

    public void setTurretRight(){

        if (rotation.getEncoder().getPosition() > -170){
            rotation.set(-.2);
        }else{
            rotation.set(0);
        }

    }

    public void setTurretLeft(){

        if (rotation.getEncoder().getPosition() < 110){
            rotation.set(.2);
        }else{
            rotation.set(0);
        }
    }

    public void stopTurret(){
        rotation.stopMotor();
    }

    public double getTurretAngle(){
        return rotation.getEncoder().getPosition();
    }

    public double getPitchAngle(){
        return pitch.getAbsoluteEncoder().getPosition();
    }

    public double getFlywheelRPM(){
        return flywheel.getEncoder().getVelocity();
    }

    /***************                    flywheel                   ****************/
    public void setShootingSpeed(double speed){
        flywheelPID.setSetpoint(speed, ControlType.kDutyCycle);
    }

    public void stopFlywheel(){
        flywheel.set(0);
    }

}
