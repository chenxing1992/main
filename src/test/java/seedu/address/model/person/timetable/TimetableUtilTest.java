package seedu.address.model.person.timetable;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

//@@author marlenekoh
public class TimetableUtilTest {

    private final String validLongUrl = "https://nusmods.com/timetable/sem-2/"
            + "share?CS2101=SEC:C01&CS2103T=TUT:C01&CS3230=LEC:1,TUT:4&"
            + "CS3241=LAB:3,LEC:1,TUT:3&CS3247=LAB:1,LEC:1&GES1021=LEC:SL1";
    private final String validShortUrl = "http://modsn.us/wNuIW";
    private final String invalidShortUrl = "http://modsn.us/123";
    private HashMap<String, TimetableModule> expectedListOfModules;

    @Before
    public void setUp() {
        expectedListOfModules = new HashMap<String, TimetableModule>();
        HashMap<String, String> tempLessonPair;

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Sectional Teaching", "C01");
        expectedListOfModules.put("CS2101", new TimetableModule("CS2101",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Tutorial", "C01");
        expectedListOfModules.put("CS2103T", new TimetableModule("CS2103T",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Lecture", "1");
        tempLessonPair.put("Tutorial", "4");
        expectedListOfModules.put("CS3230", new TimetableModule("CS3230",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Laboratory", "3");
        tempLessonPair.put("Lecture", "1");
        tempLessonPair.put("Tutorial", "3");
        expectedListOfModules.put("CS3241", new TimetableModule("CS3241",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Laboratory", "1");
        tempLessonPair.put("Lecture", "1");
        expectedListOfModules.put("CS3247", new TimetableModule("CS3247",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Lecture", "SL1");
        expectedListOfModules.put("GES1021", new TimetableModule("GES1021",
                tempLessonPair));
    }

    @Test
    public void expandShortTimetableUrl_invalidShortUrl_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setAndExpandShortTimetableUrl(
                        new Timetable(("")))); // empty string
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setAndExpandShortTimetableUrl(
                        new Timetable(("www.google.com")))); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setAndExpandShortTimetableUrl(
                        new Timetable(("http://www.facebook.com")))); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setAndExpandShortTimetableUrl(
                        new Timetable(("http://www.modsn.us/")))); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setAndExpandShortTimetableUrl(
                        new Timetable(("http://www.modsn.us/q7cLP")))); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setAndExpandShortTimetableUrl(
                        new Timetable(("http://www.modsn.us/")))); // code-part needs at least 1 character
    }

    @Test
    public void expandShortTimetableUrl_validUrl() throws ParseException {
        Timetable timetable = new Timetable(validShortUrl);
        TimetableUtil.setAndExpandShortTimetableUrl(timetable);
        assertEquals(timetable.getExpandedUrl(), validLongUrl);
    }

    @Test
    public void expandShortTimetableUrl_invalidUrl_throwsParseException() {
        Assert.assertThrows(ParseException.class, () ->
                TimetableUtil.setAndExpandShortTimetableUrl(new Timetable(invalidShortUrl)));
    }

    @Test
    public void splitLongTimetableUrl () {
        Timetable timetable = new Timetable(validShortUrl);
        timetable.setExpandedUrl(validLongUrl);
        TimetableUtil.splitLongTimetableUrl(timetable);
        assertEquals(expectedListOfModules, timetable.getListOfModules());
    }

    @Test
    public void setAndExpandShortTimetableUrl() {
        Timetable expectedTimetable = new Timetable(validShortUrl);
        expectedTimetable.setExpandedUrl(validLongUrl);

        Timetable actualTimetable = new Timetable(validShortUrl);

        Assert.assertDoesNotThrow(() -> TimetableUtil.setAndExpandShortTimetableUrl(actualTimetable));
        assertEquals(expectedTimetable.getExpandedUrl(), actualTimetable.getExpandedUrl());
    }

    @Test
    public void setSemNumFromExpandedUrl() {
        Timetable expectedTimetable = new Timetable(validShortUrl);
        expectedTimetable.setCurrentSemester(2);

        Timetable actualTimetable = new Timetable(validShortUrl);
        TimetableUtil.setSemNumFromExpandedUrl(actualTimetable);

        assertEquals(expectedTimetable.getCurrentSemester(), actualTimetable.getCurrentSemester());
    }
}
