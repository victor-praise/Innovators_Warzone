package main.java.utils;

import main.java.commands.BaseCommand;
import main.java.commands.BaseFunctionality;
import main.java.commands.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static main.java.arena.Game.sharedInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author kevin on 2023-09-21
 */
class CommandParserTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        sharedInstance().getD_map().setD_continents(new ArrayList<>());
        sharedInstance().getD_map().setD_countries(new ArrayList<>());
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
        assertEquals(BaseCommand.None, l_invalidCommand.d_command, "Expected None command but found: " + l_invalidCommand.d_command);
    }
}