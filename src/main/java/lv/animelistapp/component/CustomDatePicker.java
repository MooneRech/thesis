package lv.animelistapp.component;

import com.vaadin.flow.component.datepicker.DatePicker;

import java.util.Locale;

public class CustomDatePicker extends DatePicker {

    public CustomDatePicker(String label) {
        setLabel(label);
        setLocale(Locale.getDefault());
    }

}
