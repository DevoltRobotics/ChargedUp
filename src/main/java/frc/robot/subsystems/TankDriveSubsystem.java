
package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {

  private MotorController left;
  private MotorController right;
  private AHRS gyro;

  private RelativeEncoder leftEncoder;
  private RelativeEncoder rightEncoder;

  private DifferentialDrive drive;

  private double ratio = 1 / 10.75;
  private double wheelDiameterMeters = (6 * 2.54) / 100;

  private DifferentialDriveOdometry localizer = null;
  private Pose2d lastPose = new Pose2d();

  private final Field2d field = new Field2d();

  public static int ticksPerRev = 8192;
  public static double wheelDiameter = 16 * 2.54;

  public static int ticksPerCm = (int) Math.round(ticksPerRev / (wheelDiameter * Math.PI));
  public static int ticksPerM = ticksPerCm * 100;

  /** Creates a new TankDriveSubsystem. */
  public TankDriveSubsystem(MotorController left, MotorController right, RelativeEncoder leftEncoder, RelativeEncoder rightEncoder, AHRS gyro) {
    this.left = left;
    this.right = right;
    this.gyro = gyro;

    left.setInverted(true);
    right.setInverted(true);

    this.leftEncoder = leftEncoder;
    leftEncoder.setPosition(0);

    this.rightEncoder = rightEncoder;
    rightEncoder.setPosition(0);

    gyro.calibrate();

    SmartDashboard.putData("Field", field);

    localizer = new DifferentialDriveOdometry(getRotation2d(), getLeftDistance(), getRightDistance());
  }

  @Override
  public void periodic() {
    lastPose = localizer.update(getRotation2d(), getLeftDistance(), getRightDistance());

    field.setRobotPose(lastPose);

    SmartDashboard.putNumber("Gyro", gyro.getAngle());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public MotorController getLeft() {
    return left;
  }
  public MotorController getRight() {
    return right;
  }

  public DifferentialDrive getDifferentialDrive() {
    if(drive == null) {
      drive = new DifferentialDrive(left, right);
    }

    return drive;
  }

  public RelativeEncoder getLeftEncoder() {
    return leftEncoder;
  }

  public double getLeftRevs() {
    return leftEncoder.getPosition() * ratio;
  }

  public double getLeftDistance() {
    return getLeftRevs() * wheelDiameterMeters * Math.PI;
  }
  
  public RelativeEncoder getRightEncoder() {
    return rightEncoder;
  }

  public double getRightRevs() {
    return -rightEncoder.getPosition() * ratio;
  }
  
  public double getRightDistance() {
    return getRightRevs() * wheelDiameterMeters * Math.PI;
  }

  public Pose2d getLastPose() {
    return lastPose;
  }

  public void resetPose(Pose2d pose) {
    localizer.resetPosition(getRotation2d(), getLeftDistance(), getRightDistance(), pose);
  }

  public Rotation2d getRotation2d() {
    try {
      return gyro.getRotation2d();
    } catch(Exception ex) {
      return Rotation2d.fromDegrees(0);
    }
  }

  public double getDrivetrainAngle() {
    try {
      return gyro.getAngle();
    } catch(Exception ex) {
      return 0;
    }
  }
  
  public double getForwardTiltAngle() {
    try {
      return gyro.getPitch();
    } catch(Exception ex) {
      return 0;
    }
  }
}
