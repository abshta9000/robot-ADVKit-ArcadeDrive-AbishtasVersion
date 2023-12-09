package frc.robot.subsystems.intake; 
import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {   
    @AutoLog
    public static class IntakeIOInputs{  
        public double velocity = 0;
        public double position = 0;
        public double speed = 0;
        public double current = 0;
        public double temperature = 0;
    }    
        
    public default void updateInputs(IntakeIOInputs inputs){} 
        
        
    public default void setVoltage(double volts){}  



        
 
    } 
