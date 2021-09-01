package lv.animelistapp.view.permitted.anime;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import lv.animelistapp.component.CustomPrimaryButton;
import lv.animelistapp.component.MainMenuBar;
import lv.animelistapp.model.AnimeDetails;
import lv.animelistapp.model.AnimeListModel;
import lv.animelistapp.model.Codificator;
import lv.animelistapp.model.CodificatorValue;
import lv.animelistapp.model.defaults.LvalUserDetails;
import lv.animelistapp.repository.AnimeListRepository;
import lv.animelistapp.repository.CodificatorRepository;
import lv.animelistapp.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@Route("/animelist")
public class AnimeList extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    AnimeListRepository animeListRepository;

    @Autowired
    CodificatorRepository codificatorRepository;

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        this.param = parameter;
        menuBar.setParam(param);
        setData();
        boolean isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;

        if(!isAnonymous) {
            LvalUserDetails user = (LvalUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (parameter.equals(user.getUsername())) {
                allEditColumn.setVisible(true);
                currentEditColumn.setVisible(true);
                completedEditColumn.setVisible(true);
                onHoldEditColumn.setVisible(true);
                droppedEditColumn.setVisible(true);
                pwtEditColumn.setVisible(true);
            }
        }
    }

    private String param = "";

    MainMenuBar menuBar = new MainMenuBar(MainMenuBar.ANIME_LIST);;
    Grid<AnimeDetails> animeDetailsGrid = new Grid<>(AnimeDetails.class, false);

    Dialog addAnimeDialog = new Dialog();
    Grid<AnimeListModel> addToListGrid = new Grid<>(AnimeListModel.class, false);
    TextField addAnimeSearchField = new TextField();

    ComboBox<CodificatorValue> status = new ComboBox<>(Msg.getMsg("animelist.dialog.grid.details.status"));
    ComboBox<CodificatorValue> score = new ComboBox<>(Msg.getMsg("animelist.dialog.grid.details.score"));
    NumberField episodes = new NumberField(Msg.getMsg("animelist.dialog.grid.details.episodes"));
    TextArea tags = new TextArea(Msg.getMsg("animelist.dialog.grid.details.tags"));

    ComponentEventListener<ClickEvent<Button>> addAnimeListener;

    Tabs animeTabs = new Tabs();
    Tab allTab = new Tab(Msg.getMsg("animelist.page.tabs.all"));
    Tab currentTab = new Tab(Msg.getMsg("animelist.page.tabs.current"));
    Tab completedTab = new Tab(Msg.getMsg("animelist.page.tabs.completed"));
    Tab onHoldTab = new Tab(Msg.getMsg("animelist.page.tabs.on.hold"));
    Tab droppedTab = new Tab(Msg.getMsg("animelist.page.tabs.dropped"));
    Tab pwtTab = new Tab(Msg.getMsg("animelist.page.tabs.pwt"));

    VerticalLayout gridLayout = new VerticalLayout();
    Grid<AnimeListModel> allGrid = new Grid<>(AnimeListModel.class, false);
    Grid<AnimeListModel> currentGrid = new Grid<>(AnimeListModel.class, false);
    Grid<AnimeListModel> completedGrid = new Grid<>(AnimeListModel.class, false);
    Grid<AnimeListModel> onHoldGrid = new Grid<>(AnimeListModel.class, false);
    Grid<AnimeListModel> droppedGrid = new Grid<>(AnimeListModel.class, false);
    Grid<AnimeListModel> pwtGrid = new Grid<>(AnimeListModel.class, false);

    Grid.Column<AnimeListModel> allEditColumn;
    Grid.Column<AnimeListModel> currentEditColumn;
    Grid.Column<AnimeListModel> completedEditColumn;
    Grid.Column<AnimeListModel> onHoldEditColumn;
    Grid.Column<AnimeListModel> droppedEditColumn;
    Grid.Column<AnimeListModel> pwtEditColumn;

    GridListDataView<AnimeListModel> allDataView;
    GridListDataView<AnimeListModel> currentDataView;
    GridListDataView<AnimeListModel> completedDataView;
    GridListDataView<AnimeListModel> onHoldDataView;
    GridListDataView<AnimeListModel> droppedDataView;
    GridListDataView<AnimeListModel> pwtDataView;

    TextField animeListSearchField = new TextField();

    private CodificatorValue plannedValue;
    private CodificatorValue completedValue;

    private AnimeListModel selectedValues;
    private List<AnimeListModel> addAnimeList;

    private List<AnimeListModel> allAnimeList;
    private List<AnimeListModel> currentAnimeList;
    private List<AnimeListModel> completedAnimeList;
    private List<AnimeListModel> onHoldAnimeList;
    private List<AnimeListModel> dropAnimeList;
    private List<AnimeListModel> pwtAnimeList;

    public AnimeList(AnimeListRepository animeListRepository,
                     CodificatorRepository codificatorRepository) {
        this.animeListRepository = animeListRepository;
        this.codificatorRepository = codificatorRepository;

        selectedValues = new AnimeListModel();
        addAnimeList = new ArrayList<>();
        plannedValue = codificatorRepository.getSpecificCdvValue(CodificatorValue.PWT, Codificator.ANIME_STATUS);
        completedValue = codificatorRepository.getSpecificCdvValue(CodificatorValue.COMPLETED, Codificator.ANIME_STATUS);

        status.setItems(codificatorRepository.getCodifValuesByCode(Codificator.ANIME_STATUS));
        status.setItemLabelGenerator(CodificatorValue::getName);
        status.setAllowCustomValue(false);
        score.setItems(codificatorRepository.getCodifValuesByCode(Codificator.SCORE));
        score.setItemLabelGenerator(CodificatorValue::getNameAndDescription);
        score.setAllowCustomValue(false);
        status.setWidth("200px");
        score.setWidth("200px");
        episodes.setWidth("100px");

        setDialog();
        menuBar.setClickListenerForAddButton(addAnimeListener);

        animeTabs.add(allTab, currentTab, completedTab, onHoldTab, droppedTab, pwtTab);
        animeTabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        animeTabs.setWidthFull();

        animeTabs.addSelectedChangeListener(this::setTab);

        setGridColumns();
        animeListSearchField.setWidth("30vw");
        animeListSearchField.setPlaceholder(Msg.getMsg("animelist.page.search"));
        animeListSearchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        animeListSearchField.setValueChangeMode(ValueChangeMode.EAGER);

        gridLayout.add(animeListSearchField,allGrid);
        animeListSearchField.addValueChangeListener(e -> allDataView.refreshAll());
        add(menuBar, animeTabs, gridLayout);
    }

    private boolean setAnimeListFilter(AnimeListModel animeListModel) {
        String value = animeListSearchField.getValue().trim();

        if(value.isEmpty()) {
            return true;
        }

        boolean matchesTitleJP = matches(animeListModel.getTitleJP(), value);
        boolean matchesTitleEN = matches(animeListModel.getTitleEN(), value);
        boolean matchesTitleLV = matches(animeListModel.getTitleLV(), value);
        boolean matchesStatus = matches(animeListModel.getUserStatus(), value);
        boolean matchesType = matches(animeListModel.getType(), value);
        boolean matchesGenres = matches(animeListModel.getGenresString(), value);
        boolean matchesTags = matches(animeListModel.getTagsString(), value);

        return matchesTitleJP ||matchesTitleEN || matchesTitleLV ||
                matchesStatus || matchesType || matchesGenres || matchesTags;


    }
    private void setData() {
        allAnimeList = animeListRepository.getUserAnimeList(null, param);
        if (allAnimeList == null) {
            allAnimeList = new ArrayList<>();
        }
        allDataView = allGrid.setItems(allAnimeList);
        allDataView.addFilter(this::setAnimeListFilter);

        currentAnimeList = animeListRepository.getUserAnimeList(CodificatorValue.WATCHING, param);
        if(currentAnimeList == null) {
            currentAnimeList = new ArrayList<>();
        }
        currentDataView = currentGrid.setItems(currentAnimeList);
        currentDataView.setFilter(this::setAnimeListFilter);

        completedAnimeList = animeListRepository.getUserAnimeList(CodificatorValue.COMPLETED, param);
        if(completedAnimeList == null) {
            completedAnimeList = new ArrayList<>();
        }
        completedDataView = completedGrid.setItems(completedAnimeList);
        completedDataView.setFilter(this::setAnimeListFilter);

        onHoldAnimeList = animeListRepository.getUserAnimeList(CodificatorValue.ON_HOLD, param);
        if(onHoldAnimeList == null) {
            onHoldAnimeList = new ArrayList<>();
        }
        onHoldDataView = onHoldGrid.setItems(onHoldAnimeList);
        onHoldDataView.setFilter(this::setAnimeListFilter);

        dropAnimeList = animeListRepository.getUserAnimeList(CodificatorValue.DROPPED, param);
        if(dropAnimeList == null) {
            dropAnimeList = new ArrayList<>();
        }
        droppedDataView = droppedGrid.setItems(dropAnimeList);
        droppedDataView.setFilter(this::setAnimeListFilter);

        pwtAnimeList = animeListRepository.getUserAnimeList(CodificatorValue.PWT, param);
        if(pwtAnimeList == null) {
            pwtAnimeList = new ArrayList<>();
        }
        pwtDataView = pwtGrid.setItems(pwtAnimeList);
        pwtDataView.setFilter(this::setAnimeListFilter);
    }

    private void setDialog() {
        addToListGrid.addThemeVariants(GridVariant.LUMO_COMPACT);
        addToListGrid.addColumn(AnimeListModel::getTitleJP)
                .setHeader(Msg.getMsg("animelist.dialog.grid.title.jp"))
                .setAutoWidth(true);
        addToListGrid.addColumn(AnimeListModel::getTitleEN)
                .setHeader(Msg.getMsg("animelist.dialog.grid.title.en"))
                .setAutoWidth(true);
        addToListGrid.addColumn(AnimeListModel::getType)
                .setHeader(Msg.getMsg("animelist.dialog.grid.type"))
                .setAutoWidth(true);
        addToListGrid.addColumn(AnimeListModel::getEpisodes)
                .setHeader(Msg.getMsg("animelist.dialog.gid.episodes"))
                .setAutoWidth(true);
        addToListGrid.addComponentColumn(this::getAddButton)
                .setAutoWidth(true);
        addToListGrid.setDetailsVisibleOnClick(false);
        addToListGrid.setItemDetailsRenderer(new ComponentRenderer<VerticalLayout, AnimeListModel>(this::getItemDetails));

        addAnimeSearchField.setPlaceholder(Msg.getMsg("animelist.dialog.search"));
        addAnimeSearchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        addAnimeSearchField.setValueChangeMode(ValueChangeMode.EAGER);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.add(addAnimeSearchField, addToListGrid);

        addAnimeSearchField.setWidth("30vw");

        addAnimeDialog.add(verticalLayout);
        addAnimeDialog.setWidthFull();

        addAnimeListener = e -> {
            addAnimeList = animeListRepository.getAnimeListForDialog();
            if(addAnimeList == null) {
                addAnimeList = new ArrayList<>();
            }
            setGridDataAndFilter();
            addAnimeDialog.open();
        };
    }

    private void setGridDataAndFilter() {
        GridListDataView<AnimeListModel> dataView = addToListGrid.setItems(addAnimeList);
        addAnimeSearchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(this::getFilter);
    }

    private boolean getFilter(AnimeListModel value) {
        String searchValue = addAnimeSearchField.getValue().trim();
        if(searchValue.isEmpty()) {
            return true;
        }

        boolean matchTitleJP = matches(value.getTitleJP(), searchValue);
        boolean matchTitleEN = matches(value.getTitleEN(), searchValue);
        boolean matchTitleLV = matches(value.getTitleLV(), searchValue);

        return matchTitleJP || matchTitleEN || matchTitleLV;
    }

    private boolean matches(String value, String serachValue) {
        return serachValue == null || serachValue.isEmpty() ||
                value.toLowerCase().contains(serachValue.toLowerCase());
    }

    private Button getAddButton(AnimeListModel value) {
        Button addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL);
        addButton.addClickListener(e -> addToListGrid.setDetailsVisible(value,!addToListGrid.isDetailsVisible(value)));

        return addButton;
    }

    private VerticalLayout getItemDetails(AnimeListModel animeListModel) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.getThemeList().add("spacing-xs");

        verticalLayout.add(status);
        verticalLayout.add(score);

        episodes.setSuffixComponent(new Div(new Text("/"+animeListModel.getEpAsString())));
        episodes.setValue((double) animeListModel.getUserEpisodes());
        verticalLayout.add(episodes);
        status.addValueChangeListener(e -> {
            if(status.getValue().getName().equals(completedValue.getName())) {
                episodes.setValue((double) animeListModel.getEpisodes());
            }

        });
        if(animeListModel.getUserStatusId() == 0) {
            status.setValue(plannedValue);
        } else {
            status.setValue(codificatorRepository.getSpecificCdvValue(animeListModel.getUserStatus(), Codificator.ANIME_STATUS));
        }

        if(animeListModel.getScore() != 0) {
            score.setValue(codificatorRepository.getSpecificCdvValue(String.valueOf(animeListModel.getScore()), Codificator.SCORE));
        } else {
            score.setValue(null);
        }

        HorizontalLayout buttonLayout = new HorizontalLayout();
        CustomPrimaryButton add = new CustomPrimaryButton(Msg.getMsg("add.button"));
        Button cancel = new Button(Msg.getMsg("cancel.button"));
        add.addClickListener(e -> addToListDialogChangeListener(animeListModel));
        cancel.addClickListener(e -> addToListGrid.setDetailsVisible(animeListModel,false));
        buttonLayout.add(cancel,add);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        verticalLayout.add(buttonLayout);

        return verticalLayout;
    }

    private void addToListDialogChangeListener(AnimeListModel values) {
        selectedValues.setAnimeId(values.getAnimeId());
        selectedValues.setUserEpisodes(episodes.getValue().intValue());
        selectedValues.setScore((score.getValue() == null) ? 0 : Integer.parseInt(score.getValue().getName()));
        selectedValues.setUserStatusId(status.getValue().getId());

        if(values.getId() == 0) {
            animeListRepository.addToList(selectedValues);
        } else {
            animeListRepository.updateListEntry(selectedValues);
        }

        addToListGrid.setDetailsVisible(values,false);
        values.setId(selectedValues.getId());
        setData();
    }

    //TODO: links
    //TODO: tag/genre search
    private void setGridColumns() {
        allGrid/*.addColumn(AnimeListModel::getTitleJP)*/.addComponentColumn(this::getRedirectButton)
                .setHeader(Msg.getMsg("animelist.page.grid.title.jp"))
                .setAutoWidth(true);
        allEditColumn =
        allGrid.addComponentColumn(this::addUpdateButton)
                .setHeader("")
                .setAutoWidth(true);
        allEditColumn.setVisible(false);
        allGrid.addColumn(AnimeListModel::getScore)
                .setHeader(Msg.getMsg("animelist.page.grid.score"))
                .setWidth("80px");
        allGrid.addColumn(AnimeListModel::getProgress)
                .setHeader(Msg.getMsg("animelist.page.grid.progress"))
                .setWidth("90px");
        allGrid.addColumn(AnimeListModel::getUserStatus)
                .setHeader(Msg.getMsg("animelist.page.grid.status"))
                .setAutoWidth(true);
        allGrid.addColumn(AnimeListModel::getType)
                .setHeader(Msg.getMsg("animelist.page.grid.type"))
                .setWidth("70px");
        allGrid.addColumn(AnimeListModel::getGenresString)
                .setHeader(Msg.getMsg("animelist.page.grid.genres"))
                .setWidth("250px");
        allGrid.addColumn(AnimeListModel::getTagsString)
                .setHeader(Msg.getMsg("animelist.page.grid.tags"))
                .setWidth("250px");
        allGrid.setHeightByRows(true);
        allGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        currentGrid/*.addColumn(AnimeListModel::getTitleJP)*/.addComponentColumn(this::getRedirectButton)
                .setHeader(Msg.getMsg("animelist.page.grid.title.jp"))
                .setAutoWidth(true);
        currentEditColumn =
                currentGrid.addComponentColumn(this::addUpdateButton)
                        .setHeader("")
                        .setAutoWidth(true);
        currentEditColumn.setVisible(false);
        currentGrid.addColumn(AnimeListModel::getScore)
                .setHeader(Msg.getMsg("animelist.page.grid.score"))
                .setWidth("80px");
        currentGrid.addColumn(AnimeListModel::getProgress)
                .setHeader(Msg.getMsg("animelist.page.grid.progress"))
                .setWidth("90px");
        currentGrid.addColumn(AnimeListModel::getType)
                .setHeader(Msg.getMsg("animelist.page.grid.type"))
                .setWidth("70px");
        currentGrid.addColumn(AnimeListModel::getGenresString)
                .setHeader(Msg.getMsg("animelist.page.grid.genres"))
                .setWidth("250px");
        currentGrid.addColumn(AnimeListModel::getTagsString)
                .setHeader(Msg.getMsg("animelist.page.grid.tags"))
                .setWidth("250px");
        currentGrid.setHeightByRows(true);
        currentGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        completedGrid/*.addColumn(AnimeListModel::getTitleJP)*/.addComponentColumn(this::getRedirectButton)
                .setHeader(Msg.getMsg("animelist.page.grid.title.jp"))
                .setAutoWidth(true);
        completedEditColumn =
                completedGrid.addComponentColumn(this::addUpdateButton)
                        .setHeader("")
                        .setAutoWidth(true);
        completedEditColumn.setVisible(false);
        completedGrid.addColumn(AnimeListModel::getScore)
                .setHeader(Msg.getMsg("animelist.page.grid.score"))
                .setWidth("80px");
        completedGrid.addColumn(AnimeListModel::getProgress)
                .setHeader(Msg.getMsg("animelist.page.grid.progress"))
                .setWidth("90px");
        completedGrid.addColumn(AnimeListModel::getType)
                .setHeader(Msg.getMsg("animelist.page.grid.type"))
                .setWidth("70px");
        completedGrid.addColumn(AnimeListModel::getGenresString)
                .setHeader(Msg.getMsg("animelist.page.grid.genres"))
                .setWidth("250px");
        completedGrid.addColumn(AnimeListModel::getTagsString)
                .setHeader(Msg.getMsg("animelist.page.grid.tags"))
                .setWidth("250px");
        completedGrid.setHeightByRows(true);
        completedGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        onHoldGrid/*.addColumn(AnimeListModel::getTitleJP)*/.addComponentColumn(this::getRedirectButton)
                .setHeader(Msg.getMsg("animelist.page.grid.title.jp"))
                .setAutoWidth(true);
        onHoldEditColumn =
                onHoldGrid.addComponentColumn(this::addUpdateButton)
                        .setHeader("")
                        .setAutoWidth(true);
        onHoldEditColumn.setVisible(false);
        onHoldGrid.addColumn(AnimeListModel::getScore)
                .setHeader(Msg.getMsg("animelist.page.grid.score"))
                .setWidth("80px");
        onHoldGrid.addColumn(AnimeListModel::getProgress)
                .setHeader(Msg.getMsg("animelist.page.grid.progress"))
                .setWidth("90px");
        onHoldGrid.addColumn(AnimeListModel::getType)
                .setHeader(Msg.getMsg("animelist.page.grid.type"))
                .setWidth("70px");
        onHoldGrid.addColumn(AnimeListModel::getGenresString)
                .setHeader(Msg.getMsg("animelist.page.grid.genres"))
                .setWidth("250px");
        onHoldGrid.addColumn(AnimeListModel::getTagsString)
                .setHeader(Msg.getMsg("animelist.page.grid.tags"))
                .setWidth("250px");
        onHoldGrid.setHeightByRows(true);
        onHoldGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        droppedGrid/*.addColumn(AnimeListModel::getTitleJP)*/.addComponentColumn(this::getRedirectButton)
                .setHeader(Msg.getMsg("animelist.page.grid.title.jp"))
                .setAutoWidth(true);
        droppedEditColumn =
                droppedGrid.addComponentColumn(this::addUpdateButton)
                        .setHeader("")
                        .setAutoWidth(true);
        droppedEditColumn.setVisible(false);
        droppedGrid.addColumn(AnimeListModel::getScore)
                .setHeader(Msg.getMsg("animelist.page.grid.score"))
                .setWidth("80px");
        droppedGrid.addColumn(AnimeListModel::getProgress)
                .setHeader(Msg.getMsg("animelist.page.grid.progress"))
                .setWidth("90px");
        droppedGrid.addColumn(AnimeListModel::getType)
                .setHeader(Msg.getMsg("animelist.page.grid.type"))
                .setWidth("70px");
        droppedGrid.addColumn(AnimeListModel::getGenresString)
                .setHeader(Msg.getMsg("animelist.page.grid.genres"))
                .setWidth("250px");
        droppedGrid.addColumn(AnimeListModel::getTagsString)
                .setHeader(Msg.getMsg("animelist.page.grid.tags"))
                .setWidth("250px");
        droppedGrid.setHeightByRows(true);
        droppedGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        pwtGrid/*.addColumn(AnimeListModel::getTitleJP)*/.addComponentColumn(this::getRedirectButton)
                .setHeader(Msg.getMsg("animelist.page.grid.title.jp"))
                .setAutoWidth(true);
        pwtEditColumn =
                pwtGrid.addComponentColumn(this::addUpdateButton)
                        .setHeader("")
                        .setAutoWidth(true);
        pwtEditColumn.setVisible(false);
        pwtGrid.addColumn(AnimeListModel::getScore)
                .setHeader(Msg.getMsg("animelist.page.grid.score"))
                .setWidth("80px");
        pwtGrid.addColumn(AnimeListModel::getProgress)
                .setHeader(Msg.getMsg("animelist.page.grid.progress"))
                .setWidth("90px");
        pwtGrid.addColumn(AnimeListModel::getType)
                .setHeader(Msg.getMsg("animelist.page.grid.type"))
                .setWidth("70px");
        pwtGrid.addColumn(AnimeListModel::getGenresString)
                .setHeader(Msg.getMsg("animelist.page.grid.genres"))
                .setWidth("250px");
        pwtGrid.addColumn(AnimeListModel::getTagsString)
                .setHeader(Msg.getMsg("animelist.page.grid.tags"))
                .setWidth("250px");
        pwtGrid.setHeightByRows(true);
        pwtGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    }

    private Button addUpdateButton(AnimeListModel value) {
        Button update = new Button(Msg.getMsg("update.button"));
        update.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        update.addClickListener(e -> setUpdateDialog(value));

        return update;
    }

    private void setUpdateDialog(AnimeListModel animeListModel) {
        Dialog updateDialog = new Dialog();
        updateDialog.open();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setJustifyContentMode(JustifyContentMode.END);
        VerticalLayout verticalLayout = new VerticalLayout();
        CustomPrimaryButton save = new CustomPrimaryButton(Msg.getMsg("save.button"));

        status.setValue(codificatorRepository.getSpecificCdvValue(animeListModel.getUserStatus(), Codificator.ANIME_STATUS));
        score.setValue(codificatorRepository.getSpecificCdvValue(String.valueOf(animeListModel.getScore()), Codificator.SCORE));
        episodes.setValue((double) animeListModel.getUserEpisodes());
        episodes.setPlaceholder(animeListModel.getEpAsString());
        tags.setValue(animeListModel.getTagsString());
        tags.setHeightFull();
        tags.setMaxHeight("260px");

        save.addClickListener(e -> {
            addToListDialogChangeListener(animeListModel);
            animeListRepository.deleteAnimeTags(animeListModel);
            if (tags.getValue() != null && !tags.getValue().equals("")) {
                for(String tag :  tags.getValue().split(",")) {
                    animeListRepository.createAnimeTag(animeListModel.getAnimeId(), tag.trim());
                }
            }
            updateDialog.close();
        });

        verticalLayout.add(status, score, episodes);
        horizontalLayout.add(verticalLayout,tags);
        updateDialog.add(horizontalLayout, save);

    }


    private void setTab(Tabs.SelectedChangeEvent event) {
        if(event.getSelectedTab() == allTab) {
            gridLayout.removeAll();
            gridLayout.add(animeListSearchField,allGrid);
            animeListSearchField.addValueChangeListener(e -> allDataView.refreshAll());
        }

        if(event.getSelectedTab() == currentTab) {
            gridLayout.removeAll();
            gridLayout.add(animeListSearchField,currentGrid);
            animeListSearchField.addValueChangeListener(e -> currentDataView.refreshAll());
        }

        if(event.getSelectedTab() == completedTab) {
            gridLayout.removeAll();
            gridLayout.add(animeListSearchField,completedGrid);
            animeListSearchField.addValueChangeListener(e -> completedDataView.refreshAll());
        }

        if(event.getSelectedTab() == onHoldTab) {
            gridLayout.removeAll();
            gridLayout.add(animeListSearchField,onHoldGrid);
            animeListSearchField.addValueChangeListener(e -> onHoldDataView.refreshAll());
        }

        if(event.getSelectedTab() == droppedTab) {
            gridLayout.removeAll();
            gridLayout.add(animeListSearchField,droppedGrid);
            animeListSearchField.addValueChangeListener(e -> droppedDataView.refreshAll());
        }

        if(event.getSelectedTab() == pwtTab) {
            gridLayout.removeAll();
            gridLayout.add(animeListSearchField,pwtGrid);
            animeListSearchField.addValueChangeListener(e -> pwtDataView.refreshAll());
        }

    }

    private Button getRedirectButton(AnimeListModel animeDetails) {
        Button button = new Button(animeDetails.getTitleJP());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        button.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("anime/" + animeDetails.getAnimeId())));
        return button;
    }

}
