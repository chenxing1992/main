# traceurgan
###### /resources/view/JournalWindow.fxml
``` fxml
<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.Scene?>
<?import javafx.stage.Stage?>


<fx:root onCloseRequest="#handleJournalClose" type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8.0.141"
         xmlns:fx="http://javafx.com/fxml/1">
    <scene>
        <Scene>
            <ScrollPane fitToHeight="true" fitToWidth="true">
                <StackPane fx:id="journalTextPlaceholder"/>
            </ScrollPane>
        </Scene>
    </scene>
</fx:root>
```
###### /resources/view/JournalEntryListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane prefHeight="0.0" prefWidth="150.0" HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="date" styleClass="cell_big_label" text="\\$date" />
            </HBox>
         <Label fx:id="text" alignment="TOP_LEFT" prefHeight="75.0" text="\\$text" wrapText="true" />
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
###### /resources/view/JournalEntryText.fxml
``` fxml
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.StackPane?>

<StackPane styleClass="pane-with-border" xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml">
    <TextArea fx:id="journalTextArea" prefHeight="400" prefWidth="600"
              promptText="Enter text here..." wrapText="true"/>
</StackPane>
```
###### /java/seedu/address/ui/JournalEntryText.java
``` java
/**
 * The UI component that is responsible for receiving journal entry text.
 */
public class JournalEntryText extends UiPart<Region> {

    private static final String FXML = "JournalEntryText.fxml";

    @FXML
    private TextArea journalTextArea;

    public JournalEntryText() {
        super(FXML);
    }

    public String getText() {
        return journalTextArea.getText();
    }

    public void setText(String text) {
        journalTextArea.setText(text);
    }
}
```
###### /java/seedu/address/ui/JournalEntryCard.java
``` java
/**
 * An UI component that displays information of a {@code Person}.
 */
public class JournalEntryCard extends UiPart<Region> {

    private static final String FXML = "JournalEntryListCard.fxml";

    public final JournalEntry journalEntry;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label id;
    @FXML
    private Label text;

    public JournalEntryCard(JournalEntry journalEntry, int displayedIndex) {
        super(FXML);
        this.journalEntry = journalEntry;
        id.setText(displayedIndex + ". ");
        date.setText(journalEntry.getDate());
        text.setText(journalEntry.getText());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JournalEntryCard)) {
            return false;
        }

        // state check
        JournalEntryCard card = (JournalEntryCard) other;
        return id.getText().equals(card.id.getText())
                && journalEntry.equals(card.journalEntry);
    }
}
```
###### /java/seedu/address/ui/JournalWindow.java
``` java
/**
 * Controller for a journal page
 */
public class JournalWindow extends UiPart<Stage> {

    private static final String FXML = "JournalWindow.fxml";

    private JournalEntryText journalEntryText;
    private String date;

    @FXML
    private StackPane journalTextPlaceholder;

    private JournalWindow (Stage root, String date) {
        super (FXML, root);

        this.date = date;
        fillInnerParts();

        root.setTitle(date + " - Journal");
        root.initModality(Modality.APPLICATION_MODAL);
    }

    private JournalWindow (Stage root, String date, String text) {
        super (FXML, root);

        this.date = date;
        fillInnerParts(text);

        root.setTitle(date + " - Journal");
        root.initModality(Modality.APPLICATION_MODAL);
    }

    public JournalWindow(String date, String text) {
        this(new Stage(), date, text);
    }

    public JournalWindow (String date) {
        this(new Stage(), date);
    }

    /**
     * Fills placeholder with a editable TextArea
     */
    private void fillInnerParts() {
        journalEntryText = new JournalEntryText();
        journalTextPlaceholder.getChildren().add(journalEntryText.getRoot());
    }

    /**
     * Fills placeholder with a editable TextArea
     */
    private void fillInnerParts(String text) {
        journalEntryText = new JournalEntryText();
        journalEntryText.setText(text);
        journalTextPlaceholder.getChildren().add(journalEntryText.getRoot());
    }

    /**
     * Raise JournalEntrySaveEvent on journal window close if text area is not empty
     */
    @FXML
    private void handleJournalClose() {
        if (!journalEntryText.getText().isEmpty()) {
            JournalEntry journalEntry = new JournalEntry(this.date, journalEntryText.getText());
            raise(new SaveEntryEvent(journalEntry));
        }
        return;
    }

    public void show() {
        getRoot().show();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

}
```
###### /java/seedu/address/ui/ListPanel.java
``` java
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class JournalEntryListViewCell extends ListCell<JournalEntryCard> {

        @Override
        protected void updateItem(JournalEntryCard journalEntryCard, boolean empty) {
            super.updateItem(journalEntryCard, empty);

            if (empty || journalEntryCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(journalEntryCard.getRoot());
            }
        }
    }

}
```
###### /java/seedu/address/commons/events/ui/ShowJournalWindowRequestEvent.java
``` java
/**
 * An event requesting to view the Journal Window.
 */
public class ShowJournalWindowRequestEvent extends BaseEvent {

    public final String date;

    public ShowJournalWindowRequestEvent (String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/address/commons/events/model/SaveEntryEvent.java
``` java
/** Indicates a journal entry should be created/updated*/
public class SaveEntryEvent extends BaseEvent {

