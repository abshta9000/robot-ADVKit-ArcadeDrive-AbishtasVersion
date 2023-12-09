// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.*;
import frc.robot.Constants.AutonConstants.Modes;
import frc.robot.commands.drive.ArcadeCommand;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.ArmIOSimSparkMax;
import frc.robot.subsystems.auton.Auton;
import frc.robot.subsystems.drive.*;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIOSparkMaxSim;
import edu.wpi.first.math.MathUtil;
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
  private Arm subArm;
  private Intake subIntake;

  private CommandXboxController m_driverController =  new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private CommandGenericHID buttonBoard = new CommandGenericHID(OperatorConstants.ButtonBoard.BUTTONBOARD_PORT);

  private SendableChooser<Command> autonChooser;


  Trigger buttonLOWPOS,
   buttonHIGHPOS,
   buttonMIDPOS,
   sniperMode = m_driverController.y(),
   coneToggle = m_driverController.x(),
   buttonSUBSTATIONPOS,
   buttonIDLEPOS,
   buttonOUT = m_driverController.b(), 
   buttonIN = m_driverController.a(),
   buttonGROUNDPOS,
   intakeButton = m_driverController.povUp(),
   outakeButton = m_driverController.povDown();


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {    


    subDrive = new Drive(new DriveIOSim()); 
    subArm = new Arm(new ArmIOSimSparkMax());
    subIntake = new Intake(new IntakeIOSparkMaxSim());
    subAuton = new Auton(subDrive,subArm,subIntake);
    

    SmartDashboard.putNumber("GYRO CALC", 1);
    autonChooser = new SendableChooser<>();

    // Shuffleboard.getTab("Teleoperated").add(teleopChooser);
    // teleopChooser.addOption(null, null);

    subDrive.setDefaultCommand(new ArcadeCommand(
      () -> -MathUtil.applyDeadband(m_driverController.getLeftY(),.1),
      // unfortunately, .getRightX() does not work on this project, no matter the amount of times I tried (likely a issue on my end)
      // so to substitute, we have to get the raw axis, and for my controller it is 2
      // if your controller's axis is different please update the axis below!
      () -> -MathUtil.applyDeadband(m_driverController.getRawAxis(2),.1),
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

    sniperMode.whileTrue(
      new InstantCommand(
        () -> subDrive.setDriveSniperMode(true)))
    .onFalse(
      new InstantCommand(
        () -> subDrive.setDriveSniperMode(false)));

    sniperMode.whileTrue(
      new InstantCommand(
        () -> subArm.setArmSniperMode(true)))
    .onFalse(
      new InstantCommand(
        () -> subArm.setArmSniperMode(false)));

    buttonOUT.onTrue(new InstantCommand(()-> subArm.manualArm(.9)))
    .onFalse(new InstantCommand(()-> subArm.manualArm(0)));

    buttonIN.onTrue(new InstantCommand(()-> subArm.manualArm(-.9)))
    .onFalse(new InstantCommand(()-> subArm.manualArm(0)));

    coneToggle.onTrue(new InstantCommand(() -> subIntake.setMode(subIntake.getOppositeMode())));
    
    intakeButton.whileTrue(new InstantCommand(() -> subIntake.spinIn()))
    .onFalse(new InstantCommand(() -> subIntake.spinOff()));

    outakeButton.whileTrue(new InstantCommand(() -> subIntake.spinOut()))
    .onFalse(new InstantCommand(() -> subIntake.spinOff()));

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
