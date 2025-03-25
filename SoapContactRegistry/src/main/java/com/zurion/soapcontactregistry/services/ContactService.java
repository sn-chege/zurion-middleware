package com.zurion.soapcontactregistry.services;

/**
 *
 * @author stanl
 */
/**
 *
 * @author stanl
 */
import com.zurion.soapcontactregistry.models.Contact;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;

@WebService
public interface ContactService {
    @WebMethod
    List<Contact> getContactsByOrganization(String organization);
}
