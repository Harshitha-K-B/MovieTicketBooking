package com.example.demo.request;

import java.time.LocalDate;

public class NewMovieRequest {
    private String title;
    private String director;
    private String description;
    private String genre;
    private LocalDate date;
    private String location;
    private Integer totalSeats;
    private double price;

    // Getters
    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public LocalDate getDate() { return date; }
    public String getLocation() { return location; }
    public Integer getTotalSeats() { return totalSeats; }
    public double getPrice() { return price; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setDirector(String director) { this.director = director; }
    public void setDescription(String description) { this.description = description; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public void setPrice(double price) { this.price = price; }
}
