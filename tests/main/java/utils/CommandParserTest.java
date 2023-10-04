package main.java.utils;

import main.java.commands.BaseCommand;
import main.java.commands.BaseFunctionality;
import main.java.commands.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kevin on 2023-09-21
 */
class CommandParserTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Given a valid command string,
     */
    @Test
    void testGivenValidCommandStringWhenParsedThenProperCommandCreated() {
        String l_command = "editcontinent -add Africa 3";
        CommandParser l_parser = new CommandParser();
        Command l_validCommand = l_parser.parseCommandStatement(l_command);

        assert l_validCommand != null;
        assert l_validCommand.d_command == BaseCommand.EditContinent;
        assert l_validCommand.d_functionalities[0].functionality == BaseFunctionality.Add;
        assert l_validCommand.d_functionalities[0].functionalityParams.length == 2;
    }

    /**
     * Given an invalid command string, validate that it does not generate any command object
     */
    @Test
    void testGivenInvalidCommandStringWhenParsedThenNoneCreated() {
        String l_command = "customcommand -add Africa 3";
        CommandParser l_parser = new CommandParser();
        Command l_invalidCommand = l_parser.parseCommandStatement(l_command);
        assert l_invalidCommand == null;
        assertNull(l_invalidCommand);
    }
}