package frc.robot;

import java.util.Dictionary;
import java.util.Hashtable;

public class RobotStates {

    
    public enum IntakeState { STOWED, INTAKING, HALF }
    private static IntakeState intakeState = IntakeState.STOWED;
    
    public enum SpindexerState { OFF, PULSING, ON }
    private static SpindexerState spindexerState = SpindexerState.OFF;

    public enum CentralizerState { OFF, ON }
    private static CentralizerState centralizerState = CentralizerState.OFF;
    
    public enum TurretState { OFF, AIM_HUB, AIM_BACKRIGHT, AIM_BACKLEFT, SHOOT_HUB, SHOOT_BACKRIGHT, SHOOT_BACKLEFT, SOFTSTOP}
    private static TurretState turretState = TurretState.OFF;

    public static Dictionary <TurretState, String> turretStateDict = new Hashtable<>();
    
    public enum DrivingState { OFF, HUMAN, AUTONOMOUS }
    private static DrivingState drivingState = DrivingState.OFF;

    public enum TargetState { OFF, HUB, BACKLEFT, BACKRIGHT }
    private static TargetState targetState = TargetState.OFF;

    public static Dictionary <TargetState, String> targetStateDict = new Hashtable<>();


    public static boolean softStop = false;

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

    public static TargetState getTargetState() { return targetState;}
    public static void setTargetState(TargetState state) { targetState = state; }

    public static boolean getSoftStop(){ return softStop; }
    public static void setSoftStop(boolean softStopBool){ softStop = softStopBool; }

    public static void populateDicts(){
        turretStateDict.put(TurretState.OFF, "OFF");
        turretStateDict.put(TurretState.AIM_HUB, "AIM_HUB");
        turretStateDict.put(TurretState.AIM_BACKRIGHT, "AIM_BACKRIGHT");
        turretStateDict.put(TurretState.AIM_BACKLEFT, "AIM_BACKLEFT");
        turretStateDict.put(TurretState.SHOOT_HUB, "SHOOT_HUB");
        turretStateDict.put(TurretState.SHOOT_BACKRIGHT, "SHOOT_BACKRIGHT");
        turretStateDict.put(TurretState.SHOOT_BACKLEFT, "SHOOT_BACKLEFT");
        turretStateDict.put(TurretState.SOFTSTOP, "SOFTSTOP");

        targetStateDict.put(TargetState.OFF, "OFF");
        targetStateDict.put(TargetState.BACKLEFT, "BACKLEFT");
        targetStateDict.put(TargetState.BACKRIGHT, "BACKRIGHT");
        targetStateDict.put(TargetState.HUB, "HUB");

    }
    
}
