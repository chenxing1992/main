package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.JournalWindowHandle;
import javafx.stage.Stage;

public class JournalWindowTest extends GuiUnitTest {

    private JournalWindow journalWindow;
    private JournalWindowHandle journalWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> journalWindow = new JournalWindow());
        Stage journalWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(journalWindow.getRoot().getScene()));
        FxToolkit.showStage();
        journalWindowHandle = new JournalWindowHandle(journalWindowStage);
    }

    @Test
    public void isShowing_journalWindowIsShowing_true() {
        guiRobot.interact(() -> journalWindow.show());
        assertTrue(journalWindow.isShowing());
    }

    //    @Test
    //    public void focus_isFocusedIsWorking_focused() {
    //        guiRobot.interact(() -> journalWindow.show());
    //        assertTrue(journalWindow.isFocused());
    //
    //        guiRobot.interact(() -> journalWindow.getRoot().hide());
    //        assertFalse(journalWindow.isFocused());
    //    }
    //
    //    @Test
    //    public void focus_journalWindowStaysFocused_focused() {
    //        guiRobot.interact(() -> journalWindow.show());
    //        guiRobot.removeFocus();
    //        assertTrue(journalWindow.getRoot().isFocused());
    //    }

    @Test
    public void isShowing_journalWindowIsHiding_false() {
        guiRobot.interact(() -> journalWindow.getRoot().hide());
        assertFalse(journalWindow.isShowing());
    }
}
