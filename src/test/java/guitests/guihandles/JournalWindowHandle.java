package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code JournalWindow} of the application.
 */
public class JournalWindowHandle extends StageHandle {

    public static final String JOURNAL_WINDOW_TITLE = "Journal";

    public JournalWindowHandle(Stage journalWindowStage) {
        super(journalWindowStage);
    }

    /**
     * Returns true if a journal window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(JOURNAL_WINDOW_TITLE);
    }
}
