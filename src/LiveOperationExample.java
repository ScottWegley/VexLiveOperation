import Util.SinglePacketControl;
import VexnetDriver.VEXnetDriver;

public class LiveOperationExample {
    
    public static VEXnetDriver driver;
    private static SinglePacketControl spc;

    public static void main(String[] args) {
        String[] comPorts = VEXnetDriver.availableComPorts();
        if (comPorts.length == 0) {
            System.out.println("There are no com ports available.");
            return; //Uncomment for final version. If testing w/o robot, leave commented
            // as will likely prematurely end program.
        } else {
            System.out.println("Using: " + comPorts[0]);
            driver = new VEXnetDriver(comPorts[0], VEXnetDriver.DeviceType.VEXnet_Joystick_Partner_Port);
            spc = new SinglePacketControl(driver);
        }

        spc.setSupplier(null);
        spc.start();
    }

    
}
