package com.zurion.restcontactregistry.models;

/**
 *
 * @author stanl
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Contact {
    private int id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String dateOfBirth;
    private String gender;
    private String organization;
    private String maskedName;
    private String maskedPhone;
    private String hashedPhone;

    public Contact() {}

    public Contact(String fullName, String phoneNumber, String email, String idNumber, String dateOfBirth, String gender, String organization) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.organization = organization;
        this.maskedName = maskName(fullName);
        this.maskedPhone = maskPhone(phoneNumber);
        this.hashedPhone = hashPhone(phoneNumber);
    }
    
    // Getters and Setters
    public int getId() { return id; }
    
    public void setId(int id) { this.id = id; }
    
    public String getFullName() { return fullName; }
    
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber;
        this.maskedPhone = maskPhone(phoneNumber);
        this.hashedPhone = hashPhone(phoneNumber);
    }

    public String getEmail() { return email; }
    
    public void setEmail(String email) { this.email = email; }

    public String getIdNumber() { return idNumber; }
    
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public String getDateOfBirth() { return dateOfBirth; }
    
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    
    public void setGender(String gender) { this.gender = gender; }

    public String getOrganization() { return organization; }
    
    public void setOrganization(String organization) { this.organization = organization; }

    public String getMaskedName() { return maskedName; }
    
    private String maskName(String name) {
        String[] parts = name.split(" ");
        return parts[0] + " " + (parts.length > 1 ? parts[1].charAt(0) + "." : "");
    }
    
    public String getMaskedPhone() { return maskedPhone; }
    
    public String maskPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return "N/A"; // Or any default value
        }

        int length = phone.length();
        return length > 4 ? "****" + phone.substring(length - 4) : "****";
    }
    
    public String getHashedPhone() { return hashedPhone; }
    
    private String hashPhone(String phone) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(phone.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }  

}

