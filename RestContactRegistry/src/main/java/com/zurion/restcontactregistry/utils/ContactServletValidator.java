package com.zurion.restcontactregistry.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zurion.restcontactregistry.requests.ContactRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author stanl
 */
public class ContactServletValidator {
    private static final Gson GSON = new Gson();
    
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]{3,50}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+2547[0-9]{8}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern ID_PATTERN = Pattern.compile("^[0-9]{6,10}$");
    
    
    public static String validateContactRequest(HttpServletRequest request) throws IOException {
        ContactRequest contactRequest = parseRequest(request);

        if (contactRequest == null) {
            return "{\"error\": \"Invalid JSON format.\"}";
        }

        StringBuilder errors = new StringBuilder("{");

        if (!isValidFullName(contactRequest.fullName)) {
            errors.append("\"fullName\": \"Invalid full name.\", ");
        }
        if (!isValidPhoneNumber(contactRequest.phoneNumber)) {
            errors.append("\"phoneNumber\": \"Invalid phone number.\", ");
        }
        if (!isValidEmail(contactRequest.email)) {
            errors.append("\"email\": \"Invalid email format.\", ");
        }
        if (!isValidIdNumber(contactRequest.idNumber)) {
            errors.append("\"idNumber\": \"Invalid ID number.\", ");
        }
        if (!isValidDateOfBirth(contactRequest.dateOfBirth)) {
            errors.append("\"dateOfBirth\": \"Invalid date format (yyyy-MM-dd).\", ");
        }
        if (!isValidGender(contactRequest.gender)) {
            errors.append("\"gender\": \"Gender must be Male or Female.\", ");
        }
        if (!isValidOrganization(contactRequest.organization)) {
            errors.append("\"organization\": \"Organization cannot be empty.\", ");
        }

        // Remove trailing comma and close JSON object if errors exist
        if (errors.length() > 1) {
            errors.delete(errors.length() - 2, errors.length()); 
            errors.append("}");
            return errors.toString();
        }
        
        // No validation errors
        return null; 
    }

    private static ContactRequest parseRequest(HttpServletRequest request) {
        StringBuilder jsonBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
            return GSON.fromJson(jsonBody.toString(), ContactRequest.class);
        } catch (IOException | JsonSyntaxException e) {
            return null;
        }
    }

    public static boolean isValidFullName(String fullName) {
        return fullName != null && NAME_PATTERN.matcher(fullName).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidIdNumber(String idNumber) {
        return idNumber != null && ID_PATTERN.matcher(idNumber).matches();
    }

    public static boolean isValidDateOfBirth(String dateOfBirth) {
        try {
            LocalDate.parse(dateOfBirth);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidGender(String gender) {
        return "Male".equalsIgnoreCase(gender) || "Female".equalsIgnoreCase(gender);
    }

    public static boolean isValidOrganization(String organization) {
        return organization != null && !organization.trim().isEmpty();
    }
}
