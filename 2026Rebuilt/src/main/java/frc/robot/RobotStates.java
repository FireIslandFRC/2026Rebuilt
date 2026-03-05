package frc.robot;

public class RobotStates {
    
    public enum IntakeState { STOWED, INTAKING, HALF }
    private static IntakeState intakeState = IntakeState.STOWED;
    
    public enum SpindexerState { OFF, PULSING, ON }
    private static SpindexerState spindexerState = SpindexerState.OFF;

    public enum CentralizerState { OFF, ON }
    private static CentralizerState centralizerState = CentralizerState.OFF;
    
    public enum TurretState { OFF, AIMING, SHOOTING }
    private static TurretState turretState = TurretState.OFF;
    
    public enum DrivingState { OFF, HUMAN, AUTONOMOUS }
    private static DrivingState drivingState = DrivingState.OFF;

    public static IntakeState getIntakeState() { return intakeState; }
    public static void setIntakeState(IntakeState state) { intakeState = state; }

    public static SpindexerState getSpindexerState() { return spindexerState;}
    public static void setSpindexerState(SpindexerState state) { spindexerState = state; }
    
    public static CentralizerState getCentralizerState() { return centralizerState;}
    public static void setCentralizerState(CentralizerState state) { centralizerState = state; }

    public static TurretState getTurretState() { return turretState;}
    public static void setTurretState(TurretState state) { turretState = state; }

    public static DrivingState getDrivingState() { return drivingState;}
    public static void setDrivingState(DrivingState state) { drivingState = state; }
    
}