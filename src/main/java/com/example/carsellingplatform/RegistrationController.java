package com.example.carsellingplatform;


import com.example.carsellingplatform.backend.util.DatabaseHandler;
import com.example.carsellingplatform.backend.util.Hash;
import com.example.carsellingplatform.backend.util.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationController {
    public static boolean EmailValid(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean PasswordRequirementFullfilled(String input) {
        boolean capital = false;
        boolean specialchar = false;
        boolean digit = false;
        Alert message = new Alert(Alert.AlertType.ERROR);

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                capital = true; // Found a capital letter
            }
            if (!Character.isLetterOrDigit(c)) {
                specialchar = true; // Found a capital letter
            }
            if (Character.isDigit(c)) {
                digit = true;
            }
        }

        if (!capital) {
            message.setContentText("Your password should contain at least one capital letter");
            message.show();
        }
        if (!specialchar) {
            message.setContentText("Your password should contain at least one special character (*+~#!?...)");
            message.show();
        }
        if (!digit) {
            message.setContentText("Your password should contain at least one digit");
            message.show();
        }

        if(input.length()<7)
        {
            message.setContentText("Your password should be at least 8 characters long!");
            message.show();
        }

        if (capital && specialchar && digit) {
            return true;
        } else {
            return false;
        }
    }

    Alert message = new Alert(Alert.AlertType.NONE);
    @FXML
    private TextField AddressField;
    @FXML
    private Label AddressText;
    @FXML
    private Label CityText;
    @FXML
    private TextField CityField;
    @FXML
    private PasswordField ConfirmPasswordField;
    @FXML
    private Label ConfirmPasswordText;
    @FXML
    private TextField EMailField;
    @FXML
    private Label EMailText;
    @FXML
    private TextField FirstNameField;
    @FXML
    private Label FirstNameText;
    @FXML
    private TextField LastNameField;
    @FXML
    private Label LastNameText;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Label PasswordText;
    @FXML
    private Button RegisterButton;
    @FXML
    private Label RegistrationText;
    @FXML
    private TextField UsernameField;
    @FXML
    private Label UsernameText;

    @FXML
    private Rectangle RegisterRectangle;

    @FXML
    private Text RegisterRectangleText;

    @FXML
    private Rectangle leftRectangle;

    @FXML
    private Rectangle upperRectangle;

    @FXML
    private Line AddressLine;

    @FXML
    private Line CityLine;

    @FXML
    private Line ConfirmPasswordLine;

    @FXML
    private Line EMailLine;

    @FXML
    private Line FirstNameLine;

    @FXML
    private Line LastNameLine;

    @FXML
    private Line PasswordLine;

    @FXML
    private Line UsernameLine;

    @FXML
    private ImageView logo;

    @FXML
    private Rectangle rightRectangle;

    @FXML
    private CubicCurve upperForm;

    @FXML
    private Button GoBackButton;


    @FXML
    void RegisterButtonClicked(ActionEvent event) throws ClassNotFoundException, IOException {
        boolean Register = true;


        if (!EmailValid(EMailField.getText())) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("The E-Mail address is invalid!");
            message.show();
        }

        if (FirstNameField.getText().equals("") || FirstNameField.getText().equals(null)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Enter your first name");
            message.show();
        }

        if (LastNameField.getText().equals("") || LastNameField.getText().equals(null)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Enter your last name");
            message.show();
        }

        if (CityField.getText().equals("") || CityField.getText().equals(null)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Enter the city you live in");
            message.show();
        }

        if (AddressField.getText().equals("") || AddressField.getText().equals(null)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Enter your address");
            message.show();
        }

        if (UsernameField.getText().equals("") || UsernameField.getText().equals(null)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Enter a username!");
            message.show();
        }

        // else{

        DatabaseHandler mydbhandler = DatabaseHandler.getInstance();
        String Password = new String(PasswordField.getText());
        String confirmPassword = new String(ConfirmPasswordField.getText());

        if (!Password.equals(confirmPassword)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Password and password confirmation have to be identical!");
            message.show();
            Register = false;
        }

        if (!PasswordRequirementFullfilled(Password)) {
            Register = false;
        }

        ResultSet existingUsers = mydbhandler.SelectData("User", "Username", "Username", UsernameField.getText());
        if (mydbhandler.doesExist(existingUsers)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Username is already taken!");
            message.show();
            Register = false;

        }

        ResultSet rs = mydbhandler.SelectData("User", "Email", "Email", EMailField.getText());
        if (mydbhandler.doesExist(rs)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Email address is already in use!");
            message.show();
            Register = false;

        }

        if (Register) {
            Hash Enc = new Hash();
            String salt = Enc.getSalt();
            String pw = Enc.hash(PasswordField.getText() + salt);

            mydbhandler.createUser(FirstNameField.getText(), LastNameField.getText(), UsernameField.getText(),
                    pw, salt, EMailField.getText(), CityField.getText(), AddressField.getText());
            message.setAlertType(Alert.AlertType.CONFIRMATION);
            message.setContentText("Registration successfull");
            message.show();
            Mail.sendEmailRegistration(EMailField.getText());
            SceneSwitcher sceneSwitcher = new SceneSwitcher();
            sceneSwitcher.switchToLogin(event);
        }

    }

    @FXML
    void GoBackButtonClicked(ActionEvent event) throws IOException, SQLException {

        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchToLogin(event);

    }
}



