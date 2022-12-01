import VexnetDriver.VEXnetDriver;
import VexnetDriver.VEXnetPacket;

public class SinglePacketControl extends Thread {

    private static VEXnetPacket currentState;

    private static boolean active = false;
    private static VEXnetDriver driver;
    private static ReturnLogger logger = new ReturnLogger();

    private PowerSupplier supplier;

    public SinglePacketControl(VEXnetDriver driv) {
        currentState = PacketBuilder.FULL_STOP;
        driver = driv;
    }

    @Override
    public void run() {
        logger.start();
        }
        logger.deactivate();
    }

    @Override
    public void interrupt() {
        logger.deactivate();
        deactivate();
        super.interrupt();
    }

    private synchronized void activate() {
        active = true;
    }

    private synchronized void deactivate() {
        active = false;
    }

    public synchronized void updatePacket(){

    }

    public void setSupplier(PowerSupplier inSupplier) {
        supplier = inSupplier;
        activate();
    }
}