    public final JournalEntry journalEntry;

    public SaveEntryEvent(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
    }

    @Override
    public String toString() {
        return "Saving entry...";
    }
}
```
###### /java/seedu/address/commons/events/model/JournalChangedEvent.java
``` java
/** Indicates the Journal in the model has changed*/
public class JournalChangedEvent extends BaseEvent {

    public final ReadOnlyJournal data;

    public JournalChangedEvent(ReadOnlyJournal data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of journal entries " + data.getJournalEntryList().size();
    }
}
```
###### /java/seedu/address/logic/Logic.java
``` java
    /** Returns an unmodifiable view of the list of journal entries */
    ObservableList<JournalEntry> getJournalEntryList();

    ObservableList<ReadOnlyPerson> getPersonAsList();

    ReadOnlyPerson getPartner();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
```
###### /java/seedu/address/storage/Storage.java
``` java

    String getPersonFilePath();

    Optional<ReadOnlyPerson> readPerson() throws DataConversionException, IOException;

    Optional<ReadOnlyPerson> readPerson(String filePath) throws DataConversionException, IOException;

    void savePerson(ReadOnlyPerson person) throws IOException;

    void savePerson(ReadOnlyPerson person, String filePath) throws IOException;

    /**
     * Saves the current Person to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    @Subscribe
    void handlePersonChangedEvent(PersonChangedEvent event);

    /**
     * Saves the current version of the Journal to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleJournalChangedEvent(JournalChangedEvent jce);

```
###### /java/seedu/address/storage/StorageManager.java
``` java
    @Override
    public String getJournalFilePath() {
        return journalStorage.getJournalFilePath();
    }

    @Override
    public Optional<ReadOnlyJournal> readJournal() throws DataConversionException, IOException {
        return readJournal(journalStorage.getJournalFilePath());
    }

    @Override
    public Optional<ReadOnlyJournal> readJournal(String filePath) throws DataConversionException, IOException {
        return journalStorage.readJournal(filePath);
    }

    @Override
    public void saveJournal(ReadOnlyJournal journal) throws IOException {
        saveJournal(journal, journalStorage.getJournalFilePath());
        logger.info(getJournalFilePath());
    }

    @Override
    public void saveJournal(ReadOnlyJournal journal, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        journalStorage.saveJournal(journal, filePath);
    }

    /**
     * Saves the current version of the Journal Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    @Subscribe
    public void handleJournalChangedEvent(JournalChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveJournal(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ Timetable methods ==============================

```
###### /java/seedu/address/storage/XmlAdaptedJournalEntry.java
``` java
/**
 * JAXB-friendly version of the JournalEntry.
 */
public class XmlAdaptedJournalEntry {

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String text;

