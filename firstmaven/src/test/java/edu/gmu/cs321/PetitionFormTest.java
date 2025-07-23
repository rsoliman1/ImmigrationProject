package edu.gmu.cs321;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PetitionFormTest {

    @Test
    public void testConstructorAndGetters() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "1234 Elm St", "toqa@gmail.com", "CIT123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P123456789", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("F123", "2024-10-30", "PENDING", citizen, worker, "No issues");
        assertEquals("F123", form.getFormId());
        assertEquals("2024-10-30", form.getDate());
        assertEquals("PENDING", form.getStatus());
        assertEquals(citizen, form.getCitizen());
        assertEquals(worker, form.getWorker());
        assertEquals("No issues", form.getReasons());
    }

    @Test
    public void testValidateFormComplete() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "1234 Elm St", "toqa@gmail.com", "CIT123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P123456789", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("F123", "2024-10-30", "PENDING", citizen, worker, "");
        assertTrue(form.validate());
        assertEquals("", form.getReasons());
    }
    @Test
    public void testValidateFormIncompleteCitizenInfo() {
        USCitizen citizen = new USCitizen(null, "1234 Elm St", "toqa@gmail.com", "CIT123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P123456789", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("F123", "2024-10-30", "PENDING", citizen, worker, "");
        assertFalse(form.validate());
        assertEquals("Incomplete citizen information.", form.getReasons());
    }
    @Test
    public void testApproveForm() {
        PetitionForm form = new PetitionForm("F123", "2024-10-30", "PENDING", null, null, "");
        form.approveForm();
        assertEquals("APPROVED", form.getStatus());
        assertEquals("N/A", form.getReasons());
    }

    @Test
    public void testRejectForm() {
        PetitionForm form = new PetitionForm("F123", "2024-10-30", "PENDING", null, null, "");
        form.rejectForm("Worker has insufficient documents");
        assertEquals("REJECTED", form.getStatus());
        assertEquals("Worker has insufficient documents", form.getReasons());
    }

    @Test
    public void testRequestCorrection() {
        PetitionForm form = new PetitionForm("F123", "2024-10-30", "PENDING", null, null, "");
        form.requestCorrection("Missing signature from worker");
        assertEquals("REQUIRES CORRECTION", form.getStatus());
        assertEquals("Missing signature from worker", form.getReasons());
    }
}
