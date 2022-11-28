import VexnetDriver.VEXnetDriver;

public class App {

    public static VEXnetDriver driver = null;
    private static Scheduler scheduler = new Scheduler();

    public static void main(String[] args) {

        String[] comPorts = VEXnetDriver.availableComPorts();
        if (comPorts.length == 0) {
            System.out.println("There are no com ports available.");
            // return; //Uncomment for final version. If testing w/o robot, leave commented
            // as will likely prematurely end program.
        } else {
            System.out.println("Using: " + comPorts[0]);
            driver = new VEXnetDriver(comPorts[0], VEXnetDriver.DeviceType.VEXnet_Joystick_Partner_Port);
        }

        Command.setDriver(driver);

        scheduler.add(new Command(Command.FULL_FORWARD, 5000));
        scheduler.add(new Command(Command.FULL_OPEN_CLAW, 250));
        scheduler.start();

    }
}