    /**
     * Constructs an XmlAdaptedJournalEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJournalEntry() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedJournalEntry(String date, String text) {
        this.date = date;
        this.text = text;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedJournalEntry(JournalEntry source) {
        date = source.getDate();
        text = source.getText();
    }

    /**
     * Converts this jaxb-friendly adapted journal entry object into the model's JournalEntry object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted journal entry
     */
    public JournalEntry toModelType() throws IllegalValueException {

        if (this.date == null) { //impossible, date is generated when new journal entry is created
            throw new IllegalValueException("Date missing");
        }

        return new JournalEntry(date, text);
    }

}
```
###### /java/seedu/address/storage/JournalStorage.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.Journal}.
 */
public interface JournalStorage {

    /**
     * Returns the file path of the data file.
     */
    String getJournalFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyJournal}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyJournal> readJournal() throws DataConversionException, IOException;

    /**
     * @see #getJournalFilePath()
     */
    Optional<ReadOnlyJournal> readJournal(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyJournal} to the storage.
     * @param journal cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveJournal(ReadOnlyJournal journal) throws IOException;

    /**
     * @see #saveJournal(ReadOnlyJournal)
     */
    void saveJournal(ReadOnlyJournal journal, String filePath) throws IOException;

}
```
###### /java/seedu/address/storage/XmlSerializableJournal.java
``` java
/**
 * An Immutable Journal that is serializable to XML format
 */
@XmlRootElement(name = "journal")
public class XmlSerializableJournal {

    @XmlElement
    private List<XmlAdaptedJournalEntry> journalEntries;

    /**
     * Creates an empty XmlSerializableJournal.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableJournal() {
        journalEntries = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableJournal (ReadOnlyJournal src) {
        this();
        journalEntries.addAll(src.getJournalEntryList().stream().map(
                XmlAdaptedJournalEntry::new).collect(Collectors.toList()));
    }

    /**
     * Converts this journal into the model's {@code Journal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedJournalEntry}.
     */
    public Journal toModelType() throws Exception {
        Journal journal = new Journal();

        for (XmlAdaptedJournalEntry p : journalEntries) {
            journal.addJournalEntry(p.toModelType());
        }
        return journal;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableJournal)) {
            return false;
        }

        XmlSerializableJournal otherJournal = (XmlSerializableJournal) other;
        return journalEntries.equals(otherJournal.journalEntries);
    }
}
```
###### /java/seedu/address/storage/XmlJournalStorage.java
``` java
/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlJournalStorage implements JournalStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlJournalStorage.class);

    private String filePath;

    public XmlJournalStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getJournalFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyJournal> readJournal() throws DataConversionException, IOException {
        return readJournal(filePath);
    }

    /**
     * Similar to {@link #readJournal()} ()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyJournal> readJournal(String filePath) throws DataConversionException,
            IOException {
        requireNonNull(filePath);

        File journalFile = new File(filePath);

        if (!journalFile.exists()) {
            logger.info("Journal file "  + journalFile  + " not found");
            return Optional.empty();
        }

        XmlSerializableJournal xmlJournal = XmlFileStorage.loadJournalFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlJournal.toModelType());
        } catch (Exception e) {
            logger.info("Illegal values found in " + journalFile + ": " + e.getMessage());
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveJournal(ReadOnlyJournal journal) throws IOException {
        saveJournal(journal, filePath);
    }

    /**
     * Similar to {@link #saveJournal(ReadOnlyJournal)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveJournal(ReadOnlyJournal journal, String filePath) throws IOException {
        requireNonNull(journal);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveJournalToFile(file, new XmlSerializableJournal(journal));
    }

}
```
###### /java/seedu/address/model/journalentry/UniqueJournalEntryList.java
``` java
/**
 * A list of journal entries that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see JournalEntry#equals(Object)
 */
public class UniqueJournalEntryList implements Iterable <JournalEntry> {

    private final ObservableList<JournalEntry> internalList = FXCollections.observableArrayList();
    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(JournalEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns last entry
     */
    public int getLast() {
        return (internalList.size() - 1);
    }

    /**
     * Adds a person to the list.
     *
     * @throws Exception if the person to add is a duplicate of an existing person in the list.
     */
    public void add(JournalEntry toAdd) throws Exception {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new Exception();
        }
        internalList.add(toAdd);
    }

    /**
    * Replaces the journal entry {@code target} in the list with {@code editedPerson}.
    *
    * @throws Exception if the replacement is equivalent to another existing journal entry in the list.
    * @throws Exception if {@code target} could not be found in the list.
    */
    public void setJournalEntries(UniqueJournalEntryList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setJournalEntries(List<JournalEntry> journalEntries) throws Exception {
        requireAllNonNull(journalEntries);
        final UniqueJournalEntryList replacement = new UniqueJournalEntryList();
        for (final JournalEntry journalEntry : journalEntries) {
            replacement.add(journalEntry);
        }
        setJournalEntries(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<JournalEntry> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<JournalEntry> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueJournalEntryList // instanceof handles nulls
                && this.internalList.equals(((UniqueJournalEntryList) other).internalList));
    }

    public void updateJournalEntry(JournalEntry journalEntry, int last) {
        internalList.set(last, journalEntry);
    }

    public String getDate(int last) {
        return internalList.get(last).getDate();
    }

    public JournalEntry getJournalEntry(int index) {
        return internalList.get(index);
    }
}
```
###### /java/seedu/address/model/journalentry/JournalEntry.java
``` java
/**
 * Represents a JournalEntry in the journal.
 */
public class JournalEntry {

    private final String date;
    private String text;

    /**
     * Every field must be present and not null.
     */
    public JournalEntry(String date, String text) {
        requireAllNonNull(date, text);
        this.date = date;
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JournalEntry)) {
            return false;
        }

        JournalEntry otherJournalEntry = (JournalEntry) other;
        return otherJournalEntry.getDate().equals(this.getDate())
                && otherJournalEntry.getText().equals(this.getText());
    }

    private void setText(JournalEntry updatedVersion) {
        this.text = updatedVersion.getText();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date);
    }

    static void updateJournalEntry(JournalEntry journalEntry, JournalEntry oldVersion) {
        oldVersion.setText(journalEntry);
    }
}
```
###### /java/seedu/address/model/Journal.java
``` java
/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Journal implements ReadOnlyJournal {

    private static final Logger logger = LogsCenter.getLogger(Journal.class);

    private final UniqueJournalEntryList journalEntries;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        journalEntries = new UniqueJournalEntryList();
    }

    public Journal() {}

    /**
     * Creates an Journal using the Journal Entries {@code toBeCopied}
     */
    public Journal(ReadOnlyJournal toBeCopied) {
        this();
        resetJournalData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code Journal} with {@code newData}.
     */
    public void resetJournalData(ReadOnlyJournal newData) {
        requireNonNull(newData);
        List<JournalEntry> syncedJournalEntryList = newData.getJournalEntryList().stream()
                .collect(Collectors.toList());

        try {
            setJournalEntries(syncedJournalEntryList);
        } catch (Exception e) {
            throw new AssertionError("Journal should not have duplicate entries");
        }
    }

    //// person-level operations

    /**
     * Adds a journal entry to the journal.
     *
     * @throws Exception if an equivalent journal entry already exists.
     */
    public void addJournalEntry(JournalEntry j) throws Exception {
        journalEntries.add(j);
    }

    public void setJournalEntries(List<JournalEntry> journalEntries) throws Exception {
        this.journalEntries.setJournalEntries(journalEntries);
    }

    //// util methods

    @Override
    public ObservableList<JournalEntry> getJournalEntryList() {
        return journalEntries.asObservableList();
    }

    @Override
    public String toString() {
        return journalEntries.asObservableList().size() + " journalEntries.";
    }

    @Override
    public int getLast() {
        return journalEntries.getLast();
    }

    @Override
    public JournalEntry getJournalEntry(int index) {
        return journalEntries.getJournalEntry(index);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Journal // instanceof handles nulls
                && this.journalEntries.equals(((Journal) other).journalEntries));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(journalEntries);
    }

    void updateJournalEntry(JournalEntry journalEntry, int last) {
        journalEntries.updateJournalEntry(journalEntry, last);
    }

    public String getDate(int last) {
        return journalEntries.getDate(last);
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Raises an event to indicate the journal model has changed.
     */
    private void indicateJournalChanged() {
        raise(new JournalChangedEvent(journal));
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public ReadOnlyJournal getJournal() {
        return journal;
    }

    @Override
    public synchronized void addJournalEntry(JournalEntry journalEntry) throws Exception {
        if ((this.getJournalEntryList().size() != 0) && (
                checkDate(journal.getLast()).equals(journalEntry.getDate()))) {
            journal.updateJournalEntry(journalEntry, journal.getLast());
            logger.info("Journal entry updated.");
        } else {
            journal.addJournalEntry(journalEntry);
            logger.info("Journal entry added.");
        }
        indicateJournalChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Subscribe
    public void handleSaveEntryEvent(SaveEntryEvent event) {
        try {
            addJournalEntry(event.journalEntry);
        } catch (Exception e) {
            logger.warning("Save failed");
            JournalWindow journalWindow =
                    new JournalWindow(event.journalEntry.getDate(), String.format(
                            "Save failed. Copy your text and try again.\n" + event.journalEntry.getText()));
            journalWindow.show();
        }
    }

    @Subscribe
    private void handleShowJournalWindowRequestEvent(ShowJournalWindowRequestEvent event) {
        JournalWindow journalWindow;
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if ((getJournalEntryList().size() != 0) && (checkDate(getLast()).equals(event.date))) {
            journalWindow = new JournalWindow(
                    event.date, getJournal().getJournalEntry(getLast()).getText());
        } else {
            journalWindow = new JournalWindow(event.date);
        }
        journalWindow.show();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public ObservableList<JournalEntry> getJournalEntryList() {
        return journal.getJournalEntryList();
    }

    @Override
    public int getLast() {
        return journal.getLast();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return ((partner == null && other.partner == null)
                || (partner != null && other.partner != null && partner.equals(other.partner)))
                && journal.equals(other.journal);
    }

}
```
###### /java/seedu/address/model/ReadOnlyJournal.java
``` java
/**
 * Unmodifiable view of an journal
 */
public interface ReadOnlyJournal {

    /**
     * Returns an unmodifiable view of the journal entry list.
     * This list will not contain any duplicate journal entries.
     */
    ObservableList<JournalEntry> getJournalEntryList();

    int getLast();

    JournalEntry getJournalEntry(int index);
}
```
###### /java/seedu/address/model/Model.java
``` java
    /** Returns the Journal */
    ReadOnlyJournal getJournal();

    /** Adds the given person */
    void addJournalEntry(JournalEntry journalEntry) throws Exception;

    /** Returns an unmodifiable view of the journal entry list */
    ObservableList<JournalEntry> getJournalEntryList();

    ObservableList <ReadOnlyPerson> getPersonAsList();

```
