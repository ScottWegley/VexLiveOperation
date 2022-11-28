import VexnetDriver.VEXnetPacket;

/**
 * Thread to check our driver for return packets.
 * Until deactivated, log any packets received.
 */
class ReturnLogger extends Thread {

    /**Boolean used to reflect the state of the thread
     * that starts return logger.
     * @see #deactivate()
     */
    private boolean active = false;

    @Override
    public void run() {
        active = true;
        while (active) {
            VEXnetPacket packet_receive = App.driver.ReceiveVexProtocolPacket();
            if (packet_receive != null) {
                // System.out.println(packet_receive);
            }
        }
    }

    /** Used to force the thread to finish. 
     * Useless if the thread has not been started.
     * @see #active
    */
    public void deactivate() {
        active = false;
    }
}