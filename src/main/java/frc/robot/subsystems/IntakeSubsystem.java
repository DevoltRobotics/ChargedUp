
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

  private MotorController intake;
  private DoubleSolenoid clawSolenoid;

  /** Creates a new TankDriveSubsystem. */
  public IntakeSubsystem(MotorController intake, DoubleSolenoid clawSolenoid) {
    this.intake = intake;
    this.clawSolenoid = clawSolenoid;

    intake.setInverted(false);
  }

  @Override
  public void periodic() {
    if(clawSolenoid.get() == DoubleSolenoid.Value.kForward) {
      SmartDashboard.putNumber("Gyro", 360);
    } else {
      SmartDashboard.putNumber("Gyro", 180);
    }
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
