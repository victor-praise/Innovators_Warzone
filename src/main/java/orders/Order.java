package main.java.orders;

/**
 * All orders issued by any player conforms to executable 'Order' type
 * @author kevin on 2023-10-03
 */
public interface Order {

    /**
     * Each order will have an execute method where the execution logic will be inserted
     */
    public void execute();
}
