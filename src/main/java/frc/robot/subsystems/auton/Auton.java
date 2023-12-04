// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.auton;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.drive.Drive;
import frc.robot.Constants.AutonConstants;
import frc.robot.Constants.AutonConstants.Modes;
import frc.robot.commands.drive.ArcadeCommand;
import frc.robot.commands.drive.DrivePIDCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Auton {
  /** Creates a new Auton. */

  public Drive driveSub;

  public Auton(Drive i_driveSub) {
    this.driveSub = i_driveSub;
  }
  public Command autonomousCommand(Modes val){
    switch(val){
      case DOCK: return new SequentialCommandGroup(drivePID(4));
      
      
      default: 
      return new SequentialCommandGroup (
          new InstantCommand( () -> System.out.println("auton dieed"))
      );
    }
  }
  public Command driveStraight(double time, int modifier){
    return new ArcadeCommand(() -> (modifier * AutonConstants.kdriveSpeed), () -> 0.0,driveSub).withTimeout(time);
  }
  public Command driveTurn(double time, int modifier){
    return new ArcadeCommand(() -> 0.0, () -> (AutonConstants.kdriverotation * modifier),driveSub).withTimeout(time);
  }
  // pid command engaged after going over the charge station
  public Command drivePID(double time){
    //System.out.println("drivePID43");
    return new DrivePIDCommand(driveSub);//.withTimeout(time);
    //return new test().withTimeout(time);
  }
}