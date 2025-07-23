package edu.gmu.cs321;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class USCitizenTest {

    @Test
    public void testConstructorAndGetters() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "2013 henrdin st", "toqa@gmail.com", "ABC123", "1234567890", "1999");
        assertEquals("Toqa Hassanein", citizen.getName());
        assertEquals("2013 henrdin st", citizen.getAddress());
        assertEquals("toqa@gmail.com", citizen.getEmail());
        assertEquals("ABC123", citizen.getCitizenId());
        assertEquals("1234567890", citizen.getPhoneNum());
        assertEquals("1999", citizen.getDob());
    }

    @Test
    public void testInitiatePetition() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "2013 henrdin st", "toqa@gmail.com", "ABC123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P123456789", "Engineer", "Pending");
        PetitionForm petition = citizen.initiatePetition(worker);
        assertNotNull(petition, "Petition should be successfully created and not null.");
        assertEquals("PENDING", petition.getStatus(), "New petition should have 'PENDING' status.");
        assertEquals(citizen, petition.getCitizen(), "Citizen in petition should match the initiating citizen.");
        assertEquals(worker, petition.getWorker(), "Worker in petition should match the provided worker.");
    }

    @Test
    public void testReceiveDecision() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "2013 henrdin st", "toqa@gmail.com", "ABC123", "1234567890", "1999");
        citizen.receiveDecision("Approved", "All criteria met");
    }
    @Test
    public void testUpdateContactInfo() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "2013 henrdin st", "toqa@gmail.com", "ABC123", "1234567890", "1999");
        citizen.updateContactInfo("newToqa@gmail.com", "0987654321");
        assertEquals("newToqa@gmail.com", citizen.getEmail(), "Email should be updated to newToqa@gmail.com.");
        assertEquals("0987654321", citizen.getPhoneNum(), "Phone number should be updated to 0987654321.");
    }
}