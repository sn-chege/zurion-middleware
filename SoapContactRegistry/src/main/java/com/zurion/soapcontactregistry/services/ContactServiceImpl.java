package com.zurion.soapcontactregistry.services;

/**
 *
 * @author stanl
 */
import com.zurion.soapcontactregistry.models.Contact;
import com.zurion.soapcontactregistry.daos.ContactDAO;
import jakarta.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.zurion.contactregistry.services.ContactService")
public class ContactServiceImpl implements ContactService {

    private ContactDAO contactDAO = new ContactDAO(); // Instantiate ContactDAO

    @Override
    public List<Contact> getContactsByOrganization(String organization) {
        return contactDAO.getContactsByOrganization(organization); // Call the instance method
    }
}