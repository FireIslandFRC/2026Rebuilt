package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.commands.Autos;
import frc.robot.commands.S_DriveCommand;
import frc.robot.commands.intakeDown;
import frc.robot.commands.intakeUp;
import choreo.auto.AutoChooser;
import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.subsystems.Intake;
import frc.robot.Vision;

public class RobotContainer extends SubsystemBase{
    private final AutoFactory autoFactory;
    private final AutoChooser autoChooser;
    private final Autos autos;
  
  private final SwerveSubsystem swerveSubs = new SwerveSubsystem();
  private final Intake intakeSubs = new Intake();
  //Joystick setting
  public final static XboxController D_CONTROLLER = new XboxController(ControllerConstants.kDriverControllerPort);
  //DRIVE BUTTONS     
  private final JoystickButton speedSlow = new JoystickButton(D_CONTROLLER, 11);
  private final JoystickButton speedEmergency = new JoystickButton(D_CONTROLLER, 10);
  private final JoystickButton fieldOriented = new JoystickButton(D_CONTROLLER, 12);
  private final JoystickButton up = new JoystickButton(D_CONTROLLER, 4);
  private final JoystickButton down = new JoystickButton(D_CONTROLLER, 1);
  private final JoystickButton intakeIn = new JoystickButton(D_CONTROLLER, 3);
  private final JoystickButton intakeOut = new JoystickButton(D_CONTROLLER, 2);

  public RobotContainer() {

    autoChooser = new AutoChooser();

    autoFactory = new AutoFactory(
            swerveSubs::getPose, // A function that returns the current robot pose
            swerveSubs::resetOdometry, // A function that resets the current robot pose to the provided Pose2d
            swerveSubs::followTrajectory, // The drive subsystem trajectory follower 
            true, // If alliance flipping should be enabled 
            swerveSubs // The drive subsystem
        );

    autos = new Autos(swerveSubs, autoFactory);

    // Follows deploy/choreo/myTrajectory.traj

    // swerveSubs.resetOdometry(new Pose2d(8.25,4, new Rotation2d(0)));

    autoChooser.addRoutine("MoveFoward", autos::moveFoward);
    // autoChooser.addRoutine("DriveFoward", this::exampleRoutine);


    // autoChooser = AutoBuilder.buildAutoChooserWithOptionsModifier(
    //   (stream) -> isCompetition
    //     ? stream.filter(auto -> auto.getName().startsWith("comp"))
    //     : stream
    // );

    /*swerveSubs.setDefaultCommand(
      new S_DriveCommand(
        swerveSubs,
        () -> -D_CONTROLLER.getLeftY(), 
        () -> -D_CONTROLLER.getLeftX(), 
        () -> -D_CONTROLLER.getRightX(),
        () -> fieldOriented.getAsBoolean(), 
        () -> speedSlow.getAsBoolean(),
        () -> speedEmergency.getAsBoolean() 
      )
    );*/
    configureBindings();
    SmartDashboard.putData("autoChoser", autoChooser);
        // SmartDashboard.putBoolean("HI", );

  }

  private void configureBindings() {
    //lockbutton.onTrue(new InstantCommand(() -> swerveSubs.lock())); //CHECKME not sure how it behaves
    //pitchAngle.whileTrue(new InstantCommand(() -> shooterPitch.setAngle(vision.targetDistance()))); //NOTE: reimplement old vision to work

    up.whileTrue(new InstantCommand(() -> shooterPitch.angleUp()));
    down.whileTrue(new InstantCommand(() -> shooterPitch.angleDown()));
  }

  public Command myTrajectoryCommand() {
    return autoFactory.trajectoryCmd("myTrajectory");
  }

  public Command myLineCommand() {
    return autoFactory.trajectoryCmd("line");
  }

  private AutoRoutine exampleRoutine() {
       AutoRoutine routine = autoFactory.newRoutine("taxi");

           return routine;

    }
    up.whileTrue(new intakeUp(intakeSubs));
    down.whileTrue(new intakeDown(intakeSubs));
    intakeIn.whileTrue(new InstantCommand(() -> intakeSubs.IntakeIn()));
    intakeOut.whileTrue(new InstantCommand(() -> intakeSubs.IntakeOut()));
  }

  @Override
  public void periodic() {
  }

}