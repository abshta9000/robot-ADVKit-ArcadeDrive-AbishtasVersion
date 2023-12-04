package frc.robot.subsystems.arm; 
import org.littletonrobotics.junction.AutoLog;
public interface ArmIO {   
    @AutoLog
    public static class ArmIOInputs{  
        
        public double temperature = 0;
        public double voltage = 0;
        public double position = 0;
        public double degrees = 0;
        public double rpm = 0;
        
    }    
        
    public default void updateInputs(ArmIOInputs inputs){} 
        
        
    public default void setVoltage(double voltage){}  



        
 
    } 
