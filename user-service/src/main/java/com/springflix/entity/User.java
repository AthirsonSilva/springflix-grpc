package com.springflix.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private Integer id;
    private String login;
    private String name;
    private String genre;
}
