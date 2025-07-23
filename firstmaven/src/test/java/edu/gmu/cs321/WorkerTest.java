package edu.gmu.cs321;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WorkerTest {
    @Test
    public void testConstructorAndGetters(){
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P123456789", "Software Engineer", "Pending");
        assertEquals("Ahmed Worker", worker.getName());
        assertEquals("Egyptian", worker.getNationality());
        assertEquals("P123456789", worker.getPassportNum());
        assertEquals("Software Engineer", worker.getJob());
        assertEquals("Pending", worker.getStatus());
    }

    @Test
    public void testUpdateStatus() {
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P123456789", "Software Engineer", "Pending");
        worker.updateStatus("Approved");
        assertEquals("Approved", worker.getStatus(), "Status should be updated to 'Approved'");
    }

    @Test
    public void testDisplayWorkerDetails() {
        Worker worker = new Worker("Ahmed Worker", "Egyptian", "P123456789", "Software Engineer", "Pending");
        worker.displayWorkerDetails(); 
    }
}