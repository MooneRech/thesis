package lv.animelistapp.view.permitted;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import lv.animelistapp.component.CustomEmailField;
import lv.animelistapp.component.CustomPasswordField;
import lv.animelistapp.component.CustomPrimaryButton;
import lv.animelistapp.component.CustomTextField;
import lv.animelistapp.model.User;
import lv.animelistapp.repository.UserRepository;
import lv.animelistapp.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;

@Route("registration")
public class RegistrationPage  extends FormLayout {

    @Autowired
    private final UserRepository userRepository;

    Binder<User> binder = new Binder<>(User.class);

    CustomTextField username = new CustomTextField(Msg.getMsg("registration.textfield.username"));
    CustomEmailField email = new CustomEmailField();
    CustomPasswordField password = new CustomPasswordField();
    CustomPasswordField confirmPassword = new CustomPasswordField(Msg.getMsg("registration.textfield.confirm.password"));

    CustomPrimaryButton submit = new CustomPrimaryButton(Msg.getMsg("registration.submit"));

    @Autowired
    public RegistrationPage(UserRepository userRepository) {
        this.userRepository = userRepository;

        binder.bindInstanceFields(this);
        binder.setBean(new User());

        binder.forField(username)
                .withValidator(username -> username != null
                                        && !username.isEmpty()
                                        && !username.isBlank()
                                        && username.length() >= 5,
                        Msg.getMsg("registration.username.length"))
                .bind(User::getUsername, User::setUsername);
        binder.forField(email)
                .withValidator(email.getEmailValidator())
                .bind(User::getEmail, User::setEmail);
        binder.forField(password)
                .withValidator(password.getRegexpValidator())
                .bind(User::getPassword, User::setPassword);
        binder.forField(confirmPassword)
                .withValidator(confirmPassword -> confirmPassword.equals(password.getValue()), Msg.getMsg("password.error.match"))
                .bind(User::getConfirmPassword, User::setConfirmPassword);

        H1 header = new H1(Msg.getMsg("registration.header"));

        submit.setWidth("115px");
        submit.setMaxWidth("115px");
        submit.addClickListener(e -> {
            onSubmitButtonPress();
        });

        username.setRequired(true);
        email.setRequiredIndicatorVisible(true);

        add(
                header,
                username, email,
                password, confirmPassword,
                submit
        );

        setWidth("600px");
        setColspan(header, 2);
        setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("600px", 2)
        );
    }

    private void onSubmitButtonPress() {
        User user = binder.getBean();

        if (!username.isInvalid() && !email.isInvalid()
            && !password.isInvalid() && !confirmPassword.isInvalid()) {
            try {
                userRepository.registerUser(user);
                submit.getUI().ifPresent(
                        ui -> ui.navigate("/")
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
