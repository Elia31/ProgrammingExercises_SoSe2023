package com.example.carsellingplatform;


import com.example.carsellingplatform.backend.model.User;
import com.example.carsellingplatform.backend.util.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;


public class AddListingController implements Initializable  {

    Alert message = new Alert(Alert.AlertType.NONE);

    private User user;
    private byte[] imageBytes;


    public void setUser(User user){this.user = user;}

    @FXML
    private Label ACLabel;

    @FXML
    private Button GoBack;

    @FXML
    private Pane AddListingPane;

    @FXML
    private Label AirbagsLabel;

    @FXML
    private ChoiceBox<String> BodyTypeChoice;

    @FXML
    private Label BodyTypeLabel;

    @FXML
    private Label BrandLabel;

    @FXML
    private TextField BrandTextfield;

    @FXML
    private ImageView CarPicture;

    @FXML
    private Label ColourLabel;

    @FXML
    private ChoiceBox<String> ColourPicker;

    @FXML
    private Label ConsumptionLabel;

    @FXML
    private TextField ConsumptionTextfield;

    @FXML
    private Label CylinderCapacityLabel;

    @FXML
    private ChoiceBox<Integer> CylinderCapacityChoice;

    @FXML
    private TextArea DescriptionArea;

    @FXML
    private Label DescriptionLabel;

    @FXML
    private ChoiceBox<String> DoorsChoice;

    @FXML
    private Label DoorsLabel;

    @FXML
    private ChoiceBox<String> EmissionClassChoice;

    @FXML
    private Label EmissionClassLabel;

    @FXML
    private ChoiceBox<String> EngineChoice;

    @FXML
    private Label EngineLabel;

    @FXML
    private ChoiceBox<String> EnvironmentalBadgeChoice;

    @FXML
    private Label EnvironmentalBadgeLabel;

    @FXML
    private ChoiceBox<String> FuelChoice;

    @FXML
    private Label FuelTypeLabel;

    @FXML
    private ChoiceBox<String> GearboxChoice;

    @FXML
    private Label GearboxLabel;

    @FXML
    private DatePicker GeneralInspectionDate;

    @FXML
    private Label GeneralInspectionLabel;

    @FXML
    private Label InteriorLabel;

    @FXML
    private Label ListingNameLabel;

    @FXML
    private TextField ListingNameTextfield;

    @FXML
    private Label MilesLabel;

    @FXML
    private TextField MilesTextField;

    @FXML
    private Label ModelLabel;

    @FXML
    private TextField ModelTextField;

    @FXML
    private ChoiceBox<Integer> OwnersChoice;

    @FXML
    private Label OwnersLabel;

    @FXML
    private Label PerformanceLabel;

    @FXML
    private TextField PerformanceTextField;

    @FXML
    private Label PriceLabel;

    @FXML
    private TextField PriceTextField;

    @FXML
    private DatePicker RegistrationDate;

    @FXML
    private Label RegistrationDateLabel;

    @FXML
    private ChoiceBox<Integer> SeatsChoice;

    @FXML
    private Label SeatsLabel;

    @FXML
    private Label createListingLabel;

    @FXML
    private ChoiceBox<String> ConditionChoice;

    @FXML
    private Label ConditionLabel;

    @FXML
    private ChoiceBox<String> AirBagChoice;

    @FXML
    private ChoiceBox<String> InteriorMaterialChoice;

    @FXML
    private ChoiceBox<String> ACChoice;

    @FXML
    private Button createListingButton;

    @FXML
    private Button AddPictureButton;



