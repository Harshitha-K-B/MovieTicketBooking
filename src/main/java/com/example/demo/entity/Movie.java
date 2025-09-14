package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String title;
    
    private String director;
    
    private String description;
    
    private String genre;
    
    @Column(nullable = false)
    private LocalDate date;
    
    private String location;
    
    private Integer totalSeats;
    
    private Integer availableSeats;
    
    private double price;

    public Movie() {
    }

    public Movie(String title, String director, String description, String genre, LocalDate date, String location, Integer totalSeats, Integer availableSeats, double price) {
        this.title = title;
        this.director = director;
        this.description = description;
        this.genre = genre;
        this.date = date;
        this.location = location;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.price = price;
    }

    // Getters
    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public LocalDate getDate() { return date; }
    public String getLocation() { return location; }
    public Integer getTotalSeats() { return totalSeats; }
    public Integer getAvailableSeats() { return availableSeats; }
    public double getPrice() { return price; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDirector(String director) { this.director = director; }
    public void setDescription(String description) { this.description = description; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
    public void setPrice(double price) { this.price = price; }
}
