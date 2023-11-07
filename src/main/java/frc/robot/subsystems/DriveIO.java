package frc.robot.subsystems; 
import org.littletonrobotics.junction.AutoLog;
public interface DriveIO {   
    @AutoLog
    public static class DriveIOInputs{  
        public double leftFrontMotorVelocity = 0.0; 
        public double rightFrontMotorVelocity = 0.0; 
        public double leftBackMotorVelocity = 0.0; 
        public double rightBackMotorVelocity = 0.0;  
        
        public double centerVelocity = 0.0;

        public double leftFrontMotorPosition = 0.0; 
        public double leftBackMotorPosition = 0.0; 
        public double rightBackMotorPosition = 0.0; 
        public double rightFrontMotorPosition = 0.0;  

        public double centerPosition = 0.0;
        
        public double gyroPitch = 0.0; 
        public double gyroRoll = 0.0; 
        public double gyroYaw = 0.0;
        
       
        }    
        
    public default void updateInputs(DriveIOInputs inputs){} 
        
        
    public default void setVoltage(double leftVolts, double rightVolts){}  



        
 
    } 
