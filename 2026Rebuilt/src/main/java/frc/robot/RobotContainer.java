package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.commands.Autos;
import frc.robot.commands.S_DriveCommand;
import frc.robot.commands.Intake.IntakeDown;
import frc.robot.commands.Intake.IntakeHold;
import frc.robot.commands.Intake.IntakeUp;
import choreo.auto.AutoChooser;
import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import frc.robot.commands.Turret.TurretLeft;
import frc.robot.commands.Turret.TurretRight;
import frc.robot.commands.Turret.TurretToTarget;
import frc.robot.commands.Indexer.RunSpindexer;
import frc.robot.commands.Intake.IntakeHold;
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
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.subsystems.Intake;
import frc.robot.Vision;
import frc.robot.Configs.SwerveModuleConfig;
import frc.robot.subsystems.Shooter;

public class RobotContainer extends SubsystemBase{
  private final AutoFactory autoFactory;
  private final AutoChooser autoChooser;
  private final Autos autos;
  
  private final SwerveSubsystem swerveSubs    = new SwerveSubsystem();
  private final Intake intakeSubs             = new Intake();
  private final Indexer indexerSubs           = new Indexer();
  private final Shooter shooter                = new Shooter();
  private final CustomMathUtil customMathUtil = new CustomMathUtil();
  private final Vision vision                 = new Vision(swerveSubs::addVisionMeasurement);
  //Joystick setting
  public final static XboxController D_CONTROLLER  = new XboxController(ControllerConstants.kDriverControllerPort);
  public final static XboxController OP_CONTROLLER = new XboxController(ControllerConstants.kOperatorControllerPort);
  //DRIVE BUTTONS     
  private final JoystickButton speedSlow      = new JoystickButton(D_CONTROLLER, 11);
  private final JoystickButton speedEmergency = new JoystickButton(D_CONTROLLER, 10);
  private final JoystickButton resetGyro      = new JoystickButton(D_CONTROLLER, 3);
  private final JoystickButton fieldOriented  = new JoystickButton(D_CONTROLLER, 12);
  //TODO: SLOWMODE AND FAST MODE TO TRIGGERS
  private final JoystickButton driveToScoreL  = new JoystickButton(D_CONTROLLER, 5);
  private final JoystickButton driveToScoreR  = new JoystickButton(D_CONTROLLER, 6);
  private final POVButton driveToScoreLPOV  = new POVButton(D_CONTROLLER, 5);
  private final POVButton driveToScoreRPOV  = new POVButton(D_CONTROLLER, 6);//TODO: angles
  private final JoystickButton driveToMidR     = new JoystickButton(D_CONTROLLER, 2);
  private final JoystickButton driveToMidL     = new JoystickButton(D_CONTROLLER, 2);
  private final JoystickButton driveToClimb   = new JoystickButton(D_CONTROLLER, 4);

  private final JoystickButton shoot       = new JoystickButton(OP_CONTROLLER, 1);
  private final JoystickButton shootSimple = new JoystickButton(OP_CONTROLLER, 4);
  private final JoystickButton turretLeft  = new JoystickButton(OP_CONTROLLER, 9);
  private final JoystickButton turretRight = new JoystickButton(OP_CONTROLLER, 10);
  private final JoystickButton flywheel    = new JoystickButton(OP_CONTROLLER, 3);
  private final JoystickButton intake      = new JoystickButton(OP_CONTROLLER, 5);

  private final JoystickButton runToPosition = new JoystickButton(D_CONTROLLER, 1);
  private final JoystickButton spindexer = new JoystickButton(OP_CONTROLLER, 2);

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

    autoChooser.addRoutine("MoveFoward", autos::moveFoward);

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

    SmartDashboard.putData("autoChoser", autoChooser);

  }

  private void configureBindings() {
    driveToScoreL.whileTrue(autos.MidLhoot().cmd());
    driveToScoreLPOV.whileTrue(autos.MidLhoot().cmd());
    driveToScoreR.whileTrue(autos.MidRhoot().cmd());
    driveToScoreRPOV.whileTrue(autos.MidRhoot().cmd());

    driveToMidR.whileTrue(autos.RightMid().cmd());
    driveToMidL.whileTrue(autos.LeftMid().cmd());

    intake.whileTrue(new IntakeHold(intakeSubs));

    turretLeft.whileTrue(new TurretLeft(shooter));
    turretRight.whileTrue(new TurretRight(shooter));

    resetGyro.onTrue(new InstantCommand(() -> swerveSubs.resetPigeon()));
    runToPosition.whileTrue(autos.moveFowardTele().cmd());
    spindexer.whileTrue(new RunSpindexer(indexerSubs));
  }
    
  @Override
  public void periodic() {
    vision.periodic();
  }

}