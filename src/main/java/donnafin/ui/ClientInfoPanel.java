package donnafin.ui;

import donnafin.logic.InvalidFieldException;
import donnafin.logic.PersonAdapter;
import donnafin.model.person.Attribute;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ClientInfoPanel extends UiPart<Region> {
    private static final String FXML = "ClientInfoPanel.fxml";
    private final PersonAdapter personAdapter;

    @FXML
    private VBox clientInfoList;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ClientInfoPanel(PersonAdapter personAdapter) {
        super(FXML);
        this.personAdapter = personAdapter;

        personAdapter.getAllAttributesList().stream()
                .map(attr -> createAttributePanel(attr).getRoot())
                .forEach(y -> clientInfoList.getChildren().add(y));
    }

    private AttributePanel createAttributePanel(Attribute attr) {
        String fieldInString = attr.getClass().getSimpleName();
        return new AttributePanel(
                fieldInString,
                attr.toString(),
                createEditHandler(getPersonField(fieldInString))
        );
    }

    /** Gets the PersonField enum type of attribute from label */
    private PersonAdapter.PersonField getPersonField(String fieldInString) {
        switch(fieldInString) {
        case "Name":
            return PersonAdapter.PersonField.NAME;
        case "Address":
            return PersonAdapter.PersonField.ADDRESS;
        case "Phone":
            return PersonAdapter.PersonField.PHONE;
        case "Email":
            return PersonAdapter.PersonField.EMAIL;
        default:
            throw new IllegalArgumentException("Unexpected Person Field used");
        }
    }

    private AttributePanel.EditHandler createEditHandler(PersonAdapter.PersonField field) {
        return newValue -> {
            try {
                this.personAdapter.edit(field, newValue);
                return null;
            } catch (InvalidFieldException e) {
                return e.getMessage();
            }
        };
    }
}
