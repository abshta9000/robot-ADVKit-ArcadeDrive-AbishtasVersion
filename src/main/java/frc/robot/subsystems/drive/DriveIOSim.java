package frc.robot.subsystems.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import frc.robot.Constants.DriveBaseConstants;


public class DriveIOSim implements DriveIO{
     
    private DifferentialDrivetrainSim sim = DifferentialDrivetrainSim
        .createKitbotSim(KitbotMotor.kDualCIMPerSide, KitbotGearing.k10p71, KitbotWheelSize.kSixInch, null);

    private double leftAppliedVolts = 0.0;
    private double rightAppliedVolts = 0.0;
 
    
    @Override 
    public void updateInputs(DriveIOInputs inputs){ 
        sim.update(.02);
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
        
        inputs.gyroPitch = 0;  
        inputs.gyroRoll = 0;
        inputs.gyroYaw = sim.getHeading().getDegrees(); 
    }  

    @Override
    public void setVoltage(double leftVolts, double rightVolts){ 
        
        leftAppliedVolts = MathUtil.clamp(leftVolts, -12.0, 12.0) * 3;
        rightAppliedVolts = MathUtil.clamp(rightVolts, -12.0, 12.0) * 3;
        sim.setInputs(leftAppliedVolts, rightAppliedVolts);
    }
    
    

}
