
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {

  private MotorController left;
  private MotorController right;
  private Gyro gyro;

  private Encoder leftEncoder;

  private DifferentialDrive drive;

  public static int ticksPerRev = 8192;
  public static double wheelDiameter = 16 * 2.54;

  public static int ticksPerCm = (int) Math.round(ticksPerRev / (wheelDiameter * Math.PI));
  public static int ticksPerM = ticksPerCm * 100;

  /** Creates a new TankDriveSubsystem. */
  public TankDriveSubsystem(MotorController left, MotorController right, Gyro gyro, Encoder leftEncoder) {
    this.left = left;
    this.right = right;
    this.gyro = gyro;
    this.leftEncoder = leftEncoder;
  }

  @Override
  public void periodic() {
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

  public Encoder getLeftEncoder() {
    return leftEncoder;
  }

  public double getAngle() {
    return 0;
  }
}
