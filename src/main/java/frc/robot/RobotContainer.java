// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.*;
import frc.robot.Constants.AutonConstants.Modes;
import frc.robot.commands.drive.ArcadeCommand;
import frc.robot.subsystems.auton.Auton;
import frc.robot.subsystems.drive.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private Drive subDrive;
  private Auton subAuton;

  private CommandXboxController m_driverController =  new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private CommandGenericHID buttonBoard = new CommandGenericHID(OperatorConstants.ButtonBoard.BUTTONBOARD_PORT);

  private SendableChooser<Command> autonChooser;


  Trigger buttonLOWPOS,
   buttonHIGHPOS,
   buttonMIDPOS,
   sniperToggle = buttonBoard.button(OperatorConstants.ButtonBoard.SNIPERMODE_BUTTON),
   coneToggle,
   buttonSUBSTATIONPOS,
   buttonIDLEPOS,
   buttonOUT, 
   buttonIN,
   buttonGROUNDPOS,
   toggleIntakeButton,
   toggleOutakeButton,
   snipermodeButton;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {    


    subDrive = new Drive(new DriveIOSim()); 
    subAuton = new Auton(subDrive);

    SmartDashboard.putNumber("GYRO CALC", 1);
    autonChooser = new SendableChooser<>();

    // Shuffleboard.getTab("Teleoperated").add(teleopChooser);
    // teleopChooser.addOption(null, null);

    subDrive.setDefaultCommand(new ArcadeCommand(
      () -> -m_driverController.getLeftY(),
      () -> m_driverController.getRightX(),
      subDrive
    ));

    Shuffleboard.getTab("Autonomous: ").add(autonChooser);
    autonChooser.addOption("DOCK", subAuton.autonomousCommand(Modes.DOCK));
    
    configureBindings();  
    
  } 
  

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    //code from rookie rewrite (by me btw)
    sniperToggle
    .and(m_driverController.leftTrigger()
    .and(m_driverController.rightTrigger()
    .whileTrue(
      new InstantCommand(
        () -> subDrive.setDriveSniperMode(true)))
    .onFalse(
      new InstantCommand(
        () -> subDrive.setDriveSniperMode(false)))));
  }


  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
}
