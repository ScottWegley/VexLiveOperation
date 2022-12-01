import VexnetDriver.VEXnetDriver;
import VexnetDriver.VEXnetPacket;

public class SinglePacketControl extends Thread {

    private static VEXnetPacket currentState;

    private static boolean active = false;
    private static VEXnetDriver driver;
    private static ReturnLogger logger = new ReturnLogger();

    @Override
    public void run() {
        logger.start();
        }
        logger.deactivate();
    }

    public synchronized void activate(){
        active = true;
    }

    private synchronized void deactivate(){
        active = false;
    }

    public synchronized void updatePacket(){

    }

    public synchronized VEXnetPacket getPacket(){
        return currentState;
    }
}
