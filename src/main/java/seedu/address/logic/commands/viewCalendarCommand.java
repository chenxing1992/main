package seedu.address.logic.commands;



import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;



/**
 * Lists all persons in the address book to the user.
 */
public class viewCalendarCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_ALIAS = "v";

    public static final String MESSAGE_SUCCESS = "Calendar of all events are out!";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
