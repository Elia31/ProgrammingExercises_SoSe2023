package com.example.carsellingplatform;

import com.example.carsellingplatform.backend.model.User;
import com.example.carsellingplatform.backend.util.DatabaseHandler;
import com.example.carsellingplatform.backend.util.Mail;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListingViewController implements Initializable {

    private byte[] imageBytes;


    @FXML
    private StackPane tableViewPaneTechnical;

    @FXML
    private StackPane tableViewPaneAusstattung;


    @FXML
    private TextField ListingNameTextfield;

    @FXML
    private TextField priceTextfield;

    @FXML
    private Button showButton;

    @FXML
    private StackPane tableViewPane;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private Button writeMailButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TableView<ObservableList<String>> listingTable;

    @FXML
    private TableColumn<ObservableList<String>, String> airBagsColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> airConditioningColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> bodyTypeColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> brandColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> colorColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> conditionColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> consumptionColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> cylinderCapacityColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> descriptionColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> doorsColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> emissionClassColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> engineColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> environmentalBadgeColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> fuelTypeColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> gearboxColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> generalInspectionColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> interiorColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> listingNameColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> milesColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> modelColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> ownersColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> performanceColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> priceColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> registrationDateColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> seatsColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> usernameColumn;

    @FXML
    private ImageView carImageView;

    @FXML
    private TableView<ObservableList<String>> technicalDataTable;

    @FXML
    private TableView<ObservableList<String>> carAusstattungTable;


    private User user;
    private int listingID;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Your initialization code
    }

    public void loadListingData() {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        ResultSet rs = databaseHandler.selectListingAndUser(listingID);

        try {
            if (rs.next()) {
                String listingName = rs.getString("ListingName");
                double price = rs.getDouble("Price");
                String username = rs.getString("Username");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                String city = rs.getString("City");
                Blob blob = rs.getBlob("Picture");

                ListingNameTextfield.setText(listingName);
                priceTextfield.setText(String.valueOf(price));
                usernameTextfield.setText(username);
                emailTextField.setText(email);
                addressTextField.setText(address);
                cityTextField.setText(city);

                if (blob != null) {
                    byte[] imageBytes = ((Blob) blob).getBytes(1, (int) blob.length());
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                    Image image = new Image(inputStream);
                    carImageView.setImage(image);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
        loadListingData();
    }

    public int getListingID() {
        return listingID;
    }

    public void backButton(ActionEvent e) throws IOException {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchToDashboard(e, user);
    }

    public void extendButton() {

        tableViewPaneTechnical.setVisible(true);
        tableViewPaneAusstattung.setVisible(true);


        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        ResultSet rs = databaseHandler.selectListingById(listingID);

        try {
            ObservableList<ObservableList<String>> technicalData = FXCollections.observableArrayList();
            ObservableList<ObservableList<String>> carAusstattung = FXCollections.observableArrayList();

            if (rs.next()) {
                ObservableList<String> technicalDataRow = FXCollections.observableArrayList();
                ObservableList<String> carAusstattungRow = FXCollections.observableArrayList();

                // Populate technical data
                technicalDataRow.add(rs.getString("Brand"));
                technicalDataRow.add(rs.getString("Colour"));
                technicalDataRow.add(rs.getString("Model"));
                technicalDataRow.add(rs.getString("BodyType"));
                technicalDataRow.add(rs.getString("Doors"));
                technicalDataRow.add(rs.getString("Seats"));
                technicalDataRow.add(rs.getString("RegistrationDate"));
                technicalDataRow.add(rs.getString("generalInspection"));
                technicalDataRow.add(rs.getString("Consumption"));
                technicalDataRow.add(rs.getString("Miles"));
                technicalDataRow.add(rs.getString("Owners"));
                technicalDataRow.add(rs.getString("EmissionClass"));
                technicalDataRow.add(rs.getString("EnvironmentalBadge"));
                technicalDataRow.add(rs.getString("Engine"));
                technicalDataRow.add(rs.getString("Performance"));
                technicalDataRow.add(rs.getString("CylinderCapacity"));
                technicalDataRow.add(rs.getString("Gearbox"));
                technicalDataRow.add(rs.getString("FuelType"));

                // Populate car Ausstattung
                carAusstattungRow.add(rs.getString("Description"));
                carAusstattungRow.add(rs.getString("Interior"));
                carAusstattungRow.add(rs.getString("Condition"));
                carAusstattungRow.add(rs.getString("AirConditioning"));
                carAusstattungRow.add(rs.getString("AirBags"));


                technicalData.add(technicalDataRow);
                carAusstattung.add(carAusstattungRow);
            }

            technicalDataTable.setItems(technicalData);
            carAusstattungTable.setItems(carAusstattung);

            // Set CellValueFactory for each column in technical data table
            brandColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
            colorColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
            modelColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
            bodyTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3)));
            doorsColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(4)));
            seatsColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(5)));
            registrationDateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(6)));
            generalInspectionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(7)));
            consumptionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(8)));
            milesColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(9)));
            ownersColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(10)));
            emissionClassColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(11)));
            environmentalBadgeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(12)));
            engineColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(13)));
            performanceColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(14)));
            cylinderCapacityColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(15)));
            gearboxColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(16)));
            fuelTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(17)));

            // Set CellValueFactory for each column in car Ausstattung table
            descriptionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
            interiorColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
            conditionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
            airConditioningColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3)));
            airBagsColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(4)));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Get Images
    public byte[] getImageBytes(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] result = new byte[width * height * 4];

        PixelReader pixelReader = image.getPixelReader();
        ByteBuffer buffer = ByteBuffer.wrap(result);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                buffer.put((byte) (color.getRed() * 255));
                buffer.put((byte) (color.getGreen() * 255));
                buffer.put((byte) (color.getBlue() * 255));
                buffer.put((byte) (color.getOpacity() * 255));
            }
        }
        return result;
    }


    public void writeEmail(ActionEvent e) {
        int listingID = getListingID(); // Replace with the actual listing ID

        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        ResultSet rs = databaseHandler.selectSellerEmailAndListingName(listingID);

        try {
            if (rs.next()) {
                String sellerEmail = rs.getString("Email");
                String listingName = rs.getString("ListingName");

                // Open a dialog for the user to enter the email text
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Compose Email");
                TextArea textArea = new TextArea();
                textArea.setWrapText(true);
                textArea.setPrefHeight(300);
                dialog.getDialogPane().setContent(textArea);

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String emailText = textArea.getText(); // Retrieve the email text from the TextArea

                    if (!emailText.isEmpty()) {
                        //String senderEmail = user.getEmail(); // Fetch the sender's email
                        String senderUsername = user.getUsername(); // Fetch the sender's username
                        String senderEmail = databaseHandler.getEmailIfExists(senderUsername);


                        // Perform the email sending using the Mail class
                        Mail.sendMessage(sellerEmail, senderEmail, senderUsername, listingName, emailText);

                        // Show a confirmation message
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Email Sent");
                        alert.setHeaderText(null);
                        alert.setContentText("The email has been sent.");
                        alert.showAndWait();
                    } else {
                        // Show an error message if the email text is empty
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Email Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter your email message.");
                        alert.showAndWait();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}


