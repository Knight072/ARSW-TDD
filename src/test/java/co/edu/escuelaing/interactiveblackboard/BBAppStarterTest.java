package co.edu.escuelaing.interactiveblackboard;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BBAppStarterTest {

    @Test
    void testGetPortDefault() {
        // Test the default port value
        assertThat(BBAppStarter.getPort()).isEqualTo(8080);
    }

    @Test
    void testPortConfiguration() {
        System.setProperty("PORT", "8080");
        BBAppStarter.main(new String[0]);
        assertThat(BBAppStarter.getPort()).isEqualTo(8080);
        System.clearProperty("PORT");
    }
}


