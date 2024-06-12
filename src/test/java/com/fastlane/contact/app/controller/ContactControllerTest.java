package com.fastlane.contact.app.controller;

import com.fastlane.contact.app.model.Contact;
import com.fastlane.contact.app.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;


import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ContactControllerTest {

    @InjectMocks
    ContactController contactController;

    @Mock
    ContactService contactService;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void viewHomePage() {
        when(contactService.getAllContacts()).thenReturn(Arrays.asList(new Contact(), new Contact()));
        String view = contactController.viewHomePage(model);
        verify(model, times(1)).addAttribute(eq("listContacts"), anyList());
        assertEquals("index", view);
    }

    @Test
    void showNewContactForm() {
        String view = contactController.showNewContactForm(model);
        verify(model, times(1)).addAttribute(eq("contact"), any(Contact.class));
        assertEquals("new_contact", view);
    }

    @Test
    void saveContact() {
        Contact contact = new Contact();
        String view = contactController.saveContact(contact);
        verify(contactService, times(1)).saveContact(contact);
        assertEquals("redirect:/", view);
    }

    @Test
    void showFormForUpdate() {
        Contact contact = new Contact();
        when(contactService.getContactById(anyLong())).thenReturn(Optional.of(contact));
        String view = contactController.showFormForUpdate(1L, model);
        verify(model, times(1)).addAttribute("contact", contact);
        assertEquals("update_contact", view);
    }

    @Test
    void deleteContact() {
        String view = contactController.deleteContact(1L);
        verify(contactService, times(1)).deleteContact(1L);
        assertEquals("redirect:/", view);
    }
}