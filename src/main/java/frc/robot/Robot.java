// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.tank.ArcadeTankDriveCommand;
import frc.robot.commands.tank.DistanceTankDriveCommand;
import frc.robot.commands.tank.DistanceTurnTankDriveCommand;
import frc.robot.commands.TogglingCommand;
import frc.robot.commands.arm.ArmDownLockCommand;
import frc.robot.commands.arm.ArmDownReleaseCommand;
import frc.robot.commands.arm.ArmDriveCommand;
import frc.robot.commands.arm.ArmGoToPositionCommand;
import frc.robot.commands.intake.IntakeClawCloseCommand;
import frc.robot.commands.intake.IntakeClawOpenCommand;
import frc.robot.commands.intake.IntakeGrabCommand;
import frc.robot.commands.intake.IntakeNZRunCommand;
import frc.robot.commands.intake.IntakeReleaseCommand;
import frc.robot.commands.intake.IntakeRunCommand;
import frc.robot.commands.intake.IntakeStopCommand;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private static final String kConTodoPaDelante = "Con todo pa delante";
  private static final String kBalanceoPaDelante = "Balanceo pa delante";

  private static final String kPonerCubo = "Poner cubo";
  private static final String kPonerCuboYConTodoPaDelante = "Poner cubo y con todo pa tras";
  private static final String kPonerCuboYBalanceoPaAtras = "Poner cubo y balanceo pa atras";
  
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    CameraServer.startAutomaticCapture();
    
    new ArmDownReleaseCommand(m_robotContainer.armSubsystem).schedule();

    SmartDashboard.putStringArray("Auto List", new String[]{kConTodoPaDelante, kBalanceoPaDelante, kPonerCubo, kPonerCuboYConTodoPaDelante, kPonerCuboYBalanceoPaAtras});
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    SmartDashboard.putNumber("Drive Encoder Left", m_robotContainer.tankDriveSubsystem.getLeftEncoder().getPosition());
    SmartDashboard.putNumber("Drive Encoder Right", m_robotContainer.tankDriveSubsystem.getRightEncoder().getPosition());
    SmartDashboard.putNumber("Arm Encoder", m_robotContainer.armEncoder.get());

    SmartDashboard.putNumberArray("RobotDrive Motors", new double[]{m_robotContainer.left.get(), m_robotContainer.right.get(), m_robotContainer.left.get(), m_robotContainer.right.get()});
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_robotContainer.armEncoder.reset();

    SequentialCommandGroup autonomousCommand = new SequentialCommandGroup();

    autonomousCommand.addCommands(new IntakeClawOpenCommand(m_robotContainer.intakeSubsystem));

    String selection = SmartDashboard.getString("Auto Selector", kConTodoPaDelante);

    if(selection.equals(kPonerCubo) || selection.equals(kPonerCuboYConTodoPaDelante) || selection.equals(kPonerCuboYBalanceoPaAtras)) {
      autonomousCommand.addCommands(
        new ParallelRaceGroup(
          new IntakeNZRunCommand(m_robotContainer.intakeSubsystem, () -> 0.2),
          new WaitCommand(0.8)
        ),

        new ParallelRaceGroup(
          new ArmGoToPositionCommand(m_robotContainer.armSubsystem, () -> 1000, () -> 0.1),
          new WaitCommand(0.8)
        ),
        
        //new ParallelRaceGroup(
          //new IntakeClawCloseCommand(m_robotContainer.intakeSubsystem),
          //new WaitCommand(0.5)
        //),

        new ArmGoToPositionCommand(m_robotContainer.armSubsystem, () -> 15100, () -> 0.7)
      );

      if(!selection.equals(kPonerCubo) && !selection.equals(kPonerCuboYBalanceoPaAtras)) {
        autonomousCommand.addCommands(
          new ParallelRaceGroup(
            new DistanceTankDriveCommand(m_robotContainer.tankDriveSubsystem, () -> 0.84, () -> 0.05),
            new WaitCommand(2.0)
          )
        );
      } else {
        new ParallelRaceGroup(
            new DistanceTankDriveCommand(m_robotContainer.tankDriveSubsystem, () -> 0.12, () -> 0.02),
            new WaitCommand(1)
        );
      }

      autonomousCommand.addCommands(
        //new IntakeClawOpenCommand(m_robotContainer.intakeSubsystem),
        new ParallelRaceGroup(
          new IntakeReleaseCommand(m_robotContainer.intakeSubsystem),
          new WaitCommand(1.0)
        )
      );

      
      if(!selection.equals(kPonerCubo) && !selection.equals(kPonerCuboYBalanceoPaAtras)) {
        autonomousCommand.addCommands(
        new ParallelRaceGroup(
          new IntakeRunCommand(m_robotContainer.intakeSubsystem, () -> 0),

          new SequentialCommandGroup(
            new ParallelRaceGroup(
              new DistanceTankDriveCommand(m_robotContainer.tankDriveSubsystem, () -> -0.6, () -> 0.06),
              new WaitCommand(1.5)
            ),
            new ParallelRaceGroup(
              new ArmGoToPositionCommand(m_robotContainer.armSubsystem, () -> -1000, () -> 0.2),
              new WaitCommand(3.8)
            )
          )
        )
        );
      } else {
        autonomousCommand.addCommands(
        new ParallelRaceGroup(
          new IntakeRunCommand(m_robotContainer.intakeSubsystem, () -> 0),

          new SequentialCommandGroup(
            new ParallelRaceGroup(
              new ArmGoToPositionCommand(m_robotContainer.armSubsystem, () -> -1000, () -> 0.2),
              new WaitCommand(3.8)
            )
          )
        )
        );
      }
    }

    if(selection.equals(kConTodoPaDelante)) {
      autonomousCommand.addCommands(
        new DistanceTankDriveCommand(m_robotContainer.tankDriveSubsystem, () -> 2.1, () -> 0.01)
      );
    } else if(selection.equals(kPonerCuboYConTodoPaDelante)) {
      autonomousCommand.addCommands(
        new DistanceTankDriveCommand(m_robotContainer.tankDriveSubsystem, () -> -2.25, () -> 0.03),
        new ParallelRaceGroup(
          new DistanceTurnTankDriveCommand(m_robotContainer.tankDriveSubsystem, () -> -0.88, () -> 0.04),
          new WaitCommand(3.0)
        )
      );
    } else if(selection.equals(kBalanceoPaDelante)) {
      autonomousCommand.addCommands(
        new DistanceTankDriveCommand(m_robotContainer.tankDriveSubsystem, () -> 2.5, () -> 0.01),
        new WaitCommand(1.0),
        new DistanceTankDriveCommand(m_robotContainer.tankDriveSubsystem, () -> -1.5, () -> 0.01)
      );
    } else if(selection.equals(kPonerCuboYBalanceoPaAtras)) {
      autonomousCommand.addCommands(
        new DistanceTankDriveCommand(m_robotContainer.tankDriveSubsystem, () -> -1.5, () -> 0.031)
      );
    }

    autonomousCommand.schedule();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    CommandScheduler.getInstance().cancelAll();

    new ArcadeTankDriveCommand(m_robotContainer.tankDriveSubsystem, m_robotContainer.joystick, true).schedule();
    m_robotContainer.armSubsystem.setDefaultCommand(new ArmDriveCommand(m_robotContainer.armSubsystem, () -> -m_robotContainer.gamepad.getLeftY()));

    new IntakeClawOpenCommand(m_robotContainer.intakeSubsystem).schedule();

    Trigger aButton = new Trigger(() -> m_robotContainer.gamepad.getAButton());
    Trigger bButton = new Trigger(() -> m_robotContainer.gamepad.getBButton());

    Trigger xButton = new Trigger(() -> m_robotContainer.gamepad.getXButton());
    Trigger yButton = new Trigger(() -> m_robotContainer.gamepad.getYButton());

    Trigger armAutoUpButton = new Trigger(() -> m_robotContainer.gamepad.getPOV() == 0);
    Trigger armAutoMiddleButton = new Trigger(() -> m_robotContainer.gamepad.getPOV() == 90);
    Trigger armAutoSaveButton = new Trigger(() -> m_robotContainer.gamepad.getPOV() == 180);

    aButton
      .onTrue(new IntakeGrabCommand(m_robotContainer.intakeSubsystem))
      .onFalse(new IntakeStopCommand(m_robotContainer.intakeSubsystem));

    bButton
      .onTrue(new IntakeReleaseCommand(m_robotContainer.intakeSubsystem))
      .onFalse(new IntakeStopCommand(m_robotContainer.intakeSubsystem));

    xButton
      .onTrue(new TogglingCommand(new ArmDownLockCommand(m_robotContainer.armSubsystem), new ArmDownReleaseCommand(m_robotContainer.armSubsystem)));

    yButton.onTrue(new TogglingCommand(new IntakeClawOpenCommand(m_robotContainer.intakeSubsystem), new IntakeClawCloseCommand(m_robotContainer.intakeSubsystem)));

    armAutoUpButton.onTrue(new ArmDownReleaseCommand(m_robotContainer.armSubsystem).andThen(new ArmGoToPositionCommand(m_robotContainer.armSubsystem, () -> 14000)));
    armAutoMiddleButton.onTrue(new ArmDownReleaseCommand(m_robotContainer.armSubsystem).andThen(new ArmGoToPositionCommand(m_robotContainer.armSubsystem, () -> 8000)));
    armAutoSaveButton.onTrue(new ArmDownReleaseCommand(m_robotContainer.armSubsystem).andThen(new ArmGoToPositionCommand(m_robotContainer.armSubsystem, () -> 100, () -> 0.2)));
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    m_robotContainer.tankDriveSubsystem.getDifferentialDrive().feed();

    if(m_robotContainer.joystick.getPOV() != -1 && m_robotContainer.armSubsystem.getCurrentCommand() != m_robotContainer.armSubsystem.getDefaultCommand()) {
      m_robotContainer.armSubsystem.getCurrentCommand().cancel();
    }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
