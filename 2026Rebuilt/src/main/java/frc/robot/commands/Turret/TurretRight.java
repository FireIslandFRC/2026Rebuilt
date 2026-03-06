package frc.robot.commands.Turret;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.CustomMathUtil;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;

public class TurretRight extends Command {
  private Shooter shooterSubs;

  /* * * CONSTRUCTOR * * */
  /* 
   * @param swerveSubs the swerve subsystem 
   * @param xSupplier value input for strafe on x-axis 
   * @param ySupplier value input for strafe on y-axis 
   * @param zSupplier value input for rotation 
   * @param fieldOriented whether or not we want the bot to run in field oriented 
   */
  public TurretRight(Shooter shooterSubs) {
    this.shooterSubs = shooterSubs;
    addRequirements(shooterSubs);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooterSubs.setTurretRight();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterSubs.stopTurret();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}