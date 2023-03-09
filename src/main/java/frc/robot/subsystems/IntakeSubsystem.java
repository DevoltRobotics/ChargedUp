
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

  private MotorController intake;

  /** Creates a new TankDriveSubsystem. */
  public IntakeSubsystem(MotorController intake) {
    this.intake = intake;
  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public MotorController getIntake() {
    return intake;
  }
}
