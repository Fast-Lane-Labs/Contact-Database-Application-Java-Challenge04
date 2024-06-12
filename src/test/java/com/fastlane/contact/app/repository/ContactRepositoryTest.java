package com.fastlane.contact.app.repository;

import com.fastlane.contact.app.model.Contact;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void getContactByIdTest() {
        // Retrieve a contact from the database using its ID
        Optional<Contact> foundContact = contactRepository.findById(1L); // Assuming ID 1 exists in your data.sql
        assertTrue(foundContact.isPresent()); // Check if contact is found
        assertEquals("John", foundContact.get().getFirstName()); // Check first name of the contact
        assertEquals("Doe", foundContact.get().getLastName()); // Check last name of the contact
        assertEquals("john.doe@example.com", foundContact.get().getEmail()); // Check email of the contact
    }

    @Test
    public void deleteContactTest() {
        // Delete a contact from the database using its ID
        contactRepository.deleteById(1L); // Assuming ID 1 exists in your data.sql
        // Attempt to retrieve the deleted contact from the database
        Optional<Contact> deletedContact = contactRepository.findById(1L); // Assuming ID 1 exists in your data.sql
        assertTrue(deletedContact.isEmpty()); // Check if the deleted contact is not found in the database
    }

    @PersistenceContext
    private EntityManager entityManager;

    @AfterAll
    public void tearDown() {
        // Clean up the database after all tests
        contactRepository.deleteAll();
        
    }

}
