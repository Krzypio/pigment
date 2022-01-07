package com.krzypio.springsecuritybasic.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    private boolean active;

    private String roles;

    private Date creationDate;

    protected User() {
    }

    public User(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.active = true;
        this.creationDate = new Date();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles='" + roles + '\'' +
                ", creationDate=" + formatter.format(creationDate) +
                '}';
    }
}
