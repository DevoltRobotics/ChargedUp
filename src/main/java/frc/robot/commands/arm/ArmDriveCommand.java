// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.arm;

import frc.robot.subsystems.ArmSubsystem;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/** An example command that uses an example subsystem. */
public class ArmDriveCommand extends CommandBase {
  private final ArmSubsystem m_subsystem;
  private DoubleSupplier speed;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArmDriveCommand(ArmSubsystem subsystem, DoubleSupplier speed) {
    m_subsystem = subsystem;
    this.speed = speed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var speedValue = speed.getAsDouble();
    
    if(speedValue < 0 && m_subsystem.getEncoder().get() <= -100) {
      speedValue = 0;
    }

    m_subsystem.getArm().set(speedValue);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
