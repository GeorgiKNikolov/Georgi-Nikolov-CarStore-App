package app.carstore.model.dto;

import app.carstore.model.validators.FieldMatch;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class ForgottenPasswordDTO {

    private String email;
    private String givenPassword;
    private String password;
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public ForgottenPasswordDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGivenPassword() {
        return givenPassword;
    }

    public ForgottenPasswordDTO setGivenPassword(String givenPassword) {
        this.givenPassword = givenPassword;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ForgottenPasswordDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public ForgottenPasswordDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
