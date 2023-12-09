// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.IntakeConstants;

public class IntakeIOSparkMax implements IntakeIO {
  /** Creates a new IntakeIOSparkMax. */
  private CANSparkMax intakeMotor;
  
  public IntakeIOSparkMax() {
    intakeMotor = new CANSparkMax(IntakeConstants.kintakePort,MotorType.kBrushless);
    intakeMotor.restoreFactoryDefaults();
    intakeMotor.clearFaults();
    intakeMotor.setSmartCurrentLimit(IntakeConstants.kcurrentLimit);
    intakeMotor.burnFlash();

  }

  @Override 
  public void updateInputs(IntakeIOInputs inputs){ 
    inputs.velocity = intakeMotor.getEncoder().getVelocity();
    inputs.position = intakeMotor.getEncoder().getPosition();
    inputs.speed = intakeMotor.get();
    inputs.current = intakeMotor.getOutputCurrent();
    inputs.temperature = intakeMotor.getMotorTemperature();
  }  


  @Override
  public void setVoltage(double volts){ 
    intakeMotor.setVoltage(volts);
  }
}
