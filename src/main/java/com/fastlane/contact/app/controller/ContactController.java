package com.fastlane.contact.app.controller;


import com.fastlane.contact.app.model.Contact;
import com.fastlane.contact.app.service.ContactService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listContacts", contactService.getAllContacts());
        return "index";
    }

    @GetMapping("/showNewContactForm")
    public String showNewContactForm(Model model) {
        Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "new_contact";
    }

    @PostMapping("/saveContact")
    public String saveContact(@ModelAttribute("contact") Contact contact) {
        contactService.saveContact(contact);
        return "redirect:/";
    }

    @GetMapping("/EditContact/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Contact contact = contactService.getContactById(id).orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));
        model.addAttribute("contact", contact);
        return "update_contact";
    }

    @GetMapping("/deleteContact/{id}")
    public String deleteContact(@PathVariable(value = "id") long id) {
        contactService.deleteContact(id);
        return "redirect:/";
    }


    @GetMapping("/bulkImport")
    public String bulkInsert() {
        return "bulk_new_contacts";
    }
    
    @GetMapping("/contacts/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contacts.csv");

            List<Contact> contacts = contactService.getAllContacts();

            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"ID", "First Name", "Last Name", "Email"};
            String[] nameMapping = {"id", "firstName", "lastName", "email"};

            csvWriter.writeHeader(csvHeader);

            for (Contact contact : contacts) {
                csvWriter.write(contact, nameMapping);
            }

            csvWriter.close();
        } catch (IOException e) {
            // handle exception
        }
    }


    @PostMapping("/contacts/import")
    public String importFromCSV(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            // handle empty file
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             ICsvBeanReader csvReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE)) {
            String[] nameMapping = {"firstName", "lastName", "email"};
            Contact contact;

            // Skip the first row
            csvReader.getHeader(true);

            while ((contact = csvReader.read(Contact.class, nameMapping)) != null) {
                contactService.saveContact(contact);
            }
        } catch (IOException e) {
            // handle exception
        }

        return "redirect:/";
    }
}
