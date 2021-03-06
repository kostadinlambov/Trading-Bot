package com.kl.tradingbot.user.infrastructure.persistence.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.core.style.ToStringCreator;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity implements GrantedAuthority {

    private String authority;

    public UserRoleEntity() {
    }

    @Column(name = "authority", nullable = false)
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        UserRoleEntity that = (UserRoleEntity) o;
        return authority.equals(that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), authority);
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("authority", authority)
            .toString();
    }
}
