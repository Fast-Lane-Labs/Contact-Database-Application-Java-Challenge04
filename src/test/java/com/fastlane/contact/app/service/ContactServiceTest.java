package com.fastlane.contact.app.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fastlane.contact.app.model.Contact;

class ContactTest {

    @Test
    void testGetId() {
        Contact contact = new Contact();
        contact.setId(1L);
        assertEquals(1L, (long) contact.getId());
    }

    @Test
    void testGetFirstName() {
        Contact contact = new Contact();
        contact.setFirstName("John");
        assertEquals("John", contact.getFirstName());
    }

    @Test
    void testGetLastName() {
        Contact contact = new Contact();
        contact.setLastName("Doe");
        assertEquals("Doe", contact.getLastName());
    }

    @Test
    void testGetEmail() {
        Contact contact = new Contact();
        contact.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", contact.getEmail());
    }

    @Test
    void testToString() {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setEmail("john.doe@example.com");
        String expected = "Contact{id=1, firstName='John', lastName='Doe', email='john.doe@example.com'}";
        assertEquals(expected, contact.toString());
    }
}