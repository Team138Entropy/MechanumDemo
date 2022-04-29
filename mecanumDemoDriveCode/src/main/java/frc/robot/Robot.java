// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.XboxController.Axis;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.Jaguar;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot {
  //new contoller with code that i totally stole
  XboxController DriveController = new XboxController(0);
  //jaguar setup, the channels are just placeholders
  //front left
  PWMTalonSRX Talon1 = new PWMTalonSRX(0);
  //back left
  PWMTalonSRX Talon2 = new PWMTalonSRX(1);
  //front right
  PWMTalonSRX Talon3 = new PWMTalonSRX(2);
  //back right
  PWMTalonSRX Talon4 = new PWMTalonSRX(3);

  //mecanum drive object
  MecanumDrive robotDrive = new MecanumDrive(Talon1, Talon2, Talon3, Talon4);

  //default stuff
  private SendableChooser<Boolean> mDriveMode;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    
    
    
  }


  @Override
  public void robotPeriodic() {
    updateSmartDashboard();   
    //setup for the types of mecanum drives
    if (mDriveMode.getSelected()) {
      robotDrive.drivePolar(
       DriveController.getJoystick(XboxController.Side.LEFT,XboxController.Axis.Y),
       DriveController.getJoystick(XboxController.Side.LEFT,XboxController.Axis.X),
       DriveController.getJoystick(XboxController.Side.RIGHT,XboxController.Axis.Y));
    }
    else {
      robotDrive.driveCartesian(
      DriveController.getJoystick(XboxController.Side.LEFT,XboxController.Axis.Y),
      DriveController.getJoystick(XboxController.Side.LEFT,XboxController.Axis.X),
      DriveController.getJoystick(XboxController.Side.RIGHT,XboxController.Axis.Y));
    }
  }

  //separate function for the dashboard so things don't get messy
  public void updateSmartDashboard() {
    //selector for testing the types of drives
    mDriveMode = new SendableChooser<Boolean>();
    mDriveMode.setDefaultOption("Polar drive", true);
    mDriveMode.addOption("Cartesian drive", false);
    SmartDashboard.putBoolean("on drive polar", mDriveMode.getSelected());
    SmartDashboard.putNumber("Talon1", Talon1.get());
    SmartDashboard.putNumber("Talon2", Talon2.get());
    SmartDashboard.putNumber("Talon3", Talon3.get());
    SmartDashboard.putNumber("Talon4", Talon4.get());
    //there is a mecanum drive diagram widget on the shuffleboard so i hope this will make it work.
    SmartDashboard.putData("mecanum drive", robotDrive);
  }


  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {

  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {

  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {

  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
    System.out.println("Simulation is up");
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    System.out.println("simulation is going");
  }
}
