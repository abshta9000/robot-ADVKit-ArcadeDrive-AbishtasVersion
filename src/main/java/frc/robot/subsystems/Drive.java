package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.subsystems.DriveIO.DriveIOInputs;

public class Drive extends SubsystemBase{ 
    private final DriveIO io;  
    private final DriveIOInputsAutoLogged inputs = new DriveIOInputsAutoLogged();  

    public Drive(DriveIO io){ 
      this.io = io; 
    } 

    @Override 
    public void periodic(){   
        io.updateInputs(inputs);   
        Logger.getInstance().processInputs("Drive Train", inputs);

    } 

    public void arcadeDrive(double speed, double rotation){ 
        var speeds = DifferentialDrive.arcadeDriveIK(speed, rotation, false);  
        io.setVoltage(speeds.left, speeds.right); 
    } 

    public void tankDrive(double leftVolts, double rightVolts){ 
        var speeds = DifferentialDrive.tankDriveIK(leftVolts, rightVolts, false);  
        io.setVoltage(speeds.left,speeds.right); 
    } 

    public void stop(){ 
        io.setVoltage(0,0);
    }
} 
