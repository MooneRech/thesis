package lv.animelistapp.component;

import com.vaadin.flow.component.textfield.TextField;

public class CustomTextField extends TextField {

    public CustomTextField(String label) {
        setLabel(label);
        //setDefaults();
    }

    private void setDefaults() {
        setMaxWidth("250px");
        setWidth("250px");
    }

}
