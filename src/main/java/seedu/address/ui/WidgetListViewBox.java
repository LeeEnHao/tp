package seedu.address.ui;

import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.logic.commands.CountryNoteCommand;
import seedu.address.model.note.CountryNote;

public class WidgetListViewBox extends UiPart<Region> {

    private static final String FXML = "WidgetListViewBox.fxml";

    @FXML
    private VBox viewBox;

    @FXML
    private VBox scrollView;

    @FXML
    private Label header;

    /**
     * Initializes a {@code WidgetListViewBox}
     */
    public WidgetListViewBox() {
        super(FXML);
        header.setText("Country Notes");
    }

    public void update(ObservableList<CountryNote> countryNoteObservableList) {
        scrollView.getChildren().clear();
        int indx = 1;
        for (CountryNote countryNote : countryNoteObservableList) {
            CountryNoteCard countryNoteCard = new CountryNoteCard(indx, countryNote);
            scrollView.getChildren().add(countryNoteCard.getCountryNoteContainer());
            indx++;
        }
    }


}
