package main.java.utils.logger;

/**
 * Observable interface
 * @author Victor
 */
public interface Observable {
    /**
     * notifies Observer when there is a change.
     *
     * @param p_String the observable
     */
    public void notifyObservers(String p_String);

    /**
     * add observer
     *
     * @param p_Observer observer object
     */
    public void addObserver(Observer p_Observer);

    /**
     * clear observer
     */
    public void clearObservers();
}
