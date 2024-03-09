package app.carstore.model.view;

public class UserDisplayView {

    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private String imageUrl;

    public Long getId() {
        return id;
    }

    public UserDisplayView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDisplayView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDisplayView setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDisplayView setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDisplayView setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserDisplayView setActive(boolean active) {
        isActive = active;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserDisplayView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
