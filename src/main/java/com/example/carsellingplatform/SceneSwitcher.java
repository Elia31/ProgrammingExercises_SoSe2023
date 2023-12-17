package com.example.carsellingplatform;

import com.example.carsellingplatform.backend.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToRegistration(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



    public void switchToLogin(ActionEvent event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public void switchToDashboard(ActionEvent event, User user) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        DashboardController controller = loader.<DashboardController>getController();
        controller.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        //Set dashboard to the middle of the screen of the user
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);

        stage.show();
    }


    public void switchToAddListing(ActionEvent e, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddListing.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        AddListingController controller = loader.<AddListingController>getController();
        controller.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToEditListing(ActionEvent e, User user,int ListingID) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditListing.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        EditListingController controller = loader.<EditListingController>getController();
        controller.setUser(user);
        controller.setListingID(ListingID);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
        public void switchToMyListings(ActionEvent e, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyListings.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        MyListingsController controller = loader.<MyListingsController>getController();
        controller.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }

    public void switchToListingView(ActionEvent e, User user, int listingID) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListingView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        ListingViewController controller = loader.getController();
        controller.setUser(user);
        controller.setListingID(listingID);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }

    public void switchToResetPassword(ActionEvent e) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResetPassword.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        ResetPasswordController controller = loader.<ResetPasswordController>getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToEditProfil(ActionEvent e, User user) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProfil.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        EditProfilController controller = loader.<EditProfilController>getController();
        controller.setUser(user);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
