package frc.robot;

import com.ctre.phoenix6.configs.TorqueCurrentConfigs;

import choreo.auto.AutoChooser;
import choreo.auto.AutoFactory;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.Constants.ControllerConstants;
import frc.robot.RobotStates.TargetState;
import frc.robot.commands.Autos;
import frc.robot.commands.S_DriveCommand;
import frc.robot.commands.Intake.IntakeDown;
import frc.robot.commands.Intake.IntakeIn;
import frc.robot.commands.Intake.IntakeUp;
import frc.robot.commands.Turret.Shoot;
import frc.robot.commands.Turret.TurretLeft;
import frc.robot.commands.Turret.TurretMode;
import frc.robot.commands.Turret.TurretRight;
import frc.robot.commands.Turret.angleDownTurret;
import frc.robot.commands.Turret.angleUpTurret;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;

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
  // public final static XboxController D_CONTROLLER  = new XboxController(ControllerConstants.kDriverControllerPort);
  public final static XboxController OP_CONTROLLER = new XboxController(ControllerConstants.kOperatorControllerPort);
  public final static Joystick D_CONTROLLER        = new Joystick(ControllerConstants.kDriverControllerPort);
  public final static XboxController TEST          = new XboxController(5);
  //DRIVE BUTTONS     


  // private final double speedSlow              = D_CONTROLLER.getLeftTriggerAxis();
  // private final double speedFast              = D_CONTROLLER.getRightTriggerAxis();
  private final JoystickButton resetGyro      = new JoystickButton(D_CONTROLLER, 8);
  private final JoystickButton fieldOriented  = new JoystickButton(D_CONTROLLER, 12);

  // private final JoystickButton driveToScoreL  = new JoystickButton(D_CONTROLLER, 5);
  
  private final JoystickButton twoDrive  = new JoystickButton(D_CONTROLLER,2);


  // private final JoystickButton driveToScoreR  = new JoystickButton(D_CONTROLLER, 6);
  // // private final POVButton driveToScoreLPOV    = new POVButton(D_CONTROLLER, 5);
  // // private final POVButton driveToScoreRPOV    = new POVButton(D_CONTROLLER, 6);//TODO: angles
  // private final JoystickButton driveToMidR    = new JoystickButton(D_CONTROLLER, 2);
  // private final JoystickButton driveToMidL    = new JoystickButton(D_CONTROLLER, 2);
  // private final JoystickButton driveToClimb   = new JoystickButton(D_CONTROLLER, 4);



  private final JoystickButton stopTurret        = new JoystickButton(OP_CONTROLLER, 1);
  private final JoystickButton shootRight        = new JoystickButton(OP_CONTROLLER, 2);
  private final JoystickButton shootLeft        = new JoystickButton(OP_CONTROLLER, 3);
  private final JoystickButton shootHub        = new JoystickButton(OP_CONTROLLER, 4);
  
  private final JoystickButton indexBack  = new JoystickButton(OP_CONTROLLER, 5);
  private final JoystickButton index  = new JoystickButton(OP_CONTROLLER, 6);

  private final JoystickButton intakeOut  = new JoystickButton(OP_CONTROLLER, 7);
  private final JoystickButton intake  = new JoystickButton(OP_CONTROLLER, 8);

  private final POVButton turretLeft  = new POVButton(OP_CONTROLLER, 270);
  private final POVButton turretRight  = new POVButton(OP_CONTROLLER, 90);
  private final POVButton turretUp  = new POVButton(OP_CONTROLLER, 0);
  private final POVButton turretDown  = new POVButton(OP_CONTROLLER, 180);



  // private final JoystickButton turretLeft   = new JoystickButton(OP_CONTROLLER, 9);
  // private final JoystickButton turretRight  = new JoystickButton(OP_CONTROLLER, 10);
  // private final JoystickButton flywheel     = new JoystickButton(OP_CONTROLLER, 3);
  // private final JoystickButton intakeL      = new JoystickButton(OP_CONTROLLER, 5);
  // private final JoystickButton intakeR      = new JoystickButton(OP_CONTROLLER, 6);

  // private final JoystickButton runToPosition = new JoystickButton(D_CONTROLLER, 1);
  // private final JoystickButton spindexer     = new JoystickButton(OP_CONTROLLER, 2);

  private final JoystickButton one   = new JoystickButton(TEST, 1);
  private final JoystickButton two   = new JoystickButton(TEST, 2);
  private final JoystickButton three = new JoystickButton(TEST, 3);
  private final JoystickButton four  = new JoystickButton(TEST, 4);
  private final JoystickButton five  = new JoystickButton(TEST, 5);
  private final JoystickButton six   = new JoystickButton(TEST, 6);
  private final JoystickButton seven = new JoystickButton(TEST, 7);
  private final JoystickButton eight = new JoystickButton(TEST, 8);
  private final JoystickButton nine  = new JoystickButton(TEST, 9);
  private final JoystickButton ten  = new JoystickButton(TEST, 10);

  public RobotContainer() {

  // SmartDashboard.putData("Command", new Sendable() {
  //   @Override
  //   public void initSendable(SendableBuilder builder) {
  //     builder.setSmartDashboardType("RunCommand");

  //     builder.setActuator(true);
  //     builder.addBooleanProperty("boolean", () -> false, null);

  //   }
  // });

    autoChooser = new AutoChooser();

    autoFactory = new AutoFactory(
            swerveSubs::getPose, // A function that returns the current robot pose
            swerveSubs::resetOdometry, // A function that resets the current robot pose to the provided Pose2d
            swerveSubs::followTrajectory, // The drive subsystem trajectory follower 
            true, // If alliance flipping should be enabled 
            swerveSubs // The drive subsystem
        );

    autos = new Autos(swerveSubs, autoFactory, indexerSubs, shooter, intakeSubs);

    autoChooser.addRoutine("MoveFoward", autos::moveFoward);
    autoChooser.addRoutine("six", autos::ShootsiX);
    // autoChooser.addCmd("ShootingAuto", autos::Shoot);

    swerveSubs.setDefaultCommand(
      new S_DriveCommand(
        swerveSubs,
        () -> -D_CONTROLLER.getY(), 
        () -> -D_CONTROLLER.getX(), 
        () -> -D_CONTROLLER.getTwist(),
        () -> fieldOriented.getAsBoolean(),
        () -> D_CONTROLLER.getTrigger()
      )
      
    );

    configureBindings();

    RobotStates.populateDicts();

    SmartDashboard.putData("autoChoser", autoChooser);

    // Schedule the selected auto during the autonomous period
    RobotModeTriggers.autonomous().whileTrue(autoChooser.selectedCommandScheduler());

  }

  private void configureBindings() {

    resetGyro.onTrue(new TurretMode(shooter, customMathUtil, swerveSubs, index));
    resetGyro.onTrue(new Shoot(shooter, indexerSubs, customMathUtil, swerveSubs));
    shootRight.onTrue(new InstantCommand(() -> RobotStates.setTargetState(TargetState.BACKRIGHT)));
    shootLeft.onTrue(new InstantCommand(() -> RobotStates.setTargetState(TargetState.BACKLEFT)));
    shootHub.onTrue(new InstantCommand(() -> RobotStates.setTargetState(TargetState.HUB)));

    intake.whileTrue(new IntakeDown(intakeSubs).withTimeout(.4));
    intake.whileFalse(new IntakeUp(intakeSubs));

    turretUp.whileTrue(new angleUpTurret(shooter));
    turretDown.whileTrue(new angleDownTurret(shooter));

    turretLeft.whileTrue(new TurretLeft(shooter));
    turretRight.whileTrue(new TurretRight(shooter));
    
    // twoDrive.whileTrue(new InstantCommand(() -> swerveSubs.lock()));
    // driveToScoreL.whileTrue(autos.MidLhoot().cmd());
    // driveToScoreLPOV.whileTrue(autos.MidLhoot().cmd());
    // driveToScoreR.whileTrue(autos.MidRhoot().cmd());
    // driveToScoreRPOV.whileTrue(autos.MidRhoot().cmd());

    // driveToMidR.whileTrue(autos.RightMid().cmd());
    // driveToMidL.whileTrue(autos.LeftMid().cmd());

    // intakeL.whileTrue(new IntakeToggle(intakeSubs));

    resetGyro.onTrue(new InstantCommand(() -> swerveSubs.resetPigeon()));

    // runToPosition.whileTrue(autos.moveFowardTele().cmd());

    // spindexer.whileTrue(new RunSpindexer(indexerSubs));
    
    // one.whileTrue(new angleUpTurret(shooter));
    // two.whileTrue(new angleDownTurret(shooter));

    // three.whileTrue(new TurretLeft(shooter));
    // four.whileTrue(new TurretRight(shooter));

    one.whileTrue( new InstantCommand(() -> shooter.setShootingSpeed(-1)))
       .onFalse(   new InstantCommand(() -> shooter.setShootingSpeed(0)));

    five.whileTrue(new SequentialCommandGroup(
                    new InstantCommand(() -> indexerSubs.setCentralizer(-0.2)), 
                    new InstantCommand(() -> indexerSubs.setSpindexer(-1))));

    six.whileTrue(new SequentialCommandGroup(
                    new InstantCommand(() -> indexerSubs.setCentralizer(1)),
                    new InstantCommand(() -> indexerSubs.setSpindexer(1))));

    five.onFalse(new SequentialCommandGroup(
                    new InstantCommand(() -> indexerSubs.setCentralizer(0)), 
                    new InstantCommand(() -> indexerSubs.setSpindexer(0))));

    six.onFalse(new SequentialCommandGroup(
                    new InstantCommand(() -> indexerSubs.setCentralizer(0)), 
                    new InstantCommand(() -> indexerSubs.stopSpindexer())));

    // four.onTrue(new IntakeUp(intakeSubs));

    ten.whileTrue(new IntakeIn(intakeSubs));

    // seven.whileTrue(new IntakeDown(intakeSubs).withTimeout(.4));
    // seven.whileFalse(new IntakeUp(intakeSubs));  FIXME

    seven.whileTrue(new angleUpTurret(shooter));
    eight.whileTrue(new angleDownTurret(shooter));

    three.whileTrue(new TurretLeft(shooter));
    two.whileTrue(new TurretRight(shooter));

    nine.onTrue(new TurretMode(shooter, customMathUtil, swerveSubs, six));

    nine.onTrue(new Shoot(shooter, indexerSubs, customMathUtil, swerveSubs));

    // one.onFalse(new InstantCommand(() -> shooter.stopTurret()));

    // one.whileTrue(new InstantCommand(() -> shooter.setShootingAngle(.2)));
    // one.onFalse(new InstantCommand(() -> shooter.stopPitch()));

    // eight.whileTrue(new SequentialCommandGroup(new InstantCommand(() -> swerveSubs.lock()), 
    //                                           new InstantCommand(() -> indexerSubs.setCentralizer(0.2)), 
    //                                           new InstantCommand(() -> shooter.setShootingSpeed(-.55)), 
    //                                           new InstantCommand(() -> indexerSubs.setSpindexer(-0.12)),  
    //                                           new WaitCommand(.6), 
    //                                           new InstantCommand(() -> indexerSubs.setCentralizer(-.65)), 
    //                                           new WaitCommand(.5), new RunSpindexer(indexerSubs)));

    

    // eight.whileFalse(new InstantCommand(() -> shooter.setShootingSpeed(0)));
    // eight.whileFalse(new InstantCommand(() -> indexerSubs.setCentralizer(0)));
    // eight.whileFalse(new InstantCommand(() -> indexerSubs.setSpindexer(0)));

    // seven.whileTrue(new RunCentralizer(indexerSubs));
  }

  @Override
  public void periodic() {
    // new Trigger(() -> true).whileTrue(new TurretMode(shooter, customMathUtil, swerveSubs, shootHub));

    vision.periodic();
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

    SmartDashboard.putNumber("Left Arm", intakeSubs.getIntakeLAngle());
    SmartDashboard.putNumber("Right Arm", intakeSubs.getIntakeRAngle());
    SmartDashboard.putNumber("TurretAngle", shooter.getTurretAngle());
    SmartDashboard.putNumber("TurretRPM", shooter.getFlywheelRPM());
    SmartDashboard.putNumber("CentRPM", indexerSubs.getCentralizerRPM());
    SmartDashboard.putBoolean("Shooting", eight.getAsBoolean());
    SmartDashboard.putNumber("Pitch", shooter.getPitchAngle());
    SmartDashboard.putString("TurretState", RobotStates.turretStateDict.get(RobotStates.getTurretState()));
    SmartDashboard.putString("TargetState", RobotStates.targetStateDict.get(RobotStates.getTargetState()));
    SmartDashboard.putBoolean("SoftStop", RobotStates.getSoftStop());
    SmartDashboard.putNumber("Hub Distance", CustomMathUtil.distanceToTarget(swerveSubs.getPose()));

    // SmartDashboard.putNumber("AAngleToTarget", CustomMathUtil.turretAngleToTarget(swerveSubs.getPose(), PoseConstants.TargetHubPose != null ? PoseConstants.TargetHubPose: new Pose2d()));
    // SmartDashboard.putNumber("TurretAngleNeeded", -MathUtil.inputModulus(swerveSubs.getRotation2d().getDegrees(), -180.0, 180.0));
    // System.out.println(shooter.getPitchAngle());
    // SmartDashboard.putData(D_CONTROLLER.);
    
  }

  // public Command getAutonomousCommand(){
  //   return new SequentialCommandGroup(new InstantCommand(() -> swerveSubs.lock()), 
  //                                             new InstantCommand(() -> indexerSubs.setCentralizer(0.2)), 
  //                                             new InstantCommand(() -> shooter.setShootingSpeed(-.55)), 
  //                                             new InstantCommand(() -> indexerSubs.setSpindexer(-0.12)),  
  //                                             new WaitCommand(.6), 
  //                                             new InstantCommand(() -> indexerSubs.setCentralizer(-.65)), 
  //                                             new WaitCommand(.5), new RunSpindexer(indexerSubs));
  // }

  // public void scheduleTurret(){
  //   CommandScheduler.getInstance().schedule(new TurretMode(shooter, customMathUtil, swerveSubs, shootHub));
  // }


}