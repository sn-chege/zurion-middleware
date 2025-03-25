package com.zurion.soapcontactregistry;

/**
 *
 * @author stanl
 */
import com.zurion.soapcontactregistry.services.ContactServiceImpl;
import jakarta.xml.ws.Endpoint;

public class ContactPublisher {
    public static void main(String[] args) {
        String url = "http://localhost:8084/ContactService";
        Endpoint.publish(url, new ContactServiceImpl());
        System.out.println("SOAP Web Service running at: " + url + "?wsdl");
    }
}
