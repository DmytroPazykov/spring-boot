package com.astt.strt.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

import static java.util.Objects.isNull;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
public class User {

    private @Id
    @GeneratedValue
    Integer id;

    private String login;
    private String password;
    private String username;
    private Role role;
    private Boolean isNotBusy;

    public User(String login, String password, String username, Role role) {
        this.login = login;
        this.password = password;
        this.username = username;
        this.role = role;
        this.isNotBusy = Boolean.TRUE;
    }

    @Override
    public boolean equals(Object toCompareWith) {
        if (this == toCompareWith) {
            return true;
        }
        if (isNull(toCompareWith) || getClass() != toCompareWith.getClass()) {
            return false;
        }
        User user = (User) toCompareWith;
        return Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(username, user.username)
                && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, username, role);
    }
}
