import java.util.LinkedList;

/**
 * A Thread-Like class to store a FIFO queue of commands.
 * On run, these commands are iterated through and executed.
 * Previous commands must finish before continuing to the next.
 */
class Scheduler extends Thread {
    /**A FIFO LinkedList of {@link Command}'s to be executed sequentially.
     * Added to through {@link #add(Command)} and cleared with {@link #reset()}
     */
    private LinkedList<Command> commandQueue = new LinkedList<Command>();
    private ReturnLogger logger = new ReturnLogger();

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
        } finally {
            logger.deactivate();
        }
    }

    /** Add a command to the {@link #commandQueue} */
    public synchronized void add(Command c) {
        commandQueue.add(c);
    }

    /**
     *  Clear the {@link #commandQueue}.
    */
    public synchronized void reset() {
        commandQueue.clear();
    }
}
