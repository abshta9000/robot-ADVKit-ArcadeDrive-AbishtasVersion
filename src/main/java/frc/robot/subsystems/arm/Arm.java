// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class Arm extends SubsystemBase {

  private final ArmIO io;  
  private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();  

  private Mechanism2d mech2d = new Mechanism2d(2,2);
  private MechanismRoot2d root = mech2d.getRoot("elbow", 2, 0);
  private MechanismLigament2d humurus = root.append(new MechanismLigament2d("humerus", 1, 0));

  private Pose2d pose2d;

  private boolean sniperMode = false;
  private boolean shouldHoldArm = false;

  /** Creates a new Arm. */
  public Arm(ArmIO io) {
    this.io =io;
    
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);   
    humurus.setAngle(inputs.degrees);
    pose2d = new Pose2d(0,ArmConstants.karmLengthMeters,new Rotation2d(inputs.degrees));
    Logger.getInstance().recordOutput("ArmPosition",pose2d);
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
    } else io.setVoltage(speed);
  }

  public boolean safeTemp(){
    if (inputs.temperature > ArmConstants.kSafeTemp){
      System.out.println("ARM TEMPERATURE EXCEEDED");
      return false;
    } return true;
  }


}
