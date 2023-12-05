package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.Logger;
import frc.robot.Constants.DriveBaseConstants;
import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase{ 
    private final DriveIO io;  
    private final DriveIOInputsAutoLogged inputs = new DriveIOInputsAutoLogged();  

    private DifferentialDriveOdometry odometry;
    // private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(
    //     DriveBaseConstants.kTrackWidth
    // );

    private boolean drivesniperMode = false;
    private double initialGyroPitch;

    public Drive(DriveIO io){ 
      this.io = io; 
      this.initialGyroPitch = getGyroPitch();

      this.odometry = new DifferentialDriveOdometry(getRotation2d(), inputs.leftFrontMotorPosition, inputs.rightFrontMotorPosition);
    } 

    @Override 
    public void periodic(){   
        odometry.update(getRotation2d(), inputs.leftFrontMotorPosition, inputs.rightFrontMotorPosition);
        io.updateInputs(inputs);   
        Logger.getInstance().recordOutput("DrivePosition", odometry.getPoseMeters());
        Logger.getInstance().processInputs("DriveTrain", inputs);
        SmartDashboard.putBoolean("DriveSniperMode", drivesniperMode);
    } 

    public void arcadeDrive(double i_speed, double i_rotation){
        
        double speed = i_speed;
        double rotation = i_rotation;
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

    public double getGyroPitch(){
        return inputs.gyroPitch;
    }

    public double getInitialGyroPitch(){
        return initialGyroPitch;
    }
    public Rotation2d getRotation2d(){
        return new Rotation2d(Math.toRadians(inputs.gyroYaw));
    }
} 
