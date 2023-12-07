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
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.Constants.ArmConstants;

public class ArmIOSimSparkMax implements ArmIO {
  /** Creates a new ArmIOSparkMax. */

  private CANSparkMax armMotor = new CANSparkMax(ArmConstants.karmPort, MotorType.kBrushless);
  private Encoder encoder = new Encoder(ArmConstants.encoder.kchannelA,ArmConstants.encoder.kchannelB);
  private EncoderSim simEncoder;
  private SingleJointedArmSim sim = new SingleJointedArmSim(
      DCMotor.getNEO(1), 
      ArmConstants.kgearRatio, 
      SingleJointedArmSim.estimateMOI(ArmConstants.karmLengthMeters, ArmConstants.karmMassKg), 
      ArmConstants.karmLengthMeters, 
      ArmConstants.kReverseSoftLimit / ArmConstants.kgearRatio, 
      ArmConstants.kForwardSoftLimit / ArmConstants.kgearRatio, 
      false);

  private double voltage = 0;
  
  
  public ArmIOSimSparkMax() {

    armMotor.restoreFactoryDefaults();
    armMotor.clearFaults();
    armMotor.setIdleMode(IdleMode.kBrake);
    armMotor.setSmartCurrentLimit(60);
    armMotor.setSoftLimit(SoftLimitDirection.kForward, ArmConstants.kForwardSoftLimit);
    armMotor.setSoftLimit(SoftLimitDirection.kReverse, ArmConstants.kReverseSoftLimit);
    armMotor.burnFlash();

    encoder.reset();
    encoder.setDistancePerPulse(1);

    simEncoder = new EncoderSim(encoder);
    simEncoder.setCount(10);
  }

  @Override 
  public void updateInputs(ArmIOInputs inputs){


    // System.out.println(simEncoder.getCount());
    inputs.temperature = 0;
    inputs.radians = Math.toRadians(simEncoder.getCount() * ArmConstants.kgearRatio);
    inputs.position = inputs.radians * Math.PI / 180;
    inputs.rpm = (simEncoder.getRate() / 360) * 60;
    inputs.velocity = simEncoder.getRate() / 22.75;
    sim.setInputVoltage(voltage);
    sim.update(.02);
    simEncoder.setCount((int) ((sim.getAngleRads() / 6.28) * ArmConstants.kgearRatio));
  }

  @Override
  public void setVoltage(double voltage){
    this.voltage = voltage;
  }
}
