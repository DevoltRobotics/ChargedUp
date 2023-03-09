
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

  private MotorController arm;

  /** Creates a new TankDriveSubsystem. */
  public ArmSubsystem(MotorController arm) {
    this.arm = arm;
  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public MotorController getArm() {
    return arm;
  }
}
