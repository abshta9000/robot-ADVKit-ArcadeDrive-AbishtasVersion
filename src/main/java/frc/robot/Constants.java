// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static class ButtonBoard{
      public static final int BUTTONBOARD_PORT = 1;
      public static final int HIGH_BUTTON = 1;
      public static final int MID_BUTTON = 2;
      public static final int LOW_BUTTON = 3;
      public static final int SNIPERMODE_BUTTON = 4;
      public static final int CONE_BUTTON = 5;
      public static final int OUTTAKE_BUTTON = 6;
      public static final int GROUND_BUTTON = 4;
      public static final int SUBSTITUTION_BUTTON = 8;
      public static final int IDLE_BUTTON = 9;
      public static final int OUT_BUTTON = 10;
      public static final int IN_BUTTON = 11;
      public static final int INTAKE_BUTTON = 12;
      
  }
  } 
  public static class DriveBaseConstants {   
    // From Armstrong directly 
    public static final int frontLeft = 11; 
    public static final int frontRight = 13; 
    public static final int backRight = 15 ; 
    public static final int backLeft = 12;

    public static final double DRIVE_SNIPER_SPEED = 0.2;
    public static final double SPEED_REDUCTION = 0.95;
    public static final double ROTATION_REDUCTION = 0.6;


    public static final double gearRatio = 7.89; 
    public static final double wheelRadius = 3;
    public static final double kwheelCircumference = 18.85; 
    public static final double linConvFactor = Units.inchesToMeters(1/gearRatio*2*Math.PI*1*Units.inchesToMeters(wheelRadius) * (2.16/0.548));
    public static final double kEncoderCPR = 4096.0;
    public static final double kEncoderDistancePerPulse = ((2*wheelRadius) * Math.PI) / (double) kEncoderCPR;
  } 

  public static class AutonConstants{
    public enum Modes{
      EXP_RED_CONE_MOBILITY_TURN,
      EXP_CONE_MOBILITY_TURN_EXTEND,
      CUBE_MOBILITY_EXTEND_GRAB,
      CUBE_SCORE_ONLY,
      CONE_SCORE_ONLY,
      CONE_MOBILITY_DOCK,
      CONE_MOBILITY,
      DOCK,
    }
    public static final double kdriveSpeed = 0;
    public static final double kdriverotation = 0;
    public static final double kMaxTurnRateDegPerS = 0;
    public static final double kMaxTurnAccelerationDegPerSSquared = 0;
    public static final double kcontrollermodifier = 1;
    public static final double kfeedforwardmodifier = 1;
      

    public static double kp = 0;
    public  static double ki = 0;
    public  static double kd = 0;
  }


  public static final String driveType = "A"; 
}
