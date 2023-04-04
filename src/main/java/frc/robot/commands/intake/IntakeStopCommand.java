package frc.robot.commands.intake;

import frc.robot.subsystems.IntakeSubsystem;

public class IntakeStopCommand extends IntakeRunCommand {

    public IntakeStopCommand(IntakeSubsystem subsystem) {
        super(subsystem, () -> 0.0);
    }
    
}
