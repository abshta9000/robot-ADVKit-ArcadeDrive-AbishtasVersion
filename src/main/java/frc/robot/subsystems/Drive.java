package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.DriveIO.DriveIOInputs;

public class Drive extends SubsystemBase{ 
    private final DriveIO io;  
   // private final DriveIOInputsAutoLogged inputs = new DriveIOInputs();  

    public Drive(DriveIO io){ 
      this.io = io; 
    } 

    @Override 
    public void periodic(){   
        // io.updateInputs(inputs);   
        // Logger.getInstance().processInputs("Drive Train", inputs);

    }
} 
