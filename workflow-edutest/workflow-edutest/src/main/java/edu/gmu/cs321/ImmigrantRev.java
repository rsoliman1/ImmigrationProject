package edu.gmu.cs321;
import com.cs321.Workflow;


public class ImmigrantRev {   
    String ANumber; 
    public String getANumber() {
        Workflow workflow = new Workflow();
        int result1 = workflow.AddWFItem(1, "Review");
        System.out.println("Result: " + result1);
        return ANumber;
    }
    public void setANumber(String aNumber) {
        ANumber = aNumber;
    }
    public static boolean checkANum (String anumid){
        return true;
        //return false;  //make fail
    }
}