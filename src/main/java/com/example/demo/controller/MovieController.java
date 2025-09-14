package com.example.demo.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.BookingHistory;
import com.example.demo.entity.Movie;
import com.example.demo.request.NewMovieRequest;
import com.example.demo.service.MovieService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movie")
@CrossOrigin // Add this line to enable CORS for the entire controller
public class MovieController {

    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String genre
    ) {
        return movieService.getAllMovies(title, date, location, genre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Integer id) {
        return movieService.getMovieById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/booking/history/{userId}")
    public List<BookingHistory> getBookingHistory(@PathVariable("userId") String userId){
        return movieService.getBookingHistory(userId);
    }

    @PostMapping
    public void addMovie(@RequestBody NewMovieRequest movie) {
        movieService.addMovie(movie);
    }

    @PostMapping("/booking/{movieId}/{quantity}/{totalPrice}/{userId}")
    public void createBooking(
        @PathVariable("movieId") Integer movieId,
        @PathVariable("quantity") Integer quantity,
        @PathVariable("totalPrice") Double totalPrice,
        @PathVariable("userId") String userId
    ) {
        movieService.bookTickets(movieId, quantity, totalPrice, userId);
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable("movieId") Integer id) {
        movieService.deleteMovie(id);
    }

    @PutMapping("/{movieId}")
    public void updateMovie(@PathVariable("movieId") Integer id, @RequestBody NewMovieRequest movie) {
        movieService.updateMovie(id, movie);
    }
}
