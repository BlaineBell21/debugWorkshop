package nl.pluralsight.stagepass.controller;

import nl.pluralsight.stagepass.model.Artist;
import nl.pluralsight.stagepass.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ArtistController.class)
class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArtistService artistService;

//    @Test
//    void getAllArtists() {
//    }
//
//    @Test
//    void getArtistById() {
//    }

    @Test
    void createArtist() throws Exception {
        // ARRANGE
        Artist testArtist = new Artist("Artist Name", "Artist Genre", "Artist Bio");
        testArtist.setId(1L);

        when(artistService.createArtist(any(Artist.class)))
                .thenReturn(testArtist);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/artists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                {
                "id": 1,
                "artistName": "Artist Name",
                "artistGenre": "Artist Genre",
                "artistBio": "Artist Bio"
                }
                """
                ))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Artist Name"))
                .andExpect(jsonPath("$.genre").value("Artist Genre"))
                .andExpect(jsonPath("$.bio").value("Artist Bio"));

    }

//    @Test
//    void updateArtist() {
//    }
//
//    @Test
//    void deleteArtist() {
//    }
}