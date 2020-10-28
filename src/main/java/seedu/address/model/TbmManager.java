package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.UniqueClientList;
import seedu.address.model.country.CountryNotesManager;
import seedu.address.model.note.CountryNote;
import seedu.address.model.note.Note;
import seedu.address.model.tag.UniqueTagSet;

/**
 * Wraps all data at TbmManager level
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class TbmManager implements ReadOnlyTbmManager {

    private final UniqueClientList clients;
    private final UniqueTagSet tags;
    private final CountryNotesManager countryNotesManager;
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        clients = new UniqueClientList();
        tags = new UniqueTagSet();
        countryNotesManager = new CountryNotesManager();
    }

    public TbmManager() {}

    /**
     * Creates an TbmManager using the Clients in the {@code toBeCopied}
     */
    public TbmManager(ReadOnlyTbmManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients} and the contents of the tag set with
     * the union over all client tags, then update client tag sets with unique tags.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);
    }

    /**
     * Replaces all notes in TbmManager with the given list of notes.
     *
     * @param notes The given list of notes.
     */
    public void setNotes(List<Note> notes) {
        for (Note note: notes) {
            if (note.isClientNote()) {
                // handle client notes
                // todo:  =======================================================
                //        decided to not store the client notes in a separete set
                //        because the notes are going to be stored within clients
                //        itself. The setClient function aldy does this. Adding in
                //        a collection of client notes in this class would mean that
                //        collection needs to be constantly updated. As such, it
                //        might be good enough to just modify the getNoteList method
                //        ===========================================================
            } else {
                countryNotesManager.addCountryNote((CountryNote) note);
            }
        }
    }

    /**
     * Resets the existing data of this {@code TbmManager} with {@code newData}.
     */
    public void resetData(ReadOnlyTbmManager newData) {
        requireNonNull(newData);
        setClients(newData.getClientList());
        setNotes(newData.getNoteList());
    }

    //// client-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in TManager.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Adds the client to TbmManager.
     * The client must not already exist in TbmManager.
     */
    public void addClient(Client client) {
        clients.add(client);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in TbmManager.
     * The client identity of {@code editedClient} must not be the same as another existing client in TbmManager.
     */
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);
        clients.setClient(target, editedClient);
    }

    /**
     * Removes {@code key} from this {@code TbmManager}. {@code key} must exist in TbmManager.
     */
    public void removeClient(Client key) {
        clients.remove(key);
    }

    /**
     * Checks whether the given country has the given countryNote.
     *
     * @param countryNote The given countryNote
     * @return True if the given country has the given countryNote.
     */
    public boolean hasCountryNote(CountryNote countryNote) {
        requireNonNull(countryNote);
        return countryNotesManager.hasCountryNote(countryNote);
    }

    /**
     * Adds the given countryNote to the given country.
     *
     * @param countryNote The given countryNote
     */
    public void addCountryNote(CountryNote countryNote) {
        requireNonNull(countryNote);
        countryNotesManager.addCountryNote(countryNote);
    }

    /**
     * Deletes the given country note.
     *
     * @param countryNoteToDelete The country note to delete.
     */
    public void deleteCountryNote(CountryNote countryNoteToDelete) {
        requireNonNull(countryNoteToDelete);
        countryNotesManager.deleteCountryNote(countryNoteToDelete);
    }

    //// util methods

    @Override
    public String toString() {
        return clients.asUnmodifiableObservableList().size() + " clients";
        // TODO: refine later
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    //TODO: add client notes also. NOTE: THIS ONLY RETURNS COUNTRY NOTES FOR NOW.
    @Override
    public ObservableList<Note> getNoteList() {
        ArrayList<Note> accumulated = new ArrayList<>(getCountryNoteList());
        this.clients.forEach(client -> accumulated.addAll(client.getClientNotes()));
        return FXCollections.observableArrayList(accumulated);
    }

    /**
     * Gets the list of country notes in TBM as an unmodifiable observable list.
     *
     * @return The unmodifiable observable list of country notes in TBM.
     */
    public ObservableList<CountryNote> getCountryNoteList() {
        return countryNotesManager.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TbmManager // instanceof handles nulls
                && clients.equals(((TbmManager) other).clients)
                && countryNotesManager.equals(((TbmManager) other).countryNotesManager)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(clients, countryNotesManager);
    }
}