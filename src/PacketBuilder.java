import VexnetDriver.VEXnetPacket;

public class PacketBuilder {
    static VEXnetPacket FULL_STOP = VEXnetPacket.compileControllerPacket(
            (byte) (127), (byte) (127), (byte) (127), (byte) (127),
            false, false,
            false, false,
            false, false, false, false,
            false, false, false, false,
            (byte) 127, (byte) 127, (byte) 127);

    static VEXnetPacket FULL_FORWARD = drive(255, 0);

    static VEXnetPacket FULL_REVERSE = drive(0, 255);

    static VEXnetPacket FULL_OPEN_CLAW = claw(255);

    static VEXnetPacket FULL_CLOSE_CLAW = claw(0);

    /** Function used to construct packets that send only driving signals. */
    public static VEXnetPacket drive(int i1, int i2) {
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

    /** Function used to construct packets that send only arm control signals. */
    public static VEXnetPacket arm(int i1) {
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

    /** Function used to construct packets that send only claw control signals. */
    public static VEXnetPacket claw(int i1) {
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

    public static VEXnetPacket fullControl(int i1, int i2, int i3, int i4){
        if (i1 <= 255 && i1 >= 0 && i2 <= 255 && i2 >= 0 && i3 <= 255 && i3 >= 0 && i4 <= 255 && i4 >= 0) {
            return VEXnetPacket.compileControllerPacket(
                    (byte) (i1), (byte) (i2), (byte) (i3), (byte) (i4),
                    false, false,
                    false, false,
                    false, false, false, false,
                    false, false, false, false,
                    (byte) 127, (byte) 127, (byte) 127);
        } else
            return FULL_STOP;
    }
}
