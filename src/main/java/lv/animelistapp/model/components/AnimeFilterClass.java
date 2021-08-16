package lv.animelistapp.model.components;

import com.vaadin.flow.component.grid.dataview.GridListDataView;
import lv.animelistapp.model.AnimeDetails;

public class AnimeFilterClass {
    private final GridListDataView<AnimeDetails> dataView;

    private String titleJP;

    private String titleEN;

    private String rating;

    private String studios;

    private String status;

    public AnimeFilterClass(GridListDataView<AnimeDetails> dataView) {
        this.dataView = dataView;
        this.dataView.addFilter(this::test);
    }

    public void setTitleJP(String titleJP) {
        this.titleJP = titleJP;
        this.dataView.refreshAll();
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
        this.dataView.refreshAll();
    }

    public void setRating(String rating) {
        this.rating = rating;
        this.dataView.refreshAll();
    }

    public void setStudios(String studios) {
        this.studios = studios;
        this.dataView.refreshAll();
    }

    public void setStatus(String status) {
        this.status = status;
        this.dataView.refreshAll();
    }

    public boolean test(AnimeDetails animeDetails) {
        boolean matchesTitleJP = matches(animeDetails.getTitleJP(), titleJP);
        boolean matchesTitleEN = matches(animeDetails.getTitleEN(), titleEN);
        boolean matchesRating = matches(animeDetails.getRating(),rating);
        boolean matchesStudios = matches(animeDetails.getStudios(), studios);
        boolean matchesStatus = matches(animeDetails.getStatus(), status);

        return matchesTitleJP && matchesTitleEN &&
                matchesRating && matchesStudios && matchesStatus;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty() || value
                .toLowerCase().contains(searchTerm.toLowerCase());
    }
}