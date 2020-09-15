package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Remark {

    public final String remark;

    /**
     * Constructor for Remark.
     * @param remark The given remark for the Remark object
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.remark = remark;
    }

    @Override
    public String toString() {
        return remark;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && remark.equals(((Remark) other).remark)); // state check
    }

    @Override
    public int hashCode() {
        return remark.hashCode();
    }
}
