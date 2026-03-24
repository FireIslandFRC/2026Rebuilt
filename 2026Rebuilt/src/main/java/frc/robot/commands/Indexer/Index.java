package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotStates;
import frc.robot.RobotStates.CentralizerState;
import frc.robot.RobotStates.SpindexerState;
import frc.robot.subsystems.Indexer;

public class Index extends Command {
  private Indexer indexerSubs; 


  public Index(Indexer indexerSubs) {
    this.indexerSubs = indexerSubs;
    addRequirements(indexerSubs);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotStates.setCentralizerState(CentralizerState.ON);
    RobotStates.setSpindexerState(SpindexerState.ON);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    indexerSubs.setSpindexer();
    indexerSubs.setCentralizer();
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    indexerSubs.stopCentralizer();
    indexerSubs.stopSpindexer();
    RobotStates.setCentralizerState(CentralizerState.OFF);
    RobotStates.setSpindexerState(SpindexerState.OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
