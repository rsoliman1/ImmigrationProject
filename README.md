This project is a basic system for managing petitions. It includes different classes to handle information and processes related to a petition. The main parts of the project are:

USCitizen: Holds information about the citizen who is requesting the petition.
Worker: Stores details about the worker involved in the petition.
PetitionForm: Keeps the petition data, including ID, date, status, and reasons.
DataEntry: Manages form submission and basic validation before review.
Reviewer: Reviews forms submitted by DataEntry to make sure they’re ready for processing.
Approval: Approves or rejects petitions based on the review, and also sends out notifications.

Usage:
DataEntry submits a new PetitionForm after validating it.
Reviewer checks if the form is complete and ready.
Approval approves or rejects the petition based on Reviewer feedback.

Testing:
JUnit tests are provided for all main classes: USCitizen, Worker, PetitionForm, DataEntry, Reviewer, and Approval.
Each test checks basic functionality like validating forms, updating status, and approving or rejecting petitions.
Requirements:

Java 11 or higher
JUnit 5 for testing
Running Tests: To run tests, set up JUnit and run each test class individually or use an IDE to run all tests at once.


