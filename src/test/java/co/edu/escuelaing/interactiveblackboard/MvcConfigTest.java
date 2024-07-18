package co.edu.escuelaing.interactiveblackboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(MvcConfig.class)
class MvcConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser // Simulates a logged-in user
    @Test
    void testHomeView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home.html"));
    }

    @WithMockUser // Simulates a logged-in user
    @Test
    void testHelloView() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("hello.html"));
    }

    /*@WithMockUser // Simulates a logged-in user
    @Test
    void testLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login.html"));
    }

    @Test
    void testStaticIndexView() throws Exception {
        mockMvc.perform(get("/static/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index.html"));
    }*/
}

