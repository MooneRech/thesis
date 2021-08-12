package lv.animelistapp.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lv.animelistapp.model.defaults.LvalUserDetails;
import lv.animelistapp.security.SecurityConfiguration;
import lv.animelistapp.utils.Msg;
import lv.animelistapp.view.LoginPage;
import lv.animelistapp.view.admin.codif.CodificatorPage;
import lv.animelistapp.view.permitted.RegistrationPage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;


public class MainMenuBar extends HorizontalLayout {

    LvalUserDetails user;

    MenuBar menuBar = new MenuBar();
    Button loginPage = new Button(Msg.getMsg("menu.login"));
    Button registrationPage = new Button(Msg.getMsg("menu.registration"));

    public MainMenuBar() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(Alignment.END);

        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            //add profile
            user = (LvalUserDetails) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
        } else {
            horizontalLayout.add(loginPage, registrationPage);
        }


        /* Button listeners*/
        loginPage.addClickListener(e -> {
           loginPage.getUI().ifPresent(ui ->
                   ui.navigate(LoginPage.class));
        });
        registrationPage.addClickListener(e -> {
            registrationPage.getUI().ifPresent(ui ->
                    ui.navigate(RegistrationPage.class));
        });

        /* Menu listeners*/
        ComponentEventListener<ClickEvent<MenuItem>> root =
                e -> getUI().ifPresent(ui -> ui.navigate("/"));
        ComponentEventListener<ClickEvent<MenuItem>> codifListener =
                e -> getUI().ifPresent(ui -> ui.navigate(CodificatorPage.class));

        /* Menu & submenu*/
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
        menuBar.setOpenOnHover(true);

        menuBar.addItem(Msg.getMsg("menu.anime"), root);
        menuBar.addItem(Msg.getMsg("menu.community"));

        if(user != null && user.isUserInRole(SecurityConfiguration.ROLE_ADMIN)) {
            MenuItem adminItem = menuBar.addItem(Msg.getMsg("menu.admin"));
            SubMenu adminSubmenu = adminItem.getSubMenu();
            adminSubmenu.addItem(Msg.getMsg("menu.admin.codificators"), codifListener);
        }



        add(menuBar, horizontalLayout);


    }

}
