package main.java.utils.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Singleton class that records all actions of the game.
 *
 * @author Victor
 */
public class LogEntryBuffer implements Observable{
    /**
     * A static object of LogEntryBuffer
     */
    private static LogEntryBuffer Logger = null;
    /**
     * A list of observers
     */
    private List<Observer> d_ObserverList = new ArrayList<>();

    /**
     * gets the instance of LogEntryBuffer
     * @return LogEntryBuffer Logger
     */
    public static LogEntryBuffer getInstance() {
        if (Objects.isNull(Logger)) {
            Logger = new LogEntryBuffer();
        }
        return Logger;
    }

    /**
     * gets information and notifies the Observer.
     *
     * @param p_message The message to be notified
     */
    public void log(String p_message) {
        System.out.println(p_message);
        notifyObservers(p_message);
    }

    /**
     * Clear logs
     */
    public void clear() {
        clearObservers();
    }

    /**
     * updates the Observer with the message.
     *
     * @param p_message The message to be updated
     */
    @Override
    public void notifyObservers(String p_message) {
        d_ObserverList.forEach(p_observer -> p_observer.update(p_message));
    }

    /**
     * adds an observer to the list of observers
     * @param p_Observer The observer to be added
     */
    @Override
    public void addObserver(Observer p_Observer) {
        this.d_ObserverList.add(p_Observer);
    }

    @Override
    public void clearObservers() {
        d_ObserverList.forEach(Observer::clearLogs);
    }
}