    /*private static final Map<String, String> colorMap = createColourMap();
    private static Map<String, String> createColourMap() {
        Map<String, String> map = new HashMap<>();
        map.put("0xff00ffff", "Aqua");
        map.put("0xff000000", "Black");
        map.put("0xff0000ff", "Blue");
        map.put("0xffff00ff", "Fuchsia");
        map.put("0xff808080", "Gray");
        map.put("0xff00ff00", "Green");
        map.put("0xff00ff00", "Lime");
        map.put("0xff800000", "Maroon");
        map.put("0xff000080", "Navy");
        map.put("0xff808000", "Olive");
        map.put("0xffffa500", "Orange");
        map.put("0xff800080", "Purple");
        map.put("0xffff0000", "Red");
        map.put("0xffc0c0c0", "Silver");
        map.put("0xff008080", "Teal");
        map.put("0xffffffff", "White");
        map.put("0xffffff00", "Yellow");
        map.put("0xfff0f8ff", "Alice Blue");
        map.put("0xfffaebd7", "Antique White");
        map.put("0xff7fffd4", "Aquamarine");
        map.put("0xfff0ffff", "Azure");
        map.put("0xfff5f5dc", "Beige");
        map.put("0xffffe4c4", "Bisque");
        map.put("0xffffebcd", "Blanched Almond");

        return map;
    }

    public static String getColourName(String colourCode) {
        String colourName = colorMap.get(colourCode);
        if (colourName != null) {
            return colourName;
        } else {
            return "Unknown";
        }
    }*/

    private String[] Colours = {"Alice Blue", "Antique White", "Aqua", "Aquamarine", "Azure", "Beige", "Bisque",
            "Black", "Blanched Almond", "Blue", "Fuchsia", "Gray", "Green", "Lime", "Maroon", "Navy", "Olive", "Orange",
            "Purple", "Red", "Silver", "Teal", "White", "Yellow"};

    private String[] BodyTypes = {"Hatchback", "Sedan", "MUV/SUV", "Coupe", "Convertible", "Wagon", "Van", "Pickup Truck"};
    private String[] EmissionClasses ={"Euro1","Euro2","Euro3","Euro4","Euro5","Euro6","Euro6c","Euro6d-TEMP","Euro6d"};

    private String[] EnvironmentalBadges ={"None","Red","Yellow","Green","Blue"};

    private String[] FuelTypes = {"Petrol","Diesel", "Compressed Natural Gas (CNG)", "Bio-Diesel", "Liquid Petroleum Gas (LPG)",
            "Ethanol" ,"Methanol","Electric","Hybrid (Petrol)","Hybrid (Diesel)"};

    private String[] EngineTypes = {"Boxer Four", "Boxer Six", "Inline Three", "Inline Four", "Inline Five", "Inline Six",
            "Inline Eight", "Inline Ten", "Rotary", "V6", "V8", "V10", "V12", "W8", "W12", "W16"};

    private String[] Gearboxes = {"Automatic","Manual","Half-Automatic"};

    private String[] doors = {"none","2/3","4/5","6/7"};

    private Integer[] seats = {1,2,5,7};

    private Integer[] preOwners =  {0,1,2,3,4,5,6,7,8,9};

    private String[] Conditions = {"New","Used","Accident Vehicle","Oldtimer"};

    private Integer[] CylinderCapacities ={600,800,900,1000,1200,1400, 1600, 1800, 2000,2200,2400,2500,2800,
            3000,3200,3500,3600,3800,4000,4200,4400,4600,5000,5500,6000,6200,6400,6600,7000 };

    private String[] AirBags = {"Driver-Airbag", "Front-Airbags", "Front- & Side-Airbags",
            "Front- Side- & more Airbags"};

    private String[] InteriorMaterials = {"Alcantara","Fabric","Partial leather","velour","full leather","Other"};

