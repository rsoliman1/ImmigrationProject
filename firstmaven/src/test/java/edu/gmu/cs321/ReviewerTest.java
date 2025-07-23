package edu.gmu.cs321;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReviewerTest {

    @Test
    public void testConstructorAndGetCurrentForm() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "2013 Herndon St", "toqa@gmail.com", "ABC123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "A123456789", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("G123", "2024-11-01", "PENDING", citizen, worker, "");
        Reviewer reviewer = new Reviewer(form);
        assertEquals(form, reviewer.getCurrentForm());
    }

    @Test
    public void testReviewFormWorkerDoesNotExist() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "2013 Herndon St", "toqa@gmail.com", "ABC123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "A123456789", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("G123", "2024-11-01", "PENDING", citizen, worker, "");

        Reviewer reviewer = new Reviewer(form);
        reviewer.reviewForm(form);
        assertEquals("", form.getReasons(), "Form should not be rejected if worker does not exist");
    }

    @Test
    public void testReviewFormWorkerExists() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "2013 Herndon St", "toqa@gmail.com", "ABC123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "A123456789", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("G123", "2024-11-01", "PENDING", citizen, worker, "");

        Reviewer reviewer = new Reviewer(form);
        form.rejectForm("Worker already exists in the system.");
        assertEquals("Worker already exists in the system.", form.getReasons(), "Form should be rejected with the correct reason");
    }
}