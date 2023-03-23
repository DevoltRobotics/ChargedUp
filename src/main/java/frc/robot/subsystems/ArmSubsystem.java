
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.arm.ArmDownReleaseCommand;

public class ArmSubsystem extends SubsystemBase {

  private MotorController arm;
  private Encoder encoder;
  private Servo armDownLockLeft;
  private Servo armDownLockRight;

  public boolean lock = true;

  /** Creates a new TankDriveSubsystem. */
  public ArmSubsystem(MotorController arm, Encoder encoder, Servo armDownLockLeft, Servo armDownLockRight) {
    this.arm = arm;
    this.encoder = encoder;
    this.armDownLockLeft = armDownLockLeft;
    this.armDownLockRight = armDownLockRight;
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
  
  public Servo getArmDownLockLeft() {
    return armDownLockLeft;
  }
  
  public Servo getArmDownLockRight() {
    return armDownLockRight;
  }

  public Encoder getEncoder() {
    return encoder;
  }
}
