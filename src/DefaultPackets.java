import VexnextDriver.VEXnetPacket;

public interface DefaultPackets {
    VEXnetPacket FULL_STOP = VEXnetPacket.compileControllerPacket(
            (byte) (127), (byte) (127), (byte) (127), (byte) (127),
            false, false,
            false, false,
            false, false, false, false,
            false, false, false, false,
            (byte) 127, (byte) 127, (byte) 127);

    VEXnetPacket FULL_FORWARD = drive(255,255);

    VEXnetPacket FULL_REVERSE = drive(0,0);

    VEXnetPacket FULL_OPEN_CLAW = claw(255);

    VEXnetPacket FULL_CLOSE_CLAW = claw(0);

    private static VEXnetPacket drive(int i1, int i2) {
        if (i1 <= 255 && i1 >= 0 && i2 <= 255 && i2 >= 0) {
            return VEXnetPacket.compileControllerPacket(
                    (byte) (i1), (byte) (i2), (byte) (127), (byte) (127),
                    false, false,
                    false, false,
                    false, false, false, false,
                    false, false, false, false,
                    (byte) 127, (byte) 127, (byte) 127);
        } else return FULL_STOP;
    }

    private static VEXnetPacket arm(int i1) {
        if(i1 <= 255 && i1 >= 0){
            return VEXnetPacket.compileControllerPacket(
                (byte) (127), (byte) (127), (byte) (i1), (byte) (127),
                false, false,
                false, false,
                false, false, false, false,
                false, false, false, false,
                (byte) 127, (byte) 127, (byte) 127);
        }
        else return FULL_STOP;
    }

    private static VEXnetPacket claw(int i1) {
        if(i1 <= 255 && i1 >= 0){
            return VEXnetPacket.compileControllerPacket(
                (byte) (127), (byte) (127), (byte) (127), (byte) (i1),
                false, false,
                false, false,
                false, false, false, false,
                false, false, false, false,
                (byte) 127, (byte) 127, (byte) 127);
        }
        else return FULL_STOP;
    }
}
