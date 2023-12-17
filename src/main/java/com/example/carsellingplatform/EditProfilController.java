package com.example.carsellingplatform;

import com.example.carsellingplatform.backend.model.User;
import com.example.carsellingplatform.backend.util.DatabaseHandler;
import com.example.carsellingplatform.backend.util.Hash;
import com.example.carsellingplatform.backend.util.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.carsellingplatform.RegistrationController.PasswordRequirementFullfilled;

public class EditProfilController implements Initializable {


    @FXML
    private TextField AddressField;

    @FXML
    private Label AddressText;

    @FXML
    private TextField CityField;

    @FXML
    private Label CityText;

    @FXML
    private PasswordField ConfirmPasswordField;

    @FXML
    private Label ConfirmPasswordText;

    @FXML
    private Button DeleteButton;

    @FXML
    private TextField FirstNameField;

    @FXML
    private Label FirstNameText;

    @FXML
    private Button GoBackButton;

    @FXML
    private TextField LastNameField;

    @FXML
    private Label LastNameText;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label PasswordText;

    @FXML
    private Label PasswordText1;

    @FXML
    private Label PasswordText11;

    @FXML
    private Label PasswordText12;

    @FXML
    private Label PasswordText13;

    @FXML
    private Label RegistrationText;

    @FXML
    private Button UpdateButton;

    @FXML
    private Rectangle leftRectangle;

    @FXML
    private Rectangle rightRectangle;

    @FXML
    private Rectangle upperRectangle;


    private User user;
    private ResultSet rs;

    Alert message = new Alert(Alert.AlertType.NONE);

    public void UpdateButtonClicked(ActionEvent event) throws IOException {
        boolean update = true;

        // Check if any required fields are empty
        if (FirstNameField.getText().isEmpty() || LastNameField.getText().isEmpty()
                || CityField.getText().isEmpty() || AddressField.getText().isEmpty()) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please fill in all the required fields");
            message.show();
            update = false;
        }


        // Check if the passwords match only if there is a value in the PasswordField
        String password = PasswordField.getText();
        String confirmPassword = ConfirmPasswordField.getText();
        if (!password.isEmpty() && !password.equals(confirmPassword)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Password and password confirmation have to be identical!");
            message.show();
            update = false;
        }

        // Perform password validation only if there is a value in the PasswordField
        if (!password.isEmpty() && !PasswordRequirementFullfilled(password)) {
            update = false;
        }


        if (update) {
            DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
            Hash hasher = new Hash();
            String salt = hasher.getSalt();
            String hashedPassword = password.isEmpty() ? "" : hasher.hash(password + salt);

            boolean updated = true;

            // Update user in the database
            if(PasswordField.getText().equals(null)||PasswordField.getText().equals("") )
            {
                updated = databaseHandler.updateUsernoPassword(user.getUsername(), FirstNameField.getText(),
                        LastNameField.getText(), CityField.getText(), AddressField.getText());
            }
            else{
                 updated = databaseHandler.updateUser(user.getUsername(), FirstNameField.getText(),
                    LastNameField.getText(), hashedPassword, salt, CityField.getText(), AddressField.getText());}

            if (updated) {
                message.setAlertType(Alert.AlertType.CONFIRMATION);
                message.setContentText("Update successful");
                message.show();

                // Update the user object with the new information
                user.setFirstName(FirstNameField.getText());
                user.setLastName(LastNameField.getText());
                user.setCity(CityField.getText());
                user.setAddress(AddressField.getText());

                // Display the updated information in the frame
                usernameLabel.setText(user.getUsername());
                FirstNameField.setText(user.getFirstName());
                LastNameField.setText(user.getLastName());
                CityField.setText(user.getCity());
                AddressField.setText(user.getAddress());

                // Clear password fields
                PasswordField.clear();
                ConfirmPasswordField.clear();
            } else {
                message.setAlertType(Alert.AlertType.ERROR);
                message.setContentText("Failed to update user");
                message.show();
            }
        }
    }



    public void GoBackButtonClicked(ActionEvent event) throws IOException {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchToDashboard(event, user);
    }

    public void setUser(User user) {
        this.user = user;
        usernameLabel.setText(user.getUsername()); // Set the label text to the current user's username
        loadUserData();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (user == null) {
            //System.out.println("User object is null");
        }
    }

    public void loadUserData() {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        ResultSet rs = databaseHandler.getUser(user.getUsername()); // Pass the username

        try {
            if (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String city = rs.getString("City");
                String address = rs.getString("Address");

                FirstNameField.setText(firstName);
                LastNameField.setText(lastName);
                AddressField.setText(address);
                CityField.setText(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void DeleteButtonClicked(ActionEvent event) throws IOException {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Delete Account");
        confirmationDialog.setHeaderText("Confirmation");
        confirmationDialog.setContentText("Are you sure you want to delete your account? This action cannot be undone!");

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed the deletion, proceed with deletion
            DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
            boolean deletionSuccessful = databaseHandler.deleteUser(user.getUsername());

            if (deletionSuccessful) {
                // Account deleted successfully, switch to login screen
                Mail.sendEmailDeletion(user.getEmail());
                SceneSwitcher sceneSwitcher = new SceneSwitcher();
                sceneSwitcher.switchToLogin(event);
            } else {
                // Deletion failed, display an error message
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setTitle("Deletion Failed");
                errorDialog.setHeaderText(null);
                errorDialog.setContentText("An error occurred while deleting your account. Please try again.");
                errorDialog.showAndWait();
            }
        }
    }
}
