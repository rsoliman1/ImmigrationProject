package edu.gmu.cs321;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataEntryTest {

    @Test
    public void testSubmitFormValid() {
        USCitizen citizen = new USCitizen("Toqa Hassanein", "123 abc st", "toqa@gmail.com", "CIT123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P123456789", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("F123", "2024-10-30", "Draft", citizen, worker, "");
        DataEntry dataEntry = new DataEntry();  
        dataEntry.submitForm(form);
        assertEquals("Pending", form.getStatus(), "Form status should be set to 'Pending' for valid form submission.");
    }

    @Test
    public void testSubmitFormInvalid() {
        USCitizen citizen = new USCitizen(null, "123 abc st", "toqa@gmail.com", "CIT123", "1234567890", "1999");
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P123456789", "Engineer", "Pending");
        PetitionForm form = new PetitionForm("F123", "2024-10-30", "Draft", citizen, worker, "");
        DataEntry dataEntry = new DataEntry();
        dataEntry.submitForm(form);
        assertEquals("REQUIRES CORRECTION", form.getStatus(), "Form status should be 'REQUIRES CORRECTION' for invalid form submission.");
        assertEquals("Form needs attention for incompleteness or invalid information.", form.getReasons(), "Correction reason should be set correctly.");
    }
}
