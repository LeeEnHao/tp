package seedu.address.model.country;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import seedu.address.model.note.CountryNote;

/**
 * A high-level class responsible for mapping ISO3166 2-letter country codes to countries.
 */
public class CountryManager {

    private static final String[] COUNTRY_CODES = Locale.getISOCountries();
    private final Map<Country, Set<CountryNote>> countryToCountryNoteMap;
    private final Set<CountryNote> countryNoteSet;

    /**
     * Initializes a CountryManager with a Map that maps ISO3166 2-letter country codes to countries.
     */
    public CountryManager() {
        countryToCountryNoteMap = initCountryToCountryNotesMap();
        countryNoteSet = new LinkedHashSet<>();
    }

    /**
     * Checks if countryCode is a valid ISO3166 code.
     *
     * @param countryCode The country code.
     * @return Whether countryCode is a valid ISO3166 code.
     */
    public static boolean isValidCountryCode(String countryCode) {
        for (int i = 0; i < COUNTRY_CODES.length; i++) {
            if (COUNTRY_CODES[i].equals(countryCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initializes mapping from country to its countryNotes.
     *
     * @return The Mapping from country to its countryNotes.
     */
    private static Map<Country, Set<CountryNote>> initCountryToCountryNotesMap() {
        Map<Country, Set<CountryNote>> newCountryNotesMap = new LinkedHashMap<>();
        for (String countryCode : COUNTRY_CODES) {
            newCountryNotesMap.put(new Country(countryCode), new LinkedHashSet<>());
        }
        return newCountryNotesMap;
    }

    /**
     * Checks if {@code country} contains {@code countryNote}.
     *
     * @param countryNote The note to be checked.
     * @return Whether {@code country} contains {@code countryNote}.
     */
    public boolean hasCountryNote(CountryNote countryNote) {
        return countryNoteSet.contains(countryNote);
    }

    /**
     * Adds the {@code countryNote} to the {@code country}.
     *
     * @param countryNote The country note to be added.
     */
    public void addCountryNote(CountryNote countryNote) {
        if (!countryToCountryNoteMap.containsKey(countryNote.getCountry())) {
            return;
        }

        countryToCountryNoteMap.get(countryNote.getCountry()).add(countryNote);
        countryNoteSet.add(countryNote);
    }

    /**
     * Returns the set of notes associated to a particular country.
     *
     * @param country The country from which we get CountryNotes.
     */
    public Set<CountryNote> getCountryNote(Country country) {
        return this.countryToCountryNoteMap.get(country);
    }

    /**
     * Returns all Country Notes currently in the map.
     * Returning a collection of sets prevents deep-copying.
     *
     * @return Collection of sets of country notes in the map.
     */
    public List<CountryNote> getAllCountryNotesAsList() {
        return new ArrayList<>(countryNoteSet);
    }

}
