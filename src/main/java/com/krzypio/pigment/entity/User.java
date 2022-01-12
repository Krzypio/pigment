package com.krzypio.pigment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String username;

    @Size(min = 4, message = "Password should have at least 4 characters")
    private String password;

    private boolean active;

    private String roles;

    private Date creationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

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

    public List<Post> getPosts() {
        return posts;
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
