package by.yermak.eliblary.entity.user;

import by.yermak.eliblary.entity.builder.UserBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    public User() {
    }

    public User(UserBuilder builder) {
        id = builder.getId();
        login = builder.getLogin();
        password = builder.getPassword();
        firstName = builder.getFirstName();
        secondName = builder.getSecondName();
        email = builder.getEmail();
        role = builder.getRole();
        status = builder.getStatus();
        activationDate = builder.getActivationDate();
        deactivationDate = builder.getDeactivationDate();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDateTime getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) &&
               Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) &&
               Objects.equals(email, user.email) && role == user.role && status == user.status &&
               Objects.equals(activationDate, user.activationDate) && Objects.equals(deactivationDate, user.deactivationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, firstName, secondName, email, role, status, activationDate, deactivationDate);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass());
        builder.append(" id = ").append(id);
        builder.append(" login = ").append(login);
        builder.append(", password = ").append(password);
        builder.append(", firstName = ").append(firstName);
        builder.append(", secondName = ").append(secondName);
        builder.append(", email = ").append(email);
        builder.append(", status = ").append(status);
        builder.append(", activationDate = ").append(activationDate);
        builder.append(", deactivationDate = ").append(deactivationDate);
        return builder.toString();
    }
}
