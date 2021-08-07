package lv.animelistapp.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lv.animelistapp.utils.Msg;


//TODO: make login possible first
public class MainMenuBar extends VerticalLayout {

    MenuBar menuBar = new MenuBar();
    Button loginPage = new Button(Msg.getMsg("menu.login"));
    Button registrationPage = new Button(Msg.getMsg("menu.registration"));

    public MainMenuBar() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(loginPage,registrationPage);

        loginPage.addClickListener(e -> {
           loginPage.getUI().ifPresent(ui ->
                   ui.navigate("login"));
        });
        registrationPage.addClickListener(e -> {
            registrationPage.getUI().ifPresent(ui ->
                    ui.navigate("registration"));
        });


    }

}
