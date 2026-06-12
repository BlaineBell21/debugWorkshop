package nl.pluralsight.stagepass.controller;

import nl.pluralsight.stagepass.model.Artist;
import nl.pluralsight.stagepass.model.Concert;
import nl.pluralsight.stagepass.model.Venue;
import nl.pluralsight.stagepass.service.BookingService;
import nl.pluralsight.stagepass.service.ConcertService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ConcertController.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConcertService concertService;

    @MockitoBean
    private BookingService bookingService;

//    @Test
//    void getAllConcerts() {
//    }
//
//    @Test
//    void getConcertById() {
//    }

    @Test
    void createConcert_shouldReturnCreatedConcert_andCorrectCode() throws Exception {
        // ARRANGE
        LocalDate date = LocalDate.now();
        BigDecimal ticketPrice = BigDecimal.valueOf(0.6);

        Venue testVenue = new Venue();
        testVenue.setId(1L);

        Artist testArtist = new Artist();
        testArtist.setId(1L);

        Concert testConcert = new Concert("Concert Title", date, testArtist, testVenue,
                3, 3, ticketPrice);
        testConcert.setId(1L);

        when(concertService.createConcert(any(Concert.class)))
                .thenReturn(testConcert);

        // ACT

        mockMvc.perform(MockMvcRequestBuilders.post("/api/concerts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                "concertTitle": "Concert Title",
                "concertDate": "2026-06-12",
                "artist": {
                    "id": 1
                  },
                "venue": {
                    "id": 1
                  },
                "totalSeats": 3,
                "availableSeats": 3,
                "ticketPrice": 0.6
                }
                """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Concert Title"))
                .andExpect(jsonPath("$.date").value("2026-06-12"))
                .andExpect(jsonPath("$.artist.id").value(1))
                .andExpect(jsonPath("$.venue.id").value(1))
                .andExpect(jsonPath("$.totalSeats").value(3))
                .andExpect(jsonPath("$.availableSeats").value(3))
                .andExpect(jsonPath("$.ticketPrice").value(ticketPrice));
                }

//    @Test
//    void updateConcert() {
//    }
//
//    @Test
//    void deleteConcert() {
//    }
}