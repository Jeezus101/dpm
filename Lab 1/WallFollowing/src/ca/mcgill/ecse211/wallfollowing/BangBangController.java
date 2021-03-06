package ca.mcgill.ecse211.wallfollowing;

import lejos.hardware.motor.*;

public class BangBangController implements UltrasonicController {

  private final int bandCenter;
  private final int bandwidth;
  private final int motorLow;
  private final int motorHigh;
  private int distance;

  public BangBangController(int bandCenter, int bandwidth, int motorLow, int motorHigh) {
    // Default Constructor
    this.bandCenter = bandCenter;
    this.bandwidth = bandwidth;
    this.motorLow = motorLow;
    this.motorHigh = motorHigh;
    
    WallFollowingLab.leftMotor.setSpeed(motorHigh); // Start robot moving forward
    WallFollowingLab.rightMotor.setSpeed(motorHigh);
    WallFollowingLab.leftMotor.forward();
    WallFollowingLab.rightMotor.forward();
  }


  @Override
  public void processUSData(int distance) {
    this.distance = distance;
    // Declare local variables
    int deltaSpeed = 190, error = 0;
    Boolean left = false;
    
    // Calculate distance error
    error = distance - bandCenter;
    
    // Decide to move left or right
    if (error < 0) left = true;
    
    // Check if error greater than bandwidth
    if (Math.abs(error) > bandwidth) {
		if (left) {
        	WallFollowingLab.leftMotor.setSpeed(motorHigh - deltaSpeed);
        } else {
        	WallFollowingLab.leftMotor.setSpeed(motorHigh + deltaSpeed);
        }
    }
  }
  
  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
