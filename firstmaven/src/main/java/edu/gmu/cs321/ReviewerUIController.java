package edu.gmu.cs321;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewerUIController {

    @FXML
    private TextField formIdField;

    @FXML
    private TextArea petitionDetailsArea;

    @FXML
    private Label errorLabel;

    @FXML
    private Label confirmationLabel;

    @FXML
    private Button markAsCompleteButton;

    @FXML
    private Button forwardToApprovalButton;

    /**
     * Fetches the details of the form with the given form ID and displays them.
     */
    @FXML
    private void fetchFormDetails() {
        try {
            int formId = Integer.parseInt(formIdField.getText().trim());
            String details = getFormDetails(formId);
            if (details != null) {
                petitionDetailsArea.setText(details);
                setError(""); // Clear any error messages
            } else {
                setError("Form not found.");
            }
        } catch (NumberFormatException e) {
            setError("Invalid form ID. Please enter a valid number.");
        }
    }

    /**
     * Retrieves form details from the database by form ID.
     */
    private String getFormDetails(int formId) {
        String query = "SELECT * FROM workflow WHERE form_id = ?";
        StringBuilder details = new StringBuilder();
    
        try (Connection conn = TestDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, formId); // Bind formId parameter
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                details.append("Form ID: ").append(formId).append("\n");
                details.append("Current Step: ").append(rs.getString("current_step")).append("\n");
                details.append("Next Step: ").append(rs.getString("next_step")).append("\n");
                details.append("Status: ").append(rs.getString("status")).append("\n");
    
                // Check if the worker exists more than once
                if (doesWorkerExistMoreThanOnce(formId)) {
                    details.append("Worker exists more than once in the system.");
                } else {
                    details.append("Worker is unique in the system.");
                }
    
                return details.toString();
            } else {
                return null; // No result found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            setError("Database error occurred.");
        }
        return null;
    }

    /**
     * Checks if a worker exists more than once in the system using their name and passport number.
     */
    private boolean doesWorkerExistMoreThanOnce(int formId) {
        String query = "SELECT COUNT(*) AS count FROM forms WHERE form_id = ?";
        try (Connection conn = TestDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, formId);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                return rs.getInt("count") > 1; // Worker exists more than once
            }
        } catch (SQLException e) {
            e.printStackTrace();
            setError("Error checking worker existence.");
        }
        return false;
    }

    /**
     * Marks the current workflow item as complete, updating the database.
     */
    @FXML
    private void markAsComplete() {
        try {
            int formId = Integer.parseInt(formIdField.getText().trim());

            try (Connection conn = TestDatabase.getConnection()) {
                String query = "UPDATE workflow SET current_step = 'Reviewer', next_step = 'Approval', status = 'Pending' WHERE form_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, formId);
                    int rowsUpdated = stmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        confirmationLabel.setText("Form marked as complete.");
                        errorLabel.setText("");
                    } else {
                        setError("No matching form found for the provided ID.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            setError("Invalid form ID. Please enter a valid number.");
        } catch (SQLException e) {
            e.printStackTrace();
            setError("Database error occurred.");
        }
    }

    /**
     * Forwards the current workflow item to the Approval UI without additional checks.
     */
    @FXML
    private void forwardToApproval() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/gmu/cs321/ApprovalUI.fxml"));
            Parent approvalRoot = loader.load();

            ApprovalUIController approvalController = loader.getController();

            int formId = Integer.parseInt(formIdField.getText().trim());
            String petitionDetails = petitionDetailsArea.getText();
            approvalController.initializeWithData(formId, petitionDetails);

            Stage approvalStage = new Stage();
            approvalStage.setTitle("Approval Panel");
            approvalStage.setScene(new Scene(approvalRoot));
            approvalStage.show();

            Stage currentStage = (Stage) forwardToApprovalButton.getScene().getWindow();
            currentStage.close();
        } catch (NumberFormatException e) {
            setError("Invalid form ID. Please enter a valid number.");
        } catch (IOException e) {
            e.printStackTrace();
            setError("Error opening Approval UI. Ensure the FXML file is in the correct location.");
        }
    }
    /**
     * Sets an error message in the error label and clears the confirmation label.
     */
    private void setError(String message) {
        errorLabel.setText(message);
        confirmationLabel.setText("");
    }

    /**
     * Initializes the UI with data (optional method for testing).
     */
    public void initializeWithData(int formId, String currentStep, String nextStep, String status) {
        formIdField.setText(String.valueOf(formId));
        petitionDetailsArea.setText(
            "Current Step: " + currentStep + "\n" +
            "Next Step: " + nextStep + "\n" +
            "Status: " + status
        );
        setError(""); // Clear any previous error messages
    }
}
