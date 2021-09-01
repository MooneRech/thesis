package lv.animelistapp.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lv.animelistapp.model.defaults.LvalUserDetails;
import lv.animelistapp.security.SecurityConfiguration;
import lv.animelistapp.utils.Msg;
import lv.animelistapp.view.permitted.security.LoginPage;
import lv.animelistapp.view.admin.anime.AdminAnimePage;
import lv.animelistapp.view.admin.codif.CodificatorPage;
import lv.animelistapp.view.permitted.security.RegistrationPage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


public class MainMenuBar extends HorizontalLayout {

    public static final String DEFAULT = "DEFAULT";
    public static final String ANIME_LIST = "LIST";

    private String mode;
    private String param;

    LvalUserDetails user;

    MenuBar menuBar = new MenuBar();
    CustomPrimaryButton loginPage = new CustomPrimaryButton(Msg.getMsg("menu.login"));
    Button registrationPage = new Button(Msg.getMsg("menu.registration"));
    Button addAnime = new Button(Msg.getMsg("menu.animelist.add"));
    Button animelist = new Button();

    HorizontalLayout horizontalLayout = new HorizontalLayout();
    VerticalLayout verticalLayout = new VerticalLayout();

    //TODO: search
    public MainMenuBar(String mode) {
        this.mode = mode;
        this.param = "";
        createMenu();
    }

    public MainMenuBar(String mode, String param) {
        this.mode = mode;
        this.param = param;
        createMenu();
    }

    public void setParam(String param) {
        this.param = param;
        removeAll();
        addToLayout();
    }

    private void createMenu() {
        setWidthFull();
        verticalLayout.getStyle().set("padding", "0");
        verticalLayout.setWidthFull();
        verticalLayout.setAlignItems(Alignment.END);

        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        boolean isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;

        /* Menu listeners*/
        ComponentEventListener<ClickEvent<MenuItem>> root =
                e -> getUI().ifPresent(ui -> ui.navigate("/"));
        ComponentEventListener<ClickEvent<MenuItem>> codifListener =
                e -> getUI().ifPresent(ui -> ui.navigate(CodificatorPage.class));
        ComponentEventListener<ClickEvent<MenuItem>> adminAnimePage =
                e -> getUI().ifPresent(ui -> ui.navigate(AdminAnimePage.class));

        /* Menu & submenu*/
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
        menuBar.setOpenOnHover(true);

        menuBar.addItem(Msg.getMsg("menu.anime"), root);
        /*menuBar.addItem(Msg.getMsg("menu.community"));*/

        if(isAuthenticated && !isAnonymous) {
            //add profile
            user = (LvalUserDetails) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            animelist.setText(user.getUsername());
            animelist.addClickListener(e -> {
                animelist.getUI().ifPresent(ui -> {
                    ui.navigate("/animelist/" + user.getUsername());
                });
            });
            animelist.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            horizontalLayout.add(animelist);
            verticalLayout.add(horizontalLayout);

        } else {
            /* Button listeners*/
            loginPage.addClickListener(e -> {
                loginPage.getUI().ifPresent(ui ->
                        ui.navigate(LoginPage.class));
            });
            registrationPage.addClickListener(e -> {
                registrationPage.getUI().ifPresent(ui ->
                        ui.navigate(RegistrationPage.class));
            });

            /* Button theme*/
            loginPage.addThemeVariants(ButtonVariant.LUMO_SMALL);
            registrationPage.addThemeVariants(ButtonVariant.LUMO_SMALL);

            horizontalLayout.add(loginPage, registrationPage);
            verticalLayout.add(horizontalLayout);

        }

        if(user != null && user.isUserInRole(SecurityConfiguration.ROLE_ADMIN) ) {
            //if(!mode.equals(MainMenuBar.ANIME_LIST)) {
                MenuItem adminItem = menuBar.addItem(Msg.getMsg("menu.admin"));
                SubMenu adminSubmenu = adminItem.getSubMenu();
                adminSubmenu.addItem(Msg.getMsg("menu.admin.codificators"), codifListener);
                adminSubmenu.addItem(Msg.getMsg("menu.admin.anime"), adminAnimePage);
            //}
        }
        addToLayout();
    }

    public void setClickListenerForAddButton(ComponentEventListener<ClickEvent<Button>> clickListener) {
        addAnime.addClickListener(clickListener);
    }

    private void addToLayout() {
        add(menuBar);

        if (mode.equals(MainMenuBar.ANIME_LIST) && user != null
                && user.isUserInRole(SecurityConfiguration.ROLE_USER)){
            if(user.getUsername().equals(param)) {
                addAnime.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_TERTIARY);
                addAnime.setWidth("100px");
                add(addAnime);
            }
        }

        /* Login buttons*/
        add(verticalLayout);
    }

}
