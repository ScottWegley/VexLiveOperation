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
        activate();
        while (active) {
            VEXnetPacket packet_receive = Autonomous.driver.ReceiveVexProtocolPacket();
            if (packet_receive != null) {
                System.out.println(packet_receive);
            }
        }
    }

    /** Used to force the thread to finish. 
     * Useless if the thread has not been started.
     * @see #active
    */
    public synchronized void deactivate() {
        active = false;
    }

    /** (OPTIONAL) Used to enable the thread loop. 
     * Only useful before thread start.
     * @see #active
    */
    public synchronized void activate() {
        active = true;
    }
}