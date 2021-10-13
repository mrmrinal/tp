package donnafin.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AttributePanel extends UiPart<Region> {

    private static final String FXML = "AttributePanel.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label fieldLabel;

    @FXML
    private TextField valueTextField;

    @FXML
    private Label valueLabel;

    @FXML
    private AnchorPane anchorPane;

    private final EditHandler editor;
    private State state = State.VIEW_MODE;
    private String value;

    enum State {
        EDIT_MODE,
        VIEW_MODE
    }

    @FunctionalInterface
    interface EditHandler {
        /**
         * EditHandlers will take in the new value and apply it to the given field.
         * If the edit was successful, it will return null else return the error
         * message thrown when trying to edit the field in this manner.
         *
         * @param newValue the new value to be used in the field.
         * @return error message thrown by {@code PersonAdapter}.
         */
        String applyEdit(String newValue);
    }


    /**
     * Constructor for Attribute panel
     *
     * @param fieldInString field heading.
     * @param value initial value of the attribute.
     * @param editor the callback used to commit changes to model.
     */
    public AttributePanel(String fieldInString, String value, EditHandler editor) {
        super(FXML);
        this.editor = editor;
        this.value = value;

        fieldLabel.setText(fieldInString);
        valueLabel.setText(this.value);
        valueTextField.setText(this.value);
        setEditable(false);
        valueTextField.focusedProperty().addListener((
            ignoreObservable, ignoreOldValue, newValue) -> setHighlight(newValue));
    }

    private void setHighlight(boolean isOnFocus) {
        if (isOnFocus) {
            anchorPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");
        } else {
            anchorPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
        }
    }

    private void setEditable(boolean isToBeEditable) {
        int textFieldOpacity = isToBeEditable ? 1 : 0;
        int labelOpacity = isToBeEditable ? 0 : 1;

        this.valueTextField.setOpacity(textFieldOpacity);
        this.valueLabel.setOpacity(labelOpacity);
        this.valueTextField.setEditable(isToBeEditable);
    }

    /**
     * Handles the Enter button pressed event.
     * Updates client info after user edit.
     */
    @FXML
    private void handleCommandEntered() {
        this.value = valueTextField.getText();
        this.valueTextField.setText(this.value);
        this.valueLabel.setText(this.value);

        switch (this.state) {
        case EDIT_MODE:
            String errMessage = editor.applyEdit(this.value);
            if (errMessage != null) {
                handleError(errMessage);
            } else {
                setEditable(false);
                this.state = State.VIEW_MODE;
            }
            break;
        case VIEW_MODE:
            setEditable(true);
            this.state = State.EDIT_MODE;
            break;
        default:
            assert false : "Attribute Panel in an unexpected state";
        }
    }

    private void handleError(String errMessage) {
        Alert editFailed = new Alert(Alert.AlertType.ERROR);
        editFailed.setHeaderText("Could not save your edit.");
        editFailed.setContentText(errMessage);
        editFailed.show();
    }
}
