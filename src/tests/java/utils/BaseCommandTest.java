package tests.java.utils;

import main.java.utils.BaseCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kevin on 2023-09-22
 */
class BaseCommandTest {

    @Test
    void givenEnumType_whenLabelExtracted_thenIsAsExpected() {
        BaseCommand command = BaseCommand.EditMap;
        assert command.label.equals("editmap");
    }

    @Test
    void givenValidString_whenEnumRequested_thenEnumCreated() {
        String commandString = "showmap";
        BaseCommand command = BaseCommand.from(commandString);
        assert command == BaseCommand.ShowMap;
    }

    @Test
    void givenAmericanEnglishString_whenNeighborRequested_thenNeighbourCreated() {
        String commandString = "editneighbor";
        BaseCommand command = BaseCommand.from(commandString);
        assert command == BaseCommand.EditNeighbour;
    }
}