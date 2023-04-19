package frc.robot.commands.intake;

import frc.robot.subsystems.IntakeSubsystem;

public class IntakeHoldCubeCommand extends IntakeRunCommand {

    public IntakeHoldCubeCommand(IntakeSubsystem subsystem) {
        super(subsystem, () -> -0);
    }
    
}
