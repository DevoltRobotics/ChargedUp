// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TankDriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    
  MotorControllerGroup left = new MotorControllerGroup(new PWMSparkMax(0), new PWMSparkMax(1));
  MotorControllerGroup right = new MotorControllerGroup(new PWMSparkMax(2), new PWMSparkMax(3));

  Joystick joystick = new Joystick(0);

  TankDriveSubsystem tankDriveSubsystem = new TankDriveSubsystem(left, right);

  public Command getAutonomousCommand() {
    return null;
  }

}
