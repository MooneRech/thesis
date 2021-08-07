package lv.animelistapp.component;

import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.data.validator.EmailValidator;
import lv.animelistapp.utils.Msg;

public class CustomEmailField extends EmailField {

    public CustomEmailField() {
        setLabel(Msg.getMsg("email.field"));
        //setDefaults();
    }

    public CustomEmailField(String label) {
        setLabel(label);
        setDefaults();
    }

    private void setDefaults() {
        setClearButtonVisible(true);
        setMaxWidth("250px");
        setWidth("250px");
    }

    public EmailValidator getEmailValidator() {
        return new EmailValidator(Msg.getMsg("email.error"));
    }

}
