package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.commands.S_DriveCommand;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Shooter;

public class RobotContainer extends SubsystemBase{
  
  private final SwerveSubsystem swerveSubs = new SwerveSubsystem();
  private final Shooter shooterPitch = new Shooter();
  private final CustomMathUtil customMathUtil = new CustomMathUtil();
  private final Vision vision = new Vision(swerveSubs::addVisionMeasurement);
  //Joystick setting
  public final static XboxController D_CONTROLLER = new XboxController(ControllerConstants.kDriverControllerPort);
  //DRIVE BUTTONS     
  private final JoystickButton speedSlow = new JoystickButton(D_CONTROLLER, 10);
  private final JoystickButton left = new JoystickButton(D_CONTROLLER, 5);
  private final JoystickButton right = new JoystickButton(D_CONTROLLER, 6);
  private final JoystickButton resetPigeonButton = new JoystickButton(D_CONTROLLER, 2);
  //private final JoystickButton lockbutton = new JoystickButton(D_CONTROLLER, 10); //Implement
  private final JoystickButton pitchAngle = new JoystickButton(D_CONTROLLER, 1); //Implement
  private final JoystickButton fieldOriented = new JoystickButton(D_CONTROLLER, 11);
  private final JoystickButton speedEmergency = new JoystickButton(D_CONTROLLER, 12);
  private final JoystickButton shooterFlywheel = new JoystickButton(D_CONTROLLER, 3);
  private final JoystickButton up = new JoystickButton(D_CONTROLLER, 7);
  private final JoystickButton down = new JoystickButton(D_CONTROLLER, 8);
  private final JoystickButton index = new JoystickButton(D_CONTROLLER, 4);
  public RobotContainer() {

        swerveSubs.resetOdometry(new Pose2d(8.33,4.23, new Rotation2d(0)));

    swerveSubs.setDefaultCommand(
      new S_DriveCommand(
        swerveSubs,
        () -> -D_CONTROLLER.getLeftY(), 
        () -> -D_CONTROLLER.getLeftX(), 
        () -> -D_CONTROLLER.getRightX(), 
        () -> fieldOriented.getAsBoolean(), 
        () -> speedSlow.getAsBoolean(),
        () -> speedEmergency.getAsBoolean() 
      )
    );
    configureBindings();
  }

  private void configureBindings() {
    resetPigeonButton.onTrue(new InstantCommand(() -> swerveSubs.resetPigeon()));  
    //lockbutton.onTrue(new InstantCommand(() -> swerveSubs.lock())); //CHECKME not sure how it behaves
    //pitchAngle.whileTrue(new InstantCommand(() -> shooterPitch.setAngle(vision.targetDistance())));

    // left.onTrue(new InstantCommand(() -> shooterPitch.angleLeft()));
    left.onTrue(new InstantCommand(() -> customMathUtil.angleToTarget(new Pose2d(3, 4, new Rotation2d(0)))));
    right.onTrue(new InstantCommand(() -> shooterPitch.angleRight()));
    up.onTrue(new InstantCommand(() -> shooterPitch.angleUp()));
    down.onTrue(new InstantCommand(() -> shooterPitch.angleDown()));
    shooterFlywheel.whileTrue(new InstantCommand(() -> shooterPitch.setShootingSpeed(.8)));
    shooterFlywheel.whileFalse(new InstantCommand(() -> shooterPitch.stopFlywheel()));
    index.whileTrue(new InstantCommand(() -> shooterPitch.setIntake()));
    index.whileFalse(new InstantCommand(() -> shooterPitch.stopIntake()));
    
  }

  @Override
  public void periodic() {
    //vision.periodic();

    if (!up.getAsBoolean() && !down.getAsBoolean() && !pitchAngle.getAsBoolean()){
      shooterPitch.angleStop();
    }
    //System.out.println(shooterPitch.getAngle());
  }


}