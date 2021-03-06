package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;

/*
    An Xbox Controller Class
*/
public class XboxController {
  private final Joystick mController;

  // Button Enums
  public enum Side {
    LEFT,
    RIGHT
  }

  public enum Axis {
    X,
    Y
  }

  public enum Button {
    A(1),
    B(2),
    X(3),
    Y(4),
    LB(5),
    RB(6),
    BACK(7),
    START(8),
    L_JOYSTICK(9),
    R_JOYSTICK(10);

    public final int id;

    Button(int id) {
      this.id = id;
    }
  }

  private boolean Rumble = false;

  // Pass in the port of the Controller
  public XboxController(int portArg) {
    mController = new Joystick(portArg);
    checkNameAndPort();
  }

  private boolean alreadyWarnedInSimulator = false;

  public boolean checkNameAndPort() {
    return true;
  }

  double getJoystick(Side side, Axis axis) {
    double deadband = 0.15;

    boolean left = side == Side.LEFT;
    boolean y = axis == Axis.Y;
    // multiplies by -1 if y-axis (inverted normally)
    return handleDeadband(
        (y ? -1 : 1) * mController.getRawAxis((left ? 0 : 4) + (y ? 1 : 0)), deadband);
  }

  // analog trigger version
  double getTriggerValue(Side side) {
    return mController.getRawAxis(side == Side.LEFT ? 2 : 3);
  }

  // boolean trigger version
  boolean getTrigger(Side side) {
    return mController.getRawAxis(side == Side.LEFT ? 2 : 3)
        > 0.15;
  }

  boolean getButton(Button button) {
    return mController.getRawButton(button.id);
  }

  /*
      Xbox Controller D-Pad
      up -> 0, right -> 90, down -> 180, left -> 270
  */
  int getDPad() {
    return mController.getPOV();
  }

  public void setRumble(boolean on) {
    // Check to see if we are already on
    if (Rumble != on) {
      mController.setRumble(RumbleType.kRightRumble, on ? 1 : 0);
      Rumble = on;
    }
  }

  private double handleDeadband(double value, double deadband) {
    return (Math.abs(value) > Math.abs(deadband)) ? value : 0;
  }
}