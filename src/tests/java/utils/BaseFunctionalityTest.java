package tests.java.utils;

import main.java.commands.BaseFunctionality;
import org.junit.jupiter.api.Test;

/**
 * @author kevin on 2023-09-23
 */
class BaseFunctionalityTest {
    /**
     * given a proper functionality enum, its label should be as expected.
     */
    @Test
    void givenEnumTypeWhenLabelExtractedThenIsAsExpected() {
        BaseFunctionality command = BaseFunctionality.Remove;
        assert command.d_label.equals("remove");
    }

    /**
     * Given a valid functionality string, valid the generated enum is as expected
     */
    @Test
    void givenValidStringWhenEnumRequestedThenEnumCreated() {
        String commandString = "add";
        BaseFunctionality command = BaseFunctionality.from(commandString);
        assert command == BaseFunctionality.Add;
    }

    /**
     * Given an invalid string, validate that enum generated is 'None'
     */
    @Test
    void givenInvalidStringWhenCommandRequestedThenNoneCreated() {
        String functionalityString = "add_invalid";
        BaseFunctionality functionality = BaseFunctionality.from(functionalityString);
        assert functionality == BaseFunctionality.None;
    }
}