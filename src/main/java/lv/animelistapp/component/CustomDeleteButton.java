package lv.animelistapp.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import lv.animelistapp.utils.Msg;

public class CustomDeleteButton extends Button {

    public CustomDeleteButton() {
        setText(Msg.getMsg("delete.button"));
        addThemeVariants(ButtonVariant.LUMO_ERROR);
    }

}
