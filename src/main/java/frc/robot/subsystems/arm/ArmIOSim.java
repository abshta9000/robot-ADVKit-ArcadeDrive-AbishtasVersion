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

public class ArmIOSim implements ArmIO {
  /** Creates a new ArmIOSparkMax. */


  private SingleJointedArmSim sim;
  private Encoder encoder;   
  
  public ArmIOSim() {
    sim = new SingleJointedArmSim(
        new DCMotor(0,
            1, 
            1, 
            0, 
            0, 
            0), 
        0, 
        0, 
        0, 
        0, 
        0, 
        false);
    encoder = new Encoder(ArmConstants.encoder.kchannelA,ArmConstants.encoder.kchannelB);

    encoder.reset();
    armMotor.restoreFactoryDefaults();
    armMotor.clearFaults();
    armMotor.setIdleMode(IdleMode.kBrake);
    armMotor.setSmartCurrentLimit(60);
    armMotor.setSoftLimit(SoftLimitDirection.kForward, ArmConstants.kForwardSoftLimit);
    armMotor.setSoftLimit(SoftLimitDirection.kReverse, ArmConstants.kReverseSoftLimit);

  }

  @Override 
  public void updateInputs(ArmIOInputs inputs){
    inputs.temperature = 0;
    inputs.degrees = encoder.get() * ArmConstants.kgearRatio;
    inputs.position = inputs.degrees * Math.PI / 180;
  }

  public void setVoltage(int voltage){
    sim.setInput(voltage);
  }
}
