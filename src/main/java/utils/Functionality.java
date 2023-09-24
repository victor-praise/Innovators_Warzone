package main.java.utils;

/**
 * Represents an individual Functionality in the user command string, e.g. -add Asia 4, for an editcontinent command
 * @author kevin on 2023-09-21
 */
public class Functionality {
    /**
     * functionality of the base command, e.g. 'Add' in -add Asia 4
     */
    public BaseFunctionality functionality;

    /**
     * set of parameters for the given functionality, e.g. <i>Asia</i> and <i>4</i> are params to <i>add Asia 4</i>
     */
    public String[] functionalityParams;

    /**
     * Creates a Functionality form associated data
     * @param p_functionality functionality of the base command
     * @param p_functionalityParams parameters to a given functionality
     */
    public Functionality(BaseFunctionality p_functionality, String[] p_functionalityParams) {
        this.functionality = p_functionality;
        this.functionalityParams = p_functionalityParams;
    }
}