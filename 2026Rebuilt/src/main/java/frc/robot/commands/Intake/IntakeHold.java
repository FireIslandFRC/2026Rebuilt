package frc.robot.commands.Intake;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotStates;
import frc.robot.RobotStates.IntakeState;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.SwerveSubsystem;

public class IntakeHold extends Command {
  private Intake intakeSubs; 


  public IntakeHold(Intake intakeSubs) {
    this.intakeSubs = intakeSubs;
    addRequirements(intakeSubs);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakeSubs.setIntakeDown();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (RobotStates.getIntakeState() == IntakeState.INTAKING){
      intakeSubs.setIntakeDown();
      intakeSubs.setIntakeIn();
    }else{
      intakeSubs.setIntakeUp();
      intakeSubs.stopRollers();
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean finished;
    if (RobotStates.getIntakeState() == IntakeState.INTAKING && Math.abs( intakeSubs.getIntakeLAngle() - Constants.IntakeConstants.kIntakeDownPos) < .1){
      finished = true;
    }else{
      finished = false;
    }
    return finished;
  }
}
