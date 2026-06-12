package nl.pluralsight.stagepass.controller;

import nl.pluralsight.stagepass.model.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.print.Book;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingController bookingController;

    @Test
    void getAllBookings() {
    }

    @Test
    void getBookingById() {
    }

    @Test
    void getBookingsByConcert() {
    }

    @Test
    void createBooking_shouldReturnCreatedBooking_andCorrectCode() {
        Booking bookingTest = new Booking("customer name", "customer email", "concert", "tickets", "price", "date");
    }

    @Test
    void cancelBooking() {
    }
}