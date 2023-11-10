package frc.robot.subsystems.drive;

//import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveBaseConstants;


public class DriveIOSim implements DriveIO{
     
    private DifferentialDrivetrainSim sim = DifferentialDrivetrainSim
        .createKitbotSim(KitbotMotor.kDualCIMPerSide, KitbotGearing.k10p71, KitbotWheelSize.kSixInch, null);
    //gearing may be wrong, not sure what it is, so i copied from akvantage kit
    //std holy crap

    private double leftAppliedVolts = 0.0;
    private double rightAppliedVolts = 0.0;
 
    
    @Override 
    public void updateInputs(DriveIOInputs inputs){ 
        double leftBackMotorPosition = Units.radiansToRotations(sim.getLeftPositionMeters() / Units.inchesToMeters(DriveBaseConstants.wheelRadius));
        double leftFrontMotorPosition = Units.radiansToRotations(sim.getLeftPositionMeters() / Units.inchesToMeters(DriveBaseConstants.wheelRadius));
        double rightBackMotorPosition = Units.radiansToRotations(sim.getRightPositionMeters() / Units.inchesToMeters(DriveBaseConstants.wheelRadius));
        double rightFrontMotorPosition = Units.radiansToRotations(sim.getRightPositionMeters() / Units.inchesToMeters(DriveBaseConstants.wheelRadius));
        
        inputs.leftBackMotorPosition = leftBackMotorPosition;
        inputs.leftFrontMotorPosition = leftFrontMotorPosition; 
        inputs.rightBackMotorPosition = rightBackMotorPosition; 
        inputs.rightFrontMotorPosition = rightFrontMotorPosition;  

        inputs.centerPosition = (leftBackMotorPosition + leftFrontMotorPosition + rightBackMotorPosition + rightFrontMotorPosition) / 4; 

        double leftBackMotorVelocity = Units.radiansToRotations(sim.getLeftVelocityMetersPerSecond() / Units.inchesToMeters(DriveBaseConstants.wheelRadius));
        double leftFrontMotorVelocity = Units.radiansToRotations(sim.getLeftVelocityMetersPerSecond() / Units.inchesToMeters(DriveBaseConstants.wheelRadius));
        double rightFrontMotorVelocity = Units.radiansToRotations(sim.getRightVelocityMetersPerSecond() / Units.inchesToMeters(DriveBaseConstants.wheelRadius));
        double rightBackMotorVelocity = Units.radiansToRotations(sim.getRightVelocityMetersPerSecond() / Units.inchesToMeters(DriveBaseConstants.wheelRadius));  
        
        inputs.leftBackMotorVelocity = leftBackMotorVelocity;
        inputs.leftFrontMotorVelocity = leftFrontMotorVelocity;
        inputs.rightFrontMotorVelocity = rightFrontMotorVelocity;
        inputs.rightBackMotorVelocity = rightBackMotorVelocity;

        inputs.centerVelocity = (leftBackMotorVelocity + leftFrontMotorVelocity + rightFrontMotorVelocity + rightBackMotorVelocity) / 4;
        
        inputs.gyroPitch = new Rotation2d(0);  
        inputs.gyroRoll = new Rotation2d(0);
        inputs.gyroYaw = sim.getHeading(); 
    }  

    @Override
    public void setVoltage(double leftVolts, double rightVolts){ 
        leftAppliedVolts = MathUtil.clamp(leftVolts, -12.0, 12.0);
        rightAppliedVolts = MathUtil.clamp(rightVolts, -12.0, 12.0);
        sim.setInputs(leftAppliedVolts, rightAppliedVolts);
    }
    
    

}
