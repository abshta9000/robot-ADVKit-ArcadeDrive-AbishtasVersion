// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class IntakeIOSTalonFXSim implements IntakeIO {
  
  private SingleJointedArmSim armSim;
  
  public IntakeIOSTalonFXSim() {
    armSim = new SingleJointedArmSim(
      DCMotor.getFalcon500(1),
      50,
      0.5, 
      0.5, 
      0.0, 
      Math.PI * 2, 
      true);

  }

  @Override 
  public void updateInputs(IntakeIOInputs inputs){ 
    armSim.update(.02);

    inputs.velocity = armSim.getVelocityRadPerSec();
    inputs.position = armSim.getAngleRads();
    inputs.current = armSim.getCurrentDrawAmps();
  }  


  @Override
  public void setVoltage(double volts){ 
    armSim.setInputVoltage(volts);
  }
}
