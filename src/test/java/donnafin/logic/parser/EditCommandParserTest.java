package donnafin.logic.parser;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.EditCommand;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Address;
import donnafin.model.person.Email;
import donnafin.model.person.Name;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditCommandParserTest {

    private PersonAdapter personAdapter;
    private Model model;
    private Person person;
    private EditCommandParser parser;
    private final String EMPTY_SPACE = " ";

    @BeforeEach
    private void reset() {
        person = getTypicalPersons().get(0);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        this.personAdapter = new PersonAdapter(model, person);
        parser = new EditCommandParser(personAdapter);
    }

    @Test
    public void parseCommand_changesName() throws CommandException, ParseException {
        parser.parse(EditCommand.COMMAND_WORD + EMPTY_SPACE + "n/Allison Wang").execute(model);
        assertEquals(new Name("Allison Wang"),
                model.getFilteredPersonList().get(0).getName());
    }

    @Test
    public void parseCommand_changesPhone() throws CommandException, ParseException {
        parser.parse(EditCommand.COMMAND_WORD +  EMPTY_SPACE + "p/90725679").execute(model);
        assertEquals(new Phone("90725679"),
                model.getFilteredPersonList().get(0).getPhone());
    }

    @Test
    public void parseCommand_changesEmail() throws CommandException, ParseException {
        parser.parse(EditCommand.COMMAND_WORD +  EMPTY_SPACE + "e/allison@email.com").execute(model);
        assertEquals(new Email("allison@email.com"),
                model.getFilteredPersonList().get(0).getEmail());
    }

    @Test
    public void parseCommand_changesAddress() throws CommandException, ParseException {
        parser.parse(EditCommand.COMMAND_WORD +  EMPTY_SPACE + "a/759 Yishun Street 72").execute(model);
        assertEquals(new Address("759 Yishun Street 72"),
                model.getFilteredPersonList().get(0).getAddress());
    }

    @Test
    public void parseCommand_invalidFieldPrefix() throws CommandException, ParseException {
        assertThrows(ParseException.class, () -> parser.parse(EditCommand.COMMAND_WORD +  EMPTY_SPACE +
                "m/Allison Wang").execute(model));
    }

}
