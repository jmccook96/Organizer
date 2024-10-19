package com.example.service;

import com.bookclub.mao.RSVPMAO;
import com.bookclub.mao.UserMAO;
import com.bookclub.model.Event;
import com.bookclub.model.RSVP;
import com.bookclub.model.User;
import com.bookclub.service.RSVPService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RSVPServiceTest {

    private UserMAO userMAO;
    private RSVPMAO rsvpMAO;
    private Event event;

    @BeforeEach
    public void setUp() {
        userMAO = new UserMAO();
        rsvpMAO = new RSVPMAO();
        event = new Event(1, 1, "testBook", "testUser", LocalDateTime.of(2024, 10, 2, 1, 30), "testLocation");

        RSVPService.initialize(rsvpMAO, userMAO);
    }

    @Test
    void testGetEventRSVPUsernames_NoRSVP() {
        List<String> expected = new ArrayList<>();
        List<String> actual = RSVPService.getInstance().getEventRSVPUsernames(event);

        assertEquals(expected, actual);
    }

    @Test
    void testGetEventRSVPUsernames_OneRSVP() {
        userMAO.addUser(new User(1, "testUser1", "testPassword", "Test1 Name1", "test1@example.com"));
        rsvpMAO.addRSVP(new RSVP(event.getId(), 1, RSVP.RSVPStatus.ACCEPTED));

        List<String> expected = List.of("testUser1");
        List<String> actual = RSVPService.getInstance().getEventRSVPUsernames(event);

        assertEquals(expected, actual);
    }

    @Test
    void testGetEventRSVPUsernames_EveryUserRSVP() {
        userMAO.addUser(new User(1, "testUser1", "testPassword", "Test1 Name1", "test1@example.com"));
        rsvpMAO.addRSVP(new RSVP(event.getId(), 1, RSVP.RSVPStatus.ACCEPTED));
        userMAO.addUser(new User(2, "testUser2", "testPassword", "Test2 Name2", "test2@example.com"));
        rsvpMAO.addRSVP(new RSVP(event.getId(), 2, RSVP.RSVPStatus.MAYBE));
        userMAO.addUser(new User(3, "testUser3", "testPassword", "Test3 Name3", "test3@example.com"));
        rsvpMAO.addRSVP(new RSVP(event.getId(), 3, RSVP.RSVPStatus.DECLINED));
        userMAO.addUser(new User(4, "testUser4", "testPassword", "Test4 Name4", "test4@example.com"));
        rsvpMAO.addRSVP(new RSVP(event.getId(), 4, RSVP.RSVPStatus.PENDING));

        List<String> expected = List.of("testUser1", "testUser2", "testUser3", "testUser4");
        List<String> actual = RSVPService.getInstance().getEventRSVPUsernames(event);

        assertEquals(expected, actual);
    }

    @Test
    void testGetEventRSVPUsernames_SomeUsersRSVP() {
        userMAO.addUser(new User(1, "testUser1", "testPassword", "Test1 Name1", "test1@example.com"));
        rsvpMAO.addRSVP(new RSVP(event.getId(), 1, RSVP.RSVPStatus.ACCEPTED));
        userMAO.addUser(new User(2, "testUser2", "testPassword", "Test2 Name2", "test2@example.com"));
        userMAO.addUser(new User(3, "testUser3", "testPassword", "Test3 Name3", "test3@example.com"));
        rsvpMAO.addRSVP(new RSVP(event.getId(), 3, RSVP.RSVPStatus.MAYBE));
        userMAO.addUser(new User(4, "testUser4", "testPassword", "Test4 Name4", "test4@example.com"));

        List<String> expected = List.of("testUser1", "testUser3");
        List<String> actual = RSVPService.getInstance().getEventRSVPUsernames(event);

        assertEquals(expected, actual);
    }

    @Test
    void testGetRSVP_RSVPExists() {
        User user = new User(1, "testUser1", "testPassword", "Test1 Name1", "test1@example.com");

        rsvpMAO.addRSVP(new RSVP(event.getId(), user.getId(), RSVP.RSVPStatus.ACCEPTED));

        RSVP actual = RSVPService.getInstance().getRSVP(event, user);

        assertNotNull(actual);
        assertEquals(1, actual.getEventId());
        assertEquals(1, actual.getUserId());
        assertEquals(RSVP.RSVPStatus.ACCEPTED, actual.getStatus());
    }

    @Test
    void testGetRSVP_RSVPDoesNotExist() {
        User user = new User(1, "testUser1", "testPassword", "Test1 Name1", "test1@example.com");

        RSVP actual = RSVPService.getInstance().getRSVP(event, user);

        assertNull(actual);
    }

    @Test
    void testSaveRSVP_RSVPExists() {
        User user = new User(1, "testUser1", "testPassword", "Test1 Name1", "test1@example.com");

        RSVP actual = new RSVP(event.getId(), user.getId(), RSVP.RSVPStatus.ACCEPTED);
        rsvpMAO.addRSVP(actual);
        RSVPService.getInstance().saveRSVP(event, user, RSVP.RSVPStatus.DECLINED);

        assertEquals(1, actual.getEventId());
        assertEquals(1, actual.getUserId());
        assertEquals(RSVP.RSVPStatus.DECLINED, actual.getStatus());
    }

    @Test
    void testSaveRSVP_RSVPDoesNotExist() {
        User user = new User(1, "testUser1", "testPassword", "Test1 Name1", "test1@example.com");

        RSVPService.getInstance().saveRSVP(event, user, RSVP.RSVPStatus.MAYBE);

        RSVP actual = RSVPService.getInstance().getRSVP(event, user);

        assertNotNull(actual);
        assertEquals(1, actual.getEventId());
        assertEquals(1, actual.getUserId());
        assertEquals(RSVP.RSVPStatus.MAYBE, actual.getStatus());
    }
}
