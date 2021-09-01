package lv.animelistapp.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import lv.animelistapp.component.CustomPrimaryButton;
import lv.animelistapp.component.MainMenuBar;
import lv.animelistapp.model.AnimeDetails;
import lv.animelistapp.model.Codificator;
import lv.animelistapp.model.CodificatorValue;
import lv.animelistapp.repository.AnimeRepository;
import lv.animelistapp.repository.CodificatorRepository;
import lv.animelistapp.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Route("/")
public class MainPage extends VerticalLayout {

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    CodificatorRepository codificatorRepository;

    MainMenuBar menuBar = new MainMenuBar(MainMenuBar.DEFAULT);
    Scroller newArrivals = new Scroller();
    //Grid<AnimeDetails> animeListGrid = new Grid<>(AnimeDetails.class, false);
    PaginatedGrid<AnimeDetails> animeListGrid = new PaginatedGrid<>(/*AnimeDetails.class, false*/);

    TextField title = new TextField(Msg.getMsg("main.filter.title"));
    ComboBox<CodificatorValue> type = new ComboBox<>(Msg.getMsg("main.filter.type"));
    ComboBox<CodificatorValue> rating = new ComboBox<>(Msg.getMsg("main.filter.rating"));
    ComboBox<CodificatorValue> studios = new ComboBox<>(Msg.getMsg("main.filter.studios"));
    //ComboBox<CodificatorValue> status = new ComboBox<>(Msg.getMsg("main.filter.status"));
    ComboBox<CodificatorValue> genres = new ComboBox<>(Msg.getMsg("main.filet.genres"));


    List<AnimeDetails> arrivals;
    List<AnimeDetails> animeList;
    List<CodificatorValue> typeList;
    List<CodificatorValue> ratingList;
    List<CodificatorValue> studiosList;
    List<CodificatorValue> genreList;

    public MainPage(AnimeRepository animeRepository,
                    CodificatorRepository codificatorRepository) {
        this.animeRepository = animeRepository;
        this.codificatorRepository = codificatorRepository;
        setData();

        H3 arrivalTitle = new H3(Msg.getMsg("main.arrivals.title"));

        newArrivals.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
        HorizontalLayout arrivalContent = setScroller(arrivals);
        newArrivals.setContent(arrivalContent);
        newArrivals.setMaxWidth("95vw");

        animeListGrid.setHeightByRows(true);
        /*animeListGrid.addColumn(AnimeDetails::getTitleJP)
                .setHeader(Msg.getMsg("main.grid.title")).setAutoWidth(true);*/
        animeListGrid.addComponentColumn(this::getRedirectButton)
                .setHeader(Msg.getMsg("main.grid.title")).setAutoWidth(true);
        animeListGrid.addColumn(AnimeDetails::getTitleLV).setHeader("").setAutoWidth(true);
        animeListGrid.addColumn(AnimeDetails::getType)
                .setHeader(Msg.getMsg("main.grid.type")).setAutoWidth(true);
        animeListGrid.addColumn(AnimeDetails::getEpisodesString)
                .setHeader(Msg.getMsg("main.grid.episodes")).setAutoWidth(true);
        animeListGrid.addColumn(AnimeDetails::getRating)
                .setHeader(Msg.getMsg("main.grid.rating")).setAutoWidth(true);
        animeListGrid.addColumn(AnimeDetails::getStudios)
                .setHeader(Msg.getMsg("main.grid.studios")).setAutoWidth(true);
        animeListGrid.setPageSize(10);
        animeListGrid.setPaginatorTexts(Msg.getMsg("main.grid.page"), Msg.getMsg("main.grid.page.of"));

        GridListDataView<AnimeDetails> dataView = animeListGrid.setItems(animeList);

        HorizontalLayout firstRow = new HorizontalLayout();

        type.setItemLabelGenerator(CodificatorValue::getName);
        type.setItems(typeList);

        rating.setItemLabelGenerator(CodificatorValue::getName);
        rating.setItems(typeList);

        studios.setItemLabelGenerator(CodificatorValue::getName);
        studios.setItems(studiosList);

        genres.setItemLabelGenerator(CodificatorValue::getName);
        genres.setItems(genreList);

        CustomPrimaryButton filter = new CustomPrimaryButton(Msg.getMsg("main.filer.button"));
        filter.addClickListener(e -> filterGrid());

        firstRow.add(title, type, rating, studios, genres, filter);
        firstRow.setAlignItems(Alignment.END);
        add(menuBar, arrivalTitle, newArrivals, firstRow, animeListGrid);
    }

    private void setData() {
        arrivals = animeRepository.getArrivals();
        if (arrivals == null) {
            arrivals = new ArrayList<>();
        }

        animeList = animeRepository.getAnimeListForMainPage(null,0,0, 0, null);
        if (animeList == null) {
            animeList = new ArrayList<>();
        }

        CodificatorValue emptyValue = new CodificatorValue();
        emptyValue.setName("");

        typeList = new ArrayList<>();
        typeList.add(emptyValue);
        typeList.addAll(codificatorRepository.getCodifValuesByCode(Codificator.TYPE));

        ratingList = new ArrayList<>();
        ratingList.add(emptyValue);
        ratingList.addAll(codificatorRepository.getCodifValuesByCode(Codificator.RATING));

        studiosList = new ArrayList<>();
        studiosList.add(emptyValue);
        studiosList.addAll(codificatorRepository.getCodifValuesByCode(Codificator.STUDIOS));

        genreList = new ArrayList<>();
        genreList.add(emptyValue);
        genreList.addAll(codificatorRepository.getCodifValuesByCode(Codificator.GENRE));
    }

    private HorizontalLayout setScroller(List<AnimeDetails> details) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        for(AnimeDetails detail : details) {
            VerticalLayout verticalLayout = new VerticalLayout();
            Image image = new Image();
            Button link = new Button();

            image.setVisible(true);
            image.setMaxHeight("150px");
            InputStream inputStream = new ByteArrayInputStream(detail.getImage());
            InputStreamFactory inputStreamFactory = new InputStreamFactory() {
                @Override
                public InputStream createInputStream() {
                    return inputStream;
                }
            };
            StreamResource streamResource = new StreamResource(detail.getImageName(), inputStreamFactory);
            image.setSrc(streamResource);

            link.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            link.setText(detail.getTitleJP());
            link.setMaxWidth("115px");
            link.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("/anime/" + detail.getId())));

            verticalLayout.add(image, link);
            horizontalLayout.add(verticalLayout);
        }
        return horizontalLayout;
    }

    private void filterGrid() {
        animeList = animeRepository.getAnimeListForMainPage(
                title.getValue(),
                (type.getValue() == null) ? 0 : type.getValue().getId(),
                (rating.getValue() == null) ? 0 : rating.getValue().getId(),
                (studios.getValue() == null) ? 0 : studios.getValue().getId(),
                (genres.getValue() == null) ? null : genres.getValue().getName()
        );

        if(animeList == null) {
            animeList = new ArrayList<>();
        }

        animeListGrid.setItems(animeList);
    }

    private Button getRedirectButton(AnimeDetails animeDetails) {
        Button button = new Button(animeDetails.getTitleJP());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        button.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("anime/" + animeDetails.getId())));
        return button;
    }
}
