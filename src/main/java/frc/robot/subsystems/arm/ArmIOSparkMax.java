// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants.ArmConstants;

public class ArmIOSparkMax implements ArmIO {
  /** Creates a new ArmIOSparkMax. */

  private CANSparkMax armMotor;
  private Encoder encoder;   
  
  public ArmIOSparkMax() {
    armMotor = new CANSparkMax(ArmConstants.karmPort, MotorType.kBrushless);
    encoder = new Encoder(ArmConstants.encoder.kchannelA,ArmConstants.encoder.kchannelB);

    armMotor.restoreFactoryDefaults();
    armMotor.clearFaults();
    armMotor.setIdleMode(IdleMode.kBrake);
    armMotor.setSmartCurrentLimit(60);
    armMotor.setSoftLimit(SoftLimitDirection.kForward, ArmConstants.kForwardSoftLimit);
    armMotor.setSoftLimit(SoftLimitDirection.kReverse, ArmConstants.kReverseSoftLimit);

    encoder.reset();

  }

  @Override 
  public void updateInputs(ArmIOInputs inputs){
    inputs.temperature = armMotor.getMotorTemperature();
    inputs.degrees = encoder.get() * ArmConstants.kgearRatio;
    inputs.rpm = (encoder.getRate() / 360) * 60;
    inputs.position = inputs.degrees * Math.PI / 180;
    inputs.velocity = encoder.getRate() / 22.75;
  }

  public void setVoltage(int voltage){
    armMotor.setVoltage(voltage);
  }
}
