package com.example.carsellingplatform;

import com.example.carsellingplatform.backend.util.DatabaseHandler;
import com.example.carsellingplatform.backend.util.Hash;
import com.example.carsellingplatform.backend.util.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {

    Alert message = new Alert(Alert.AlertType.NONE);

    @FXML
    private TextField CodeTextField;

    @FXML
    private PasswordField ConfirmNewPasswordTextField;

    @FXML
    private Label ConfirmPasswordLabel;

    @FXML
    private PasswordField NewPasswordTextField;

    @FXML
    private Label PasswordLabel;

    @FXML
    private Label ResetPasswordTextField;

    @FXML
    private TextField UsernameEmailTextField;

    @FXML
    private Button backButton;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label VerificationCodeLabel;

    @FXML
    private Button confirmCode;

    @FXML
    private Button resetPassword;

    @FXML
    private Button sendCode;

    private String onetimeCode = null;
    private String userMail = null;
    private String newPassword = null;
    private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.CodeTextField.setVisible(false);
        this.confirmCode.setVisible(false);
        this.NewPasswordTextField.setVisible(false);
        this.ConfirmNewPasswordTextField.setVisible(false);
        this.sendCode.setVisible(true);
        this.resetPassword.setVisible(false);
        this.VerificationCodeLabel.setVisible(false);
        this.PasswordLabel.setVisible(false);
        this.ConfirmPasswordLabel.setVisible(false);

    }

    @FXML
    private void sendCodeClicked(ActionEvent event) {

        userMail = databaseHandler.getEmailIfExists(UsernameEmailTextField.getText());

        //if username or email exists
        if(userMail != null)
        {
            onetimeCode = Hash.getSalt().substring(26).toUpperCase();
            Mail.sendOnetimeCode(userMail, onetimeCode);
            //System.out.println("pop up mit code wurde versendet");

            this.CodeTextField.setVisible(true);
            this.confirmCode.setVisible(true);
            this.VerificationCodeLabel.setVisible(true);
            this.sendCode.setVisible(false);
        } else {
            //System.out.println("pop up mit username oder email konnte nicht gefunden werden");
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("No Account linked to this Email address or Username could be found!");
            message.show();
        }
    }


    @FXML
    private void confirmCodeClicked(ActionEvent event) {

        if(CodeTextField.getText().equals(onetimeCode))
        {
            //System.out.println("pop up mit code ist korrekt bitte setz dein passwort jetzt");


            this.confirmCode.setVisible(false);
            this.NewPasswordTextField.setVisible(true);
            this.ConfirmNewPasswordTextField.setVisible(true);
            this.resetPassword.setVisible(true);
            this.PasswordLabel.setVisible(true);
            this.ConfirmPasswordLabel.setVisible(true);
        } else {
            //System.out.println("pop up mit code war nicht korrekt");
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("The Code you entered was not correct!");
            message.show();
        }
    }

    @FXML
    private void resetPasswordClicked(ActionEvent event) throws IOException
    {
        boolean passValid = false;
        //hier passwort auf kriterien untersuchen oder so
        if(RegistrationController.PasswordRequirementFullfilled(NewPasswordTextField.getText()))
        {
            passValid = true;
        }
        if(NewPasswordTextField.getText().equals(ConfirmNewPasswordTextField.getText()) && passValid)
        {
            Mail.sendNewPasswordConfirmation(userMail);
            databaseHandler.updateUserPassword(userMail,NewPasswordTextField.getText());
            //System.out.println("pop up mit dein passwort wurde geändert");

            SceneSwitcher sceneswitcher = new SceneSwitcher();
            sceneswitcher.switchToLogin(event);
        } else {
            //System.out.println("pop up mit passwort nicht gleich oder zu kurz");
        }
    }


    public void backButtonClicked(ActionEvent event) throws IOException{
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchToLogin(event);
    }
}

