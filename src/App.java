
import VexnextDriver.VEXnetDriver;
import VexnextDriver.VEXnetPacket;

public class App {

    public static VEXnetDriver driver = null;
    public static void main(String[] args) {


        String[] comPorts = VEXnetDriver.availableComPorts();
        if (comPorts.length == 0) {
            System.out.println("There are no com ports available.");
            //return; //Uncomment for final version.  If testing w/o robot, leave commented as will likely prematurely end program.
        } else {
            System.out.println("Using: " + comPorts[0]);
            driver = new VEXnetDriver(comPorts[0], VEXnetDriver.DeviceType.VEXnet_Joystick_Partner_Port);
        }
class Scheduler extends Thread {
    LinkedList<Command> commandQueue = new LinkedList<Command>();
    ReturnLogger logger = new ReturnLogger();

    @Override
    public void run() {
        try {
            logger.start();
            for (Command command : commandQueue) {
                command.start();
                command.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.deactivate();
        } finally {
            logger.deactivate();
        }
    }

    public void add(Command c) {
        commandQueue.add(c);
    }

    public void reset() {
        commandQueue.clear();
    }
}

/*
 * Thread to check our driver for return packets.
 * Until deactivated, log any packets received.
 */
class ReturnLogger extends Thread {

    private boolean active = false;

    @Override
    public void run() {
        active = true;
        while (active) {
            VEXnetPacket packet_receive = App.driver.ReceiveVexProtocolPacket();
            if (packet_receive != null) {
                System.out.println(packet_receive);
            }
        }
    }

    public void deactivate(){
        active = false;
    }
}

/*
 * Thread that contains a VEXnetPacket to send.
 * The thread is paused for a specified duration after sending.
 * Then a packet to stop all motors is sent before ending.
 * This last packets makes the transition to the next command smoother on the motors.
 */
class Command extends Thread implements DefaultPackets {
    private VEXnetPacket toExecute;
    private long duration;
    private static VEXnetDriver driver;

    /**
     * @param exec A VexnetPacket to be executed
     * @param dur How long to execute in milliseconds.
     */
    public Command(VEXnetPacket exec, long dur) {
        this.toExecute = exec;
        this.duration = dur;
    }

    public static void setDriver(VEXnetDriver driv){
        driver = driv;
    }

    /**
     * @TODO Log any values from the packet that aren't stop values.
     */
    @Override
    public void run() {
        try {
            driver.SendVexProtocolPacket(toExecute);
            Thread.sleep(duration);
            driver.SendVexProtocolPacket(FULL_STOP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