    private String[] AC = {"None", "Air conditioning or automatic","climate control","2-zone automatic climate control",
            "3-zone automatic climate control","4-zone automatic climate control"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConditionChoice.getItems().addAll(Conditions);
        ColourPicker.getItems().addAll(Colours);
        BodyTypeChoice.getItems().addAll(BodyTypes);
        DoorsChoice.getItems().addAll(doors);
        SeatsChoice.getItems().addAll(seats);
        InteriorMaterialChoice.getItems().addAll(InteriorMaterials);
        AirBagChoice.getItems().addAll(AirBags);
        ACChoice.getItems().addAll(AC);
        OwnersChoice.getItems().addAll(preOwners);
        EmissionClassChoice.getItems().addAll(EmissionClasses);
        EnvironmentalBadgeChoice.getItems().addAll(EnvironmentalBadges);
        FuelChoice.getItems().addAll(FuelTypes);
        EngineChoice.getItems().addAll(EngineTypes);
        CylinderCapacityChoice.getItems().addAll(CylinderCapacities);
        GearboxChoice.getItems().addAll(Gearboxes);

        PriceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                PriceTextField.setText(oldValue);
                message.setAlertType(Alert.AlertType.ERROR);
                message.setContentText("Please only enter numbers for the price value");
                message.show();

            }
        });

        MilesTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                MilesTextField.setText(oldValue);
                message.setAlertType(Alert.AlertType.ERROR);
                message.setContentText("Please only enter numbers for the miles value");
                message.show();

            }
        });

        PerformanceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                PerformanceTextField.setText(oldValue);
                message.setAlertType(Alert.AlertType.ERROR);
                message.setContentText("Please only enter numbers for the performance value");
                message.show();

            }
        });

        ConsumptionTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                ConsumptionTextfield.setText(oldValue);
                message.setAlertType(Alert.AlertType.ERROR);
                message.setContentText("Please only enter numbers for the consumption value");
                message.show();

            }
        });
    }

    @FXML
    void AddPictureButtonClicked(ActionEvent event) throws IOException {
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File file = filechooser.showOpenDialog(null);

        if(file != null){
            this.imageBytes = Files.readAllBytes(file.toPath());
            Image image = new Image(file.getAbsolutePath());
            CarPicture.setImage(image);
        }

    }

    @FXML
    void CreateListingButtonClicked(ActionEvent event) throws IOException {
        boolean createListing = true;
        java.time.LocalDate currentDate = java.time.LocalDate.now();


        if (ListingNameTextfield.getText() == null || ListingNameTextfield.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please enter a name for your listing!");
            message.show();
            createListing = false;
        }

        if (PriceTextField.getText() == null || PriceTextField.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please enter a price for your listing!");
            message.show();
            createListing = false;
        }

        if (BrandTextfield.getText() == null || BrandTextfield.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the car brand of your car ");
            message.show();
            createListing = false;
        }

        if (ModelTextField.getText() == null || ModelTextField.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the car model of your car (model description only)");
            message.show();
            createListing = false;
        }

        if (ConsumptionTextfield.getText() == null || ConsumptionTextfield.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the consumption of your car (in liters per 100 km) ");
            message.show();
            createListing = false;
        }

        if (PerformanceTextField.getText() == null || PerformanceTextField.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the performanceof your car (in horsepowers) ");
            message.show();
            createListing = false;
        }

        if (MilesTextField.getText() == null || MilesTextField.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the amount of miles your car has been driven!");
            message.show();
            createListing = false;
        }

        if (ConsumptionTextfield.getText() == null || ConsumptionTextfield.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the consumption of your car (in liters per 100 km) ");
            message.show();
            createListing = false;
        }

        if (DescriptionArea.getText() == "") {
            DescriptionArea.setText("null");
        }

        if (ConditionChoice.getValue() == null || ConditionChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the condition of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (BodyTypeChoice.getValue() == null || BodyTypeChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the body type of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (DoorsChoice.getValue() == null || DoorsChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the number of doors of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (SeatsChoice.getValue() == null || SeatsChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the number of seats of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (InteriorMaterialChoice.getValue() == null || InteriorMaterialChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the interior material of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (AirBagChoice.getValue() == null || AirBagChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the Air Bag equipment of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (ACChoice.getValue() == null || ACChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the Air-Conditioning equipment of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (OwnersChoice.getValue() == null || OwnersChoice.getValue().toString() == "") {
            OwnersChoice.setValue(0);
        }

        if (!(OwnersChoice.getValue() == 0) && ConditionChoice.getValue().toString().equals("New")) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("A car that has been used before (had a pre-owner), can not be listed as new!");
            message.show();
            createListing = false;
        }

        if ((OwnersChoice.getValue() == 0) && (ConditionChoice.getValue().toString().equals("Used"))) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("A car that has never had an owner cannot be used!");
            message.show();
            createListing = false;
        }

        if (EmissionClassChoice.getValue() == null || EmissionClassChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the emission class of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (EnvironmentalBadgeChoice.getValue() == null || EnvironmentalBadgeChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the environmental badge of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (FuelChoice.getValue() == null || FuelChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the compatible fuel type of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (EngineChoice.getValue() == null || EngineChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the engine type of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (CylinderCapacityChoice.getValue() == null || CylinderCapacityChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state cylinder capacity of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (GearboxChoice.getValue() == null || GearboxChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state cylinder capacity of the car you want to sell!");
            message.show();
            createListing = false;
        }

        if (RegistrationDate.getValue().toString() == "" || RegistrationDate.getValue().toString() == null) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the date of your car´s first registration");
            message.show();
            createListing = false;
        }

        if (GeneralInspectionDate.getValue().toString() == "" || GeneralInspectionDate.getValue().toString() == null) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the date of your car´s last general inspection");
            message.show();
            createListing = false;
        }


        if (RegistrationDate.getValue().compareTo(GeneralInspectionDate.getValue()) >= 0) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("The date of the cars last general inspection can not be earlier than the registration date");
            message.show();
            createListing = false;
        }

        if (RegistrationDate.getValue().isAfter(currentDate)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Registration date cannot be set to a future date!");
            message.show();
            createListing = false;
        }

        if (GeneralInspectionDate.getValue().isAfter(currentDate)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Date of last general inspection cannot be set to a future date!");
            message.show();
            createListing = false;
        }



        if (createListing == true) {

            DatabaseHandler mydbhandler = DatabaseHandler.getInstance();
            mydbhandler.createListing(this.user, ListingNameTextfield.getText(), Double.parseDouble(PriceTextField.getText()), ConditionChoice.getValue().toString(),
                    BrandTextfield.getText(), ModelTextField.getText(), BodyTypeChoice.getValue().toString(),ColourPicker.getValue().toString(), DoorsChoice.getValue().toString(),
                    SeatsChoice.getValue(), InteriorMaterialChoice.getValue().toString(), AirBagChoice.getValue().toString(), ACChoice.getValue().toString(),
                    Date.valueOf(RegistrationDate.getValue()), Date.valueOf(GeneralInspectionDate.getValue()), OwnersChoice.getValue(),
                    Integer.parseInt(MilesTextField.getText()), EmissionClassChoice.getValue().toString(), EnvironmentalBadgeChoice.getValue().toString(),
                    Double.parseDouble(ConsumptionTextfield.getText()), FuelChoice.getValue().toString(), EngineChoice.getValue().toString(),
                    Integer.parseInt(PerformanceTextField.getText()), CylinderCapacityChoice.getValue(),
                    GearboxChoice.getValue().toString(), DescriptionArea.getText(), this.imageBytes,
                    new java.sql.Date(java.util.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).getTime()));
            message.setAlertType(Alert.AlertType.CONFIRMATION);
            message.setContentText("Listing created");
            message.show();

            SceneSwitcher sceneswitcher = new SceneSwitcher();
            sceneswitcher.switchToDashboard(event,user);

        }
    }

    @FXML
    void GoBackClicked(ActionEvent e) throws IOException {

        SceneSwitcher sceneswitcher = new SceneSwitcher();
        sceneswitcher.switchToDashboard(e,user);
    }



}

