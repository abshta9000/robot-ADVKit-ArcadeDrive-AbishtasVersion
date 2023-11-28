package frc.robot.subsystems.drive;

//import org.littletonrobotics.junction.Logger;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SPI;
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

    //private DifferentialDrive drive; 

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

        //drive = new DifferentialDrive(mFrontLeft,mFrontRight);  

        gyro.calibrate();  
        gyro.reset(); 
    } 
    
    @Override 
    public void updateInputs(DriveIOInputs inputs){ 
        inputs.leftBackMotorPosition = encBackLeft.getPosition();  
        inputs.leftFrontMotorPosition = encFrontLeft.getPosition(); 
        inputs.rightBackMotorPosition = encBackRight.getPosition(); 
        inputs.rightFrontMotorPosition = encFrontRight.getPosition();  

        inputs.centerPosition = (encBackLeft.getPosition() + encFrontLeft.getPosition() + encBackRight.getPosition() + encFrontRight.getPosition()) / 4; 

        inputs.leftBackMotorVelocity = encBackLeft.getVelocity();  
        inputs.leftFrontMotorVelocity = encFrontLeft.getVelocity(); 
        inputs.rightFrontMotorVelocity = encFrontRight.getVelocity(); 
        inputs.rightBackMotorVelocity = encBackRight.getVelocity();  

        inputs.centerVelocity = (encBackLeft.getVelocity() + encFrontLeft.getVelocity() + encFrontRight.getVelocity() + encBackRight.getVelocity()) / 4;
        
        inputs.gyroPitch = (gyro.getPitch());  
        inputs.gyroRoll = (gyro.getRoll()); 
        inputs.gyroYaw = (gyro.getYaw()); 
    }  

    @Override
    public void setVoltage(double leftVolts, double rightVolts){ 
        mFrontRight.set(rightVolts); 
        mFrontLeft.set(leftVolts);  
    }
    
    

}
