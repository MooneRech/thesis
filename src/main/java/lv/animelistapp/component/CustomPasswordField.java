package lv.animelistapp.component;

import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.validator.RegexpValidator;
import lv.animelistapp.utils.Msg;

public class CustomPasswordField extends PasswordField {

    public CustomPasswordField() {
        setLabel(Msg.getMsg("password.field"));
        //setDefaults();
    }

    public CustomPasswordField(String label) {
        setLabel(label);
        //setDefaults();
    }

    private void setDefaults(){
        setMaxWidth("250px");
        setWidth("250px");
    }

    public RegexpValidator getRegexpValidator() {
        return new RegexpValidator(Msg.getMsg("password.help.pattern"),"^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$");
    }

}
