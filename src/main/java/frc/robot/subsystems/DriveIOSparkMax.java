package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveBaseConstants;


public class DriveIOSparkMax implements DriveIO{
     
    private CANSparkMax mFrontLeft; 
    private CANSparkMax mFrontRight; 
    private CANSparkMax mBackLeft; 
    private CANSparkMax mBackRight; 
    
    private RelativeEncoder encFrontLeft; 
    private RelativeEncoder encFrontRight; 
    private RelativeEncoder encBackLeft; 
    private RelativeEncoder encBackRight; 
    
    private AHRS gyro; 

    private DifferentialDrive drive; 

    public DriveIOSparkMax(){ 
        mFrontLeft = new CANSparkMax(DriveBaseConstants.frontLeft, MotorType.kBrushless); 
        mFrontRight  = new CANSparkMax(DriveBaseConstants.frontRight,MotorType.kBrushless); 
        mBackLeft = new CANSparkMax(DriveBaseConstants.backLeft, MotorType.kBrushless); 
        mBackRight = new CANSparkMax(DriveBaseConstants.backRight, MotorType.kBrushless);  
        
        mBackRight.follow(mFrontRight);   
        mBackLeft.follow(mFrontLeft); 
        
        encFrontLeft = mFrontLeft.getEncoder(); 
        encFrontLeft.setPositionConversionFactor(DriveBaseConstants.linConvFactor); 
        encFrontLeft.setVelocityConversionFactor(DriveBaseConstants.linConvFactor / 60); 

        encFrontRight = mFrontRight.getEncoder();  
        encFrontRight.setPositionConversionFactor(DriveBaseConstants.linConvFactor); 
        encFrontRight.setVelocityConversionFactor(DriveBaseConstants.linConvFactor / 60); 

        encBackRight = mBackRight.getEncoder(); 
        encBackRight.setPositionConversionFactor(DriveBaseConstants.linConvFactor); 
        encBackRight.setVelocityConversionFactor(DriveBaseConstants.linConvFactor/60);

        encBackLeft = mBackLeft.getEncoder(); 
        encBackLeft.setPositionConversionFactor(DriveBaseConstants.linConvFactor); 
        encBackLeft.setVelocityConversionFactor(DriveBaseConstants.linConvFactor / 60); 
        
        gyro = new AHRS(SPI.Port.kMXP); 

        drive = new DifferentialDrive(mFrontLeft,mFrontRight);  

        gyro.calibrate();  
        gyro.reset(); 
    } 

    public void arcadeDrive(double speed, double rotation){ 
        if (speed < 0.05){speed = 0;} 
        if (rotation < 0.05){rotation = 0;}  

        drive.arcadeDrive(speed,rotation); 
        drive.feed();
    } 

    public double getFlPos(){ 
        return encFrontLeft.getPosition();
    }  

    public double getFrPos(){ 
        return encFrontRight.getPosition(); 
    } 

    public double getBrPos(){ 
        return encBackRight.getPosition();
    } 

    public double getBlPos(){ 
        return encBackLeft.getPosition(); 
    } 

    public double getCenterPos(){ 
        return (getFlPos() + getFrPos() + getBlPos() + getBrPos()) / 4; 
    }   

    public double getBrVelPerSec(){ 
        return encBackRight.getVelocity();
    } 

    public double getBlVelPerSec(){ 
        return encBackLeft.getVelocity();
    } 

    public double getFrVelPerSec(){ 
        return encFrontRight.getVelocity();  
    } 

    public double getFlVelPerSec(){ 
        return encFrontLeft.getVelocity();
    } 

    public double getCenterVelocity(){ 
        return (getBlVelPerSec() + getBrVelPerSec() + getFlVelPerSec() + getFrVelPerSec()) / 4;
    } 

    public double getPitch(){ 
        return gyro.getPitch();
    } 

    public double getRoll(){ 
        return gyro.getRoll();
    }  

    public double getYaw(){ 
        return gyro.getYaw();
    }



}
