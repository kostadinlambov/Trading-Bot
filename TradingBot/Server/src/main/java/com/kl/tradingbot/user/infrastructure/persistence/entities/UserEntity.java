package com.kl.tradingbot.user.infrastructure.persistence.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.core.style.ToStringCreator;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePicUrl;
    private Set<UserRoleEntity> authorities;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public UserEntity() {
        this.authorities = new HashSet<>();
    }

    @Override
    @Column(nullable = false, name = "username", unique = true, columnDefinition = "VARCHAR(50) BINARY")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false, name = "email", unique = true, columnDefinition = "VARCHAR(50) BINARY")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    @Column(nullable = false, name = "password")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "profile_pic_url", columnDefinition = "TEXT")
    public String getProfilePicUrl() {
        return this.profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    @Override
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public Set<UserRoleEntity> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<UserRoleEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    @Column(name = "is_account_non_expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    public boolean isAccountNonExpired() {
        return true;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    @Column(name = "is_account_non_locked", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    public boolean isAccountNonLocked() {
        return true;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    @Column(name = "is_credentials_non_expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Override
    @Column(name = "is_enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    public boolean isEnabled() {
        return true;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return username.equals(that.username) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("username", username)
            .append("email", email)
            .append("password", password)
            .append("firstName", firstName)
            .append("lastName", lastName)
            .append("isAccountNonExpired", isAccountNonExpired)
            .append("isAccountNonLocked", isAccountNonLocked)
            .append("isCredentialsNonExpired", isCredentialsNonExpired)
            .append("isEnabled", isEnabled)
            .toString();
    }
}
