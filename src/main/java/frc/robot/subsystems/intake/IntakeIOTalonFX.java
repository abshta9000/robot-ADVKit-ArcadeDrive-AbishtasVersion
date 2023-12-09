// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.robot.Constants.IntakeConstants;

public class IntakeIOTalonFX implements IntakeIO {
  /** Creates a new IntakeIOSparkMax. */
  private WPI_TalonFX intakeMotor;
  
  public IntakeIOTalonFX() {
    intakeMotor = new WPI_TalonFX(IntakeConstants.kintakePort);

  }

  @Override 
  public void updateInputs(IntakeIOInputs inputs){ 
    inputs.velocity = intakeMotor.getSelectedSensorVelocity();
    inputs.position = intakeMotor.getSelectedSensorPosition();
    inputs.speed = intakeMotor.get();
    inputs.current = intakeMotor.getSupplyCurrent();
    inputs.temperature = intakeMotor.getTemperature();
  }  


  @Override
  public void setVoltage(double volts){ 
    intakeMotor.setVoltage(volts);
  }
}
