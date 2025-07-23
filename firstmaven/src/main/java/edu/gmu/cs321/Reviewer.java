package edu.gmu.cs321;
import com.cs321.Workflow;

public class Reviewer {
    private PetitionForm currentForm;

    public Reviewer(PetitionForm currentForm){
        this.currentForm = currentForm;
    }
    public PetitionForm getCurrentForm(){
        return currentForm;
    }
    public void setCurrentForm(PetitionForm currentForm){
        this.currentForm = currentForm;
    }
    public void reviewForm(PetitionForm form){
        if (workerExists(form.getWorker())){
            rejectForm(form, "Worker already exists in the system.");
        } 
        else{
            System.out.println("Form ID " + form.getFormId() + " is ready for further processing.");
        }
    }
    //covered in the controller
    private boolean workerExists(Worker worker) {
        //String query = "SELECT COUNT(*) FROM Worker WHERE passportNum = ?";
        //return database.query(query, worker.getPassportNum()) != null;
        return false;
    }
    private void rejectForm(PetitionForm form, String reason) {
        form.rejectForm(reason);
        System.out.println("Form ID " + form.getFormId() + " has been rejected: " + reason);
    }
}
