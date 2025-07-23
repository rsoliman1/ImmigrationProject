package edu.gmu.cs321;
import com.cs321.Workflow;

public class DataEntry {
    //Methods
    public void submitForm(PetitionForm form) {
        if (validateForm(form)) {
            form.setStatus("Pending");
            System.out.println("Form submitted for review.");
        } 
        else {
            requestCorrection(form, "Form needs attention for incompleteness or invalid information.");
        }
    }
    private boolean validateForm(PetitionForm form) {
        return form.validate();
    }
    private void requestCorrection(PetitionForm form, String reason) {
        form.requestCorrection(reason);
        System.out.println("Form " + form.getFormId() + " is requested for correctness for " + reason + ".");
    }
}
