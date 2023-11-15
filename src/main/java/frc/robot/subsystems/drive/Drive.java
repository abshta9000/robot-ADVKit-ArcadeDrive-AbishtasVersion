package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.Logger;
import frc.robot.Constants.DriveBaseConstants;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//likely necessary but cant figure it out
//import frc.robot.subsystems.DriveIOInputsAutoLogged;

public class Drive extends SubsystemBase{ 
    private final DriveIO io;  
    private final DriveIOInputsAutoLogged inputs = new DriveIOInputsAutoLogged();  

    private boolean drivesniperMode = false;

    public Drive(DriveIO io){ 
      this.io = io; 
    } 

    @Override 
    public void periodic(){   
        io.updateInputs(inputs);   
        Logger.getInstance().processInputs("Drive Train", inputs);
        SmartDashboard.putBoolean("DriveSniperMode", drivesniperMode);
    } 

    public void arcadeDrive(double i_speed, double i_rotation){
        double speed = 0;
        double rotation = 0;
        if (drivesniperMode) {
            System.out.println("DriveSubsystem -Sniper Mode is running");
            speed *= DriveBaseConstants.DRIVE_SNIPER_SPEED;
            rotation *= DriveBaseConstants.DRIVE_SNIPER_SPEED;
          }else {
            speed *= DriveBaseConstants.SPEED_REDUCTION;
            rotation *= DriveBaseConstants.ROTATION_REDUCTION;
          }
        
        var speeds = DifferentialDrive.arcadeDriveIK(speed, rotation, false);  
        io.setVoltage(speeds.left, speeds.right); 
    } 

    public void autonomousArcadeDrive(double i_speed, double i_rotation){
        SmartDashboard.putNumber("TURN OUTPUT", i_rotation);
        var speeds = DifferentialDrive.arcadeDriveIK(i_speed, i_rotation, false);  
        io.setVoltage(speeds.left, speeds.right); 
    }

    public void tankDrive(double leftVolts, double rightVolts){ 
        var speeds = DifferentialDrive.tankDriveIK(leftVolts, rightVolts, false);  
        io.setVoltage(speeds.left,speeds.right); 
    } 

    public void stop(){ 
        io.setVoltage(0,0);
    }

    public void setDriveSniperMode(boolean b) {
        drivesniperMode = b;
    }
} 
