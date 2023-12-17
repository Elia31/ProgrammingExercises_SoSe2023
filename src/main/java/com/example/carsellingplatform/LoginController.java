package com.example.carsellingplatform;

import com.example.carsellingplatform.backend.model.User;
import com.example.carsellingplatform.backend.util.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private PasswordField passwordPasswordfield;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private Button forgotPassword;


    public void loginButtonOnAction(ActionEvent e) throws IOException, SQLException {
        String username = usernameTextfield.getText();
        String password = String.valueOf(passwordPasswordfield.getText());

        if (!(username.isBlank()) && !(password.isBlank())) {
            DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

            if (databaseHandler.isLoginCorrect(username, password)) {
                loginMessageLabel.setText("CORRECT!");

                User user = null;
                ResultSet FirstNameR = databaseHandler.SelectData("User","FirstName","Username",username);
                ResultSet LastNameR = databaseHandler.SelectData("User","LastName","Username",username);
                ResultSet EmailR = databaseHandler.SelectData("User","Email","Username",username);
                ResultSet AddressR = databaseHandler.SelectData("User","Address","Username",username);
                ResultSet CityR = databaseHandler.SelectData("User","City","Username",username);

                if (FirstNameR.next() && LastNameR.next() && EmailR.next() && AddressR.next() && CityR.next()) {
                    String FirstName = FirstNameR.getString("FirstName");
                    String LastName = LastNameR.getString("LastName");
                    String Email = EmailR.getString("Email");
                    String Address = AddressR.getString("Address");
                    String City = CityR.getString("City");
                    user = new User(FirstName,LastName,username,Email,Address,City);
                }

                if (user != null) {
                    SceneSwitcher sceneswitcher = new SceneSwitcher();
                    sceneswitcher.switchToDashboard(e,user);
                } else {
                    loginMessageLabel.setText("User data not found");
                }
            } else {
                loginMessageLabel.setText("Username or password not correct!");
            }
        } else {
            loginMessageLabel.setText("Please enter the Information!");
        }
    }


    public void registerButtonOnAction(ActionEvent e) throws IOException {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchToRegistration(e);

    }

    @FXML
    void forgotPasswordButtonOnAction(ActionEvent e) throws IOException {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchToResetPassword(e);
    }

}
