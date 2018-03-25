package seedu.address.logic.commands;

import seedu.address.ui.JournalWindow;

/**
 * Creates a new window for typing journal entries
 */
public class NewJournal extends Command {

    public static final String COMMAND_WORD = "new";
    public static final String COMMAND_ALIAS = "n";

    public static final String NEW_JOURNAL_ENTRY_CREATED = "New Journal Entry Created.";

    @Override
    public CommandResult execute() {
        JournalWindow journalWindow = new JournalWindow();
        journalWindow.show();

        return new CommandResult(NEW_JOURNAL_ENTRY_CREATED);
    }
}
