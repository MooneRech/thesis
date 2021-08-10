package lv.animelistapp.model;

public class User {
    
    private long id;

    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    private String passwordEncrypted;

    public User() {

    }
    public User(User userSuper) {
        this.id = userSuper.getId();
        this.username = userSuper.getUsername();
        this.password  = userSuper.getPassword();
        this.email = userSuper.getEmail();
    }

    public String getPasswordEncrypted() {
        return passwordEncrypted;
    }

    public void setPasswordEncrypted(String passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
