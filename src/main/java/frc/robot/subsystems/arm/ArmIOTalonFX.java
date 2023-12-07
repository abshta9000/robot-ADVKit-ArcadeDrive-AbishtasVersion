// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants.ArmConstants;

public class ArmIOTalonFX implements ArmIO {
  /** Creates a new ArmIOSparkMax. */

  private WPI_TalonFX armMotor;
  private Encoder encoder;   
  
  public ArmIOTalonFX() {
    armMotor = new WPI_TalonFX(ArmConstants.karmPort);
    encoder = new Encoder(ArmConstants.encoder.kchannelA,ArmConstants.encoder.kchannelB);

    armMotor.setSafetyEnabled(false);
    armMotor.configForwardSoftLimitThreshold(ArmConstants.kForwardSoftLimit);
    armMotor.configReverseSoftLimitThreshold(ArmConstants.kReverseSoftLimit);
    armMotor.feed();
    encoder.reset();

  }

  @Override 
  public void updateInputs(ArmIOInputs inputs){
    inputs.temperature = armMotor.getTemperature();
    inputs.radians = encoder.get() * ArmConstants.kgearRatio;
    inputs.rpm = (encoder.getRate() / 360) * 60;
    inputs.position = inputs.radians * Math.PI / 180;
    inputs.velocity = encoder.getRate() / 22.75;
  }

  public void setVoltage(int voltage){
    armMotor.setVoltage(voltage);
  }
}
