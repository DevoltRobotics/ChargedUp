
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

  private MotorController arm;
  private Encoder encoder;
  private Servo armDownLock;

  public boolean lock = true;

  /** Creates a new TankDriveSubsystem. */
  public ArmSubsystem(MotorController arm, Encoder encoder, Servo armDownLock) {
    this.arm = arm;
    this.encoder = encoder;
    this.armDownLock = armDownLock;
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
  
  public Servo getArmDownLock() {
    return armDownLock;
  }

  public Encoder getEncoder() {
    return encoder;
  }
}
