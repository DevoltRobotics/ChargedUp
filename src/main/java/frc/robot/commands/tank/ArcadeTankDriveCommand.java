// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.tank;

import frc.robot.subsystems.TankDriveSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ArcadeTankDriveCommand extends CommandBase {
  private final TankDriveSubsystem m_subsystem;

  private DifferentialDrive drive;
  private Joystick joystick;

  private boolean speedControl;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArcadeTankDriveCommand(TankDriveSubsystem subsystem, Joystick joystick, boolean speedControl) {
    m_subsystem = subsystem;
    this.joystick = joystick;
    this.speedControl = speedControl;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive = new DifferentialDrive(m_subsystem.getLeft(), m_subsystem.getRight());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turbo = 1.0;

    if(speedControl) {
        turbo = 1.0 - joystick.getRawAxis(4) * 0.7;
    }
    
    drive.arcadeDrive(-joystick.getY() * turbo, -joystick.getX() * turbo);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
