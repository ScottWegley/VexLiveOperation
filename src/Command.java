import VexnetDriver.VEXnetDriver;
import VexnetDriver.VEXnetPacket;

/**
 * Thread that contains a VEXnetPacket to send.
 * The thread is paused for a specified duration after sending.
 * Then a packet to stop all motors is sent before ending.
 * This last packets makes the transition to the next command smoother on the
 * motors.
 */
class Command extends Thread {
    /**
     * Stores the packet that is sent on execution.
     * Assigned in the constructor.
     */
    private VEXnetPacket toExecute;
    /**
     * Stores the duration of the command in milliseconds.
     * Assigned in the constructor.
     */
    private long duration;
    /**
     * Stores a reference to the VEXnetDriver that all commands can use.
     * Set with {@link #setDriver(VEXnetDriver)}
     */
    private static VEXnetDriver driver;

    /**
     * @param exec A VexnetPacket to be executed
     * @param dur  How long to execute in milliseconds.
     */
    public Command(VEXnetPacket exec, long dur) {
        this.toExecute = exec;
        this.duration = dur;
    }

    /**
     * Function to assign the VEXnetDriver value so all instances of Command can use
     * it.
     * 
     * @see #driver
     */
    public static void setDriver(VEXnetDriver driv) {
        driver = driv;
    }

    @Override
    public void run() {
        try {
            System.out.println("Command Begins");
            for (int i = 0; i < this.toExecute.data.length; i++) {
                if (this.toExecute.data[i] != PacketBuilder.FULL_STOP.data[i]) {
                    System.out.println(PacketData.values()[i] + ": " + this.toExecute.data[i]);
                }
            }
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < duration) {
                driver.SendVexProtocolPacket(toExecute);
            }
            driver.SendVexProtocolPacket(PacketBuilder.FULL_STOP);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}