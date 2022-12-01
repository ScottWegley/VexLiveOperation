import Util.Command;
import Util.PacketBuilder;
import Util.Scheduler;
import VexnetDriver.VEXnetDriver;

public class Autonomous {

    public static VEXnetDriver driver;
    private static Scheduler scheduler;

    public static void main(String[] args) {

        String[] comPorts = VEXnetDriver.availableComPorts();
        if (comPorts.length == 0) {
            System.out.println("There are no com ports available.");
            return; //Uncomment for final version. If testing w/o robot, leave commented
            // as will likely prematurely end program.
        } else {
            System.out.println("Using: " + comPorts[0]);
            driver = new VEXnetDriver(comPorts[0], VEXnetDriver.DeviceType.VEXnet_Joystick_Partner_Port);
            scheduler = new Scheduler(driver);
            Command.setDriver(driver);
        }

        scheduler.add(new Command(PacketBuilder.FULL_FORWARD, 2500));
        scheduler.add(new Command(PacketBuilder.FULL_STOP, 1000));
        scheduler.add(new Command(PacketBuilder.FULL_REVERSE, 1000));
        scheduler.start();

    }
}
