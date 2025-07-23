package edu.gmu.cs321;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkflowManager {

    public static ResultSet getNextWorkflowItem(String currentStep) throws SQLException {
        Connection conn = TestDatabase.getConnection();
        String query = "SELECT * FROM workflow WHERE current_step = ? AND status = 'Pending' ORDER BY updated_at ASC LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, currentStep);
        return stmt.executeQuery();
    }

    public static void updateWorkflow(int formId, String currentStep, String nextStep, String status) throws SQLException {
        try (Connection conn = TestDatabase.getConnection()) {
            String query = "UPDATE workflow SET current_step = ?, next_step = ?, status = ?, updated_at = NOW() WHERE form_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, currentStep);
            stmt.setString(2, nextStep);
            stmt.setString(3, status);
            stmt.setInt(4, formId);
            stmt.executeUpdate();
        }
    }
}