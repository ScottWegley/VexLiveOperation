import VEXnetDriver.VEXnetDriver;
import VEXnetDriver.VEXnetPacket;

import java.util.concurrent.TimeUnit;

public class example {
    private static VEXnetDriver driver = null;

    public static void main(String[] args) {

        // Get a list of serial ports on attached to your computer
        String[] comPorts = VEXnetDriver.availableComPorts();
        String[] comPortsDescriptive = VEXnetDriver.availableComPortsDescriptive();

        String comPort = null;

        // If there are no serial ports detected, exit the program
        if (comPorts.length == 0) {
            System.out.println("There are no com ports available.");
            return;
        }
        // Use the first serial device (assumes that there is only one connected to your computer).
        // If there is more than one device, change the value of comPort to the name of the
        // desired serial port based on its name in comPorts.
        comPort = comPorts[0];
        System.out.println("Using: " + comPort);

        // Create an instance of the driver
        driver = new VEXnetDriver(comPort, VEXnetDriver.DeviceType.VEXnet_Joystick_Partner_Port);

        // Create a controller packet. This is the packet type used to send control information
        // The analogue values (the first four) are a range of 0 to 255 with 127 being the centered value.
        // The first byte controls the left wheel's motor, the second byte controls the right wheel's motor,
        // the third byte controls the arm's motor, and the forth byte controls the claw's motor
        VEXnetPacket packet_send = VEXnetPacket.compileControllerPacket(
                (byte)(127), (byte)(127), (byte)(127), (byte)(127),
                false, false,
                false, false,
                false, false, false, false,
                false, false, false, false,
                (byte)127, (byte)127, (byte)127);

        // Send the packet to the robot
        driver.SendVexProtocolPacket(packet_send);

        // Attempt to retrieve a packet from the robot.
        // This packet will not contain any useful information, but can be used to detect if the
        // robot has become disconnected. The robot will send packets to the computer less frequently
        // than it expects packets from the computer.
        VEXnetPacket packet_receive = driver.ReceiveVexProtocolPacket();
        // If the function above returned null, no packet was received.
        if (packet_receive != null)
            System.out.println(packet_receive); // Print out the contents of the packet.
    }
}
