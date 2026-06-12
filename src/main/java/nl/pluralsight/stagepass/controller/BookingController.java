package nl.pluralsight.stagepass.controller;

import jakarta.validation.Valid;
import nl.pluralsight.stagepass.model.Booking;
import nl.pluralsight.stagepass.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/concert/{concertId}")
    public ResponseEntity<List<Booking>> getBookingsByConcert(@PathVariable Long concertId) {
        return ResponseEntity.ok(bookingService.getBookingsByConcert(concertId));
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        // fixed error causing booking to return incorrect response code
        Booking created = bookingService.createBooking(booking);
        URI location = URI.create("/api/bookings" + created.getId());

        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        if (bookingService.cancelBooking(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
