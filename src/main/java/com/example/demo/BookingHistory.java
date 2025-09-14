package com.example.demo;

import java.time.LocalDate;
import java.util.Date;

public class BookingHistory {
    private Integer movieId;
    private String movieTitle;
    private Integer quantity;
    private Double totalPrice;
    private Date bookingDate;
    private String location;
    private LocalDate movieDate;

    public BookingHistory(Integer movieId, String movieTitle, Integer quantity, Double totalPrice,
                          Date bookingDate, String location, LocalDate movieDate) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.location = location;
        this.movieDate = movieDate;
    }

    // Getters
    public Integer getMovieId() { return movieId; }
    public String getMovieTitle() { return movieTitle; }
    public Integer getQuantity() { return quantity; }
    public Double getTotalPrice() { return totalPrice; }
    public Date getBookingDate() { return bookingDate; }
    public String getLocation() { return location; }
    public LocalDate getMovieDate() { return movieDate; }

    // Setters
    public void setMovieId(Integer movieId) { this.movieId = movieId; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public void setLocation(String location) { this.location = location; }
    public void setMovieDate(LocalDate movieDate) { this.movieDate = movieDate; }
}
