package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for a journal page
 */
public class JournalWindow extends UiPart<Stage> {

    private static final String FXML = "JournalWindow.fxml";

    @FXML
    private AnchorPane journalTextPlaceholder;

    public JournalWindow (Stage root) {
        super (FXML, root);

        TextArea journalTextArea = new TextArea();
        setAnchor(journalTextArea);
        journalTextPlaceholder.getChildren().add(journalTextArea);

        root.setTitle("Journal");
        root.initModality(Modality.APPLICATION_MODAL);
    }

    public JournalWindow () {
        this(new Stage());
    }

    private void setAnchor(TextArea journalTextArea) {
        AnchorPane.setTopAnchor(journalTextArea, 0.0);
        AnchorPane.setBottomAnchor(journalTextArea, 0.0);
        AnchorPane.setLeftAnchor(journalTextArea, 0.0);
        AnchorPane.setRightAnchor(journalTextArea, 0.0);
    }


    public void show() {
        getRoot().show();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }
}
