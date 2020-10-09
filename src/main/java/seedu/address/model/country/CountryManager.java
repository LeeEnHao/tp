package seedu.address.model.country;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import seedu.address.model.note.Note;

/**
 * A high-level class responsible for mapping ISO3166 2-letter country codes to countries.
 */
public class CountryManager {

    //TODO: Should include checking for 3-letter Country Code?
    public static final String MESSAGE_CONSTRAINTS = "Must be a 2-letter country code"; // TODO: better msg
    private static final String[] COUNTRY_CODES = Locale.getISOCountries();
    private final Map<String, Country> countryCodeMap;

    /**
     * Initializes a CountryManager with a Map that maps ISO3166 2-letter country codes to countries.
     */
    public CountryManager() {
        countryCodeMap = initCountryCodeMap();
    }

    public static boolean isValidCode(String countryCode) {
        for (int i = 0; i < COUNTRY_CODES.length; i++) {
            if (COUNTRY_CODES[i].equals(countryCode)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCountryNote(Country country, Note countryNote) {
        if (!isValidCode(country.getCountryCode())) {
            return false;
        }
        return countryCodeMap.get(country.getCountryCode()).hasCountryNote(countryNote);
    }

    public void addCountryNote(Country country, Note countryNote) {
        if (isValidCode(country.getCountryCode())) {
            countryCodeMap.get(country.getCountryCode()).addCountryNote(countryNote);
        }
    }

    /**
     * Returns the country that is identified by the given country code.
     *
     * @param countryCode The ISO3166 2-letter country code of the particular country.
     * @return The country that is identified by the given country code.
     */
    public Country getCountryFromCode(String countryCode) {
        if (isValidCode(countryCode)) {
            return countryCodeMap.get(countryCode);
        } else {
            throw new NullPointerException();
        }
    }

    private static Map<String, Country> initCountryCodeMap() {
        Map<String, Country> countryCodeMap = new HashMap<>();
        for (String countryCode : COUNTRY_CODES) {
            countryCodeMap.put(countryCode, new Country(countryCode));
        }
        return countryCodeMap;
    }
}
