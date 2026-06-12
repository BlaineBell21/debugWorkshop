package nl.pluralsight.stagepass.controller;

import nl.pluralsight.stagepass.model.Booking;
import nl.pluralsight.stagepass.model.Concert;
import nl.pluralsight.stagepass.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

//    @Test
//    void getAllBookings() {
//    }
//
//    @Test
//    void getBookingById() {
//    }
//
//    @Test
//    void getBookingsByConcert() {
//    }

    @Test
    void createBooking_shouldReturnCreatedBooking_andCorrectCode() throws Exception {
        //ARRANGE
        BigDecimal ticketPrice = BigDecimal.valueOf(0.6);
        LocalDate date = LocalDate.now();

        Concert concertTest = new Concert(); // fake concert object to put in booking object
        concertTest.setId(1L);
        concertTest.setTicketPrice(ticketPrice);
        concertTest.setDate(date);

        Booking bookingTest = new Booking(
                "customer name",
                "customer email",
                concertTest ,
                4,
                ticketPrice,
                date);
        bookingTest.setId(1L);
        when(bookingService.createBooking(any(Booking.class)))
                .thenReturn(bookingTest);

        //ACT
        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                {
                "customerName": "Customer Name",
                "customerEmail": "Customer Email",
                "concert": {
                            "id": 1
                             },
                "numberOfTickets": 4,
                "ticketPrice": 0.6,
                "date": "2026-06-12"
                }
                """
                ))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // should return code 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("customer name"))
                .andExpect(jsonPath("$.customerEmail").value("customer email"))
                .andExpect(jsonPath("$.concert.id").value(1))
                .andExpect(jsonPath("$.numberOfTickets").value(4))
                .andExpect(jsonPath("$.concert.ticketPrice").value(ticketPrice))
                .andExpect(jsonPath("$.concert.date").value("2026-06-12"));
    }

//    @Test
//    void cancelBooking() {
//    }
}