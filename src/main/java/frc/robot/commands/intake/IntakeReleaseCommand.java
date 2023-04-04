package frc.robot.commands.intake;

import frc.robot.subsystems.IntakeSubsystem;

public class IntakeReleaseCommand extends IntakeRunCommand {

    public IntakeReleaseCommand(IntakeSubsystem subsystem) {
        super(subsystem, () -> -0.3);
    }
    
}