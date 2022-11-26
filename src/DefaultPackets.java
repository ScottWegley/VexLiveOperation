import VexnextDriver.VEXnetPacket;

public interface DefaultPackets {
    VEXnetPacket FULL_STOP = VEXnetPacket.compileControllerPacket(
            (byte) (127), (byte) (127), (byte) (127), (byte) (127),
            false, false,
            false, false,
            false, false, false, false,
            false, false, false, false,
            (byte) 127, (byte) 127, (byte) 127);
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
}
