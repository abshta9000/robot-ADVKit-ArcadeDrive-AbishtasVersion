// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.auton;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.drive.Drive;
import frc.robot.Constants.AutonConstants.Modes;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Auton {
  /** Creates a new Auton. */

  public Drive driveSub;

  public Auton(Drive i_driveSub) {
    this.driveSub = i_driveSub;
  }
  public Command autonomousCommand(Modes val){
    switch(val){
      case EXP_RED_CONE_MOBILITY_TURN: return new SequentialCommandGroup (new InstantCommand( () -> System.out.println("auton in progress")));
      case EXP_CONE_MOBILITY_TURN_EXTEND: return new SequentialCommandGroup (new InstantCommand( () -> System.out.println("auton in progress")));
      case CUBE_MOBILITY_EXTEND_GRAB: return new SequentialCommandGroup (new InstantCommand( () -> System.out.println("auton in progress")));
      case CUBE_SCORE_ONLY: return new SequentialCommandGroup (new InstantCommand( () -> System.out.println("auton in progress")));
      case CONE_SCORE_ONLY: return new SequentialCommandGroup (new InstantCommand( () -> System.out.println("auton in progress")));
      case CONE_MOBILITY_DOCK: return new SequentialCommandGroup (new InstantCommand( () -> System.out.println("auton in progress")));
      case CONE_MOBILITY: return new SequentialCommandGroup (new InstantCommand( () -> System.out.println("auton in progress")));
      default: 
      return new SequentialCommandGroup (
          new InstantCommand( () -> System.out.println("ERROR: Autonomous Failure."))
      );
    }

  }
}