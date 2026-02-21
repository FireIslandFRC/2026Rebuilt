package frc.robot.commands.Indexer;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.SwerveSubsystem;

public class Index extends Command {
  private Indexer indexerSubs; 


  public Index(Indexer indexerSubs) {
    this.indexerSubs = indexerSubs;
    addRequirements(indexerSubs);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    indexerSubs.runSpindexer();
    indexerSubs.runCentralizer();
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    indexerSubs.runSpindexer(0);
    indexerSubs.runCentralizer(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
