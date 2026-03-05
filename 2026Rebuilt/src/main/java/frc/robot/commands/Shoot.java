package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.CustomMathUtil;
import frc.robot.Robot;
import frc.robot.RobotStates;
import frc.robot.RobotStates.CentralizerState;
import frc.robot.RobotStates.SpindexerState;
import frc.robot.RobotStates.TurretState;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;

public class Shoot extends Command {
  private Shooter shooterSubs; 
  private CustomMathUtil customMathUtil;
  private SwerveSubsystem swerveSubs;
  private Indexer indexerSubs;

  /* * * CONSTRUCTOR * * */
  /* 
   * @param swerveSubs the swerve subsystem 
   * @param xSupplier value input for strafe on x-axis 
   * @param ySupplier value input for strafe on y-axis 
   * @param zSupplier value input for rotation 
   * @param fieldOriented whether or not we want the bot to run in field oriented 
   */
  public Shoot(Shooter shooterSubs, Indexer indexerSubs, CustomMathUtil customMathUtil, SwerveSubsystem swerveSubs) {
    this.shooterSubs = shooterSubs; 
    this.customMathUtil = customMathUtil;
    this.swerveSubs = swerveSubs;
    this.indexerSubs = indexerSubs;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotStates.setCentralizerState(CentralizerState.ON);
    RobotStates.setTurretState(TurretState.SHOOTING);
    RobotStates.setSpindexerState(SpindexerState.ON);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooterSubs.setShootingSpeed(.8);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotStates.setTurretState(TurretState.OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}