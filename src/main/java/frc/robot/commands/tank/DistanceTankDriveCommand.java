package frc.robot.commands.tank;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TankDriveSubsystem;

public class DistanceTankDriveCommand extends CommandBase {
     
  private TankDriveSubsystem m_subsystem;

  private DoubleSupplier distance;
  private DoubleSupplier speed;

  private PIDController controller = new PIDController(0.002, 0, 0, 0.1);

  public DistanceTankDriveCommand(TankDriveSubsystem subsystem, DoubleSupplier distance, DoubleSupplier speed) {
    m_subsystem = subsystem;

    this.distance = distance;
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    controller.setSetpoint(distance.getAsDouble() * TankDriveSubsystem.ticksPerM);
    controller.setTolerance(800);

    m_subsystem.getLeftEncoder().setPosition(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var power = controller.calculate(m_subsystem.getLeftEncoder().getPosition()) * speed.getAsDouble();

    m_subsystem.getLeft().set(-power);
    m_subsystem.getRight().set(power);

    SmartDashboard.putNumber("Drive Target", controller.getSetpoint());
    
    controller.setSetpoint(distance.getAsDouble() * TankDriveSubsystem.ticksPerM);
  }

  @Override
  public void end(boolean interrupted) {
    m_subsystem.getLeft().set(0);
    m_subsystem.getRight().set(0);
  }

  @Override
  public boolean isFinished() {
    return controller.atSetpoint();
  }

}
