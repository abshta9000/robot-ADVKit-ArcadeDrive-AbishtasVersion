// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants.Modes;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private final IntakeIO io;  
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();  

  private Modes mode;

  public Intake(IntakeIO io) {
    this.io = io;
  }

  public void setMode(Modes mode) {
    this.mode = mode;
  }

  public Modes getMode(){
    return mode;
  }

  public void spinOut(){
    if (mode == Modes.CUBE) io.setVoltage(-0.5);
    else io.setVoltage(-1);
  }

  public void spinIn(){
    if (mode == Modes.CUBE) io.setVoltage(0.5);
    else io.setVoltage(1);
  }

  public void spinOff(){
    io.setVoltage(0);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);  
    Logger.getInstance().processInputs("Intake", inputs);

    if (inputs.temperature > Constants.kSafeTemp){
      System.out.println("Intake - Motor temeprature exceeded");
      SmartDashboard.putBoolean("IntakeSafeTemperature",false);
    } else SmartDashboard.putBoolean("IntakeSafeTemperature",true);
  }

  public Modes getOppositeMode() {
    if (mode == Modes.CONE){
      return Modes.CUBE;
    } else if(mode == Modes.CUBE){
      return Modes.CONE;
    }
    return null;
  }
}
