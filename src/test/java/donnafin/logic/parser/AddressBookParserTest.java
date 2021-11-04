package donnafin.logic.parser;

import donnafin.logic.commands.*;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.NameContainsKeywordsPredicate;
import donnafin.model.person.Person;
import donnafin.testutil.PersonBuilder;
import donnafin.testutil.PersonUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static donnafin.commons.core.Messages.*;
import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, "") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearThrowsException_invalidInput() {
        assertThrows(ParseException.class, () -> parser.parseCommand(ClearCommand.COMMAND_WORD, "25"));
        assertThrows(ParseException.class, () -> parser.parseCommand(ClearCommand.COMMAND_WORD, "apple"));
    }

    @Test
    public void parseCommand_multipleWordsClear_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, () ->
                parser.parseCommand(ClearCommand.COMMAND_WORD, "task"));
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, "") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitThrowsException_invalidInput() {
        assertThrows(ParseException.class, () -> parser.parseCommand(ExitCommand.COMMAND_WORD, "25"));
        assertThrows(ParseException.class, () -> parser.parseCommand(ExitCommand.COMMAND_WORD, "apple"));
    }

    @Test
    public void parseCommand_multipleWordsExit_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, () -> parser.parseCommand("exit client"));
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, "") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpThrowsException_invalidInput() {
        assertThrows(ParseException.class, () -> parser.parseCommand(HelpCommand.COMMAND_WORD, "25"));
    }

    @Test
    public void parseCommand_multipleWordsHelp_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, () -> parser.parseCommand("help me"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, "") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listThrowsException_invalidInput() {
        assertThrows(ParseException.class, () -> parser.parseCommand(ListCommand.COMMAND_WORD, "25"));
        assertThrows(ParseException.class, () -> parser.parseCommand(ListCommand.COMMAND_WORD, "apple"));
    }

    @Test
    public void parseCommand_multipleWordsList_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, () -> parser.parseCommand("list 2"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_viewCommand() throws Exception {
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD, "1") instanceof ViewCommand);
    }

    @Test
    public void parseCommand_viewCommandInvalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(ViewCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(ViewCommand.COMMAND_WORD, ""));
        assertThrows(ParseException.class, () -> parser.parseCommand(ViewCommand.COMMAND_WORD, "0"));
    }

    @Test
    public void parseCommand_appendCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(AppendCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(AppendCommand.COMMAND_WORD, ""));
        assertThrows(ParseException.class, () -> parser.parseCommand(AppendCommand.COMMAND_WORD,
                "append n/Diamond Policy i/AIA iv/$10000 pr/$200 c/$1000"));
        assertThrows(ParseException.class, () -> parser.parseCommand(AppendCommand.COMMAND_WORD,
                "append n/Property debt with DBS ty/debt v/$100000 r/10% annual interest"));

    }

    @Test
    public void parseCommand_removeCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(RemoveCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(RemoveCommand.COMMAND_WORD, ""));
        assertThrows(ParseException.class, () -> parser.parseCommand(RemoveCommand.COMMAND_WORD, "1"));
    }

    @Test
    public void parseCommand_editCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(EditCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(EditCommand.COMMAND_WORD, ""));
        assertThrows(ParseException.class, () -> parser.parseCommand(EditCommand.COMMAND_WORD, "n/Allison Wang"));
        assertThrows(ParseException.class, () -> parser.parseCommand(EditCommand.COMMAND_WORD, "a/#12-123 Phua Chu Kang Ave 7"));
        assertThrows(ParseException.class, () -> parser.parseCommand(EditCommand.COMMAND_WORD, "p/89012303"));
        assertThrows(ParseException.class, () -> parser.parseCommand(EditCommand.COMMAND_WORD, "e/allison@email.com"));
    }

    @Test
    public void parseCommand_switchCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(SwitchTabCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(SwitchTabCommand.COMMAND_WORD, ""));
        assertThrows(ParseException.class, () -> parser.parseCommand(SwitchTabCommand.COMMAND_WORD, "asset"));
        assertThrows(ParseException.class, () -> parser.parseCommand(SwitchTabCommand.COMMAND_WORD, "liability"));
        assertThrows(ParseException.class, () -> parser.parseCommand(SwitchTabCommand.COMMAND_WORD, "note"));
        assertThrows(ParseException.class, () -> parser.parseCommand(SwitchTabCommand.COMMAND_WORD, "contact"));
    }
}
