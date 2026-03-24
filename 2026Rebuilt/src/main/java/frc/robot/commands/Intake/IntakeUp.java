package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeUp extends Command {
  private Intake intakeSubs; 


  public IntakeUp(Intake intakeSubs) {
    this.intakeSubs = intakeSubs;
    addRequirements(intakeSubs);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
        intakeSubs.setIntakeIn(.2);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    intakeSubs.setIntakeUp();
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intakeSubs.stopArms();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
