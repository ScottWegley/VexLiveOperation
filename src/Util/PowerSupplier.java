package Util;

/**This interface can be added to any class
 * that provides pollable integer values 
 * to be used for live robot-control.
 * Process any inputs in {@link #getLeftDrive()},
 * {@link #getRightDrive()},  {@link #getArmPower()},
 * and {@link #getClawPower()} before returning.
 */
public interface PowerSupplier {
    
    /**Returns an integer value for the left driving motor.
     * Currently only values between 0-255 are accepted by
     * the VEXnetDriver.
     */
    public int getLeftDrive();
    /**Returns an integer value for the right driving motor.
     * Currently only values between 0-255 are accepted by
     * the VEXnetDriver.
     */
    public int getRightDrive(); 
    /**Returns an integer value for the claw motor.
     * Currently only values between 0-255 are accepted by
     * the VEXnetDriver.
     */
    public int getClawPower();
    /**Returns an integer value for the arm motor.
     * Currently only values between 0-255 are accepted by
     * the VEXnetDriver.
     */
    public int getArmPower();

}
