// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TankDriveSubsystem;
 
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  int revPHId = 2;

  DoubleSolenoid solenoid = new DoubleSolenoid(revPHId, PneumaticsModuleType.REVPH, 14, 15);
    
  MotorControllerGroup left = new MotorControllerGroup(new CANSparkMax(3, CANSparkMax.MotorType.kBrushed), new CANSparkMax(4, CANSparkMax.MotorType.kBrushed));
  MotorControllerGroup right = new MotorControllerGroup(new CANSparkMax(5, CANSparkMax.MotorType.kBrushed), new CANSparkMax(6, CANSparkMax.MotorType.kBrushed));

  MotorControllerGroup arm = new MotorControllerGroup(new CANSparkMax(7, CANSparkMax.MotorType.kBrushed), new CANSparkMax(8, CANSparkMax.MotorType.kBrushed));

  CANSparkMax intake = new CANSparkMax(9, CANSparkMax.MotorType.kBrushless);

  SPI.Port gyroPort = SPI.Port.kMXP;

  ADXRS450_Gyro gyro = new ADXRS450_Gyro(gyroPort);

  Joystick joystick = new Joystick(0);

  TankDriveSubsystem tankDriveSubsystem = new TankDriveSubsystem(left, right, gyro);
  ArmSubsystem armSubsystem = new ArmSubsystem(arm);
  IntakeSubsystem intakeSubsystem = new IntakeSubsystem(intake);

  public Command getAutonomousCommand() {
    return null;
  }

}
