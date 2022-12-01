package Util;
import VexnetDriver.VEXnetDriver;
import VexnetDriver.VEXnetPacket;

/**
 * A Thread-Like class storing a synchronized access 
 * packet retrieving power values from a {@link PowerSupplier}
 * to send continually updated packets through VEXnet.
 * @author ScottWegley
 * @version 1.0
 */
public class SinglePacketControl extends Thread {

    /**This stores the current packet to be sent through a 
     * {@link VEXnetDriver}.  This should only be accessed
     * through {@link #updatePacket()} and {@link #getPacket()}.
     */
    private static VEXnetPacket currentState;

    private static boolean active = false;
    private VEXnetDriver driver;
    /**A logging utility to output packets received 
     * over the VEXnet while the scheduler remains active.
     */
    private static ReturnLogger logger;

    /**This object is polled with {@link #updatePacket()} 
     * in a loop to retrieve values and store them
     * before they are send off.
     */
    private PowerSupplier supplier;

    /**Creates a new {@link SinglePacketControl} with
     * a {@link VEXnetDriver} to send packets from.
     */
    public SinglePacketControl(VEXnetDriver driv) {
        currentState = PacketBuilder.FULL_STOP;
        this.driver = driv;
        logger = new ReturnLogger(this.driver);
    }

    @Override
    public void run() {
        if (!active)
            throw new RuntimeException("Power Supplier not set.");
        logger.start();
        while (active) {
            updatePacket();
            driver.SendVexProtocolPacket(getPacket());
        }
        logger.deactivate();
    }

     /**Force the exit of the thread loop and the loops
     * of all sub-threads.
     */
    @Override
    public void interrupt() {
        logger.deactivate();
        deactivate();
        super.interrupt();
    }

    /** Indicates that necessary 
     * outside values have been set
     * by updating {@link #active}
     * @see #deactivate()
    */
    private synchronized void activate() {
        active = true;
    }

    /** Used to force the thread to finish. 
     * Useless if the thread has not been started.
     * @see #activate()
    */
    private synchronized void deactivate() {
        active = false;
    }

    /**Updates the stored packet with values from
     * the {@link #supplier}.
     * @see #getPacket()
     */
    private synchronized void updatePacket() {
        currentState = PacketBuilder.fullControl(supplier.getLeftDrive(),
                supplier.getRightDrive(),
                supplier.getArmPower(),
                supplier.getClawPower());
    }

    /**Provides Thread-safe access to the current packet.
     * @see #updatePacket()
     */
    private synchronized VEXnetPacket getPacket() {
        return currentState;
    }

    /** (REQUIRED) Used to store the supplier from which 
     * this controller will pull values to send.
     * @see PowerSupplier
    */
    public synchronized void setSupplier(PowerSupplier inSupplier) {
        supplier = inSupplier;
        activate();
    }
}
