package com.springflix.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private String login;
    private String name;
    private String genre;
}
