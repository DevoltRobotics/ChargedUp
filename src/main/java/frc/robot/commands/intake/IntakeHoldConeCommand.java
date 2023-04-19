package frc.robot.commands.intake;

import frc.robot.subsystems.IntakeSubsystem;

public class IntakeHoldConeCommand extends IntakeRunCommand {

    public IntakeHoldConeCommand(IntakeSubsystem subsystem) {
        super(subsystem, () -> 0);
    }
    
}
