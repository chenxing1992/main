package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.AddAppointmentParser;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.appointment.Appointment;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TIMETABLE = "http://modsn.us/wNuIW";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Timetable timetable;
    private Person person;

    public PersonBuilder() {

        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        timetable = new Timetable(DEFAULT_TIMETABLE);
        List<Appointment> defaultAppointments = new ArrayList<>();
        this.person = new Person(name, phone, email,
                address, timetable, defaultAppointments);

    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(ReadOnlyPerson personToCopy) {
        this.person = new Person(personToCopy);
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        timetable = personToCopy.getTimetable();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Timetable} of the {@code Person} that we are building.
     */
    public PersonBuilder withTimetable(String timetable) {
        this.timetable = new Timetable(timetable);
        return this;
    }

    /**
     * Sets appointment with Date of the person that we are building
     */
    public PersonBuilder withAppointment(String... arg) {
        List<Appointment> list = new ArrayList<>();
        for (String s : arg) {
            try {
                list.add(AddAppointmentParser.getAppointmentFromString(s));
            } catch (seedu.address.logic.parser.exceptions.ParseException e) {
                e.printStackTrace();
            }
        }
        this.person.setAppointment(list);
        return this;
    }

    public Person build() {
        return this.person;
    }

}
