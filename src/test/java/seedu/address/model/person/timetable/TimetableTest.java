package seedu.address.model.person.timetable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author marlenekoh
public class TimetableTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Timetable(null));
    }

    @Test
    public void constructor_invalidTimetable_throwsIllegalArgumentException() {
        String invalidTimetable = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Timetable(invalidTimetable));
    }

    @Test
    public void isValidTimetable() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Timetable.isValidTimetable(null));

        // invalid timetables
        assertFalse(Timetable.isValidTimetable("")); // empty string
        assertFalse(Timetable.isValidTimetable(" ")); // spaces only
        assertFalse(Timetable.isValidTimetable("www.google.com")); // invalid host
        assertFalse(Timetable.isValidTimetable("http://www.facebook.com")); // invalid host
        assertFalse(Timetable.isValidTimetable("http://www.modsn.us/")); // invalid host
        assertFalse(Timetable.isValidTimetable("http://www.modsn.us/q7cLP")); // invalid host
        assertFalse(Timetable.isValidTimetable("http://modsn.us/")); // code-part needs at least 1 character

        // valid timetables
        assertTrue(Timetable.isValidTimetable("http://modsn.us/wNuIW"));
        assertTrue(Timetable.isValidTimetable("http://modsn.us/q7cLP")); // code-part can be alphanumeric
    }
}
