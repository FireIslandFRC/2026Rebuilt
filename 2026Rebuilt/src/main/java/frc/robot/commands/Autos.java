package frc.robot.commands;

import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import choreo.auto.AutoTrajectory;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Indexer.RunSpindexer;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;

public final class Autos {
    private final SwerveSubsystem swerveDrive;
    private final AutoFactory autoFactory;
    private final int kMaxTrajectoryTimeoutSeconds;
    private final Indexer indexerSubs;
    private final Shooter shooterSubs;
    private final Intake intakeSubs;

    public Autos(SwerveSubsystem swerveDrive, AutoFactory autoFactory, Indexer indexerSubs, Shooter shooterSubs, Intake intakeSubs) {
        this.swerveDrive = swerveDrive;
        this.autoFactory = autoFactory;
        this.indexerSubs = indexerSubs;
        this.shooterSubs = shooterSubs;
        this.intakeSubs = intakeSubs;
        kMaxTrajectoryTimeoutSeconds = 5;
    }

    public AutoRoutine moveFowardTele() {
        AutoRoutine routine = autoFactory.newRoutine("moveFoward");

        AutoTrajectory drive = routine.trajectory("drive");

        routine.active().onTrue(drive.cmd().withTimeout(kMaxTrajectoryTimeoutSeconds));

        return routine;
    }
    
    public AutoRoutine moveFoward() {
        AutoRoutine routine = autoFactory.newRoutine("moveFoward");

        AutoTrajectory drive = routine.trajectory("drive");

        routine.active().onTrue(
                Commands.sequence(drive.resetOdometry(),
                                  drive.cmd().withTimeout(kMaxTrajectoryTimeoutSeconds)));

        return routine;
    }

    public AutoRoutine RightMid() {
        AutoRoutine routine = autoFactory.newRoutine("RightMid");

        AutoTrajectory RightMid = routine.trajectory("RightMid");

        routine.active().onTrue(
                        RightMid.cmd(
                        ).withTimeout(kMaxTrajectoryTimeoutSeconds));

        return routine;
    }

    public AutoRoutine LeftMid() {
        AutoRoutine routine = autoFactory.newRoutine("LeftMid");

        AutoTrajectory LeftMid = routine.trajectory("LeftMid");

        routine.active().onTrue(
                        LeftMid.cmd(
                        ).withTimeout(kMaxTrajectoryTimeoutSeconds));

        return routine;
    }

    public AutoRoutine MidRhoot() {
        AutoRoutine routine = autoFactory.newRoutine("MidRhoot");

        AutoTrajectory MidRhoot = routine.trajectory("MidRhoot");

        routine.active().onTrue(
                        MidRhoot.cmd(
                        ).withTimeout(kMaxTrajectoryTimeoutSeconds));

        return routine;
    }

    public AutoRoutine MidLhoot() {
        AutoRoutine routine = autoFactory.newRoutine("MidLhoot");

        AutoTrajectory MidLhoot = routine.trajectory("MidLhoot");

        routine.active().onTrue(
                        MidLhoot.cmd(
                        ).withTimeout(kMaxTrajectoryTimeoutSeconds));

        return routine;
    }

    public AutoRoutine ShootsiX() {
        AutoRoutine routine = autoFactory.newRoutine("ShootSix");

        routine.active().onTrue(new InstantCommand(() ->
                        new SequentialCommandGroup(new InstantCommand(() -> indexerSubs.setCentralizer(0.3)), 
                        new InstantCommand(() -> shooterSubs.setShootingSpeed(.55)), 
                        new InstantCommand(() -> indexerSubs.setSpindexer(-0.2)),  
                        new WaitCommand(.6), 
                        new InstantCommand(() -> indexerSubs.setCentralizer(-.6)), 
                        new WaitCommand(.5), 
                        new RunSpindexer(indexerSubs).withTimeout(5))));

        return routine;
    }

    // public Command Shoot(){
    //     Command command = new SequentialCommandGroup(new InstantCommand(() -> indexerSubs.setCentralizer(0.2)),
    //                                                  new InstantCommand(() -> shooterSubs.setShootingSpeed(-.55)),
    //                                                  new InstantCommand(() -> indexerSubs.setSpindexer(-0.15)),  
    //                                                  new WaitCommand(.6), 
    //                                                  new InstantCommand(() -> indexerSubs.setCentralizer(-.6)), 
    //                                                  new WaitCommand(.5), 
    //                                                  new RunSpindexer(indexerSubs));
    //     return command;
    // }

    
}

