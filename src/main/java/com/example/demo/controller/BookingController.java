package com.example.demo.controller;

import com.example.demo.BookingHistory;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Movie;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovieRepository movieRepository;

    // POST: Book tickets
    @PostMapping("/booking/{movieId}")
    public Booking bookTickets(
            @PathVariable Integer movieId,
            @RequestBody Booking bookingRequest
    ) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (bookingRequest.getQuantity() > movie.getAvailableSeats()) {
            throw new RuntimeException("Not enough seats available");
        }

        movie.setAvailableSeats(movie.getAvailableSeats() - bookingRequest.getQuantity());
        movieRepository.save(movie);

        Booking booking = new Booking();
        booking.setMovieId(movieId);
        booking.setMovieTitle(movie.getTitle());
        booking.setQuantity(bookingRequest.getQuantity());
        booking.setTotalPrice(bookingRequest.getTotalPrice());
        booking.setUserId(bookingRequest.getUserId());
        booking.setBookingDate(new Date());

        return bookingRepository.save(booking);
    }

    // GET: Booking history
    @GetMapping("/bookings/history/{userId}")
    public List<BookingHistory> getBookingHistory(@PathVariable String userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream().map(b -> {
            Movie movie = movieRepository.findById(b.getMovieId())
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            return new BookingHistory(
                    b.getMovieId(),
                    b.getMovieTitle(),
                    b.getQuantity(),
                    b.getTotalPrice(),
                    b.getBookingDate(),
                    movie.getLocation(),
                    movie.getDate() // LocalDate
            );
        }).toList();
    }
}
