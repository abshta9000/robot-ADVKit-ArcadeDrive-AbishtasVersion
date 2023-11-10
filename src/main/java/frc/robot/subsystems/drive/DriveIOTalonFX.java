package frc.robot.subsystems.drive;
//import org.littletonrobotics.junction.Logger;
import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Constants.DriveBaseConstants;


public class DriveIOTalonFX implements DriveIO{
     
    private WPI_TalonFX mFrontLeft = new WPI_TalonFX(DriveBaseConstants.frontLeft) ; 
    private WPI_TalonFX mFrontRight = new WPI_TalonFX(DriveBaseConstants.frontRight); 
    private WPI_TalonFX mBackLeft = new WPI_TalonFX(DriveBaseConstants.backLeft); 
    private WPI_TalonFX mBackRight = new WPI_TalonFX(DriveBaseConstants.backRight); 

    
    // private RelativeEncoder encFrontLeft; 
    // private RelativeEncoder encFrontRight; 
    // private RelativeEncoder encBackLeft; 
    // private RelativeEncoder encBackRight; 
    
    private AHRS gyro; 

    //private DifferentialDrive drive; 

    public DriveIOTalonFX(){ 

        
        mBackRight.follow(mFrontRight);   
        mBackLeft.follow(mFrontLeft); 
        
        // encFrontLeft.setPositionConversionFactor(DriveBaseConstants.linConvFactor); 
        // encFrontLeft.setVelocityConversionFactor(DriveBaseConstants.linConvFactor / 60); 

        // encFrontRight.setPositionConversionFactor(DriveBaseConstants.linConvFactor); 
        // encFrontRight.setVelocityConversionFactor(DriveBaseConstants.linConvFactor / 60); 

        // encBackRight.setPositionConversionFactor(DriveBaseConstants.linConvFactor); 
        // encBackRight.setVelocityConversionFactor(DriveBaseConstants.linConvFactor/60);
 
        // encBackLeft.setPositionConversionFactor(DriveBaseConstants.linConvFactor); 
        // encBackLeft.setVelocityConversionFactor(DriveBaseConstants.linConvFactor / 60); 
        
        gyro = new AHRS(SPI.Port.kMXP); 

        //drive = new DifferentialDrive(mFrontLeft,mFrontRight);  

        gyro.calibrate();  
        gyro.reset(); 
    } 
    
    @Override 
    public void updateInputs(DriveIOInputs inputs){ 
        double leftBackMotorPosition = mBackLeft.getSelectedSensorPosition() * DriveBaseConstants.kEncoderDistancePerPulse;
        double leftFrontMotorPosition = mFrontLeft.getSelectedSensorPosition()* DriveBaseConstants.kEncoderDistancePerPulse; 
        double rightBackMotorPosition = mBackRight.getSelectedSensorPosition()* DriveBaseConstants.kEncoderDistancePerPulse; 
        double rightFrontMotorPosition = mFrontRight.getSelectedSensorPosition()* DriveBaseConstants.kEncoderDistancePerPulse;  
        
        inputs.leftBackMotorPosition = leftBackMotorPosition;
        inputs.leftFrontMotorPosition = leftFrontMotorPosition; 
        inputs.rightBackMotorPosition = rightBackMotorPosition; 
        inputs.rightFrontMotorPosition = rightFrontMotorPosition;  

        inputs.centerPosition = (leftBackMotorPosition + leftFrontMotorPosition + rightBackMotorPosition + rightFrontMotorPosition) / 4; 

        double leftBackMotorVelocity = mBackLeft.getSelectedSensorVelocity() * (10.0 / DriveBaseConstants.kEncoderCPR) * DriveBaseConstants.kwheelCircumference; 
        double leftFrontMotorVelocity = mFrontLeft.getSelectedSensorVelocity() * (10.0 / DriveBaseConstants.kEncoderCPR) * DriveBaseConstants.kwheelCircumference;
        double rightFrontMotorVelocity = mFrontRight.getSelectedSensorVelocity() * (10.0 / DriveBaseConstants.kEncoderCPR) * DriveBaseConstants.kwheelCircumference;
        double rightBackMotorVelocity = mBackRight.getSelectedSensorVelocity() * (10.0 / DriveBaseConstants.kEncoderCPR) * DriveBaseConstants.kwheelCircumference;
        
        inputs.leftBackMotorVelocity = leftBackMotorVelocity;
        inputs.leftFrontMotorVelocity = leftFrontMotorVelocity;
        inputs.rightFrontMotorVelocity = rightFrontMotorVelocity;
        inputs.rightBackMotorVelocity = rightBackMotorVelocity;

        inputs.centerVelocity = (leftBackMotorVelocity + leftFrontMotorVelocity + rightFrontMotorVelocity + rightBackMotorVelocity) / 4;
        
        inputs.gyroPitch = Rotation2d.fromDegrees(gyro.getPitch());  
        inputs.gyroRoll = Rotation2d.fromDegrees(gyro.getRoll()); 
        inputs.gyroYaw = Rotation2d.fromDegrees(gyro.getYaw()); 
    }  

    @Override
    public void setVoltage(double leftVolts, double rightVolts){ 
        mFrontRight.setVoltage(rightVolts);
        mFrontLeft.setVoltage(leftVolts);  
    }
    
    

}
