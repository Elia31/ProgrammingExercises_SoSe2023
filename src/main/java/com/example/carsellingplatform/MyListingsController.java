package com.example.carsellingplatform;

import com.example.carsellingplatform.backend.model.User;
import com.example.carsellingplatform.backend.util.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyListingsController implements Initializable {
    private User user;
    private ResultSet rs;

    @FXML
    private Button backToDashboardButton;

    @FXML
    private VBox listingsBox;

    @FXML
    private Button myProfile;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label noListingsLabel;

    @FXML
    private GridPane gridPane;

    public void setUser(User user) {
        this.user = user;
        usernameLabel.setText("User:" + user.getUsername());
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        this.rs = databaseHandler.SelectData("Listing", "*", "Username", user.getUsername());
        initMyListings(); // Initialize listings once user is set
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (user == null) {
            //System.out.println("User object is null");
        }
    }

    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchToDashboard(event, user);
    }

    private void initMyListings() {
        try {
            DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
            this.rs = databaseHandler.selectListingWithUsername(user.getUsername());
            if (!rs.isBeforeFirst()){
                noListingsLabel.setText("You have no Listings.");
            }

            List<HBox> listings = new ArrayList<>();
            while (rs.next()) {
                HBox listing = new HBox();
                listing.setSpacing(10);
                listing.setPrefHeight(100);

                //Kp warum das nicht geht
                ImageView carImage = new ImageView();
                carImage.setFitHeight(100);
                carImage.setFitWidth(100);
                Blob blob = rs.getBlob("Picture");
                if (blob != null) {
                    try {
                        InputStream inputStream = blob.getBinaryStream();
                        Image image = new Image(inputStream);
                        carImage.setImage(image);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Handle the exception, such as logging an error message or showing a default image
                    }
                } else {
                    try {
                        Image image = new Image(new FileInputStream("src/main/resources/com/example/carsellingplatform/images/No-Image-Placeholder.svg.png"));
                        carImage.setImage(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        // Handle the exception, such as logging an error message or showing a default image
                    }
                }



                VBox listingInfos = new VBox(5);
                listingInfos.setPrefWidth(250);

                String listingName = rs.getString("ListingName");
                Label listingNameLabel = new Label(listingName);
                listingNameLabel.setFont(new Font(20));
                listingInfos.getChildren().add(listingNameLabel);

                String description = rs.getString("Description");
                Label descriptionLabel = new Label(description);
                listingInfos.getChildren().add(descriptionLabel);


                VBox box2 = new VBox(5);
                box2.setPrefWidth(250);
                box2.getChildren().add(new Label());
                String regDate = "Registration: " + rs.getString("RegistrationDate");
                Label regDateLabel = new Label(regDate);
                String seats = "Seats: " + rs.getString("Seats");
                Label seatsLabel = new Label(seats);
                String kms = "Kilometers: " + rs.getString("Miles");
                Label kmLabel = new Label(kms);
                box2.getChildren().add(regDateLabel);
                box2.getChildren().add(seatsLabel);
                box2.getChildren().add(kmLabel);

                VBox box3 = new VBox(5);
                box3.setPrefWidth(250);
                String listingPrice = "" + rs.getString("Price") + "€";
                Label priceLabel = new Label(listingPrice);
                priceLabel.setFont(new Font(20));
                box3.getChildren().add(priceLabel);

                Button editButton = new Button("Edit Listing");
                String listingID = rs.getString("ListingID");
                editButton.setOnAction(e -> {
                    try {
                        editListing(e, user, listingID);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                box3.getChildren().add(editButton);

                Button DeleteButton = new Button("Delete Listing");
                DeleteButton.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Deletion");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to delete this listing?");


                    ButtonType buttonTypeYes = new ButtonType("Yes");
                    ButtonType buttonTypeNo = new ButtonType("No");
                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);


                    alert.showAndWait().ifPresent(result -> {
                        if (result == buttonTypeYes) {
                            DatabaseHandler mydbhandler = DatabaseHandler.getInstance();
                            mydbhandler.DeleteListing(listingID);
                            listingsBox.getChildren().clear(); // Clear the existing listings
                            initMyListings();

                        }
                    });
                });
                box3.getChildren().add(DeleteButton);

                listing.getChildren().add(carImage);
                listing.getChildren().add(listingInfos);
                listing.getChildren().add(box2);
                listing.getChildren().add(box3);
                listings.add(listing);
            }
            listingsBox.setSpacing(5);
            listingsBox.getChildren().addAll(listings);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editListing(ActionEvent e,User user, String listingID) throws IOException {

        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        int id = Integer.parseInt(listingID);
        sceneSwitcher.switchToEditListing(e, user, id);
    }
}



