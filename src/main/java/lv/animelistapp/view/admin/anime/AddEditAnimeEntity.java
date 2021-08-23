package lv.animelistapp.view.admin.anime;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import lv.animelistapp.component.*;
import lv.animelistapp.model.AnimeDetails;
import lv.animelistapp.model.Codificator;
import lv.animelistapp.model.CodificatorValue;
import lv.animelistapp.repository.AnimeRepository;
import lv.animelistapp.repository.CodificatorRepository;
import lv.animelistapp.utils.Msg;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Route("/admin/addEditAnime")
public class AddEditAnimeEntity extends VerticalLayout implements HasUrlParameter<String>{

    @Autowired
    CodificatorRepository codificatorRepository;

    @Autowired
    AnimeRepository animeRepository;

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        if (parameter == null) {
            animeDetails = new AnimeDetails();
            animeDetails.setGenres(new ArrayList<>());
        } else {
            animeDetails = animeRepository.getAnimeById(Long.parseLong(parameter));
            animeDetails.setGenres(codificatorRepository.getGenreListByAnimeId(animeDetails.getId()));
            if(animeDetails.getGenres() == null) {
                animeDetails.setGenres(new ArrayList<>());
            }
            setRefreshData();
        }
    }

    MainMenuBar menuBar = new MainMenuBar(MainMenuBar.DEFAULT);

    CustomTextField titleJP = new CustomTextField(Msg.getMsg("anime.edit.title.jp"));
    CustomTextField titleENG = new CustomTextField(Msg.getMsg("anime.edit.title.en"));
    CustomTextField titleLV = new CustomTextField(Msg.getMsg("anime.edit.title.lv"));
    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);
    Image image = new Image();
    ComboBox<CodificatorValue> type = new ComboBox<>(Msg.getMsg("anime.edit.type"));
    ComboBox<CodificatorValue> rating = new ComboBox<>(Msg.getMsg("anime.edit.rating"));
    ComboBox<CodificatorValue> studios = new ComboBox<>(Msg.getMsg("anime.edit.studios"));
    ComboBox<CodificatorValue> status = new ComboBox<>(Msg.getMsg("anime.edit.status"));
    CustomDatePicker airedFrom = new CustomDatePicker(Msg.getMsg("anime.edit.aired.from"));
    CustomDatePicker airedTo = new CustomDatePicker(Msg.getMsg("anime.edit.aired.to"));
    NumberField episodes = new NumberField(Msg.getMsg("anime.edit.episodes"));
    NumberField duration = new NumberField(Msg.getMsg("anime.edit.duration"));
    ComboBox<CodificatorValue> source = new ComboBox<>(Msg.getMsg("anime.edit.source"));
    TextArea description = new TextArea(Msg.getMsg("anime.edit.description"));

    CustomDeleteButton delete = new CustomDeleteButton();
    CustomPrimaryButton save = new CustomPrimaryButton(Msg.getMsg("save.button"));

    ComboBox<CodificatorValue> genres = new ComboBox<>(Msg.getMsg("anime.edit.genres"));
    Grid<CodificatorValue> selectedGenreGrid = new Grid<>(CodificatorValue.class);

    Binder<AnimeDetails> binder = new Binder<>(AnimeDetails.class);

    private AnimeDetails animeDetails;

    private List<CodificatorValue> typeList;
    private List<CodificatorValue> ratingList;
    private List<CodificatorValue> studiosList;
    private List<CodificatorValue> statusList;
    private List<CodificatorValue> sourceList;
    private List<CodificatorValue> genreList;

    public AddEditAnimeEntity(CodificatorRepository codificatorRepository, AnimeRepository animeRepository) {
        this.codificatorRepository = codificatorRepository;
        this.animeRepository = animeRepository;

        binder.setBean(new AnimeDetails());

        typeList = codificatorRepository.getCodifValuesByCode(Codificator.TYPE);
        ratingList = codificatorRepository.getCodifValuesByCode(Codificator.RATING);
        studiosList = codificatorRepository.getCodifValuesByCode(Codificator.STUDIOS);
        statusList = codificatorRepository.getCodifValuesByCode(Codificator.STATUS);
        sourceList = codificatorRepository.getCodifValuesByCode(Codificator.SOURCE);
        genreList = codificatorRepository.getCodifValuesByCode(Codificator.GENRE);

        type.setItems(typeList);
        rating.setItems(ratingList);
        studios.setItems(studiosList);
        status.setItems(statusList);
        source.setItems(sourceList);
        genres.setItems(genreList);

        type.setAllowCustomValue(false);
        rating.setAllowCustomValue(false);
        studios.setAllowCustomValue(false);
        status.setAllowCustomValue(false);
        source.setAllowCustomValue(false);
        genres.setAllowCustomValue(false);

        type.setItemLabelGenerator(CodificatorValue::getName);
        rating.setItemLabelGenerator(CodificatorValue::getName);
        studios.setItemLabelGenerator(CodificatorValue::getName);
        status.setItemLabelGenerator(CodificatorValue::getName);
        source.setItemLabelGenerator(CodificatorValue::getName);
        genres.setItemLabelGenerator(CodificatorValue::getName);

        type.addValueChangeListener(e -> animeDetails.setTypeId(e.getValue().getId()));
        rating.addValueChangeListener(e -> animeDetails.setRatingId(e.getValue().getId()));
        studios.addValueChangeListener(e -> animeDetails.setStudiosId(e.getValue().getId()));
        status.addValueChangeListener(e -> animeDetails.setStatusId(e.getValue().getId()));
        source.addValueChangeListener(e -> animeDetails.setSourceId(e.getValue().getId()));
        genres.addValueChangeListener(e -> afterCodifSelection(e.getValue()));

        binder.forField(titleJP).withValidator(title -> title != null
                                                     && !title.isEmpty()
                                                     && !title.isBlank(),
                                                     Msg.getMsg("anime.edit.title.jp.error"))
                .bind(AnimeDetails::getTitleJP, AnimeDetails::setTitleJP);
        binder.forField(titleENG).bind(AnimeDetails::getTitleEN, AnimeDetails::setTitleEN);
        binder.forField(titleLV).bind(AnimeDetails::getTitleLV, AnimeDetails::setTitleLV);
        binder.forField(airedFrom).bind(AnimeDetails::getAiredFrom, AnimeDetails::setAiredFrom);
        binder.forField(airedTo).bind(AnimeDetails::getAiredTo, AnimeDetails::setAiredTo);
        binder.forField(episodes).bind(AnimeDetails::getEpDouble,AnimeDetails::setEpDouble);
        binder.forField(duration).bind(AnimeDetails::getDurDouble, AnimeDetails::setDurDouble);
        binder.forField(description).bind(AnimeDetails::getDescription, AnimeDetails::setDescription);

        selectedGenreGrid.setItems(new ArrayList<>());
        selectedGenreGrid.setWidth("350px");
        selectedGenreGrid.setColumns("name");
        selectedGenreGrid.getColumnByKey("name").setHeader(Msg.getMsg("anime.edit.grid.name")).setAutoWidth(true);
        selectedGenreGrid.addComponentColumn(this::getDeleteButton).setHeader("").setAutoWidth(true).setAutoWidth(true);

        UploadI18N i18N = new UploadI18N();
        i18N.setAddFiles(new UploadI18N.AddFiles());
        i18N.getAddFiles().setOne(Msg.getMsg("anime.edit.upload.add"));
        i18N.setDropFiles(new UploadI18N.DropFiles());
        i18N.getDropFiles().setOne("");
        upload.setI18n(i18N);
        image.setMaxWidth("255px");
        image.setMaxHeight("320px");

        episodes.setWidth("192px");
        duration.setWidth("192px");
        upload.setMaxFiles(1);
        upload.setAcceptedFileTypes("image/*");
        upload.addSucceededListener(e -> {
            String fileName = e.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            image.setVisible(true);
            try {
                byte[] imageByte = IOUtils.toByteArray(buffer.getInputStream(e.getFileName()));
                animeDetails.setImage(imageByte);
                animeDetails.setImageName(e.getFileName());
                InputStreamFactory inputStreamFactory = new InputStreamFactory() {
                    @Override
                    public InputStream createInputStream() {
                        return inputStream;
                    }
                };
                StreamResource streamResource =
                        new StreamResource(e.getFileName(), inputStreamFactory);
                image.setSrc(streamResource);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        save.addClickListener(e -> saveButtonListener());
        delete.addClickListener(e -> deleteButtonListener());
        Button back = new Button();
        back.addThemeVariants(ButtonVariant.LUMO_ICON);
        back.setIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
        back.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(AdminAnimePage.class)));

        HorizontalLayout titleAndImageLayout = new HorizontalLayout();
        VerticalLayout titleLayout = new VerticalLayout();
        HorizontalLayout typeRatingStudios = new HorizontalLayout();
        HorizontalLayout statusAndDates = new HorizontalLayout();
        HorizontalLayout epDurationSource = new HorizontalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        VerticalLayout genreLayout = new VerticalLayout();
        VerticalLayout firstHalf = new VerticalLayout();
        HorizontalLayout mainLayout = new HorizontalLayout();

        titleLayout.setWidth("210px");
        titleLayout.add(titleJP,titleENG,titleLV);
        titleAndImageLayout.add(titleLayout,upload, image);
        typeRatingStudios.add(type,rating,studios);
        statusAndDates.add(status,airedFrom,airedTo);
        epDurationSource.add(episodes,duration,source);
        buttonLayout.add(back,delete,save);
        genreLayout.add(genres,selectedGenreGrid);
        firstHalf.add(titleAndImageLayout, typeRatingStudios, statusAndDates,epDurationSource);
        mainLayout.add(firstHalf,genreLayout, description);
        mainLayout.setWidthFull();
        firstHalf.setWidth("45vw");
        genreLayout.setWidth("30vw");
        description.setWidth("100%");
        description.setMaxHeight("75vh");
        add(menuBar, mainLayout,buttonLayout);
    }

    private void setRefreshData() {
        binder.setBean(animeDetails);
        if(animeDetails.getType() != null && !animeDetails.getType().equals("")) {
            type.setValue(new CodificatorValue(animeDetails.getTypeId(), animeDetails.getType()));
        }
        if(animeDetails.getRating() != null && !animeDetails.getRating().equals("")) {
            rating.setValue(new CodificatorValue(animeDetails.getRatingId(), animeDetails.getRating()));
        }
        if(animeDetails.getStudios() != null && !animeDetails.getStudios().equals("")) {
            studios.setValue(new CodificatorValue(animeDetails.getStudiosId(), animeDetails.getStudios()));
        }
        if(animeDetails.getStatus() != null && !animeDetails.getStatus().equals("")) {
            status.setValue(new CodificatorValue(animeDetails.getStatusId(), animeDetails.getStatus()));
        }
        if(animeDetails.getSource() != null && !animeDetails.getSource().equals("")) {
            source.setValue(new CodificatorValue(animeDetails.getSourceId(), animeDetails.getSource()));
        }

        if(animeDetails.getImageName() != null && !animeDetails.getImageName().equals("")) {
            InputStream inputStream = new ByteArrayInputStream(animeDetails.getImage());
            InputStreamFactory inputStreamFactory = new InputStreamFactory() {
                @Override
                public InputStream createInputStream() {
                    return inputStream;
                }
            };
            StreamResource streamResource = new StreamResource(animeDetails.getImageName(), inputStreamFactory);
            image.setSrc(streamResource);
        }

        if(animeDetails.getGenres() != null && !animeDetails.getGenres().isEmpty()) {
            selectedGenreGrid.setItems(animeDetails.getGenres());
        }

        if(animeDetails.getDescription() != null && !animeDetails.getDescription().isEmpty()) {
            description.setValue(animeDetails.getDescription());
        }

    }

    private void saveButtonListener() {
        updateAnimeDetails();
        if (checkIfValid()) {
            if (animeDetails.getId() == 0) {
                animeRepository.createAnimeEntity(animeDetails);
                for (CodificatorValue value : animeDetails.getGenres()) {
                    codificatorRepository.addGenreToAnime(animeDetails.getId(), value.getId());
                }
            } else {
                animeRepository.updateAnimeEntity(animeDetails);
                codificatorRepository.deleteGenresByAnimeId(animeDetails.getId());
                for (CodificatorValue value : animeDetails.getGenres()) {
                    codificatorRepository.addGenreToAnime(animeDetails.getId(), value.getId());
                }
            }
        }
        Notification notification = new Notification();
        notification.setText(Msg.getMsg("notif.save.success"));
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.TOP_END);
        notification.setDuration(5000);
        notification.open();
    }

    private void updateAnimeDetails() {
        AnimeDetails details = binder.getBean();
        animeDetails.setTitleJP(details.getTitleJP());
        animeDetails.setTitleEN(details.getTitleEN());
        animeDetails.setTitleLV(details.getTitleLV());
        animeDetails.setAiredFrom(details.getAiredFrom());
        animeDetails.setAiredTo(details.getAiredTo());
        animeDetails.setEpisodes(details.getEpisodes());
        animeDetails.setDuration(details.getDuration());
        animeDetails.setDescription(details.getDescription());
    }

    private void deleteButtonListener(){
        Dialog dialog = new Dialog();
        VerticalLayout verticalLayout = new VerticalLayout();
        Div div = new Div();
        div.setText(Msg.getMsg("anime.edit.delete.text"));

        CustomDeleteButton button = new CustomDeleteButton();
        button.addClickListener(e -> {
            animeRepository.deleteAnimeEntity(animeDetails.getId());
            dialog.close();
            button.getUI().ifPresent(ui -> ui.navigate(AdminAnimePage.class));
        });

        verticalLayout.add(div,button);
        verticalLayout.setAlignItems(Alignment.END);

        dialog.add(verticalLayout);
        dialog.open();
    }

    private boolean checkIfValid() {
        boolean isValid = true;
        if (animeDetails.getTitleJP() == null || animeDetails.getTitleJP().equals("")) {
            titleJP.setInvalid(true);
            isValid = false;
        }
        return isValid;
    }

    private void afterCodifSelection(CodificatorValue value) {
        boolean hasValue = false;
        for (CodificatorValue codificatorValue : animeDetails.getGenres()) {
            if(codificatorValue.getId() == value.getId()) {
                hasValue = true;
                break;
            }
        }
        if(!hasValue) {
            animeDetails.getGenres().add(value);
            selectedGenreGrid.setItems(animeDetails.getGenres());
        }
    }

    private Button getDeleteButton(CodificatorValue value) {
        Button delete = new Button(new Icon(VaadinIcon.TRASH));
        delete.addThemeVariants(ButtonVariant.LUMO_ICON);
        delete.addClickListener(e -> {
            animeDetails.getGenres().remove(value);
            selectedGenreGrid.setItems(animeDetails.getGenres());
        });
        return delete;
    }


    //================================================================
    //                     GETTERS AND SETTERS
    //================================================================


    public List<CodificatorValue> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<CodificatorValue> genreList) {
        this.genreList = genreList;
    }

    public List<CodificatorValue> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<CodificatorValue> sourceList) {
        this.sourceList = sourceList;
    }

    public List<CodificatorValue> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<CodificatorValue> statusList) {
        this.statusList = statusList;
    }

    public List<CodificatorValue> getStudiosList() {
        return studiosList;
    }

    public void setStudiosList(List<CodificatorValue> studiosList) {
        this.studiosList = studiosList;
    }

    public List<CodificatorValue> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<CodificatorValue> ratingList) {
        this.ratingList = ratingList;
    }

    public List<CodificatorValue> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<CodificatorValue> typeList) {
        this.typeList = typeList;
    }

    public AnimeDetails getAnimeDetails() {
        return animeDetails;
    }

    public void setAnimeDetails(AnimeDetails animeDetails) {
        this.animeDetails = animeDetails;
    }
}
