// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.arm;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.arm.Arm;

public class HoldArmCommand extends CommandBase {
  /** Creates a new HoldArmCommand. */
  private Arm armSub;
  private ArmFeedforward feedforward;
  
  public HoldArmCommand(Arm armSub) {
    this.armSub = armSub;
    armSub.setArmHold(true);
    addRequirements(armSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    feedforward = new ArmFeedforward(0, 0, 0,0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (armSub.getArmHold() == true){
       armSub.manualArm(feedforward.calculate(
        Math.toRadians(armSub.getArmPosition()), 
        Math.toRadians() / 12
      ));
    }
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
