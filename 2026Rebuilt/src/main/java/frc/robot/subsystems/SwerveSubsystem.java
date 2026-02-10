package frc.robot.subsystems;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;

import com.ctre.phoenix6.hardware.Pigeon2;

import static frc.robot.Constants.Vision.*;

import java.util.Optional;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.SwerveConstants;
import frc.robot.Robot;

public class SwerveSubsystem extends SubsystemBase {
  /* * * INITIALIZATION * * */

  //initialize SwerveModules 
  private SwerveModule[] swerveModules; 

  //instantiate pigeon 
  public Pigeon2 pigeon = new Pigeon2(SwerveConstants.PIGEON_ID);

  //field2d
  public Field2d m_field;

  //instantiate poseEstimator
  private SwerveDrivePoseEstimator m_poseEstimator;

  private final PhotonCamera camera;
  private final PhotonPoseEstimator photonEstimator;

  // swervesubsystem constructor
  public SwerveSubsystem() {

    camera = new PhotonCamera("Arducam_OV9281_USB_Camera");
    photonEstimator = new PhotonPoseEstimator(kTagLayout, kRobotToCam);
    
    pigeon.reset();

    swerveModules = new SwerveModule[] {
      new SwerveModule(0, SwerveConstants.FrontLeft.constants), 
      new SwerveModule(1, SwerveConstants.BackLeft.constants), 
      new SwerveModule(2, SwerveConstants.FrontRight.constants), 
      new SwerveModule(3, SwerveConstants.BackRight.constants)
    };

    //field2d
    m_field = new Field2d();
    SmartDashboard.putData(m_field);

    m_poseEstimator = new SwerveDrivePoseEstimator(SwerveConstants.DRIVE_KINEMATICS, getRotation2d(), getModulePositions(), new Pose2d(0,0,new Rotation2d()));

  }

  /* * * RESET METHODS * * */

  public void resetPigeon() {
  /*if (DriverStation.getAlliance().isPresent()
       && DriverStation.getAlliance().get() == Alliance.Red) {
       pigeon.setYaw(180);
     } else {
       pigeon.setYaw(0);
     }*/

    pigeon.setYaw(0);
  }
  
  public void resetOdometry() {
    m_poseEstimator.resetPosition(getRotation2d(), getModulePositions(), new Pose2d(10,10, new Rotation2d(0)));
  }

  public void resetOdometry(Pose2d pose) {
    /*int flipped;
    if (DriverStation.getAlliance().isPresent()
      && DriverStation.getAlliance().get() == Alliance.Red) {
      flipped = 180;
    } else {
      flipped = 0;
    } */
    //m_poseEstimator.resetPosition(new Rotation2d(getRotation2d().getDegrees() + flipped), getModulePositions(), pose);

    m_poseEstimator.resetPosition(new Rotation2d(getRotation2d().getDegrees()), getModulePositions(), pose);
  }

  /* * * GET METHODS * * */
  //returns the Rotation2d object, a 2d coordinate represented by a point on the unit circle (the rotation of the robot)
  public Rotation2d getRotation2d() {
    return pigeon.getRotation2d();
  }

  public Pose2d getPose() {
    return m_poseEstimator.getEstimatedPosition();
  }

  // returns a ChassisSpeeds in robot relative
  public ChassisSpeeds getRobotRelativeSpeeds() {
    return new ChassisSpeeds(SwerveConstants.DRIVE_KINEMATICS.toChassisSpeeds(getModuleStates()).vxMetersPerSecond, SwerveConstants.DRIVE_KINEMATICS.toChassisSpeeds(getModuleStates()).vyMetersPerSecond, SwerveConstants.DRIVE_KINEMATICS.toChassisSpeeds(getModuleStates()).omegaRadiansPerSecond);
  }

