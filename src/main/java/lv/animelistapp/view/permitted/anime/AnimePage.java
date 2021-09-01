package lv.animelistapp.view.permitted.anime;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import lv.animelistapp.component.CustomPrimaryButton;
import lv.animelistapp.component.MainMenuBar;
import lv.animelistapp.model.AnimeDetails;
import lv.animelistapp.model.AnimeListModel;
import lv.animelistapp.model.Codificator;
import lv.animelistapp.model.CodificatorValue;
import lv.animelistapp.model.defaults.LvalUserDetails;
import lv.animelistapp.repository.AnimeListRepository;
import lv.animelistapp.repository.AnimeRepository;
import lv.animelistapp.repository.CodificatorRepository;
import lv.animelistapp.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Route("anime")
public class AnimePage extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    AnimeListRepository animeListRepository;

    @Autowired
    CodificatorRepository codificatorRepository;

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        animeDetails = animeRepository.getAnimePageById(Long.parseLong(parameter));
        isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;

        if(!isAnonymous) {
            LvalUserDetails user = (LvalUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            animeListModel = animeListRepository.getUserAnimeDetails(Long.parseLong(parameter), user.getId());
            if(animeListModel == null) {
                animeListModel = new AnimeListModel();
                animeListModel.setAnimeId(animeDetails.getId());
            }
            usrStatus = codificatorRepository.getSpecificCdvValue(animeListModel.getUserStatus(), Codificator.ANIME_STATUS);
            if(usrStatus == null) {
                usrStatus = new CodificatorValue();
            }
            usrScore = codificatorRepository.getSpecificCdvValue(String.valueOf(animeListModel.getScore()),Codificator.SCORE);
            if(usrScore == null) {
                usrScore = new CodificatorValue();
            }
        }

        setView();
    }

    boolean isAnonymous;

    MainMenuBar menuBar = new MainMenuBar(MainMenuBar.DEFAULT);
    H2 titleJP = new H2();
    H3 titleENLV = new H3();
    Image image = new Image();

    VerticalLayout informationLayout = new VerticalLayout();
    Details information = new Details(Msg.getMsg("anime.page.information"), informationLayout);

    ComboBox<CodificatorValue> scoreBox = new ComboBox();
    ComboBox<CodificatorValue> statusBox = new ComboBox();
    NumberField progress = new NumberField();
    CustomPrimaryButton saveButton = new CustomPrimaryButton(Msg.getMsg("save.button"));

    private AnimeDetails animeDetails;
    private AnimeListModel animeListModel;
    private CodificatorValue usrStatus;
    private CodificatorValue usrScore;

    public AnimePage(AnimeRepository animeRepository,
                     AnimeListRepository animeListRepository,
                     CodificatorRepository codificatorRepository) {
        this.animeRepository = animeRepository;
        this.animeListRepository = animeListRepository;
        this.codificatorRepository = codificatorRepository;

        scoreBox.setPlaceholder(Msg.getMsg("anime.page.list.edit.score"));
        statusBox.setPlaceholder(Msg.getMsg("anime.page.list.edit.status"));
        scoreBox.setItemLabelGenerator(CodificatorValue::getNameAndDescription);
        statusBox.setItemLabelGenerator(CodificatorValue::getName);

        List<CodificatorValue> scoreList = new ArrayList<>();
        scoreList.add(new CodificatorValue());
        scoreList.addAll(codificatorRepository.getCodifValuesByCode(Codificator.SCORE));
        scoreBox.setItems(scoreList);

        List<CodificatorValue> statusList = new ArrayList<>();
        statusList.addAll(codificatorRepository.getCodifValuesByCode(Codificator.ANIME_STATUS));
        statusBox.setItems(statusList);
    }

    private void setView() {
        titleJP.setText(animeDetails.getTitleJP());
        titleENLV.setText(animeDetails.getTitleENLV());
        titleENLV.getStyle().set("margin", "0");

        VerticalLayout headerLayout = new VerticalLayout();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout imageAndDetails = new VerticalLayout();
        VerticalLayout descriptionAndReviews = new VerticalLayout();

        //image.setMaxWidth("255px");
        //image.setMaxHeight("320px");
        image.setVisible(true);

        InputStream inputStream = new ByteArrayInputStream(animeDetails.getImage());
        InputStreamFactory inputStreamFactory = new InputStreamFactory() {
            @Override
            public InputStream createInputStream() {
                return inputStream;
            }
        };
        StreamResource streamResource = new StreamResource(animeDetails.getImageName(), inputStreamFactory);
        image.setSrc(streamResource);

        informationLayout.setPadding(false);
        informationLayout.setSpacing(false);

        Span type = new Span(Msg.getMsg("anime.page.information.type") + " " + animeDetails.getType());
        Span episodes = new Span(Msg.getMsg("anime.page.information.episodes") + " " + animeDetails.getEpisodes());
        Span status = new Span(Msg.getMsg("anime.page.information.status") + " " + animeDetails.getStatus());
        Span aired = new Span(Msg.getMsg("anime.page.information.aired") + " " + animeDetails.getAiredPeriod());
        Span studios = new Span(Msg.getMsg("anime.page.information.studios") + " " + animeDetails.getStudios());
        Span source = new Span(Msg.getMsg("anime.page.information.source") + " " + animeDetails.getSource());
        Span genres = new Span(Msg.getMsg("anime.page.information.genres") + " " + animeDetails.getGenresString());
        Span duration = new Span(Msg.getMsg("anime.page.information.duration") + " " + animeDetails.getDuration() + " " + Msg.getMsg("anime.page.information.duration.end"));
        Span rating = new Span(Msg.getMsg("anime.page.information.rating") + " " + animeDetails.getRating());

        informationLayout.setMaxWidth("225px");
        informationLayout.add(  type, episodes, status,
                                aired, studios, source,
                                genres, duration, rating);

        information.setOpened(true);
        information.addThemeVariants(DetailsVariant.SMALL);

        H3 avgScore = new H3(Msg.getMsg("anime.page.information.avg.score") + " " + animeDetails.getAvrScoreString());

        Span description = new Span(Msg.getMsg("anime.page.information.description"));
        description.getStyle().set("font-weight", "bold");
        Span descriptionText = new Span(animeDetails.getDescription());
        descriptionText.getStyle().set("white-space", "pre-wrap");

        HorizontalLayout animeListLayout = new HorizontalLayout();
        if(!isAnonymous) {
            statusBox.setValue(usrStatus);
            statusBox.addValueChangeListener(e -> saveButton.setEnabled(true));
            scoreBox.setValue(usrScore);
            scoreBox.addValueChangeListener(e -> saveButton.setEnabled(true));
            progress.setPlaceholder("/"+animeDetails.getEpisodes());
            progress.setValue((double) animeListModel.getEpisodes());
            progress.addValueChangeListener(e -> saveButton.setEnabled(true));
            saveButton.setEnabled(false);
            saveButton.addClickListener(e -> {

                animeListModel.setScore((scoreBox.getValue() == null) ? 0 : Integer.parseInt(scoreBox.getValue().getName()));
                animeListModel.setUserStatusId((statusBox.getValue() == null) ? 0 : statusBox.getValue().getId());
                animeListModel.setUserEpisodes(progress.getValue().intValue());

                if(animeListModel.getId() == 0) {
                    animeListRepository.addToList(animeListModel);
                } else {
                    animeListRepository.updateListEntry(animeListModel);
                }

                saveButton.setEnabled(false);
            });

            animeListLayout.add(statusBox, scoreBox, progress, saveButton);
        }

        horizontalLayout.add(imageAndDetails,descriptionAndReviews);
        horizontalLayout.getStyle().set("padding", "0");
        headerLayout.add(titleJP,titleENLV);
        imageAndDetails.add(image, information);
        imageAndDetails.setWidth("300px");
        descriptionAndReviews.add(avgScore,animeListLayout,
                                  description, descriptionText);
        add(menuBar, headerLayout, horizontalLayout);
    }


}
