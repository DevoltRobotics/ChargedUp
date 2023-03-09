package frc.robot.commands.intake;

import frc.robot.subsystems.IntakeSubsystem;

public class IntakeGrabCommand extends IntakeRunCommand {

    public IntakeGrabCommand(IntakeSubsystem subsystem) {
        super(subsystem, () -> 0.2);
    }
    
}
