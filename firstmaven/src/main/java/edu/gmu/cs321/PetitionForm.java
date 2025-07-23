package edu.gmu.cs321;
import com.cs321.Workflow;

public class PetitionForm {
    private String formId;
    private String date;
    private String status;
    private USCitizen citizen;
    private Worker worker;
    private String reasons;

    public PetitionForm(String formId,String date,String status,USCitizen citizen,Worker worker,String reasons){
        this.formId = formId;
        this.date = date;
        this.status = status;
        this.citizen = citizen;
        this.worker = worker;
        this.reasons = reasons;
    }
    public String getFormId() {
        return formId;
    }
    public void setFormId(String formId) {
        this.formId = formId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getStatus() {
            return status;
    }
    public void setStatus(String status) {
            this.status = status;
    }
    public USCitizen getCitizen() {
        return citizen;
    }
    public void setCitizen(USCitizen citizen) {
        this.citizen = citizen;
    }
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
    public String getReasons() {
        return reasons;
    }
    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
    // Methods 
    public boolean validate(){
        if (citizen == null || citizen.getName() == null || citizen.getCitizenId() == null || citizen.getAddress() == null) {
            System.out.println("Missing citizen information.");
            reasons = "Incomplete citizen information.";
            return false;
        }
        if (worker == null || worker.getName() == null || worker.getPassportNum() == null || worker.getJob() == null) {
            System.out.println("Missing worker information.");
            reasons = "Incomplete worker information.";
            return false;
        }
        if (formId == null || date == null) {
            System.out.println("Validation failed: Missing essential form information.");
            reasons = "Incomplete form information (form ID or date).";
            return false;
        }
        System.out.println("Form is complete.");
        reasons = "";
        return true;
    }
    public void approveForm() {
        this.status = "APPROVED";
        this.reasons = "N/A"; 
    }
    public void rejectForm(String reason) {
        this.status = "REJECTED";
        this.reasons = reason;
    }

    public void requestCorrection(String reason) {
        this.status = "REQUIRES CORRECTION";
        this.reasons = reason;
    }
    public void displayFormInfo(){
        System.out.println("Form ID: " + formId);
        System.out.println("Date: " + date);
        System.out.println("Status: " + status);
        System.out.println("Reasons: " + reasons); 
    }
}
