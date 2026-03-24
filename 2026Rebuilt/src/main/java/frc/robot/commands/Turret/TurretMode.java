package frc.robot.commands.Turret;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.PoseConstants;
import frc.robot.CustomMathUtil;
import frc.robot.RobotStates;
import frc.robot.RobotStates.TargetState;
import frc.robot.RobotStates.TurretState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;

public class TurretMode extends Command {
  private Shooter shooterSubs; 
  private CustomMathUtil customMathUtil;
  private SwerveSubsystem swerveSubs;
  private Trigger isShooting;

  /* * * CONSTRUCTOR * * */
  /* 
   * @param swerveSubs the swerve subsystem 
   * @param xSupplier value input for strafe on x-axis 
   * @param ySupplier value input for strafe on y-axis 
   * @param zSupplier value input for rotation 
   * @param fieldOriented whether or not we want the bot to run in field oriented 
   */
  public TurretMode(Shooter shooterSubs, CustomMathUtil customMathUtil, SwerveSubsystem swerveSubs, Trigger isShooting) {
    this.shooterSubs = shooterSubs; 
    this.customMathUtil = customMathUtil;
    this.swerveSubs = swerveSubs;
    this.isShooting = isShooting;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("asjfasupofsapifhafi");
    switch (RobotStates.getTargetState()) {
      case HUB:

        // shooterSubs.turretAngle(CustomMathUtil.turretAngleToTarget(swerveSubs.getPose(), PoseConstants.TargetHubPose));
        shooterSubs.setShootingAngleAndSpeed(CustomMathUtil.distanceToTarget(swerveSubs.getPose()));
        // shooterSubs.setShootingAngleAndSpeed(1);
        // shooterSubs.setShootingSpeed(-.5);

        if (isShooting.getAsBoolean() && !RobotStates.getSoftStop()){

          RobotStates.setTurretState(TurretState.SHOOT_HUB);

        }else if (!RobotStates.getSoftStop()){

          RobotStates.setTurretState(TurretState.AIM_HUB);

        }
        break;
      case BACKRIGHT:
        shooterSubs.turretAngle(CustomMathUtil.turretAngleToTarget(swerveSubs.getPose(), new Pose2d(15, 7, new Rotation2d())));
        shooterSubs.setShootingAngleAndSpeed(CustomMathUtil.distanceToTarget(swerveSubs.getPose()));
        // shooterSubs.setShootingSpeed(-.5);

        if (isShooting.getAsBoolean() && !RobotStates.getSoftStop()){

          RobotStates.setTurretState(TurretState.SHOOT_BACKRIGHT);
          break;

        }else if (!RobotStates.getSoftStop()){

          RobotStates.setTurretState(TurretState.AIM_BACKRIGHT);    
          break;

        }
        break;
      case BACKLEFT:
        shooterSubs.turretAngle(CustomMathUtil.turretAngleToTarget(swerveSubs.getPose(), new Pose2d(15, 1, new Rotation2d())));
        shooterSubs.setShootingAngleAndSpeed(CustomMathUtil.distanceToTarget(swerveSubs.getPose()));
        // shooterSubs.setShootingSpeed(-.5);

        if (isShooting.getAsBoolean() && !RobotStates.getSoftStop()){

          RobotStates.setTurretState(TurretState.SHOOT_BACKLEFT);
          break;

        }else if (!RobotStates.getSoftStop()){

          RobotStates.setTurretState(TurretState.AIM_BACKLEFT);    
          break;

        }
        break;
      default:

        RobotStates.setTurretState(TurretState.OFF);
        break;
    }

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