  //returns the states of the swerve modules in an array 
  //getState uses drive velocity and module rotation 
  public SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] states = new SwerveModuleState[4]; 

    for (SwerveModule swerveMod : swerveModules) {
      states[swerveMod.moduleID] = swerveMod.getState();
    }

    return states; 
  }

  //returns the positions of the swerve modules in an array 
  //getPosition uses drive enc and module rotation 
  public SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[4]; 

    for (SwerveModule swerveMod : swerveModules) {
      positions[swerveMod.moduleID] = swerveMod.getPosition();
    }

    return positions;
  }

  /* * * SET METHODS * * */
  public void setPose(Pose2d pose) {
    m_poseEstimator.resetPosition(getRotation2d(), getModulePositions(), pose);
  }

  //gets a SwerveModuleStates array from driver control and sets each module to the corresponding SwerveModuleState
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, SwerveConstants.MAX_SPEED);

    for (SwerveModule swerveMod : swerveModules) {
      swerveMod.setState(desiredStates[swerveMod.moduleID]);
    }
  }

  //Overloaded for auto 
  public void setModuleStates(SwerveModuleState[] desiredStates, double speed) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, SwerveConstants.MAX_SPEED);

    for (SwerveModule swerveMod : swerveModules) {
      swerveMod.setState(desiredStates[swerveMod.moduleID], speed);
    }
  }


  /* * * DRIVE METHODS * * */
  public void drive(double xSpeed, double ySpeed, double zSpeed, boolean fieldOriented, double SpeedMultiplier){
    
    SwerveModuleState[] states;

    if (fieldOriented) {

      states = SwerveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
        ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed * SpeedMultiplier, ySpeed * SpeedMultiplier, zSpeed * SpeedMultiplier, getRotation2d())
      );

    } else {

      states = SwerveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
        new ChassisSpeeds(xSpeed, ySpeed, zSpeed)
      );
      
    }



    // Double[] swerveArr = {states[0].angle.getDegrees(),states[1].angle.getDegrees(),states[2].angle.getDegrees(),states[3].angle.getDegrees()};

    // SmartDashboard.putNumberArray("swerveTest", swerveArr); //NOTE the thing

    setModuleStates(states);   

  }

  public void driveRobotRelative(ChassisSpeeds chassis) {

    SwerveModuleState[] state = SwerveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(chassis);

    //Limited for auto
    setModuleStates(state, 0.10);

  }

  /* * * WHEEL METHODS * * */
  public void lock() {
    SwerveModuleState[] states = new SwerveModuleState[4];

    states[0] = new SwerveModuleState(0, new Rotation2d(Math.toRadians(45)));
    states[1] = new SwerveModuleState(0, new Rotation2d(Math.toRadians(-45)));
    states[2] = new SwerveModuleState(0, new Rotation2d(Math.toRadians(45)));
    states[3] = new SwerveModuleState(0, new Rotation2d(Math.toRadians(-45)));

    for (SwerveModule swerveMod : swerveModules) {
      System.out.println(swerveMod.moduleID);
      swerveMod.setAngle(states[swerveMod.moduleID]);
    }

  }

  public void straightenWheels() { //set all wheels to 0 degrees 
    SwerveModuleState[] states = new SwerveModuleState[4]; 

    states[0] = new SwerveModuleState(0, new Rotation2d(Math.toRadians(0)));
    states[1] = new SwerveModuleState(0, new Rotation2d(Math.toRadians(0)));
    states[2] = new SwerveModuleState(0, new Rotation2d(Math.toRadians(0)));
    states[3] = new SwerveModuleState(0, new Rotation2d(Math.toRadians(0)));

    for (SwerveModule swerveMod : swerveModules) {
      swerveMod.setState(states[swerveMod.moduleID]);
    }

  }

  public void stopModules() {

    for (SwerveModule swerveMod : swerveModules) {
      swerveMod.stop();
    }

  }

  public void updateVisionOdometry() {
      Optional<EstimatedRobotPose> visionEst = Optional.empty();
        for (var result : camera.getAllUnreadResults()) {
            visionEst = photonEstimator.estimateCoprocMultiTagPose(result);
            if (visionEst.isEmpty()) {
                //visionEst = photonEstimator.estimateLowestAmbiguityPose(result);
            }

            visionEst.ifPresent(
                    est -> {
                        // Change our trust in the measurement based on the tags we can see\

                        m_poseEstimator.addVisionMeasurement(est.estimatedPose.toPose2d(), est.timestampSeconds);
                        m_field.setRobotPose(est.estimatedPose.toPose2d());

                    });

        }
  }

  @Override
  public void periodic() {    
    // This method will be called once per scheduler run
    updateVisionOdometry();
    System.out.println();

    m_poseEstimator.update(
        pigeon.getRotation2d(),
        getModulePositions());
    
    // Try to get a vision-based pose estimate from Photon and fuse it into the
    // WPILib pose estimator. PhotonPoseEstimator returns an Optional<EstimatedRobotPose>.
    
    for (SwerveModule swerveMod : swerveModules) {
      swerveMod.print();
    }

    SmartDashboard.putNumber("Pigeon", pigeon.getYaw().getValueAsDouble());
    
  //  m_field.setRobotPose(getPose());
    //SmartDashboard.putData(m_poseEstimator.getEstimatedPosition().);
  }
}