package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;


/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class CalendarDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(CalendarDisplay.class);
    private static final String FXML = "CalendarDisplay.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    public CalendarDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }


}
