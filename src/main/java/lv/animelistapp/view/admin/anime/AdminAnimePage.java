package lv.animelistapp.view.admin.anime;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import lv.animelistapp.component.MainMenuBar;
import lv.animelistapp.model.AnimeDetails;
import lv.animelistapp.model.Codificator;
import lv.animelistapp.model.CodificatorValue;
import lv.animelistapp.model.components.AnimeFilterClass;
import lv.animelistapp.repository.AnimeRepository;
import lv.animelistapp.repository.CodificatorRepository;
import lv.animelistapp.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Route("/admin/anime")
public class AdminAnimePage extends VerticalLayout {

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    CodificatorRepository codificatorRepository;

    MainMenuBar menuBar = new MainMenuBar(MainMenuBar.DEFAULT);
    Grid<AnimeDetails> animeDetailsGrid = new Grid<>(AnimeDetails.class, false);

    private List<AnimeDetails> animeDetailList;
    private List<CodificatorValue> ratingList;
    private List<CodificatorValue> studiosList;
    private List<CodificatorValue> statusList;

    public AdminAnimePage(AnimeRepository animeRepository, CodificatorRepository codificatorRepository) {
        this.animeRepository = animeRepository;
        this.codificatorRepository = codificatorRepository;

        animeDetailList = animeRepository.getAnimeList();
        if (animeDetailList == null) {
            animeDetailList = new ArrayList<>();
        }
        CodificatorValue emptyValue = new CodificatorValue();
        emptyValue.setName("");
        ratingList = new ArrayList<>();
        ratingList.add(emptyValue);
        ratingList.addAll(codificatorRepository.getCodifValuesByCode(Codificator.RATING));
        studiosList = new ArrayList<>();
        studiosList.add(emptyValue);
        studiosList.addAll(codificatorRepository.getCodifValuesByCode(Codificator.STUDIOS));
        statusList = new ArrayList<>();
        statusList.add(emptyValue);
        statusList.addAll(codificatorRepository.getCodifValuesByCode(Codificator.STATUS));


        Grid.Column<AnimeDetails> titleJPColumn = animeDetailsGrid.addColumn(AnimeDetails::getTitleJP).setAutoWidth(true);
        Grid.Column<AnimeDetails> titleENColumn = animeDetailsGrid.addColumn(AnimeDetails::getTitleEN).setAutoWidth(true);
        Grid.Column<AnimeDetails> ratingColumn = animeDetailsGrid.addColumn(AnimeDetails::getRating).setAutoWidth(true);
        Grid.Column<AnimeDetails> studiosColumn = animeDetailsGrid.addColumn(AnimeDetails::getStudios).setAutoWidth(true);
        Grid.Column<AnimeDetails> statusColumn = animeDetailsGrid.addColumn(AnimeDetails::getStatus).setAutoWidth(true);
        Grid.Column<AnimeDetails> buttonColumn = animeDetailsGrid.addComponentColumn(e -> getEditButton(e.getId())).setAutoWidth(true);

        statusColumn.setFooter(getColumnFooter(animeDetailList));
        GridListDataView<AnimeDetails> dataView = animeDetailsGrid.setItems(animeDetailList);
        AnimeFilterClass filterClass = new AnimeFilterClass(dataView);

        animeDetailsGrid.getHeaderRows().clear();
        animeDetailsGrid.setHeight("85vh");
        animeDetailsGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        HeaderRow headerRow = animeDetailsGrid.appendHeaderRow();

        headerRow.getCell(titleJPColumn).setComponent(createFilterHeader(Msg.getMsg("anime.admin.grid.title.jp"), filterClass::setTitleJP));
        headerRow.getCell(titleENColumn).setComponent(createFilterHeader(Msg.getMsg("anime.admin.grid.title.en"), filterClass::setTitleEN));
        headerRow.getCell(ratingColumn).setComponent(createFilterHeader(Msg.getMsg("anime.admin.grid.rating"), filterClass::setRating, ratingList));
        headerRow.getCell(studiosColumn).setComponent(createFilterHeader(Msg.getMsg("anime.admin.grid.studious"), filterClass::setStudios, studiosList));
        headerRow.getCell(statusColumn).setComponent(createFilterHeader(Msg.getMsg("anime.admin.grid.status"), filterClass::setStatus, statusList));
        headerRow.getCell(buttonColumn).setComponent(getSaveButton());

        add(menuBar, animeDetailsGrid);

    }

    private static Component createFilterHeader(String labelText,
                                                Consumer<String> filterChangeConsumer) {
        Label label = new Label(labelText);
        label.getStyle()
                .set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private static Component createFilterHeader(String labelText,
                                                Consumer<String> filterChangeConsumer,
                                                List<CodificatorValue> values) {
        Label label = new Label(labelText);
        label.getStyle()
                .set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        ComboBox<CodificatorValue> comboBox = new ComboBox<>();
        comboBox.setItems(values);
        comboBox.setItemLabelGenerator(CodificatorValue::getName);
        comboBox.setAllowCustomValue(true);
        comboBox.setWidthFull();
        comboBox.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue().getName()));

        VerticalLayout layout = new VerticalLayout(label, comboBox);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }


    private Button getEditButton(long id) {
        Button edit = new Button(new Icon(VaadinIcon.EDIT));
        edit.addThemeVariants(ButtonVariant.LUMO_ICON);
        edit.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("/admin/addEditAnime/" + id)));
        return edit;
    }

    private Button getSaveButton() {
        Button edit = new Button(new Icon(VaadinIcon.PLUS));
        edit.addThemeVariants(ButtonVariant.LUMO_ICON);
        edit.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("/admin/addEditAnime")));
        return edit;
    }

    private String getColumnFooter(List<AnimeDetails> values) {
        long editingCount = values.stream()
                .filter(value -> "Rediģēšanā".equals(value.getStatus()))
                .count();

        return String.format("Rediģēšanā ir %s ieraksti", editingCount);
    }

}
