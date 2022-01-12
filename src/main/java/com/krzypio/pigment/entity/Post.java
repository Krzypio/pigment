package com.krzypio.pigment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String description;

    @ManyToOne(fetch= FetchType.EAGER)
    private User user;

    private Date createDate;

    private Post() {
    }

    public Post(String description, User user) {
        this.description = description;
        this.user = user;
        this.createDate = new Date();
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", username=" + user.getUsername() +
                ", createDate=" + createDate +
                '}';
    }
}
