package main.java.utils.logger;

/**
 * Observer interface
 */
public interface Observer {
    /**
     * updates the message for the observer
     *
     * @param p_S the message to be updated
     */
    void update(String p_S);

    /**
     * clear all logs
     */
    void clearLogs();
}
