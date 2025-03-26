# Zurion Middleware

## Overview
Zurion Middleware is a Java-based middleware application that provides contact registry services through both SOAP and REST APIs. The project is built with Java 17, NetBeans 16, Maven, Tomcat 9, and MySQL.

## Project Structure
```
zurion-middleware/
|-- resources/
|   |-- database/contact_registry.sql  # Database schema
|-- RestContactRegistry/
|   |-- src/
|   |   |-- main/
|   |   |   |-- java/
|   |   |   |   |-- com/
|   |   |   |   |   |-- zurion/
|   |   |   |   |   |   |-- restcontactregistry/
|   |   |   |   |   |   |   |-- servlets/
|   |   |   |   |   |   |   |-- utils/        
|   |   |   |   |   |   |   |-- daos/         
|   |   |   |   |   |   |   |-- models/       
|   |   |   |   |   |   |   |-- requests/     
|-- SoapContactRegistry/
|   |-- src/
|   |   |-- main/
|   |   |   |-- java/
|   |   |   |   |-- com/
|   |   |   |   |   |-- zurion/
|   |   |   |   |   |   |-- soapcontactregistry/
|   |   |   |   |   |   |   |-- services/  
|   |   |   |   |   |   |   |-- daos/      
|   |   |   |   |   |   |   |-- models/    
|   |   |   |   |   |   |   |-- utils/     
```

## Setup Instructions

### Prerequisites
Ensure you have the following installed:
- Java 17
- NetBeans 16
- Apache Tomcat 9
- MySQL Server
- Maven

### Database Setup
1. Create the required database by running the script located at:
   ```
   zurion-middleware/resources/databasecontact_registry.sql
   ```
2. Update database connection details in the configuration files if necessary.

### Running the SOAP API
1. Navigate to `SoapContactRegistry` in the project.
2. Run the `ContactPublisher` class to start the SOAP web service:
   ```java
   public class ContactPublisher {
       public static void main(String[] args) {
           String url = "http://localhost:8084/ContactService";
           Endpoint.publish(url, new ContactServiceImpl());
           System.out.println("SOAP Web Service running at: " + url + "?wsdl");
       }
   }
   ```
3. The service will be accessible at `http://localhost:8084/ContactService?wsdl`.

### Running the REST API
1. Navigate to `RestContactRegistry` in the project.
2. TO COMPILE: Right click on `RestContactRegistry` and click `Clean and Build`
3. TO RUN: Right click on `RestContactRegistry` and click `Run`
4. The service will be accessible at `http://localhost:8087/RestContactRegistry/contacts`.

## Key Components

### SOAP API
#### Important Classes:
- `ContactPublisher` - Publishes the SOAP service.
- `ContactDAO` - Handles database operations for contacts.
- `Contact` - Model representing a contact.

### REST API
#### Important Classes:
- `ContactController` - Handles REST endpoints.
- `ContactDAO` - Handles database operations.
- `Contact` - Model representing a contact.

## Endpoints
### SOAP
- `http://localhost:8084/ContactService?wsdl`
- Supports operations like `getAllContacts()`, `insertContact()`, `getContactById()`, `getContactsByOrganization()`.

### REST
- `GET /api/contacts` - Fetch all contacts.
- `POST /api/contacts` - Add a new contact.
- `GET /api/contacts/{id}` - Fetch a contact by ID.
- `PUT /api/contacts/{id}` - Update a contact.
- `DELETE /api/contacts/{id}` - Delete a contact.

## Contribution
Feel free to fork this repository and submit pull requests with improvements or bug fixes.

## License
This project is licensed under the MIT License.

