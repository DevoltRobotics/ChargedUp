
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

  private MotorController intake;
  private DoubleSolenoid clawSolenoid;

  /** Creates a new TankDriveSubsystem. */
  public IntakeSubsystem(MotorController intake, DoubleSolenoid clawSolenoid) {
    this.intake = intake;
    this.clawSolenoid = clawSolenoid;

    intake.setInverted(true);;
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

  public DoubleSolenoid getClawSolenoid() {
    return clawSolenoid;
  }
}
