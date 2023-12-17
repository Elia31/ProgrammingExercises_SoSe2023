package com.example.carsellingplatform;

import com.example.carsellingplatform.backend.model.User;
import com.example.carsellingplatform.backend.util.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane backToDashboard;

    @FXML
    private ComboBox<String> brand;

    @FXML
    private ComboBox<String> model;

    @FXML
    private ComboBox<Object> registrationDate;

    @FXML
    private ComboBox<Object> price;

    @FXML
    private ComboBox<Object> mile;

    @FXML
    private ComboBox<Object> seat;

    @FXML
    private Label searchTextLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private VBox listingsBox;

    @FXML
    private Button myListings;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        try {
            brand.getItems().addAll(setBrands());
            brand.setOnAction(e -> {
                String selectedBrand = brand.getValue();
                model.getItems().clear();
                try {
                    model.getItems().addAll(setModels(selectedBrand));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        registrationDate.setItems(setRegistrationDates());
        price.setItems(setPrices());
        mile.setItems(setMiles());
        seat.setItems(setSeats());

        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        ResultSet rs = databaseHandler.selectListings();

        initializeListings(rs);

    }

    //Search Button
    public void search(ActionEvent e) {
        searchTextLabel.setText("");
        // Filter values from ComboBoxes
        String selectedBrand = brand.getValue();
        String selectedModel = model.getValue();
        Object selectedRegistrationDate = registrationDate.getValue();
        Object selectedPrice = price.getValue();
        Object selectedMile = mile.getValue();
        Object selectedSeat = seat.getValue();

        // Clear previous results
        listingsBox.getChildren().clear();

        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

        // Pass filter values to the databaseHandler's method for retrieving filtered results
        ResultSet rs = null;
        try {
            rs = databaseHandler.selectFilteredData(selectedBrand, selectedModel, selectedRegistrationDate, selectedPrice, selectedMile, selectedSeat);

            if (!rs.isBeforeFirst()){
                    searchTextLabel.setText("There are no Listings that match your criteria!");
            }
            initializeListings(rs);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //Methode for the reset Button
    public void resetButton(ActionEvent e){
        listingsBox.getChildren().clear();
        searchTextLabel.setText("");

        brand.setValue("default");
        model.getItems().clear();
        registrationDate.setValue("default");
        price.setValue("default");
        seat.setValue("default");
        mile.setValue("default");

        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        ResultSet rs = databaseHandler.selectListings();
        initializeListings(rs);
    }

    //switch to the addListing if createListing button is pressed
    public void createListingButton(ActionEvent e) throws IOException {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        String username = usernameLabel.getText();
        User user = null;
        ResultSet FirstNameR = databaseHandler.SelectData("User","FirstName","Username",username);
        ResultSet LastNameR = databaseHandler.SelectData("User","LastName","Username",username);
        ResultSet EmailR = databaseHandler.SelectData("User","Email","Username",username);
        ResultSet AddressR = databaseHandler.SelectData("User","Address","Username",username);
        ResultSet CityR = databaseHandler.SelectData("User","City","Username",username);
        try {
            if (FirstNameR.next() && LastNameR.next() && EmailR.next() && AddressR.next() && CityR.next()) {
                String FirstName = FirstNameR.getString("FirstName");
                String LastName = LastNameR.getString("LastName");
                String Email = EmailR.getString("Email");
                String Address = AddressR.getString("Address");
                String City = CityR.getString("City");
                user = new User(FirstName,LastName,username,Email,Address,City);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        SceneSwitcher sceneswitcher = new SceneSwitcher();
        sceneswitcher.switchToAddListing(e,user);
    }

    //Switch to the login Screen if the logout Button is pressed
    public void logoutButton(ActionEvent e) throws IOException {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchToLogin(e);
    }

    //set the username label to the username
    public void setUser(User user) {
       usernameLabel.setText(user.getUsername());
    }

    //viewListingButton
    public void viewListingButton(ActionEvent e, User user, String id) throws IOException {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        int ListingID = Integer.parseInt(id); // Converting String id to int ListingID
        sceneSwitcher.switchToListingView(e, user, ListingID);
    }


    @FXML
    public void myListingsButton(ActionEvent event) throws IOException {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        String username = usernameLabel.getText();
        User user = null;
        ResultSet FirstNameR = databaseHandler.SelectData("User","FirstName","Username",username);
        ResultSet LastNameR = databaseHandler.SelectData("User","LastName","Username",username);
        ResultSet EmailR = databaseHandler.SelectData("User","Email","Username",username);
        ResultSet AddressR = databaseHandler.SelectData("User","Address","Username",username);
        ResultSet CityR = databaseHandler.SelectData("User","City","Username",username);
        try {
            if (FirstNameR.next() && LastNameR.next() && EmailR.next() && AddressR.next() && CityR.next()) {
                String FirstName = FirstNameR.getString("FirstName");
                String LastName = LastNameR.getString("LastName");
                String Email = EmailR.getString("Email");
                String Address = AddressR.getString("Address");
                String City = CityR.getString("City");
                user = new User(FirstName,LastName,username,Email,Address,City);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        SceneSwitcher sceneswitcher = new SceneSwitcher();
        sceneswitcher.switchToMyListings(event,user);
    }

    public void myProfilButton(ActionEvent event) throws IOException {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        String username = usernameLabel.getText();
        User user = null;
        ResultSet FirstNameR = databaseHandler.SelectData("User","FirstName","Username",username);
        ResultSet LastNameR = databaseHandler.SelectData("User","LastName","Username",username);
        ResultSet EmailR = databaseHandler.SelectData("User","Email","Username",username);
        ResultSet AddressR = databaseHandler.SelectData("User","Address","Username",username);
        ResultSet CityR = databaseHandler.SelectData("User","City","Username",username);
        try {
            if (FirstNameR.next() && LastNameR.next() && EmailR.next() && AddressR.next() && CityR.next()) {
                String FirstName = FirstNameR.getString("FirstName");
                String LastName = LastNameR.getString("LastName");
                String Email = EmailR.getString("Email");
                String Address = AddressR.getString("Address");
                String City = CityR.getString("City");
                user = new User(FirstName,LastName,username,Email,Address,City);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        SceneSwitcher sceneswitcher = new SceneSwitcher();
        sceneswitcher.switchToEditProfil(event,user);
    }


    //Fill be combobox for Brands with data from the database
    public List<String> setBrands() throws SQLException {
        List<String> list = new ArrayList<>();
        list.add("default");
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        ResultSet rs = databaseHandler.getResultSet("SELECT DISTINCT Brand FROM Listing");
        while(rs.next()){
            list.add(rs.getString("Brand"));
        }
        return list;
    }

    //save all models from the database into a list
    public List<String> setModels(String brand) throws SQLException {
        List<String> list = new ArrayList<>();
        if (brand != "default") {
            list.add("default");
            DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
            ResultSet rs = databaseHandler.getResultSet("SELECT DISTINCT Model FROM Listing WHERE Brand = '" + brand + "'");
            while (rs.next()) {
                list.add(rs.getString("Model"));
            }
        }
        return list;
    }

    //fill the combobox price
    public ObservableList<Object> setPrices(){
        ObservableList<Object> prices = FXCollections.observableArrayList();
        prices.add("default");
        for (int i = 5000; i <= 50000; i += 5000){
            prices.add(i);
        }
        for (int i = 60000; i <= 100000; i += 10000){
            prices.add(i);
        }
        for (int i = 200000; i <= 1000000; i += 100000){
            prices.add(i);
        }
        return prices;
    }

    //fill the combobox mile
    public ObservableList<Object> setMiles(){
        ObservableList<Object> miles = FXCollections.observableArrayList();
        miles.add("default");
        for (int i = 10000; i <= 50000; i += 10000){
            miles.add(i);
        }
        for (int i = 75000; i <= 250000; i += 25000){
            miles.add(i);
        }
        for (int i = 300000; i <= 500000; i += 50000){
            miles.add(i);
        }
        return miles;
    }

    //fill seats combobox
    public ObservableList<Object> setSeats(){
        ObservableList<Object> seats = FXCollections.observableArrayList(
                "default", 2, 3, 4, 5,7
        );
        return seats;
    }

    //fill registrationDate combobox
    public ObservableList<Object> setRegistrationDates(){
        ObservableList<Object> registrationDates = FXCollections.observableArrayList();
        registrationDates.add("default");
        for (int i = 2023; i >= 1940; i--){
            registrationDates.add(i);
        }
        return registrationDates;
    }

   public void initializeListings(ResultSet rs){
       try {
           List<HBox> listings = new ArrayList<>();
           while (rs.next()) {
               HBox listing = new HBox();
               listing.setSpacing(10);
               listing.setPrefHeight(100);

               //load and display the image of the listing
               ImageView carImage = new ImageView();
               carImage.setFitHeight(100);
               carImage.setFitWidth(100);
               Blob blob = rs.getBlob(5);
               if (blob != null) {
                   InputStream inputStream = blob.getBinaryStream();
                   Image image = new Image(inputStream);
                   carImage.setImage(image);
               }
               else {
                   Image image = new Image(new FileInputStream("src/main/resources/com/example/carsellingplatform/images/No-Image-Placeholder.svg.png"));
                   carImage.setImage(image);
               }

               //VBox for Name and Description
               VBox listingInfos = new VBox(5);
               listingInfos.setPrefWidth(250);

               //add Name to the listing
               String listingName = rs.getString("ListingName");
               Label listingNameLabel = new Label(listingName);
               listingNameLabel.setFont(new Font(20));
               listingInfos.getChildren().add(listingNameLabel);

               //add description to the listing
               String description = rs.getString("Description");
               Label descriptionLabel = new Label(description);
               listingInfos.getChildren().add(descriptionLabel);

               //Button to view the listing
               Button viewListing = new Button("View Listing");
               String listingID = rs.getString("ListingID");

               viewListing.setOnAction(e -> {
                   User user = new User("?", "?", usernameLabel.getText(), "?", "?", "?");
                   try {
                       viewListingButton(e, user , listingID);
                   } catch (IOException ioException) {
                       ioException.printStackTrace();
                   }
               });


               //vbox for seats, registration year and kilometers
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

               //Vbox for Price and the view listing button
               VBox box3 = new VBox(5);
               box3.setPrefWidth(250);
               String listingPrice = "" + rs.getString("Price") + "€";
               Label priceLabel = new Label(listingPrice);
               priceLabel.setFont(new Font(20));
               box3.getChildren().add(priceLabel);
               box3.getChildren().add(viewListing);

               //add all elements to the Hbox
               listing.getChildren().add(carImage);
               listing.getChildren().add(listingInfos);
               listing.getChildren().add(box2);
               listing.getChildren().add(box3);
               //listing.getChildren().add(viewListing);
               listings.add(listing);
           }
           //add all listings to the main list
           listingsBox.setSpacing(5);
           listingsBox.getChildren().addAll(listings);
       }
       catch (SQLException | FileNotFoundException e){
           e.printStackTrace();
       }
   }
}

