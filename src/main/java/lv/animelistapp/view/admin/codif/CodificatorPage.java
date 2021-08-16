package lv.animelistapp.view.admin.codif;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import lv.animelistapp.component.CustomPrimaryButton;
import lv.animelistapp.component.MainMenuBar;
import lv.animelistapp.model.Codificator;
import lv.animelistapp.model.CodificatorValue;
import lv.animelistapp.repository.CodificatorRepository;
import lv.animelistapp.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("/admin/codificator")
public class CodificatorPage extends VerticalLayout {

    @Autowired
    CodificatorRepository codificatorRepository;

    Binder<CodificatorValue> codificatorValueBinder = new Binder<>();

    MainMenuBar menuBar = new MainMenuBar();
    ComboBox<Codificator> codificatorSelect = new ComboBox<>();
//    TextArea codificatorDescription = new TextArea();
    Grid<CodificatorValue> codificatorValueGrid = new Grid<>(CodificatorValue.class);
    TextField codifValueNameField = new TextField();
    TextArea codifValueDescriptionField = new TextArea();

    CustomPrimaryButton savePage = new CustomPrimaryButton(Msg.getMsg("save.button"));
    CustomPrimaryButton addCodifValueButton = new CustomPrimaryButton(Msg.getMsg("add.button"));

    private List<Codificator> codificatorList;
    private List<CodificatorValue> codificatorValueList;
    private Codificator selectedCodif;
    private CodificatorValue selectedCodifValue;

    public CodificatorPage (CodificatorRepository codificatorRepository) {
        this.codificatorRepository = codificatorRepository;
        codificatorList = codificatorRepository.getCodifList();

        codificatorSelect.setLabel(Msg.getMsg("codif.select"));
        codificatorSelect.setErrorMessage("");
        codificatorSelect.setAutoOpen(true);
        codificatorSelect.setAllowCustomValue(false);
        codificatorSelect.setItems(codificatorList);
        codificatorSelect.setItemLabelGenerator(Codificator::getName);
        codificatorSelect.addValueChangeListener(e -> afterCodifSelection(e.getValue()));

        codificatorValueGrid.setColumns("name", "description");
        codificatorValueGrid.getColumnByKey("name").setHeader(Msg.getMsg("codif.grid.name")).setAutoWidth(true);
        codificatorValueGrid.getColumnByKey("description").setHeader(Msg.getMsg("codif.grid.description")).setAutoWidth(true);
        codificatorValueGrid.setWidth("100%");
        codificatorValueGrid.addComponentColumn(e -> getEditButton(e)).setHeader("").setAutoWidth(true);
        codificatorValueGrid.addComponentColumn(e -> getDeleteButton(e)).setHeader("").setAutoWidth(true);
        codificatorValueGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        codifValueNameField.setLabel(Msg.getMsg("codig.field.name"));
        codifValueNameField.setRequired(true);
        codifValueDescriptionField.setLabel(Msg.getMsg("codif.field.description"));
        codifValueDescriptionField.setMaxLength(255);
        codifValueDescriptionField.setMaxHeight("150px");
        codifValueDescriptionField.setWidth("500px");

        enableAddEditCodifValue(false);

        codificatorValueBinder.forField(codifValueNameField)
                .withValidator(name -> !name.isEmpty() && !name.isBlank(), "")
                .bind(CodificatorValue::getName, CodificatorValue::setName);
        codificatorValueBinder.forField(codifValueDescriptionField)
                .bind(CodificatorValue::getDescription,CodificatorValue::setDescription);

        savePage.addClickListener(e -> {
            selectedCodifValue = codificatorValueBinder.getBean();
            if(!codifValueNameField.isInvalid()) {
                if (selectedCodifValue.getId() != 0) {
                    codificatorRepository.updateCodifValue(selectedCodifValue);
                } else {
                    codificatorRepository.createCodifValue(selectedCodifValue, selectedCodif.getCode());
                }
                refreshGrid();
                enableAddEditCodifValue(false);
            }
        });

        addCodifValueButton.setEnabled(false);
        addCodifValueButton.addClickListener(e -> {
            codificatorValueBinder.setBean(new CodificatorValue());
            enableAddEditCodifValue(true);
        });

        HorizontalLayout codifValueLayout = new HorizontalLayout();
        VerticalLayout codifGridlayout = new VerticalLayout();
        VerticalLayout addEditCodifValuesLayout = new VerticalLayout();

        codifGridlayout.setWidth("50vw");
        addEditCodifValuesLayout.setWidth("40vw");
        codifGridlayout.add(codificatorSelect, codificatorValueGrid);
        codifGridlayout.setAlignItems(Alignment.CENTER);

        addEditCodifValuesLayout.add(addCodifValueButton, codifValueNameField,codifValueDescriptionField, savePage);
        codifValueLayout.add(codifGridlayout, addEditCodifValuesLayout);
        add(menuBar, codifValueLayout);

        addEditCodifValuesLayout.setAlignSelf(Alignment.END, addCodifValueButton);
    }

    private void refreshGrid() {
        codificatorValueList = codificatorRepository.getCodifValuesByCode(selectedCodif.getCode());
        codificatorValueGrid.setItems(codificatorValueList);
    }

    private void afterCodifSelection(Codificator codificator) {
        selectedCodif = codificator;
        codificatorValueList = codificatorRepository.getCodifValuesByCode(selectedCodif.getCode());
        codificatorValueGrid.setItems(codificatorValueList);
        addCodifValueButton.setEnabled(true);
    }

    private void enableAddEditCodifValue(boolean value) {
        codifValueNameField.setEnabled(value);
        codifValueDescriptionField.setEnabled(value);
        savePage.setEnabled(value);
    }

    private Button getEditButton(CodificatorValue value) {
        Button edit = new Button(new Icon(VaadinIcon.EDIT));
        edit.addThemeVariants(ButtonVariant.LUMO_ICON);
        edit.addClickListener(e -> setItemsToEdit(value));
        return edit;
    }

    private void setItemsToEdit(CodificatorValue value) {
        enableAddEditCodifValue(true);
        codificatorValueBinder.setBean(value);
    }

    private Button getDeleteButton(CodificatorValue value) {
        Button delete = new Button(new Icon(VaadinIcon.TRASH));
        delete.addThemeVariants(ButtonVariant.LUMO_ICON);
        delete.addClickListener(e -> {
            codificatorRepository.deleteCodifValueById(value.getId());
            refreshGrid();
        });
        return delete;
    }

    //================================================================
    //                     GETTERS AND SETTERS
    //================================================================

    public CodificatorValue getSelectedCodifValue() {
        return selectedCodifValue;
    }

    public void setSelectedCodifValue(CodificatorValue selectedCodifValue) {
        this.selectedCodifValue = selectedCodifValue;
    }

    public Codificator getSelectedCodif() {
        return selectedCodif;
    }

    public void setSelectedCodif(Codificator selectedCodif) {
        this.selectedCodif = selectedCodif;
    }

    public List<CodificatorValue> getCodificatorValueList() {
        return codificatorValueList;
    }

    public void setCodificatorValueList(List<CodificatorValue> codificatorValueList) {
        this.codificatorValueList = codificatorValueList;
    }

    public List<Codificator> getCodificatorList() {
        return codificatorList;
    }

    public void setCodificatorList(List<Codificator> codificatorList) {
        this.codificatorList = codificatorList;
    }
}
