// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.Constants.ArmConstants;
import edu.wpi.first.math.numbers.N1;
import org.ejml.simple.SimpleMatrix;

public class ArmIOSimTalonFX implements ArmIO {
  /** Creates a new ArmIOSparkMax. */

  private Encoder encoder = new Encoder(ArmConstants.encoder.kchannelA,ArmConstants.encoder.kchannelB);
  private EncoderSim simEncoder;
  private SingleJointedArmSim sim;

  private double voltage = 0;

  public ArmIOSimTalonFX() {


    sim = new SingleJointedArmSim(
      DCMotor.getFalcon500(1), 
      ArmConstants.kgearRatio, 
      SingleJointedArmSim.estimateMOI(ArmConstants.karmLengthMeters, ArmConstants.karmMassKg), 
      ArmConstants.karmLengthMeters, 
      ArmConstants.kReverseSoftLimit / ArmConstants.kgearRatio, 
      ArmConstants.kForwardSoftLimit / ArmConstants.kgearRatio, 
      false);
    
    

    // im not sure what this does, but let me walk through what i think that happens
    // firstly, the .setState requires a matrick classed object, so i created one
    // theay into matrix
    // so the matrix class is just making it so that the .setState can read it
    // im not entireely sure what the double in the double aray do, ill update this comment when i do

    // left column controls velocity and rigth controls position
    // speed
    //      position
    // ⬇️   ⬇️
    // {69},{69}
    // still figuring out what the rows mean

    // if row length == column lenght, so like a square 
    // previous hypothesis is incorrect, always has to be 1x1 or theres and error

    // also btw this code basically sets the deafult state of the arm (right in the middle)
    double[][] defaultArray= {{0},{7.5}};
    Matrix defaultPosition = new Matrix<N2,N1>(new SimpleMatrix(defaultArray));
    sim.setState(defaultPosition);


    encoder.reset();
    encoder.setDistancePerPulse(1);

    simEncoder = new EncoderSim(encoder);
  }

  @Override 
  public void updateInputs(ArmIOInputs inputs){
    sim.update(.02);
    inputs.radians = Math.toRadians(simEncoder.getCount() * (ArmConstants.kgearRatio / 7.5));
    inputs.position = inputs.radians * Math.PI / 180;
    inputs.rpm = (simEncoder.getRate() / 360) * 60;
    inputs.velocity = simEncoder.getRate() / ArmConstants.kgearRatio;
    sim.setInputVoltage(voltage);
    simEncoder.setCount((int) ((sim.getAngleRads() / 6.28) * ArmConstants.kgearRatio));
  }

  @Override
  public void setVoltage(double voltage){
    this.voltage = voltage * 7.5;
  }
}
