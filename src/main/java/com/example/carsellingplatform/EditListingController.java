package com.example.carsellingplatform;


import com.example.carsellingplatform.backend.model.User;
import com.example.carsellingplatform.backend.util.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class EditListingController implements Initializable  {

    Alert message = new Alert(Alert.AlertType.NONE);

    private User user;
    private int ListingID;//getListingID();
    private byte[] imageBytes;


    public void setUser(User user){this.user = user;}
    public void setListingID(int ID) {
        this.ListingID = ID;
        this.car = mydbhandler.SelectData("Listing","*","ListingID",getListingID());
        this.populateFields();
    }

    public void populateFields() {
        try {
            if (car.next()) {
                ListingNameTextfield.setText(car.getString("ListingName"));
                PriceTextField.setText(car.getString("Price"));
                ConsumptionTextfield.setText(car.getString("Condition"));
                BrandTextfield.setText(car.getString("Brand"));
                ModelTextField.setText(car.getString("Model"));
                BodyTypeChoice.setValue(car.getString("BodyType"));
                ColourPicker.setValue(car.getString("Colour"));
                DoorsChoice.setValue(car.getString("Doors"));
                SeatsChoice.setValue(car.getInt("Seats"));
                ConditionChoice.setValue(car.getString("Condition"));
                InteriorMaterialChoice.setValue(car.getString("Interior"));
                AirBagChoice.setValue(car.getString("AirBags"));
                ACChoice.setValue(car.getString("AirConditioning"));
                RegistrationDate.setValue(car.getDate("RegistrationDate").toLocalDate());
                GeneralInspectionDate.setValue(car.getDate("GeneralInspection").toLocalDate());
                OwnersChoice.setValue(car.getInt("Owners"));
                MilesTextField.setText(Integer.toString(car.getInt("Miles")));
                EmissionClassChoice.setValue(car.getString("EmissionClass"));
                EnvironmentalBadgeChoice.setValue(car.getString("EnvironmentalBadge"));
                ConsumptionTextfield.setText(Double.toString(car.getDouble("Consumption")));
                FuelChoice.setValue(car.getString("FuelType"));
                EngineChoice.setValue(car.getString("Engine"));
                PerformanceTextField.setText(Integer.toString(car.getInt("Performance")));
                CylinderCapacityChoice.setValue(car.getInt("CylinderCapacity"));
                GearboxChoice.setValue(car.getString("Gearbox"));
                DescriptionArea.setText(car.getString("Description"));

                Blob blob = car.getBlob("Picture");
                if (blob != null) {
                    try (InputStream inputStream = blob.getBinaryStream()) {
                        Image image = new Image(inputStream);
                        CarPicture.setImage(image);
                        this.imageBytes = blob.getBytes(1, (int) blob.length());
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Handle the exception, such as logging an error message or showing a default image
                    }
                } else {
                    Image image = new Image(new FileInputStream("src/main/resources/com/example/carsellingplatform/images/No-Image-Placeholder.svg.png"));
                    CarPicture.setImage(image);
                }
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            // Handle the exception, such as logging an error message or showing a default values
        }
    }



    public int getListingID(){return this.ListingID;};

    @FXML
    private Label ACLabel;

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

    @FXML
    private Button GoBack;

    DatabaseHandler mydbhandler = DatabaseHandler.getInstance();
    ResultSet car = mydbhandler.SelectData("Listing","*","ListingID",getListingID());


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

   /* private static final Map<String, String> colorMap = createColourMap();
    private static final Map<String, String> reversedColorMap = createReversedColourMap();

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

    private static Map<String, String> createReversedColourMap() {
        Map<String, String> reversedMap = new HashMap<>();
        for (Map.Entry<String, String> entry : colorMap.entrySet()) {
            reversedMap.put(entry.getValue(), entry.getKey());
        }
        return reversedMap;
    }

    public static String getColourName(Color color) {
        String colorCode = "#" + color.toString();
        return colorMap.getOrDefault(colorCode, "Unknown");
    }

    public static Color getColour(String colourName) {
        String colorCode = reversedColorMap.getOrDefault(colourName, "#000000");
        return Color.web(colorCode);
    }*/
   private String[] Colours = {
           "Alice Blue", "Antique White", "Aqua", "Aquamarine", "Azure", "Beige", "Bisque",
           "Black", "Blanched Almond", "Blue", "Fuchsia", "Gray", "Green", "Lime", "Maroon",
           "Navy", "Olive", "Orange", "Purple", "Red", "Silver", "Teal", "White", "Yellow"};
    private String[] BodyTypes = {"Hatchback", "Sedan", "MUV/SUV", "Coupe", "Convertible", "Wagon", "Van", "Pickup Truck"};
    private String[] EmissionClasses ={"Euro1","Euro2","Euro3","Euro4","Euro5","Euro6","Euro6c","Euro6d-TEMP","Euro6d"};

    private String[] EnvironmentalBadges ={"None","Red","Yellow","Green","Blue"};

    private String[] FuelTypes = {"Petrol","Diesel", "Compressed Natural Gas (CNG)", "Bio-Diesel", "Liquid Petroleum Gas (LPG)",
            "Ethanol" ,"Methanol","Electric","Hybrid (Petrol)","Hybrid (Diesel)"};

    private String[] EngineTypes = {"Boxer Four", "Boxer Six", "Inline Three", "Inline Four", "Inline Five", "Inline Six (Straight Six)",
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
        ListingID = this.ListingID;

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




            try {
                if (car.next()) {
                ListingNameTextfield.setText(car.getString("ListingName"));
                PriceTextField.setText(car.getString("Price"));
                ConsumptionTextfield.setText(car.getString("Condition"));
                BrandTextfield.setText(car.getString("Brand"));
                ModelTextField.setText(car.getString("Model"));
                BodyTypeChoice.setValue(car.getString("BodyType"));
                ColourPicker.setValue(car.getString("Colour"));
                DoorsChoice.setValue(car.getString("Doors"));
                SeatsChoice.setValue(car.getInt("Seats"));
                ConditionChoice.setValue(car.getString("Condition"));
                InteriorMaterialChoice.setValue(car.getString("Interior"));
                AirBagChoice.setValue(car.getString("AirBags"));
                ACChoice.setValue(car.getString("AirConditioning"));
                RegistrationDate.setValue(car.getDate("RegistrationDate").toLocalDate());
                GeneralInspectionDate.setValue(car.getDate("GeneralInspection").toLocalDate());
                OwnersChoice.setValue(car.getInt("Owners"));
                MilesTextField.setText(Integer.toString(car.getInt("Miles")));
                EmissionClassChoice.setValue(car.getString("EmissionClass"));
                EnvironmentalBadgeChoice.setValue(car.getString("EnvironmentalBadge"));
                ConsumptionTextfield.setText(Double.toString(car.getDouble("Consumption")));
                FuelChoice.setValue(car.getString("FuelType"));
                EngineChoice.setValue(car.getString("Engine"));
                PerformanceTextField.setText(Integer.toString(car.getInt("Performance")));
                CylinderCapacityChoice.setValue(car.getInt("CylinderCapacity"));
                GearboxChoice.setValue(car.getString("Gearbox"));
                DescriptionArea.setText(car.getString("Description"));
                ByteArrayInputStream inputStream = new ByteArrayInputStream(car.getBytes("Picture"));
                Image image = new Image(inputStream);
                this.CarPicture.setImage(image);
                Blob blob = car.getBlob("Picture");
                this.imageBytes = blob.getBytes(1,(int)blob.length());


            }
            }catch (SQLException e) {
                throw new RuntimeException(e);
        }

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

        //Error Message wurde immer random angezeigt wenn man auf den Frame geklickt hat
        /*ConsumptionTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                ConsumptionTextfield.setText(oldValue);
                message.setAlertType(Alert.AlertType.ERROR);
                message.setContentText("Please only enter numbers for the consumption value");
                message.show();

            }
        });*/
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
    void UpdateListingButtonClicked(ActionEvent event) throws SQLException, IOException {
        boolean UpdateListing = true;
        java.time.LocalDate currentDate = java.time.LocalDate.now();


        if (ListingNameTextfield.getText() == null || ListingNameTextfield.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please enter a name for your listing!");
            message.show();
            UpdateListing = false;
        }

        if (PriceTextField.getText() == null || PriceTextField.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please enter a price for your listing!");
            message.show();
            UpdateListing = false;
        }

        if (BrandTextfield.getText() == null || BrandTextfield.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the car brand of your car ");
            message.show();
            UpdateListing = false;
        }

        if (ModelTextField.getText() == null || ModelTextField.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the car model of your car (model description only)");
            message.show();
            UpdateListing = false;
        }

        if (ConsumptionTextfield.getText() == null || ConsumptionTextfield.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the consumption of your car (in liters per 100 km) ");
            message.show();
            UpdateListing = false;
        }

        if (PerformanceTextField.getText() == null || PerformanceTextField.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the performanceof your car (in horsepowers) ");
            message.show();
            UpdateListing = false;
        }

        if (MilesTextField.getText() == null || MilesTextField.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the amount of miles your car has been driven!");
            message.show();
            UpdateListing = false;
        }

        if (ConsumptionTextfield.getText() == null || ConsumptionTextfield.getText() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the consumption of your car (in liters per 100 km) ");
            message.show();
            UpdateListing = false;
        }

        if (DescriptionArea.getText() == "") {
            DescriptionArea.setText("null");
        }

        if (ConditionChoice.getValue() == null || ConditionChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the condition of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (BodyTypeChoice.getValue() == null || BodyTypeChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the body type of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (DoorsChoice.getValue() == null || DoorsChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the number of doors of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (SeatsChoice.getValue() == null || SeatsChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the number of seats of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (InteriorMaterialChoice.getValue() == null || InteriorMaterialChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the interior material of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (AirBagChoice.getValue() == null || AirBagChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the Air Bag equipment of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (ACChoice.getValue() == null || ACChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the Air-Conditioning equipment of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (OwnersChoice.getValue() == null || OwnersChoice.getValue().toString() == "") {
            OwnersChoice.setValue(0);
        }

        if (!(OwnersChoice.getValue() == 0) && ConditionChoice.getValue().toString().equals("New")) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("A car that has been used before (had a pre-owner), can not be listed as new!");
            message.show();
            UpdateListing = false;
        }

        if ((OwnersChoice.getValue() == 0) && (ConditionChoice.getValue().toString().equals("Used"))) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("A car that has never had an owner cannot be used!");
            message.show();
            UpdateListing = false;
        }

        if (EmissionClassChoice.getValue() == null || EmissionClassChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the emission class of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (EnvironmentalBadgeChoice.getValue() == null || EnvironmentalBadgeChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the environmental badge of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (FuelChoice.getValue() == null || FuelChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the compatible fuel type of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (EngineChoice.getValue() == null || EngineChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the engine type of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (CylinderCapacityChoice.getValue() == null || CylinderCapacityChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state cylinder capacity of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (GearboxChoice.getValue() == null || GearboxChoice.getValue().toString() == "") {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state cylinder capacity of the car you want to sell!");
            message.show();
            UpdateListing = false;
        }

        if (RegistrationDate.getValue().toString() == "" || RegistrationDate.getValue().toString() == null) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the date of your car´s first registration");
            message.show();
            UpdateListing = false;
        }

        if (GeneralInspectionDate.getValue().toString() == "" || GeneralInspectionDate.getValue().toString() == null) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Please state the date of your car´s last general inspection");
            message.show();
            UpdateListing = false;
        }


        if (RegistrationDate.getValue().compareTo(GeneralInspectionDate.getValue()) >= 0) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("The date of the cars last general inspection can not be earlier than the registration date");
            message.show();
            UpdateListing = false;
        }

        if (RegistrationDate.getValue().isAfter(currentDate)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Registration date cannot be set to a future date!");
            message.show();
            UpdateListing = false;
        }

        if (GeneralInspectionDate.getValue().isAfter(currentDate)) {
            message.setAlertType(Alert.AlertType.ERROR);
            message.setContentText("Date of last general inspection cannot be set to a future date!");
            message.show();
            UpdateListing = false;
        }



        if (UpdateListing == true) {

            DatabaseHandler mydbhandler = DatabaseHandler.getInstance();
            mydbhandler.updateListing( ListingNameTextfield.getText(), Double.parseDouble(PriceTextField.getText()), ConditionChoice.getValue().toString(),
                    BrandTextfield.getText(), ModelTextField.getText(), BodyTypeChoice.getValue().toString(),ColourPicker.getValue(), DoorsChoice.getValue().toString(),
                    SeatsChoice.getValue(), InteriorMaterialChoice.getValue().toString(), AirBagChoice.getValue().toString(), ACChoice.getValue().toString(),
                    Date.valueOf(RegistrationDate.getValue()), Date.valueOf(GeneralInspectionDate.getValue()), OwnersChoice.getValue(),
                    Integer.parseInt(MilesTextField.getText()), EmissionClassChoice.getValue().toString(), EnvironmentalBadgeChoice.getValue().toString(),
                    Double.parseDouble(ConsumptionTextfield.getText()), FuelChoice.getValue().toString(), EngineChoice.getValue().toString(),
                    Integer.parseInt(PerformanceTextField.getText()), CylinderCapacityChoice.getValue(),
                    GearboxChoice.getValue().toString(), DescriptionArea.getText(), this.imageBytes, this.ListingID);
            message.setAlertType(Alert.AlertType.CONFIRMATION);
            message.setContentText("Listing updated");
            message.show();
            SceneSwitcher sceneswitcher = new SceneSwitcher();
            sceneswitcher.switchToMyListings(event,user);
        }
    }

    @FXML
    void GoBackClicked(ActionEvent e) throws IOException, SQLException {

        SceneSwitcher sceneswitcher = new SceneSwitcher();
        sceneswitcher.switchToMyListings(e,user);
    }


}

