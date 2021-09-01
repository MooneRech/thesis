package lv.animelistapp.view.permitted.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.History;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import lv.animelistapp.utils.Msg;

@Route("login")
public class LoginPage extends VerticalLayout implements BeforeEnterObserver {
    LoginForm loginForm = new LoginForm();
    History history;

    public LoginPage() {
        history = UI.getCurrent().getPage().getHistory();
        loginForm.setI18n(changeLanguage());
        loginForm.setAction("login");

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        loginForm.addForgotPasswordListener(e -> getUI().ifPresent(ui -> ui.navigate(RegistrationPage.class)));
        
        add(loginForm);

    }

    private LoginI18n changeLanguage() {
        LoginI18n login = LoginI18n.createDefault();

        LoginI18n.Form loginI18nForm = login.getForm();
        loginI18nForm.setTitle(Msg.getMsg("login.title"));
        loginI18nForm.setUsername(Msg.getMsg("login.username"));
        loginI18nForm.setPassword(Msg.getMsg("login.password"));
        loginI18nForm.setSubmit(Msg.getMsg("login.submit"));
        loginI18nForm.setForgotPassword(Msg.getMsg("login.registration"));
        login.setForm(loginI18nForm);

        LoginI18n.ErrorMessage loginErrorMessage = login.getErrorMessage();
        loginErrorMessage.setMessage("");
        loginErrorMessage.setTitle(Msg.getMsg("login.error.message"));
        login.setErrorMessage(loginErrorMessage);

        return login;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }




}
