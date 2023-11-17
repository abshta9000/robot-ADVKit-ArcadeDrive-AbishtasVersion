// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.AutonConstants;
import frc.robot.subsystems.drive.Drive;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

public class DrivePIDCommand extends PIDCommand {
  /** Creates a new DrivePIDCommand. */

  public DrivePIDCommand(Drive drivesub) {
  super(
    new PIDController(AutonConstants.kp,AutonConstants.ki,AutonConstants.kd),
    drivesub::getGyroPitch,
    drivesub.getInitialGyroPitch(),
    (output,setpoint) -> drivesub.autonomousArcadeDrive(0,output),
    drivesub);
    //this.driveSub = drivesub;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivesub);
    //getController().setTolerance(2.5);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
