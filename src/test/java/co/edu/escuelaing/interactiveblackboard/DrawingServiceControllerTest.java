package co.edu.escuelaing.interactiveblackboard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DrawingServiceController.class)
class DrawingServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser // Simula un usuario autenticado
    @Test
    void statusReturnsCorrectResponse() throws Exception {
        String expectedResponseStart = "{\"status\":\"Greetings from Spring Boot. ";

        mockMvc.perform(get("/status"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    assertThat(response).startsWith(expectedResponseStart);
                });
    }
}


