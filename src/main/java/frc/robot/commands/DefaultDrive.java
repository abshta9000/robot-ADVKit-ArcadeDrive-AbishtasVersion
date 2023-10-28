package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DefaultDrive extends CommandBase {
    private DoubleSupplier leftVoltage; 
    private DoubleSupplier rightVoltage; 
    private DriveTrain sDriveTrain; 

    public DefaultDrive(DoubleSupplier leftVoltage, DoubleSupplier rightVoltage, DriveTrain sDriveTrain){ 
        this.leftVoltage = leftVoltage; 
        this.rightVoltage = rightVoltage;  
        this.sDriveTrain = sDriveTrain; 

        addRequirements(sDriveTrain);
    } 

    @Override 
    public void execute(){ 
        sDriveTrain.tankDrive(leftVoltage.getAsDouble(), rightVoltage.getAsDouble());
    }
}
