package lv.animelistapp.component;

import com.vaadin.flow.component.button.Button;

public class CustomPrimaryButton extends Button {

    public CustomPrimaryButton(String label) {
        setText(label);
        setThemeName("primary");
    }

}
