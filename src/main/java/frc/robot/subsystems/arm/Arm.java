// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class Arm extends SubsystemBase {

  private final ArmIO io;  
  private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();  

  private boolean sniperMode = false;
  private boolean shouldHoldArm = false;

  /** Creates a new Arm. */
  public Arm(ArmIO io) {
    this.io =io;
    
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);   
    Pose3d end = new Pose3d(
      .3,
      0,
      .80,
      new Rotation3d(( inputs.radians),0,Math.PI/2));
    Logger.getInstance().recordOutput("ArmPosition",end);
    Logger.getInstance().processInputs("Arm", inputs);
    SmartDashboard.putBoolean("ArmSniperMode", sniperMode);
    SmartDashboard.putBoolean("ArmSafeTemp", safeTemp());
  }

  public void setArmSniperMode(boolean sniperMode){
    this.sniperMode = sniperMode;
  }

  public boolean getArmSniperMode(){
    return sniperMode;
  }

  public void setArmHold(boolean shouldHoldArm){
    this.shouldHoldArm = shouldHoldArm;
  }

  public boolean getArmHold(){
    return shouldHoldArm;
  }

  public double getArmPosition(){
    return inputs.position;
  }

  public double getArmVelocity(){
    return inputs.velocity;
  }

  public double getArmRPM(){
    return inputs.rpm;
  }

  public void manualArm(double speed){

    if(sniperMode){
      io.setVoltage(speed * ArmConstants.kSniperSpeed);
    } else {
      io.setVoltage(speed);
    }
    

  }

  public boolean safeTemp(){
    if (inputs.temperature > Constants.kSafeTemp){
      System.out.println("Arm - Motor temperature exceeded");
      io.setVoltage(0);
      return false;
    } return true;
  }


}
