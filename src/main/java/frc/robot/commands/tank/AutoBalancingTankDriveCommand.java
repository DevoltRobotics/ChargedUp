package frc.robot.commands.tank;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TankDriveSubsystem;

public class AutoBalancingTankDriveCommand extends CommandBase {

    private TankDriveSubsystem m_subsystem;
    
    private DoubleSupplier speed;
    private double initialDirection;

    private State state = State.FINDING_STATION;
    
    double lastAngle = 0.0;

    double timeWithoutAngleChange = 0.0;
    double timeFindingStation = 0.0;

    Timer deltaTimer = new Timer();
  
    private PIDController controller = new PIDController(0.002, 0, 0, 0.1);

    enum State { FINDING_STATION, BALANCING, BALANCED, RUNAWAY }
  
    public AutoBalancingTankDriveCommand(TankDriveSubsystem subsystem, double initialDirection, DoubleSupplier speed) {
      m_subsystem = subsystem;
      this.initialDirection = initialDirection;

      this.speed = speed;
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      controller.setSetpoint(0);
      controller.setTolerance(0.5);
 
      lastAngle = 0.0;

      timeWithoutAngleChange = 0.0;
      timeFindingStation = 0.0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      var deltaAngle = m_subsystem.getForwardTiltAngle() - lastAngle;

      if(deltaAngle == 0) {
        timeWithoutAngleChange += deltaTimer.get();
      }

      switch(state) {
        case FINDING_STATION:
          if(deltaAngle >= 15) {
            state = State.BALANCING;
          } if(timeFindingStation >= 5) {
            state = State.RUNAWAY; // danger
          } else {
            m_subsystem.getLeft().set(initialDirection);
            m_subsystem.getRight().set(initialDirection);

            timeFindingStation += deltaTimer.get();
          }
          break;
        
        case BALANCING:
          var power = MathUtil.clamp(controller.calculate(m_subsystem.getForwardTiltAngle()), 0.08, Double.POSITIVE_INFINITY) * speed.getAsDouble();
  
          m_subsystem.getLeft().set(-power);
          m_subsystem.getRight().set(power);

          if(timeWithoutAngleChange > 2) {
            state = State.RUNAWAY; // danger
          }
          break;

        case RUNAWAY: // !!!!!
        case BALANCED:
          m_subsystem.getLeft().set(0);
          m_subsystem.getRight().set(0);

          this.cancel();
          break;
      }
  
      SmartDashboard.putNumber("Balance Current", m_subsystem.getForwardTiltAngle());
      SmartDashboard.putNumber("Balance Target", controller.getSetpoint());

      lastAngle = m_subsystem.getForwardTiltAngle();

      deltaTimer.reset();
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
