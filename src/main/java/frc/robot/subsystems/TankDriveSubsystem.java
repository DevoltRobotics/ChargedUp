
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {

  private MotorController left;
  private MotorController right;

  /** Creates a new TankDriveSubsystem. */
  public TankDriveSubsystem(MotorController left, MotorController right) {
    this.left = left;
    this.right = right;
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
}
