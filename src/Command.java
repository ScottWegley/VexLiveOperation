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
    /**Stores the packet that is sent on execution.
     * Assigned in the constructor.
     */
    private VEXnetPacket toExecute;
    /**Stores the duration of the command in milliseconds.
     * Assigned in the constructor.
     */
    private long duration;
    /**Stores a reference to the VEXnetDriver that all commands can use.
     * Set with {@link #setDriver(VEXnetDriver)}
     */
    private static VEXnetDriver driver;

    static VEXnetPacket FULL_STOP = VEXnetPacket.compileControllerPacket(
            (byte) (127), (byte) (127), (byte) (127), (byte) (127),
            false, false,
            false, false,
            false, false, false, false,
            false, false, false, false,
            (byte) 127, (byte) 127, (byte) 127);

    static VEXnetPacket FULL_FORWARD = drive(255, 255);

    static VEXnetPacket FULL_REVERSE = drive(0, 0);

    static VEXnetPacket FULL_OPEN_CLAW = claw(255);

    static VEXnetPacket FULL_CLOSE_CLAW = claw(0);

    /**Function used to construct packets that send only driving signals. */
    private static VEXnetPacket drive(int i1, int i2) {
        if (i1 <= 255 && i1 >= 0 && i2 <= 255 && i2 >= 0) {
            return VEXnetPacket.compileControllerPacket(
                    (byte) (i1), (byte) (i2), (byte) (127), (byte) (127),
                    false, false,
                    false, false,
                    false, false, false, false,
                    false, false, false, false,
                    (byte) 127, (byte) 127, (byte) 127);
        } else
            return FULL_STOP;
    }

    /**Function used to construct packets that send only arm control signals. */
    private static VEXnetPacket arm(int i1) {
        if (i1 <= 255 && i1 >= 0) {
            return VEXnetPacket.compileControllerPacket(
                    (byte) (127), (byte) (127), (byte) (i1), (byte) (127),
                    false, false,
                    false, false,
                    false, false, false, false,
                    false, false, false, false,
                    (byte) 127, (byte) 127, (byte) 127);
        } else
            return FULL_STOP;
    }

    /**Function used to construct packets that send only claw control signals. */
    private static VEXnetPacket claw(int i1) {
        if (i1 <= 255 && i1 >= 0) {
            return VEXnetPacket.compileControllerPacket(
                    (byte) (127), (byte) (127), (byte) (127), (byte) (i1),
                    false, false,
                    false, false,
                    false, false, false, false,
                    false, false, false, false,
                    (byte) 127, (byte) 127, (byte) 127);
        } else
            return FULL_STOP;
    }

    /**
     * @param exec A VexnetPacket to be executed
     * @param dur  How long to execute in milliseconds.
     */
    public Command(VEXnetPacket exec, long dur) {
        this.toExecute = exec;
        this.duration = dur;
    }

    /** Function to assign the VEXnetDriver value so all instances of Command can use it.
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
                if (this.toExecute.data[i] != Command.FULL_STOP.data[i]) {
                    System.out.println(PacketData.values()[i] + ": " + this.toExecute.data[i]);
                }
            }
            // driver.SendVexProtocolPacket(toExecute);
            Thread.sleep(duration);
            // driver.SendVexProtocolPacket(FULL_STOP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}