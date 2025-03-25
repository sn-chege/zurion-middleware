package com.zurion.restcontactregistry.daos;

/**
 *
 * @author stanl
 */
import com.zurion.restcontactregistry.models.Contact;
import com.zurion.restcontactregistry.utils.DatabaseConnection;
import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ContactDAO {
    
    private Connection conn = DatabaseConnection.getConnection();

    public boolean addContact(Contact contact) {
        String query = "INSERT INTO contacts (full_name, phone_number, email, id_number, date_of_birth, gender, organization, masked_name, masked_phone, hashed_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contact.getFullName());
            stmt.setString(2, contact.getPhoneNumber());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getIdNumber());
            stmt.setString(5, contact.getDateOfBirth());
            stmt.setString(6, contact.getGender());
            stmt.setString(7, contact.getOrganization());
            stmt.setString(8, maskName(contact.getFullName()));
            stmt.setString(9, maskPhoneNumber(contact.getPhoneNumber()));
            stmt.setString(10, hashPhoneNumber(contact.getPhoneNumber()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Contact contact = new Contact(
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("id_number"),
                    rs.getString("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("organization")
                );
                contact.setId(rs.getInt("id"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public Contact getContactById(int id) {
        String query = "SELECT * FROM contacts WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Contact contact = new Contact(
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("date_of_birth"),
                        rs.getString("gender"),
                        rs.getString("organization")
                    );
                    contact.setId(rs.getInt("id"));
                    return contact;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Contact> searchContacts(String hashedPhone, String maskedName, String maskedPhone, String organization) {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts WHERE hashed_phone = ? OR (masked_name = ? AND masked_phone = ?) OR organization = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hashedPhone != null ? hashedPhone : ""); 
            stmt.setString(2, maskedName != null ? maskedName : "");
            stmt.setString(3, maskedPhone != null ? maskedPhone : "");
            stmt.setString(4, organization != null ? organization : "");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact(
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("id_number"),
                    rs.getString("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("organization")
                );
                contact.setId(rs.getInt("id"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public boolean updateContact(Contact contact) {
        String query = "UPDATE contacts SET full_name=?, phone_number=?, email=?, id_number=?, date_of_birth=?, gender=?, organization=?, masked_name=?, masked_phone=?, hashed_phone=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contact.getFullName());
            stmt.setString(2, contact.getPhoneNumber());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getIdNumber());
            stmt.setString(5, contact.getDateOfBirth());
            stmt.setString(6, contact.getGender());
            stmt.setString(7, contact.getOrganization());
            stmt.setString(8, contact.getMaskedName());
            stmt.setString(9, contact.getMaskedPhone());
            stmt.setString(10, contact.getHashedPhone());
            stmt.setInt(11, contact.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteContact(int id) {
        String query = "DELETE FROM contacts WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
     // Function to hash phone numbers using SHA-256
    private String hashPhoneNumber(String phoneNumber) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(phoneNumber.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Function to mask phone number (e.g., 0712345678 → 07*****678)
    private String maskPhoneNumber(String phoneNumber) {
        // Remove the leading +
        if (phoneNumber.startsWith("+")) {
            phoneNumber = phoneNumber.substring(1); 
        }

        if (phoneNumber.length() >= 7) {
            return phoneNumber.substring(0, 2) + "*****" + phoneNumber.substring(phoneNumber.length() - 3);
        }
        // Return as is if too short
        return phoneNumber; 
    }
    
    // Function to mask full name (e.g., John Doe → J***-D**)
    private String maskName(String fullName) {
        String[] parts = fullName.split(" ");
        StringBuilder maskedName = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() > 1) {
                maskedName.append(parts[i].charAt(0)).append("*".repeat(parts[i].length() - 1));
            } else {
                maskedName.append(parts[i]);
            }
            if (i < parts.length - 1) {
                maskedName.append("-"); // Use hyphen instead of space
            }
        }
        return maskedName.toString();
    }

}
