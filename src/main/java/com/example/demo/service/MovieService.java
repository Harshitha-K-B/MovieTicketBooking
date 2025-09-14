package com.example.demo.service;

import com.example.demo.BookingHistory;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Movie;
import com.example.demo.entity.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.NewMovieRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * This method adds a user to the database if they do not already exist.
     * This ensures that all users who interact with the booking service are
     * persisted in our local database, linked by their Firebase UID.
     * @param firebaseUid The unique ID from Firebase Authentication.
     * @param email The user's email from Firebase.
     * @return The existing or newly created User entity.
     */
    private User createOrGetUser(String firebaseUid, String email) {
        User user = userRepository.findByFirebaseUid(firebaseUid);
        if (user == null) {
            user = new User(firebaseUid, email);
            userRepository.save(user);
        }
        return user;
    }

    public List<Movie> getAllMovies(String title, LocalDate date, String location, String genre) {
        List<Movie> movies = movieRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return movies.stream()
            .filter(movie -> title == null || movie.getTitle().equalsIgnoreCase(title))
            .filter(movie -> date == null || movie.getDate().isEqual(date))
            .filter(movie -> location == null || movie.getLocation().equalsIgnoreCase(location))
            .filter(movie -> genre == null || movie.getGenre().equalsIgnoreCase(genre))
            .collect(Collectors.toList());
    }

    public Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findById(id);
    }

    public void addMovie(NewMovieRequest request) {
        Movie movie = new Movie();
        BeanUtils.copyProperties(request, movie);
        movie.setAvailableSeats(request.getTotalSeats()); // Initially, all seats are available
        movieRepository.save(movie);
    }

    public void bookTickets(Integer movieId, Integer quantity, Double totalPrice, String userId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isEmpty()) {
            throw new RuntimeException("Movie not found");
        }
        Movie movie = movieOptional.get();
        if (movie.getAvailableSeats() < quantity) {
            throw new RuntimeException("Not enough tickets available");
        }
        
        movie.setAvailableSeats(movie.getAvailableSeats() - quantity);
        movieRepository.save(movie);
        
        Booking booking = new Booking(
            movieId,
            movie.getTitle(),
            quantity,
            totalPrice,
            userId,
            new ArrayList<>(), // This should be updated to contain seat numbers
            new Date()
        );
        bookingRepository.save(booking);
    }

    public void deleteMovie(Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found");
        }
        movieRepository.deleteById(id);
    }

    public void updateMovie(Integer id, NewMovieRequest request) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()) {
            throw new RuntimeException("Movie not found");
        }
        Movie existingMovie = movieOptional.get();
        BeanUtils.copyProperties(request, existingMovie, "id");
        existingMovie.setAvailableSeats(request.getTotalSeats());
        movieRepository.save(existingMovie);
    }
    
    public List<BookingHistory> getBookingHistory(String userId) {
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
                movie.getDate()
            );
        }).collect(Collectors.toList());
    }



}
