package seedu.address.model.person.timetable;

import java.util.HashMap;

//@@author marlenekoh
/**
 * Represents a module from NUSMods timetable.
 */
public class TimetableModule {
    private final String moduleCode;
    private HashMap<String, String> listOfLessons; // Key is lesson type, Value is class type

    public TimetableModule(String moduleCode, HashMap<String, String> listOfLessons) {
        this.moduleCode = moduleCode;
        this.listOfLessons = listOfLessons;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public HashMap<String, String> getListOfLessons() {
        return listOfLessons;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this // short circuit if same object
                || (other instanceof TimetableModule // instanceof handles nulls
                && this.moduleCode.equals(((TimetableModule) other).moduleCode)
                && this.listOfLessons.equals(((TimetableModule) other).listOfLessons))); // state check
    }
}
