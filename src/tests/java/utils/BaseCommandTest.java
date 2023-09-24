package tests.java.utils;

import main.java.utils.BaseCommand;
import org.junit.jupiter.api.Test;

/**
 * @author kevin on 2023-09-22
 */
class BaseCommandTest {

    /**
     * Given a simple enum type, when the associated label is requested, it should return a lower cased expected string
     */
    @Test
    void givenEnumTypeWhenLabelExtractedThenIsAsExpected() {
        BaseCommand command = BaseCommand.EditMap;
        assert command.d_label.equals("editmap");
    }

    /**
     * Given a valid command string, validate that the expected enum is created from it.
     */
    @Test
    void givenValidStringWhenEnumRequestedThenEnumCreated() {
        String commandString = "showmap";
        BaseCommand command = BaseCommand.from(commandString);
        assert command == BaseCommand.ShowMap;
    }

    /**
     * Given an invalid command string, ensure that the command generated is of type 'NONE'
     */
    @Test
    void givenInvalidStringWhenCommandRequestedThenNoneCreated() {
        String commandString = "showmap_invalid";
        BaseCommand command = BaseCommand.from(commandString);
        assert command == BaseCommand.None;
    }

    @Test
    void givenAmericanEnglishStringWhenNeighborRequestedThenNeighbourCreated() {
        String commandString = "editneighbor";
        BaseCommand command = BaseCommand.from(commandString);
        assert command == BaseCommand.EditNeighbour;
    }
}