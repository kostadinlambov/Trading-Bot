package com.kl.tradingbot.user.infrastructure.common.model;

import java.util.Objects;
import org.springframework.core.style.ToStringCreator;

public class UserCreateViewModel {

    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    public UserCreateViewModel() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCreateViewModel that = (UserCreateViewModel) o;
        return id.equals(that.id) && username.equals(that.username) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("id", id)
            .append("username", username)
            .append("email", email)
            .append("firstName", firstName)
            .append("lastName", lastName)
            .toString();
    }
}
