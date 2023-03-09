
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {

  private MotorController left;
  private MotorController right;
  private Gyro gyro;

  private DifferentialDrive drive;

  /** Creates a new TankDriveSubsystem. */
  public TankDriveSubsystem(MotorController left, MotorController right, Gyro gyro) {
    this.left = left;
    this.right = right;
    this.gyro = gyro;
    
    drive = new DifferentialDrive(left, right);
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
    return drive;
  }

  public double getAngle() {
    return gyro.getAngle();
  }
}
