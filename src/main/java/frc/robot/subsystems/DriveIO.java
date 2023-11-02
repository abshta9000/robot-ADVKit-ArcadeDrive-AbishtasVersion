package frc.robot.subsystems; 
import org.littletonrobotics.junction.AutoLog;
public interface DriveIO {   
    @AutoLog
    public static class DriveIOInputs{  

        public double leftFrontMotorVelocity = 0.0; 
        public double rightFrontMotorVelocity = 0.0; 
        public double leftBackMotorVelocity = 0.0; 
        public double rightBackVelocity = 0.0;  
        
        public double centerVelocity = 0.0;

        public double leftFrontMotorPosition = 0.0; 
        public double leftBackMotorPosition = 0.0; 
        public double rightBackMotorPosition = 0.0; 
        public double rightFrontMotorPosition = 0.0;  

        public double centerPosition = 0.0;
        
        public double gyroPitch = 0.0; 
        public double gyroRoll = 0.0; 
        public double gyroYaw = 0.0; 
        /*
        public void logData(LogTable table){  

              table.put("Left Front (Encoder Velocity)",leftFrontMotorVelocity); 
              table.put("Left Back (Encoder Velocity)",leftBackMotorVelocity); 
              table.put("Right Front (Encoder Velocity)",rightFrontMotorVelocity); 
              table.put("Right Back (Encoder Velocity)",rightBackVelocity);  

              table.put("Center Encoder Velocity",centerVelocity);

              table.put("Left Front (Encoder Position)",leftFrontMotorPosition); 
              table.put("Left Back (Encoder Position)",leftBackMotorPosition); 
              table.put("Right Front (Encoder Position)",rightFrontMotorPosition); 
              table.put("Right Back (Encoder Positon)",rightBackMotorPosition);  

              table.put("Center Encoder Positon", centerPosition);  

              table.put("Gyro Pitch",gyroPitch); 
              table.put("Gyro Roll",gyroRoll); 
              table.put("Gyro Yaw",gyroYaw); 

        }   
        */ 
        }    
        
        public default void updateInputs(){} 
        
        
        public default void setVoltage(double leftVolts, double rightVolts){}  



        
 
    } 
