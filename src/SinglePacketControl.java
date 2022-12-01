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
        if (!active)
            throw new RuntimeException("Power Supplier not set.");
        logger.start();
        while (active) {
            updatePacket();
            driver.SendVexProtocolPacket(getPacket());
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

    private synchronized void updatePacket() {
        currentState = PacketBuilder.fullControl(supplier.getLeftDrive(),
                supplier.getRightDrive(),
                supplier.getArmPower(),
                supplier.getClawPower());
    }

    private synchronized VEXnetPacket getPacket() {
        return currentState;
    }

    public void setSupplier(PowerSupplier inSupplier) {
        supplier = inSupplier;
        activate();
    }
}
