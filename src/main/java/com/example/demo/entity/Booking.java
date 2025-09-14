package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer movieId;

    private String movieTitle;
    private Integer quantity;
    private double totalPrice;
    private String userId;

    @ElementCollection
    @CollectionTable(name = "booked_seats", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "seat_number")
    private List<String> seatNumbers;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDate;

    public Booking() {
    }

    public Booking(Integer movieId, String movieTitle, Integer quantity, double totalPrice, String userId, List<String> seatNumbers, Date bookingDate) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.seatNumbers = seatNumbers;
        this.bookingDate = bookingDate;
    }

    // Getters
    public Integer getId() { return id; }
    public Integer getMovieId() { return movieId; }
    public String getMovieTitle() { return movieTitle; }
    public Integer getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public String getUserId() { return userId; }
    public List<String> getSeatNumbers() { return seatNumbers; }
    public Date getBookingDate() { return bookingDate; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
}
