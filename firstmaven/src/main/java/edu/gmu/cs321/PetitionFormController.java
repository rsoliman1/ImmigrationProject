package edu.gmu.cs321;

import java.io.IOException;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PetitionFormController {

    @FXML
    private TextField citizenNameField, citizenPassportField, citizenContactField, workerNameField, workerJobTitleField, workerCountryField;

    @FXML
    private Label errorMessageLabel, confirmationMessageLabel;

    @FXML
    private Button submitFormButton, nextItemButton;

    /**
     * Handles form submission.
     */
    @FXML
    private void submitForm() {
        // Fetch inputs from text fields
        String citizenName = citizenNameField.getText().trim();
        String passportNumber = citizenPassportField.getText().trim();
        String contactInfo = citizenContactField.getText().trim();
        String workerName = workerNameField.getText().trim();
        String jobTitle = workerJobTitleField.getText().trim();
        String countryOfOrigin = workerCountryField.getText().trim();

        // Validate inputs
        if (!validateInputs(citizenName, passportNumber, contactInfo, workerName, jobTitle, countryOfOrigin)) {
            return; // Stop if validation fails
        }

        try {
            // Save data to database and workflow
            int formId = savePetitionToDatabase(citizenName, passportNumber, contactInfo, workerName, jobTitle, countryOfOrigin);
            saveWorkflow(formId);

            // Clear error messages and show success
            errorMessageLabel.setText("");
            confirmationMessageLabel.setText("Form submitted successfully!");
            confirmationMessageLabel.setVisible(true);

            // Clear form fields after successful submission
            clearFormFields();

        } catch (Exception e) {
            errorMessageLabel.setText("An error occurred while saving the form. Please try again.");
            confirmationMessageLabel.setVisible(false);
            e.printStackTrace(); // Log the error for debugging
        }
    }

    /**
     * Saves the workflow for the submitted petition.
     */
    private void saveWorkflow(int formId) {
        String query = "INSERT INTO workflow (form_id, current_step, next_step, status, updated_at) " +
                       "VALUES (?, ?, ?, ?, NOW())";
    
        try (Connection conn = TestDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            // Insert into workflow table with correct steps
            stmt.setInt(1, formId);
            stmt.setString(2, "Data Entry");  // Set current step to "Data Entry"
            stmt.setString(3, "Reviewer");   // Set next step to "Reviewer"
            stmt.setString(4, "Pending");    // Set status to "Pending"
    
            stmt.executeUpdate();
            System.out.println("Workflow created with Data Entry -> Reviewer.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Transitions the workflow from `Data Entry` to `Reviewer`.
     */
    private void moveToReviewer(int formId) throws SQLException {
        String query = "UPDATE workflow SET current_step = 'Reviewer', status = 'Pending' WHERE form_id = ? AND current_step = 'Data Entry'";

        try (Connection conn = TestDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, formId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Workflow moved to Reviewer.");
            } else {
                throw new SQLException("Failed to update workflow. Form may not exist or be in the correct state.");
            }
        }
    }

    /**
     * Fetches the next workflow item for the `Reviewer` step and opens the Reviewer UI.
     */
    @FXML
    private void fetchNextWorkflowItem() {
        try {
            // Load the Reviewer UI
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/gmu/cs321/ReviewerUI.fxml"));
            Parent reviewerRoot = loader.load();

            ReviewerUIController reviewerController = loader.getController();

            // You can pass dummy or default data to the Reviewer UI
            reviewerController.initializeWithData(0, "Reviewer", "Approval", "Pending");

            // Show the Reviewer UI in a new window
            Stage stage = new Stage();
            stage.setTitle("Reviewer UI");
            stage.setScene(new Scene(reviewerRoot));
            stage.show();

            // Optionally close the current PetitionForm window
            Stage currentStage = (Stage) nextItemButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            System.out.println("Error loading Reviewer UI.");
            errorMessageLabel.setText("Error loading Reviewer UI.");
            e.printStackTrace();
        }
    }
    

    /**
     * Saves the petition form data to the database and returns the generated form ID.
     */
    private int savePetitionToDatabase(String citizenName, String passportNumber, String contactInfo,
                                       String workerName, String jobTitle, String countryOfOrigin) throws SQLException {
        String query = "INSERT INTO forms (citizen_name, passport_number, contact_info, worker_name, job_title, country_of_origin, status) VALUES (?, ?, ?, ?, ?, ?, 'Pending')";

        try (Connection conn = TestDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, citizenName);
            stmt.setString(2, passportNumber);
            stmt.setString(3, contactInfo);
            stmt.setString(4, workerName);
            stmt.setString(5, jobTitle);
            stmt.setString(6, countryOfOrigin);
            stmt.executeUpdate();

            // Retrieve the generated form ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve form ID.");
            }
        }
    }

    /**
     * Validates the user inputs.
     */
    private boolean validateInputs(String citizenName, String passportNumber, String contactInfo,
                                   String workerName, String jobTitle, String countryOfOrigin) {
        if (citizenName.isEmpty() || !citizenName.matches("[a-zA-Z\\s]+")) {
            setErrorMessage("Invalid citizen name. Name must only contain letters and spaces.");
            return false;
        }
        if (passportNumber.isEmpty() || !passportNumber.matches("[a-zA-Z0-9]+")) {
            setErrorMessage("Invalid passport number. Only letters and digits are allowed.");
            return false;
        }
        if (contactInfo.isEmpty() || (!contactInfo.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") &&
                                      !contactInfo.matches("\\d{10,15}"))) {
            setErrorMessage("Invalid contact information. Enter a valid email or phone number.");
            return false;
        }
        if (workerName.isEmpty() || !workerName.matches("[a-zA-Z\\s]+")) {
            setErrorMessage("Invalid worker name. Name must only contain letters and spaces.");
            return false;
        }
        if (jobTitle.isEmpty()) {
            setErrorMessage("Job title cannot be empty.");
            return false;
        }
        if (countryOfOrigin.isEmpty() || !countryOfOrigin.matches("[a-zA-Z\\s]+")) {
            setErrorMessage("Invalid country of origin. Country must only contain letters and spaces.");
            return false;
        }
        return true;
    }

    private void setErrorMessage(String message) {
        errorMessageLabel.setText(message);
        confirmationMessageLabel.setVisible(false);
    }

    /**
     * Clears the form fields.
     */
    private void clearFormFields() {
        citizenNameField.clear();
        citizenPassportField.clear();
        citizenContactField.clear();
        workerNameField.clear();
        workerJobTitleField.clear();
        workerCountryField.clear();
    }
}
