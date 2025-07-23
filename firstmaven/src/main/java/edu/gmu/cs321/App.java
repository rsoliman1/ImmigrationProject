package edu.gmu.cs321;
import com.cs321.Workflow;


/**
 * Hello world!
 * This is the main area that will use to manage workflow and how the app/system will look 
 * "TO BE COMPLETED LATER"
 *
 */
public class App {
    public static void main(String[] args) {
        // Step 1: Create an instance of Workflow
        Workflow workflow = new Workflow();

        // Step 2: Add a workflow item
        int addResult = workflow.AddWFItem(1, "Review");
        if (addResult == 0) {
            System.out.println("Workflow item added successfully!");
        } else {
            System.out.println("Error adding workflow item: " + addResult);
        }

        // Step 3: Retrieve the next workflow item
        int nextItem = workflow.GetNextWFItem("Review");
        if (nextItem > 0) {
            System.out.println("Next workflow item retrieved: " + nextItem);
        } else {
            System.out.println("Error retrieving workflow item: " + nextItem);
        }
    }
}
