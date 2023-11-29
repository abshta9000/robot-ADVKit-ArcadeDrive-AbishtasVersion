// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.drive.Drive;

public class DrivePIDCommand extends PIDCommand {
  /** Creates a new DrivePIDCommand. */
  

  public DrivePIDCommand(Drive drivesub) {
    super(
      new PIDController(.02,0,0),
      drivesub::getGyroPitch, 
      () -> drivesub.getInitialGyroPitch(), 
      output -> {
        BangBangController controller = new BangBangController();
        drivesub.autonomousArcadeDrive(
          (controller.calculate(drivesub.getGyroPitch(),drivesub.getInitialGyroPitch())),0);
        
        //drivesub.autonomousArcadeDrive(1,0);
      });
    addRequirements(drivesub);
  }

  // // Called when the command is initially scheduled.
  // @Override
  // public void initialize() {
  
  // }

  // // Called every time the scheduler runs while the command is scheduled.
  // @Override
  // public void execute() {}

  // // Called once the command ends or is interrupted.
  // @Override
  // public void end(boolean interrupted) {}

  // // Returns true when the command should end.
  // @Override
  // public boolean isFinished() {
  //   return false;
  // }
}

