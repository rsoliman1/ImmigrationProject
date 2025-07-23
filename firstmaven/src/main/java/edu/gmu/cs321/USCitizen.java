package edu.gmu.cs321;
import com.cs321.Workflow;

public class USCitizen {
    private String name;
    private String address;
    private String email;
    private String citizenId;
    private String phoneNum;
    private String dob;
    public USCitizen(String name, String address, String email, String citizenId, String phoneNum, String dob) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.citizenId = citizenId;
        this.phoneNum = phoneNum;
        this.dob = dob;
    }
    //Getter and setter
    public String getName(){ 
        return name; 
    }
    public void setName(String name){
        this.name = name; 
    }

    public String getAddress(){
        return address; 
    }
    public void setAddress(String address){ 
        this.address = address; 
    }

    public String getEmail(){ 
        return email; 
    }
    public void setEmail(String email){ 
        this.email = email; 
    }

    public String getCitizenId(){ 
        return citizenId;}
    public void setCitizenId(String citizenId){
         this.citizenId = citizenId;
        }

    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getDob(){
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    // Methods
    public PetitionForm initiatePetition(Worker worker){
            return new PetitionForm("defaultFormId", "2024-11-10", "PENDING", this, worker, "Initial Submission");
    }
    public void receiveDecision(String decision, String reason) {
        System.out.println("Your Petition is " + decision);
        System.out.println("Reason: " + reason);
    }
    public void updateContactInfo(String newEmail, String newPhoneNum) {
        if (newEmail != null && !newEmail.isEmpty()) {
            this.email = newEmail;
        }
        if (newPhoneNum != null && !newPhoneNum.isEmpty()) {
            this.phoneNum = newPhoneNum;
        }
    }
}