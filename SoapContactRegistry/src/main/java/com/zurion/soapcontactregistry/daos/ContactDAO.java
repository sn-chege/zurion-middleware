package com.zurion.soapcontactregistry.daos;

/**
 *
 * @author stanl
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.zurion.soapcontactregistry.models.Contact;
import com.zurion.soapcontactregistry.utils.DatabaseConnection;

public class ContactDAO {

    // Insert a contact
    public boolean insertContact(Contact contact) {
        String sql = "INSERT INTO contacts (full_name, phone_number, email, id_number, date_of_birth, gender, organization) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contact.getFullName());
            stmt.setString(2, contact.getPhoneNumber());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getIdNumber());
            stmt.setDate(5, contact.getDateOfBirth() != null && !contact.getDateOfBirth().isEmpty() ?
                           java.sql.Date.valueOf(contact.getDateOfBirth()) : null);
            stmt.setString(6, contact.getGender());
            stmt.setString(7, contact.getOrganization());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contacts.add(new Contact(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("id_number"),
                    rs.getString("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("organization")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    // Fetch a single contact by ID
    public Contact getContactById(int id) {
        String sql = "SELECT * FROM contacts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Contact(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("id_number"),
                    rs.getString("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("organization")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Fetch contacts by Organization (for SOAP)
    public List<Contact> getContactsByOrganization(String organization) {
        List<Contact> contacts = new ArrayList<>();

        String query = "SELECT * FROM contacts WHERE organization = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, organization);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("id_number"),
                    rs.getString("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("organization")
                );
                contacts.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }
    
    // Update a contact
    public boolean updateContact(Contact contact) {
        String sql = "UPDATE contacts SET full_name=?, phone_number=?, email=?, id_number=?, date_of_birth=?, gender=?, organization=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contact.getFullName());
            stmt.setString(2, contact.getPhoneNumber());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getIdNumber());
            stmt.setDate(5, contact.getDateOfBirth() != null && !contact.getDateOfBirth().isEmpty() ?
                           java.sql.Date.valueOf(contact.getDateOfBirth()) : null);
            stmt.setString(6, contact.getGender());
            stmt.setString(7, contact.getOrganization());
            stmt.setInt(8, contact.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a contact
    public boolean deleteContact(int id) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
}