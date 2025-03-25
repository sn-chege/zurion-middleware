package com.zurion.soapcontactregistry.models;

/**
 *
 * @author stanl
 */
import java.io.Serializable;

public class Contact implements Serializable {
    // Properties
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
    
    // Constructors
    public Contact() {}
    
    public Contact(int id, String fullName, String phoneNumber, String email, 
                   String idNumber, String dateOfBirth, String gender, String organization) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.organization = organization;
    }

    public Contact(int id, String fullName, String phoneNumber, String email, 
                   String idNumber, String dateOfBirth, String gender, String organization,
                   String maskedName, String maskedPhone, String hashedPhone) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.organization = organization;
        this.maskedName = maskedName;
        this.maskedPhone = maskedPhone;
        this.hashedPhone = hashedPhone;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getMaskedName() {
        return maskedName;
    }

    public void setMaskedName(String maskedName) {
        this.maskedName = maskedName;
    }

    public String getMaskedPhone() {
        return maskedPhone;
    }

    public void setMaskedPhone(String maskedPhone) {
        this.maskedPhone = maskedPhone;
    }

    public String getHashedPhone() {
        return hashedPhone;
    }

    public void setHashedPhone(String hashedPhone) {
        this.hashedPhone = hashedPhone;
    }      

}
