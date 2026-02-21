package frc.robot.commands;

import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import choreo.auto.AutoTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;

import frc.robot.subsystems.SwerveSubsystem;

public final class Autos {
    private final SwerveSubsystem swerveDrive;
    private final AutoFactory autoFactory;
    private final int kMaxTrajectoryTimeoutSeconds;

    public Autos(SwerveSubsystem swerveDrive, AutoFactory autoFactory) {
        this.swerveDrive = swerveDrive;
        this.autoFactory = autoFactory;
        kMaxTrajectoryTimeoutSeconds = 5;
    }

    public AutoRoutine moveFoward() {
        AutoRoutine routine = autoFactory.newRoutine("moveFoward");

        AutoTrajectory drive = routine.trajectory("drive");

        routine.active().onTrue(
                Commands.sequence(drive.resetOdometry(),
                        drive.cmd().withTimeout(kMaxTrajectoryTimeoutSeconds)));

        return routine;
    }
}