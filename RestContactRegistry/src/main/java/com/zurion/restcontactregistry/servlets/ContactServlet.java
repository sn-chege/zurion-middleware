package com.zurion.restcontactregistry.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.Gson;
import com.zurion.restcontactregistry.daos.ContactDAO;
import com.zurion.restcontactregistry.models.Contact;
import com.zurion.restcontactregistry.utils.ContactServletValidator;

import javax.servlet.ServletException;


@WebServlet("/contacts/*")
public class ContactServlet extends HttpServlet {

    private final ContactDAO contactDAO = new ContactDAO();
    private final Gson gson = new Gson();

    // Handle GET requests (fetch all contacts or a single contact)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Contact> contacts = contactDAO.getAllContacts();
            out.write(gson.toJson(contacts));
        } else {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Contact contact = contactDAO.getContactById(id);
                if (contact != null) {
                    out.write(gson.toJson(contact));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{\"message\": \"Contact not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"message\": \"Invalid contact ID\"}");
            }
        }
    }

    // Handle POST requests (add a contact)
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        // Validate request
        String errors = ContactServletValidator.validateContactRequest(request);

        if (errors != null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(errors);
            return;
        }

        // If valid, process request
        response.getWriter().write("{\"message\": \"Contact saved successfully.\"}");
    }

    // Handle PUT requests (update a contact)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = request.getReader();
        Contact contact = gson.fromJson(reader, Contact.class);

        boolean success = contactDAO.updateContact(contact);
        response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("{\"message\": \"" + (success ? "Contact updated successfully" : "Failed to update contact") + "\"}");
    }

    // Handle DELETE requests (delete a contact)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();

        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                boolean success = contactDAO.deleteContact(id);
                response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_NOT_FOUND);
                out.write("{\"message\": \"" + (success ? "Contact deleted successfully" : "Contact not found") + "\"}");
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"message\": \"Invalid contact ID\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"message\": \"Contact ID is required\"}");
        }
    }
}
