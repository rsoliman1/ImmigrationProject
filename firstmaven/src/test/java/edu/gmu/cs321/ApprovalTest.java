package edu.gmu.cs321;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ApprovalTest {
    @Test
    public void testApproveForm() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "2013 Herndon St", "toqa@gmail.com", "ABC123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P987654321", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("F100", "2024-11-01", "PENDING", citizen, worker, "No issues");
        Approval aproval = new Approval(form);
        aproval.approveForm(form);
        assertEquals("APPROVED", form.getStatus(), "Form status should be 'APPROVED' after approval.");
        assertEquals("No issues", form.getReasons(), "Approval should not change the reasons.");
    }

    @Test
    public void testRejectForm() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "2013 Herndon St", "toqa@gmail.com", "ABC123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P987654321", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("F100", "2024-11-01", "PENDING", citizen, worker, "");
        Approval aproval = new Approval(form);
        aproval.rejectForm(form, "Missing required documentation");
        assertEquals("REJECTED", form.getStatus(), "Form status should be 'REJECTED' after rejection.");
        assertEquals("Missing required documentation", form.getReasons(), "Rejection reason should be set correctly.");
    }
}