package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.commands.S_DriveCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer extends SubsystemBase{
  
  private final SwerveSubsystem swerveSubs = new SwerveSubsystem();
  //Joystick setting
  public final static Joystick D_CONTROLLER = new Joystick(ControllerConstants.kDriverControllerPort);
  //DRIVE BUTTONS     
  private final JoystickButton speedSlow = new JoystickButton(D_CONTROLLER, 1);
  private final JoystickButton speedEmergency = new JoystickButton(D_CONTROLLER, 3);
  private final JoystickButton fieldOriented = new JoystickButton(D_CONTROLLER, 9);
  private final JoystickButton resetPigeonButton = new JoystickButton(D_CONTROLLER, 16);
  private final JoystickButton lockbutton = new JoystickButton(D_CONTROLLER, 10); //Implement

  public RobotContainer() {
    swerveSubs.setDefaultCommand(
      new S_DriveCommand(
        swerveSubs,
        () -> -D_CONTROLLER.getY(), 
        () -> -D_CONTROLLER.getX(), 
        () -> -D_CONTROLLER.getTwist(), 
        () -> fieldOriented.getAsBoolean(), 
        () -> speedSlow.getAsBoolean(),
        () -> speedEmergency.getAsBoolean() 
      )
    );
    configureBindings();
  }

  private void configureBindings() {
    resetPigeonButton.onTrue(new InstantCommand(() -> swerveSubs.resetPigeon()));  
    lockbutton.onTrue(new InstantCommand(() -> swerveSubs.lock())); //CHECKME not sure how it behaves
  }

  @Override
  public void periodic() {
    
  }

}