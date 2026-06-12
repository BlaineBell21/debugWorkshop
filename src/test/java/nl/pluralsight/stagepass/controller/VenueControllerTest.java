package nl.pluralsight.stagepass.controller;

import nl.pluralsight.stagepass.model.Venue;
import nl.pluralsight.stagepass.service.VenueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VenueController.class)
class VenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VenueService venueService;

//    @Test
//    void getAllVenues() {
//    }
//
//    @Test
//    void getVenueById() {
//    }

    @Test
    void createVenue_shouldReturnCreatedVenue_andCorrectCode() throws Exception {
        // ARRANGE
        Venue testVenue = new Venue("Venue Name", "Venue City", 3);
        testVenue.setId(1L);

        when(venueService.createVenue(any(Venue.class)))
                .thenReturn(testVenue);

        // ACT

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                "id": 1,
                "name": "Venue Name",
                "city": "City Name",
                "capacity": 3
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Venue Name"))
                .andExpect(jsonPath("$.city").value("Venue City"))
                .andExpect(jsonPath("$.capacity").value(3));
    }

//    @Test
//    void updateVenue() {
//    }
//
//    @Test
//    void deleteVenue() {
//    }
}