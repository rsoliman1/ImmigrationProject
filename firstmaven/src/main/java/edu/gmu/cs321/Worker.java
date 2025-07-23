package edu.gmu.cs321;
import com.cs321.Workflow;

public class Worker{
    private String name;
    private String nationality;
    private String passportNum;
    private String job;
    private String status;

    public Worker(String name, String nationality, String passportNum, String job, String status) {
        this.name = name;
        this.nationality = nationality;
        this.passportNum = passportNum;
        this.job = job;
        this.status = status;
    }

    public String getName(){ 
        return name; 
    }
    public void setName(String name){ 
        this.name = name; 
    }

    public String getNationality(){ 
        return nationality;
    }
    public void setNationality(String nationality){ 
        this.nationality = nationality; 
    }

    public String getPassportNum(){ 
        return passportNum; 
    }
    public void setPassportNum(String passportNum){ 
        this.passportNum = passportNum; 
    }

    public String getJob(){ 
        return job; 
    }
    public void setJob(String job){ 
        this.job = job; 
    }

    public String getStatus(){ 
        return status; 
    }
    public void setStatus(String status){
        this.status = status; 
    }

    // Methods
    public void updateStatus(String newStatus){
        this.status = newStatus;
        System.out.println("Status updated to: " + newStatus);
    }
    public void displayWorkerDetails(){
        System.out.println("Worker Name: "+name);
        System.out.println("Nationality: "+nationality);
        System.out.println("Passport Number: "+passportNum);
        System.out.println("Job: "+job);
        System.out.println("Status: "+status);
    }
    
}
