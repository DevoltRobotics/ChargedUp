// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Servo;
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
    
  CANSparkMax leftA = new CANSparkMax(3, CANSparkMax.MotorType.kBrushless);
  CANSparkMax leftB = new CANSparkMax(4, CANSparkMax.MotorType.kBrushless);

  CANSparkMax rightA = new CANSparkMax(5, CANSparkMax.MotorType.kBrushless);
  CANSparkMax rightB = new CANSparkMax(6, CANSparkMax.MotorType.kBrushless);

  MotorControllerGroup left = new MotorControllerGroup(leftA, leftB);
  MotorControllerGroup right = new MotorControllerGroup(rightA, rightB);

  AHRS gyro = new AHRS(SerialPort.Port.kUSB); 

  MotorControllerGroup arm = new MotorControllerGroup(new CANSparkMax(7, CANSparkMax.MotorType.kBrushed), new CANSparkMax(8, CANSparkMax.MotorType.kBrushed));

  Servo armDownLock = new Servo(9);

  Encoder armEncoder = new Encoder(2, 3);

  CANSparkMax intake = new CANSparkMax(9, CANSparkMax.MotorType.kBrushed);

  Joystick joystick = new Joystick(1);
  XboxController gamepad = new XboxController(0);

  TankDriveSubsystem tankDriveSubsystem = new TankDriveSubsystem(left, right, leftB.getEncoder(), rightA.getEncoder(), gyro);
  ArmSubsystem armSubsystem = new ArmSubsystem(arm, armEncoder, armDownLock);
  
  IntakeSubsystem intakeSubsystem = new IntakeSubsystem(intake, solenoid);

}
