import VexnetDriver.VEXnetDriver;
import VexnetDriver.VEXnetPacket;

public class SinglePacketControl extends Thread{
    
    static private VEXnetPacket currentState = PacketBuilder.FULL_STOP;

    static private boolean active = false;
    static VEXnetDriver driver;

    @Override
    public void run() {
        activate();
        while(active){

        }
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
