package tests.java.utils;

import main.java.utils.Command;
import main.java.utils.CommandParser;
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

    @Test
    void test_givenEditMapCommand_whenValidAddContinentRequested_thenCommandCreated() {
        String command = "editcontinent -add Africa 3";
        CommandParser parser = new CommandParser();
        Command validCommand = parser.parseCommandStatement(command);
        assert validCommand != null;
    }
    @Test
    void test_givenInvalidCommand_whenValidAddContinentRequested_thenNoneCreated() {
        String command = "customcommand -add Africa 3";
        CommandParser parser = new CommandParser();
        Command validCommand = parser.parseCommandStatement(command);
        assert validCommand == null;
    }
}