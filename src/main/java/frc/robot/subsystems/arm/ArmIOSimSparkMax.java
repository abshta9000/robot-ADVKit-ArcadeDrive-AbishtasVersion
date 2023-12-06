// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.Constants.ArmConstants;

public class ArmIOSimSparkMax implements ArmIO {
  /** Creates a new ArmIOSparkMax. */

  private CANSparkMax armMotor = new CANSparkMax(ArmConstants.karmPort, MotorType.kBrushless);
  private Encoder encoder = new Encoder(ArmConstants.encoder.kchannelA,ArmConstants.encoder.kchannelB);
  private SingleJointedArmSim sim = new SingleJointedArmSim(
      DCMotor.getNEO(1), 
      ArmConstants.kgearRatio, 
      SingleJointedArmSim.estimateMOI(ArmConstants.karmLengthMeters, ArmConstants.karmMassKg), 
      ArmConstants.karmLengthMeters, 
      ArmConstants.kReverseSoftLimit / ArmConstants.kgearRatio, 
      ArmConstants.kForwardSoftLimit / ArmConstants.kgearRatio, 
      true);
  
  
  public ArmIOSimSparkMax() {

    armMotor.restoreFactoryDefaults();
    armMotor.clearFaults();
    armMotor.setIdleMode(IdleMode.kBrake);
    armMotor.setSmartCurrentLimit(60);
    armMotor.setSoftLimit(SoftLimitDirection.kForward, ArmConstants.kForwardSoftLimit);
    armMotor.setSoftLimit(SoftLimitDirection.kReverse, ArmConstants.kReverseSoftLimit);
    armMotor.burnFlash();

    encoder.reset();

  }

  @Override 
  public void updateInputs(ArmIOInputs inputs){
    inputs.temperature = 0;
    inputs.degrees = encoder.get() * ArmConstants.kgearRatio;
    inputs.position = inputs.degrees * Math.PI / 180;
    inputs.rpm = (encoder.getRate() / 360) * 60;
    inputs.velocity = encoder.getRate() / 22.75;
    sim.setInput(inputs.degrees * 360);
    sim.update(.02);
    encoder.setDistancePerPulse(1);
  }

  public void setVoltage(int voltage){
    sim.setInput(voltage);
  }
}
