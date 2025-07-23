package edu.gmu.cs321;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ApprovalUIController {
    @FXML
    private TextArea petitionDetailsArea;
    @FXML
    private Label errorLabel;
    @FXML
    private Label confirmationLabel;
    @FXML
    private Button approveButton;
    @FXML
    private Button rejectButton;
    private int formId;
    /**
     * Initializes the Approval UI with the petition details and form ID.
     *
     * @param formId          The ID of the form being reviewed.
     * @param petitionDetails The details of the petition to display.
     */
    public void initializeWithData(int formId, String petitionDetails) {
        this.formId = formId;
        this.petitionDetailsArea.setText(petitionDetails);
    }
    /**
     * Handles the approval of the petition.
     */
    @FXML
    private void approvePetition() {
        updateDatabase(true);
    }
    /**
     * Handles the rejection of the petition.
     */
    @FXML
    private void rejectPetition() {
        updateDatabase(false);
    }
    /**
     * Updates the database with the new status for the petition.
     *
     * @param isApproved Whether the petition is approved (true) or rejected (false).
     */
    private void updateDatabase(boolean isApproved) {
        errorLabel.setText("");
        confirmationLabel.setText("");

        if (petitionDetailsArea.getText().isEmpty()) {
            errorLabel.setText("Petition details cannot be empty.");
            return;
        }
        String status = isApproved ? "Approved" : "Rejected";
 
        try (Connection conn = TestDatabase.getConnection()) {

            // Update the workflow table
            String updateWorkflowQuery = "UPDATE workflow SET current_step = 'Approval', next_step = 'Complete', status = ? WHERE form_id = ?";
            try (PreparedStatement workflowStmt = conn.prepareStatement(updateWorkflowQuery)) {
                workflowStmt.setString(1, status);
                workflowStmt.setInt(2, formId);
                int rowsUpdatedWorkflow = workflowStmt.executeUpdate();

                if (rowsUpdatedWorkflow == 0) {
                    errorLabel.setText("No matching workflow entry found for form ID " + formId);
                    return;
                }
            }

            // Update the forms table
            String updateFormQuery = "UPDATE forms SET status = ? WHERE id = ?";
            try (PreparedStatement formStmt = conn.prepareStatement(updateFormQuery)) {
                formStmt.setString(1, status);
                formStmt.setInt(2, formId);
                int rowsUpdatedForms = formStmt.executeUpdate();

                if (rowsUpdatedForms == 0) {
                    errorLabel.setText("No matching form entry found for form ID " + formId);
                    return;
                }
            }

            // Set confirmation message and close the window
            confirmationLabel.setText("Petition " + status + " successfully!");
            Stage currentStage = (Stage) approveButton.getScene().getWindow();
            currentStage.close();

        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database error: Unable to update petition status.");
        }
    }
}
