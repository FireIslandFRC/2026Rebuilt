package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.commands.Autos;
import frc.robot.commands.S_DriveCommand;
import frc.robot.commands.angleDownTurret;
import frc.robot.commands.angleUpTurret;
import frc.robot.commands.Intake.IntakeDown;
import frc.robot.commands.Intake.IntakeToggle;
import frc.robot.commands.Intake.IntakeIn;
import frc.robot.commands.Intake.IntakeUp;
import choreo.auto.AutoChooser;
import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import frc.robot.commands.Turret.TurretLeft;
import frc.robot.commands.Turret.TurretRight;
import frc.robot.commands.Turret.TurretAim;
import frc.robot.commands.Indexer.RunCentralizer;
import frc.robot.commands.Indexer.RunSpindexer;
import frc.robot.commands.Intake.IntakeToggle;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS5Controller.Axis;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Intake;
import frc.robot.Vision;
import frc.robot.subsystems.Shooter;

public class RobotContainer extends SubsystemBase{
  private final AutoFactory autoFactory;
  private final AutoChooser autoChooser;
  private final Autos autos;
  
  private final SwerveSubsystem swerveSubs    = new SwerveSubsystem();
  private final Intake intakeSubs             = new Intake();
  private final Indexer indexerSubs           = new Indexer();
  private final Shooter shooter               = new Shooter();
  private final CustomMathUtil customMathUtil = new CustomMathUtil();
  private final Vision vision                 = new Vision(swerveSubs::addVisionMeasurement);
  //Joystick setting
  public final static XboxController D_CONTROLLER  = new XboxController(ControllerConstants.kDriverControllerPort);
  public final static XboxController OP_CONTROLLER = new XboxController(ControllerConstants.kOperatorControllerPort);
  public final static XboxController TEST          = new XboxController(5);
  //DRIVE BUTTONS     
  private final double speedSlow              = D_CONTROLLER.getLeftTriggerAxis();
  private final double speedFast              = D_CONTROLLER.getRightTriggerAxis();
  private final JoystickButton resetGyro      = new JoystickButton(D_CONTROLLER, 3);
  private final JoystickButton fieldOriented  = new JoystickButton(D_CONTROLLER, 12);

  private final JoystickButton driveToScoreL  = new JoystickButton(D_CONTROLLER, 5);
  private final JoystickButton driveToScoreR  = new JoystickButton(D_CONTROLLER, 6);
  private final POVButton driveToScoreLPOV    = new POVButton(D_CONTROLLER, 5);
  private final POVButton driveToScoreRPOV    = new POVButton(D_CONTROLLER, 6);//TODO: angles
  private final JoystickButton driveToMidR    = new JoystickButton(D_CONTROLLER, 2);
  private final JoystickButton driveToMidL    = new JoystickButton(D_CONTROLLER, 2);
  private final JoystickButton driveToClimb   = new JoystickButton(D_CONTROLLER, 4);

  private final JoystickButton shoot        = new JoystickButton(OP_CONTROLLER, 1);
  private final JoystickButton shootSimple  = new JoystickButton(OP_CONTROLLER, 4);
  private final JoystickButton turretLeft   = new JoystickButton(OP_CONTROLLER, 9);
  private final JoystickButton turretRight  = new JoystickButton(OP_CONTROLLER, 10);
  private final JoystickButton flywheel     = new JoystickButton(OP_CONTROLLER, 3);
  private final JoystickButton intakeL      = new JoystickButton(OP_CONTROLLER, 5);
  private final JoystickButton intakeR      = new JoystickButton(OP_CONTROLLER, 6);

  private final JoystickButton runToPosition = new JoystickButton(D_CONTROLLER, 1);
  private final JoystickButton spindexer     = new JoystickButton(OP_CONTROLLER, 2);

  private final JoystickButton one       = new JoystickButton(TEST, 1);
  private final JoystickButton two       = new JoystickButton(TEST, 2);
  private final JoystickButton three       = new JoystickButton(TEST, 3);
  private final JoystickButton four       = new JoystickButton(TEST, 4);
  private final JoystickButton five       = new JoystickButton(TEST, 5);
  private final JoystickButton six       = new JoystickButton(TEST, 6);
  private final JoystickButton seven       = new JoystickButton(TEST, 7);
  private final JoystickButton eight       = new JoystickButton(TEST, 8);
  private final JoystickButton nine       = new JoystickButton(TEST, 9);

  public RobotContainer() {

     SmartDashboard.putData("Command", new Sendable() {
  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("RunCommand");

    builder.setActuator(true);
    builder.addBooleanProperty("boolean", () -> false, null);

  }
});

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
        () -> D_CONTROLLER.getLeftTriggerAxis(),
        () -> D_CONTROLLER.getRightTriggerAxis()
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

    intakeL.whileTrue(new IntakeToggle(intakeSubs));

    resetGyro.onTrue(new InstantCommand(() -> swerveSubs.resetPigeon()));
    runToPosition.whileTrue(autos.moveFowardTele().cmd());

    spindexer.whileTrue(new RunSpindexer(indexerSubs));
    
    one.whileTrue(new angleUpTurret(shooter));
    two.whileTrue(new angleDownTurret(shooter));

    three.whileTrue(new TurretLeft(shooter));
    four.whileTrue(new TurretRight(shooter));

    // one.whileTrue(new IntakeIn(intakeSubs));
    

    five.whileTrue(new IntakeDown(intakeSubs));
    six.whileTrue(new IntakeUp(intakeSubs));

    eight.whileTrue(new SequentialCommandGroup(new InstantCommand(() -> indexerSubs.setCentralizer(0.3)), new InstantCommand(() -> shooter.setShootingSpeed(.55)), new InstantCommand(() -> indexerSubs.setSpindexer(-0.2)),  new WaitCommand(.6), new InstantCommand(() -> indexerSubs.setCentralizer(-.6)), new WaitCommand(.5), new RunSpindexer(indexerSubs)));
    eight.whileFalse(new InstantCommand(() -> shooter.setShootingSpeed(0)));
    eight.whileFalse(new InstantCommand(() -> indexerSubs.setCentralizer(0)));

    seven.whileTrue(new RunCentralizer(indexerSubs));
  }
    
  @Override
  public void periodic() {
    // vision.periodic();
    // System.out.println("Intake L Angle: " + intakeSubs.getIntakeLAngle());
    // System.out.println("Intake R Angle: " + intakeSubs.getIntakeRAngle());
    // System.out.println("Turret Angle: " + shooter.getTurretAngle());
    // System.out.println("Pitch Angle: " + shooter.getPitchAngle());
    // if(!intakeL.getAsBoolean() && !intakeR.getAsBoolean()){
    //   intakeSubs.stopArms();
    // }

    // if(!five.getAsBoolean() && !six.getAsBoolean()){
    //   shooter.turretStop();
    // }
    // // if(!flywheel.getAsBoolean()){
    //   indexerSubs.runCentralizer(0);
    //   shooter.setShootingSpeed(0);
    // }
    // intakeSubs.intakeIn();

    // customMathUtil.turretAngleToTarget(new Pose2d(15,2, new Rotation2d(Units.degreesToRadians(0))));
    // System.out.println("TurretAngle" + shooter.getTurretAn
    // System.out.println("IntakeAngle  L"+ intakeSubs.getIntakeLAngle() + "   R  " + intakeSubs.getIntakeRAngle());

  }

}