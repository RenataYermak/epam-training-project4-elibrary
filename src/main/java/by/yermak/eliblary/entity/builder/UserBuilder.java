package by.yermak.eliblary.entity.builder;

import by.yermak.eliblary.entity.user.Role;
import by.yermak.eliblary.entity.user.Status;
import by.yermak.eliblary.entity.user.User;

import java.time.LocalDateTime;

public class UserBuilder {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private String email;
    private Role role;
    private Status status;
    private LocalDateTime activationDate;
    private LocalDateTime deactivationDate;

    public User build() {
        return new User(this);
    }

    public Long getId() {
        return id;
    }

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSecondName() {
        return secondName;
    }

    public UserBuilder setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public UserBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public UserBuilder setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
        return this;
    }

    public LocalDateTime getDeactivationDate() {
        return deactivationDate;
    }

    public UserBuilder setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
        return this;
    }
}
