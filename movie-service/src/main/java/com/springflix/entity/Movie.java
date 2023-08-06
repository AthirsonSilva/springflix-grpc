package com.springflix.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
@Data
public class Movie {
    @Id
    private Integer id;
    private String title;
    @Column(name = "release_year")
    private Integer year;
    private Double rating;
    private String genre;
}
