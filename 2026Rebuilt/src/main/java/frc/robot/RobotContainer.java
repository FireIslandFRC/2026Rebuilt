package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.ShooterPitch;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.commands.S_DriveCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ShooterPitch;

public class RobotContainer extends SubsystemBase{
  
  private final SwerveSubsystem swerveSubs = new SwerveSubsystem();
  private final ShooterPitch shooterPitch = new ShooterPitch();
  private final Vision vision = new Vision(swerveSubs::addVisionMeasurement);
  //Joystick setting
  public final static Joystick D_CONTROLLER = new Joystick(ControllerConstants.kDriverControllerPort);
  //DRIVE BUTTONS     
  //private final JoystickButton speedSlow = new JoystickButton(D_CONTROLLER, 1);
  private final JoystickButton up = new JoystickButton(D_CONTROLLER, 9);
  private final JoystickButton down = new JoystickButton(D_CONTROLLER, 10);
  private final JoystickButton resetPigeonButton = new JoystickButton(D_CONTROLLER, 16);
  //private final JoystickButton lockbutton = new JoystickButton(D_CONTROLLER, 10); //Implement
  private final JoystickButton pitchAngle = new JoystickButton(D_CONTROLLER, 1); //Implement

  public RobotContainer() {
    /*swerveSubs.setDefaultCommand(
      new S_DriveCommand(
        swerveSubs,
        () -> -D_CONTROLLER.getY(), 
        () -> -D_CONTROLLER.getX(), 
        () -> -D_CONTROLLER.getTwist(), 
        () -> fieldOriented.getAsBoolean(), 
        () -> speedSlow.getAsBoolean(),
        () -> speedEmergency.getAsBoolean() 
      )
    );*/
    configureBindings();
  }

  private void configureBindings() {
    resetPigeonButton.onTrue(new InstantCommand(() -> swerveSubs.resetPigeon()));  
    //lockbutton.onTrue(new InstantCommand(() -> swerveSubs.lock())); //CHECKME not sure how it behaves
    pitchAngle.whileTrue(new InstantCommand(() -> shooterPitch.setAngle(vision.targetDistance())));

    up.whileTrue(new InstantCommand(() -> shooterPitch.angleUp()));
    down.whileTrue(new InstantCommand(() -> shooterPitch.angleDown()));
    
  }

  @Override
  public void periodic() {
    //vision.periodic(); //NOTE
    if (!up.getAsBoolean() && !down.getAsBoolean() && !pitchAngle.getAsBoolean()){
      shooterPitch.angleStop();
    }
    System.out.println(shooterPitch.getAngle());
  }


}