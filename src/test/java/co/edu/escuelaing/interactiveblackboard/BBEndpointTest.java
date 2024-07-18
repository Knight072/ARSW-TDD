package co.edu.escuelaing.interactiveblackboard;

import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BBEndpointTest {

    private BBEndpoint bbEndpoint;
    private Session mockSession;

    @BeforeEach
    void setUp() {
        bbEndpoint = new BBEndpoint();
        mockSession = mock(Session.class);

        // Mock the RemoteEndpoint and its method
        RemoteEndpoint.Basic mockRemoteEndpoint = mock(RemoteEndpoint.Basic.class);
        when(mockSession.getBasicRemote()).thenReturn(mockRemoteEndpoint);
    }

    @Test
    void testOpenConnection() throws IOException {
        bbEndpoint.openConnection(mockSession);

        // Verify that the session was added to the queue
        assertEquals(3, BBEndpoint.queue.size());

        // Verify that a message was sent to the newly connected session
        verify(mockSession.getBasicRemote()).sendText("Connection established.");
    }

    @Test
    void testClosedConnection() {
        bbEndpoint.openConnection(mockSession);
        assertEquals(4, BBEndpoint.queue.size());

        bbEndpoint.closedConnection(mockSession);
        assertEquals(3, BBEndpoint.queue.size());
    }

    @Test
    void testProcessPoint() throws IOException {
        bbEndpoint.openConnection(mockSession);

        // Simulate another session
        Session anotherSession = mock(Session.class);
        RemoteEndpoint.Basic anotherRemoteEndpoint = mock(RemoteEndpoint.Basic.class);
        when(anotherSession.getBasicRemote()).thenReturn(anotherRemoteEndpoint);
        bbEndpoint.openConnection(anotherSession);

        // Process a point
        bbEndpoint.processPoint("Connection established.", mockSession);

        // Verify that the message was sent to the other session
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(anotherRemoteEndpoint).sendText(messageCaptor.capture());

        // Ensure the correct message was sent
        assertEquals("Connection established.", messageCaptor.getValue());
    }

    /*@Test
    void testSendMessageToAllClients() throws IOException {
        // Open multiple sessions
        RemoteEndpoint.Basic mockRemoteEndpoint = mock(RemoteEndpoint.Basic.class);
        when(mockSession.getBasicRemote()).thenReturn(mockRemoteEndpoint);

        Session anotherSession = mock(Session.class);
        RemoteEndpoint.Basic anotherRemoteEndpoint = mock(RemoteEndpoint.Basic.class);
        when(anotherSession.getBasicRemote()).thenReturn(anotherRemoteEndpoint);

        bbEndpoint.openConnection(mockSession);
        bbEndpoint.openConnection(anotherSession);

        // Send a message
        bbEndpoint.send("Hello, everyone!");

        // Verify that the other session received the message
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(anotherRemoteEndpoint).sendText(messageCaptor.capture());

        // Ensure the correct message was sent
        assertEquals("Connection established.", messageCaptor.getValue());

        // Ensure the original session did not receive the message
        verify(mockRemoteEndpoint, never()).sendText(anyString());
    }*/

    @Test
    void testErrorHandling() {
        bbEndpoint.openConnection(mockSession);
        bbEndpoint.error(mockSession, new Exception("Test error"));

        // Ensure the session was removed from the queue
        assertEquals(2, BBEndpoint.queue.size());
    }
}




