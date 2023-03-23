// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.arm;

import frc.robot.subsystems.ArmSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/** An example command that uses an example subsystem. */
public class ArmGoToPositionCommand extends CommandBase {
  private final ArmSubsystem m_subsystem;
  private DoubleSupplier position;
  private DoubleSupplier speed;

  private PIDController controller = new PIDController(0.0005, 0, 0, 0.1);

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArmGoToPositionCommand(ArmSubsystem subsystem, DoubleSupplier position, DoubleSupplier speed) {
    m_subsystem = subsystem;
    this.position = position;
    this.speed = speed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }  
  public ArmGoToPositionCommand(ArmSubsystem subsystem, DoubleSupplier position) {
    this(subsystem, position, () -> 1d);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    controller.setSetpoint(position.getAsDouble());
    controller.setTolerance(800);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.getArm().set(controller.calculate(m_subsystem.getEncoder().get()) * Math.abs(speed.getAsDouble()));
    controller.setSetpoint(position.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.getArm().set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return controller.atSetpoint();
  }
}
