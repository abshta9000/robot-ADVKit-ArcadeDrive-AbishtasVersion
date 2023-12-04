// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ArmConstants.ArmModes;
import frc.robot.subsystems.arm.Arm;

public class TeleopArmCommand extends CommandBase {
  /** Creates a new TeleopArmCommand. */

  private Arm armSub;
  private ArmModes mode;

  public TeleopArmCommand(Arm armSub, ArmModes mode) {
    this.armSub = armSub;
    this.armSub.setArmHold(false);
    this.mode = mode;
    addRequirements(armSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    armSub.manualArm(currentAngle());
  }

  private double currentAngle(){
    switch(mode){
      case IDLE:
        return ArmConstants.angles.idle;
      default:
        System.out.println("Teleop Arm enum error");
        return 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    armSub.manualArm(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
