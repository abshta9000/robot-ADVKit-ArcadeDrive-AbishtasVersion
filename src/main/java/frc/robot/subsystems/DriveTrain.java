package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveBaseConstants;

public class DriveTrain extends SubsystemBase {
     
    private CANSparkMax mFrontLeft; 
    private CANSparkMax mFrontRight; 
    private CANSparkMax mBackLeft; 
    private CANSparkMax mBackRight; 
    
    private RelativeEncoder encFrontLeft; 
    private RelativeEncoder encFrontRight; 
    private RelativeEncoder encBackLeft; 
    private RelativeEncoder encBackRight; 

    private DifferentialDrive drive; 

    public DriveTrain(){ 
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

        drive = new DifferentialDrive(mFrontLeft,mFrontRight); 
    } 

    public void tankDrive(double leftV, double rightV){ 
        if (leftV < 0.05){leftV = 0;} 
        if (rightV < 0.05){rightV = 0;}  

        drive.tankDrive(leftV,rightV); 
        drive.feed();
    } 

    public double getEncoderPos(){ 
        return (encFrontLeft.getPosition() + encFrontRight.getPosition() + encBackLeft.getPosition() + encBackRight.getPosition()) / 4; 
    }  

    public double getMotorVelocity(){ 
        return (encFrontLeft.getVelocity() + encFrontRight.getVelocity() + encBackLeft.getVelocity() + encBackRight.getVelocity()) / 4;
    }

    @Override 

    public void periodic(){ 
        Logger.getInstance().recordOutput("Drivebase/Encoder Distance in Meters", getEncoderPos()); 
        Logger.getInstance().recordOutput("Drivebase/Encoder velocity per second",getMotorVelocity());
    }
}
