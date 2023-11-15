// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;

public class ArcadeCommand extends CommandBase {
  
  private DoubleSupplier speedFunction;
  private DoubleSupplier rotationFunction;
  private Drive driveSub;
  /** Creates a new ArcadeCommand. */
  public ArcadeCommand(DoubleSupplier speedFunction, DoubleSupplier rotationFunction, Drive driveSub) {
    this.speedFunction = speedFunction;
    this.rotationFunction = rotationFunction;
    this.driveSub = driveSub;
    
    addRequirements(driveSub);
  }

  public ArcadeCommand(Object object, Object object2, Drive driveSub) {
}


  @Override
  public void initialize() {}
 
  @Override
  public void execute() {
    double reducedSpeed = speedFunction.getAsDouble();
    double reducedRotation = rotationFunction.getAsDouble();

    if(Math.abs(reducedSpeed) < 0.1) reducedSpeed = 0;
    if(Math.abs(reducedRotation) < 0.1) reducedRotation = 0;

    driveSub.arcadeDrive(reducedSpeed, reducedRotation);
  }

  
  @Override
  public void end(boolean interrupted) {}

  
  @Override
  public boolean isFinished() {
    return false;
  }
}
