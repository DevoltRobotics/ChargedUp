package frc.robot.commands.tank;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TankDriveSubsystem;

public class AutoBalancingTankDriveCommand extends CommandBase {

    private TankDriveSubsystem m_subsystem;
    
    private DoubleSupplier speed;
  
    private PIDController controller = new PIDController(0.002, 0, 0, 0.1);
  
    public AutoBalancingTankDriveCommand(TankDriveSubsystem subsystem, DoubleSupplier speed) {
      m_subsystem = subsystem;

      this.speed = speed;
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      controller.setSetpoint(0);
      controller.setTolerance(0.5);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      var power = MathUtil.clamp(controller.calculate(m_subsystem.getForwardTiltAngle()), 0.03, Double.POSITIVE_INFINITY) * speed.getAsDouble();
  
      m_subsystem.getLeft().set(-power);
      m_subsystem.getRight().set(power);
  
      SmartDashboard.putNumber("Balance Current", m_subsystem.getForwardTiltAngle());
      SmartDashboard.putNumber("Balance Target", controller.getSetpoint());
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
