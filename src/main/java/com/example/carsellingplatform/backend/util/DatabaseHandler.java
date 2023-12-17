package com.example.carsellingplatform.backend.util;


import com.example.carsellingplatform.backend.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DatabaseHandler {

    //Declaration
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    private static DatabaseHandler databaseHandler = null;

    private final String url = Config.getDatabaseUrl();

    private final String user = Config.getDatabaseUser();

    private final String password = Config.getDatabasePassword();



    public DatabaseHandler(){
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DatabaseHandler getInstance(){
        if (databaseHandler == null) databaseHandler = new DatabaseHandler();
        return databaseHandler;
    }

    public void closeConnection(){
        try {
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet SelectData (String tableName, String column1,String column2, String value) {
        // Select statement
        String sql =  "SELECT " + column1 + " FROM " + tableName + " WHERE " + column2 + "= '" + value + "';";

        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public ResultSet SelectData (String tableName, String column1,String column2, int value) {
        // Select statement
        String sql =  "SELECT " + column1 + " FROM " + tableName + " WHERE " + column2 + "= " + value + ";";

        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }


    public boolean doesExist (ResultSet rs)
    {
        boolean result= false;
        if(rs != null){
        try {

            result= rs.next();

        }
        catch (SQLException ex) {
        }}
        return result;
    }

    /*################Register User####################*/
    public ResultSet createUser(String firstName, String lastName, String username, String password, String salt, String email, String city, String address) {
        // Insert statement
        String sql = "INSERT INTO User (FirstName, LastName, Username, Password, Salt, Email, City, Address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Prepare statement
        PreparedStatement addUser;
        ResultSet rs = null;
        try {
            addUser = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            addUser.setString(1, firstName);
            addUser.setString(2, lastName);
            addUser.setString(3, username);
            addUser.setString(4, password);
            addUser.setString(5, salt);
            addUser.setString(6, email);
            addUser.setString(7, city);
            addUser.setString(8, address);
            addUser.execute();
            rs = addUser.getGeneratedKeys();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }


    /*###############Login#######################*/
    public boolean isLoginCorrect(String login, String password){
        boolean isEmail = login.contains("@");

        //User uses Email to login
        if(isEmail){
            try {
                String sql = "SELECT Salt, Password FROM b2klwcvfmvd8q1hoywvi.User WHERE Email='" + login + "'";
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                resultSet = statement.executeQuery(sql);

                if(!resultSet.isBeforeFirst()){
                    return false;
                }

                resultSet.first();
                String salt = resultSet.getString("Salt");
                String dbPassword = resultSet.getString("Password");

                //Check if pw correct
                if(Hash.isCorrect(password, dbPassword, salt)){
                    return true;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //If User uses Username to login
        else {
            try {
                String sql = "SELECT Salt, Password FROM b2klwcvfmvd8q1hoywvi.User WHERE Username='" + login + "'";
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                resultSet = statement.executeQuery(sql);

                if (!resultSet.isBeforeFirst()){
                    return false;
                }
                resultSet.first();
                String salt = resultSet.getString("Salt");
                String dbPassword = resultSet.getString("Password");

                //Check if pw correct
                if(Hash.isCorrect(password, dbPassword, salt)){
                    return true;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public ResultSet createListing(User user, String ListingName, double price, String condition, String brand, String model, String BodyType,
                                   String colour, String doors, int seats, String interior, String AirBags, String AC, Date registration,
                                   Date inspection, int owners, int Miles, String EmissionClass, String EnvironmentalBadge, double consumption,
                                   String FuelType, String EngineType, int performance, int CylinderCapacity, String gearbox, String description,byte[] Picture,Date created) {
        // Insert statement
        String sql = "INSERT INTO Listing (Username,ListingName,Price,`Condition`,Brand,Model,BodyType,"+
                     "Colour,Doors,Seats,Interior,AirBags,AirConditioning,RegistrationDate,"+
                     "GeneralInspection,Owners,Miles,EmissionClass,EnvironmentalBadge,Consumption,FuelType,"+
                     "Engine,Performance, CylinderCapacity,Gearbox,Description,Picture,creationDate) "+
                     "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        // Prepare statement
        PreparedStatement createListing ;

        ResultSet rs = null;
        try {
            createListing = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            createListing.setString(1, user.getUsername());
            createListing.setString(2, ListingName);
            createListing.setDouble(3, price);
            createListing.setString(4, condition);
            createListing.setString(5, brand);
            createListing.setString(6, model);
            createListing.setString(7, BodyType);
            createListing.setString(8, colour);
            createListing.setString(9, doors);
            createListing.setInt(10, seats);
            createListing.setString(11, interior);
            createListing.setString(12, AirBags);
            createListing.setString(13, AC);
            createListing.setDate(14, new java.sql.Date(registration.getTime()));
            createListing.setDate(15, new java.sql.Date(inspection.getTime()));
            createListing.setInt(16, owners);
            createListing.setInt(17, Miles);
            createListing.setString(18, EmissionClass);
            createListing.setString(19, EnvironmentalBadge);
            createListing.setDouble(20, consumption);
            createListing.setString(21, FuelType);
            createListing.setString(22, EngineType);
            createListing.setInt(23, performance);
            createListing.setInt(24, CylinderCapacity);
            createListing.setString(25, gearbox);
            createListing.setString(26, description);
            createListing.setBytes(27, Picture);
            createListing.setDate(28, new java.sql.Date(java.util.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).getTime()));

            // Execute the insert statement
            createListing.executeUpdate();

            // Get the generated keys (if any)
            ResultSet generatedKeys = createListing.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Return the generated keys
                return generatedKeys;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public ResultSet selectListings(){
        try{
            String sql = "SELECT * FROM Listing";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }


    public ResultSet getResultSet(String query){
        try{
            String sql = query;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet updateListing( String listingName, double price, String condition, String brand, String model, String bodyType,
                              String colour, String doors, int seats, String interior, String airBags, String ac, Date registration,
                              Date inspection, int owners, int miles, String emissionClass, String environmentalBadge, double consumption,
                              String fuelType, String engineType, int performance, int cylinderCapacity, String gearbox, String description,
                              byte[] picture,int listingId) {
        // Update statement
        String sql = "UPDATE Listing SET `ListingName` = ?, `Price` = ?, `Condition` = ?, `Brand` = ?, `Model` = ?, `BodyType` = ?, `Colour` = ?, " +
                "`Doors` = ?, `Seats` = ?, `Interior` = ?, `AirBags` = ?, `AirConditioning` = ?, `RegistrationDate` = ?, `GeneralInspection` = ?, " +
                "`Owners` = ?, `Miles` = ?, `EmissionClass` = ?, `EnvironmentalBadge` = ?, `Consumption` = ?, `FuelType` = ?, `Engine` = ?, " +
                "`Performance` = ?, `CylinderCapacity` = ?, `Gearbox` = ?, `Description` = ?, `Picture` = ? " +
                "WHERE ListingID = ?;";

        PreparedStatement UpdateListing;
        ResultSet rs = null;
        try  {

            // Set the parameter values
            UpdateListing = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            UpdateListing.setString(1, listingName);
            UpdateListing.setDouble(2, price);
            UpdateListing.setString(3, condition);
            UpdateListing.setString(4, brand);
            UpdateListing.setString(5, model);
            UpdateListing.setString(6, bodyType);
            UpdateListing.setString(7, colour);
            UpdateListing.setString(8, doors);
            UpdateListing.setInt(9, seats);
            UpdateListing.setString(10, interior);
            UpdateListing.setString(11, airBags);
            UpdateListing.setString(12, ac);
            UpdateListing.setDate(13, new java.sql.Date(registration.getTime()));
            UpdateListing.setDate(14, new java.sql.Date(inspection.getTime()));
            UpdateListing.setInt(15, owners);
            UpdateListing.setInt(16, miles);
            UpdateListing.setString(17, emissionClass);
            UpdateListing.setString(18, environmentalBadge);
            UpdateListing.setDouble(19, consumption);
            UpdateListing.setString(20, fuelType);
            UpdateListing.setString(21, engineType);
            UpdateListing.setInt(22, performance);
            UpdateListing.setInt(23, cylinderCapacity);
            UpdateListing.setString(24, gearbox);
            UpdateListing.setString(25, description);
            UpdateListing.setBytes(26, picture);
            UpdateListing.setInt(27, listingId);

            // Execute the update
            UpdateListing.executeUpdate();

            ResultSet generatedKeys = UpdateListing.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Return the generated keys
                return generatedKeys;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /*################### Used to fetch a specific listing and User Information from DB ####################*/
    public ResultSet selectListingAndUser(int listingID) {
        try {
            String sql = "SELECT Listing.ListingName, Listing.Price, User.Username, User.Email, User.Address, User.City, Listing.Picture " +
                    "FROM Listing " +
                    "INNER JOIN User ON Listing.Username = User.Username " +
                    "WHERE Listing.ListingID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, listingID);
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public ResultSet selectListingById(int listingID) {
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM Listing WHERE ListingID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, listingID);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }


    public ResultSet selectListingWithUsername(String username) {
        try {
            String sql = "SELECT Listing.*, Listing.Price, Listing.Description, Listing.ListingName, Listing.RegistrationDate, Listing.Seats, Listing.Miles, User.Username, User.Email, User.Address, Listing.Picture " +
                    "FROM Listing " +
                    "INNER JOIN User ON Listing.Username = User.Username " +
                    "WHERE User.Username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public ResultSet selectFilteredData(String brand, String model, Object registrationDate, Object price, Object mile, Object seat) throws SQLException {
        String query = "SELECT * FROM Listing WHERE 1=1";

        if (brand != null && brand != "default") {
            query += " AND Brand = ?";
        }
        if (model != null && model != "default") {
            query += " AND Model = ?";
        }
        if (registrationDate != null && registrationDate != "default") {
            query += " AND substring_index(RegistrationDate, '-', 1) >= ?";
        }
        if (price != null && price != "default") {
            query += " AND Price <= ?";
        }
        if (mile != null && mile != "default") {
            query += " AND Miles <= ?";
        }
        if (seat != null && seat != "default") {
            query += " AND Seats = ?";
        }

        PreparedStatement pstmt = this.connection.prepareStatement(query);

        int parameterIndex = 1;
        if (brand != null && brand != "default") {
            pstmt.setString(parameterIndex++, brand);
        }
        if (model != null && model != "default") {
            pstmt.setString(parameterIndex++, model);
        }
        if (registrationDate != null && registrationDate != "default") {
            pstmt.setInt(parameterIndex++, (Integer) registrationDate);
        }
        if (price != null && price != "default") {
            pstmt.setInt(parameterIndex++, (Integer) price);
        }
        if (mile != null && mile != "default") {
            pstmt.setInt(parameterIndex++, (Integer) mile);
        }
        if (seat != null && seat != "default") {
            pstmt.setInt(parameterIndex++, (Integer) seat);
        }

        return pstmt.executeQuery();
    }

    public void DeleteListing(String ListingID){
        try {
            String sql = "DELETE FROM Listing WHERE ListingID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ListingID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEmailIfExists(String emailOrUsername)
    {
        String query = "SELECT Email FROM User WHERE Email = ? OR Username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, emailOrUsername);
            statement.setString(2, emailOrUsername);

            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getString("Email");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserPassword(String email, String newPassword)
    {
        String newSalt = Hash.getSalt();
        String newPasswordHashed = Hash.hash(newPassword+newSalt);

        try {
            String sql = "UPDATE User SET Password = ?, Salt = ? WHERE Email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newPasswordHashed);


            statement.setString(2, newSalt);

            statement.setString(3, email);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateUser(String username, String firstName, String lastName, String hashedPassword, String salt, String city, String address) {
        try {
            String sql = "UPDATE User SET FirstName=?, LastName=?, Password=?, Salt=?, City=?, Address=? WHERE Username=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, hashedPassword);
            statement.setString(4, salt);
            statement.setString(5, city);
            statement.setString(6, address);
            statement.setString(7, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (update successful)
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Update failed
    }

    public boolean updateUsernoPassword(String username, String firstName, String lastName,
                                        String city, String address) {
        try {
            String sql = "UPDATE User SET FirstName=?, LastName=?, City=?, Address=? WHERE Username=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, city);
            statement.setString(4, address);
            statement.setString(5, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected (update successful)
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Update failed
    }

    public boolean deleteUser(String username) {
        try {
            String sql = "DELETE FROM User WHERE Username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ResultSet getUser(String username) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM User WHERE Username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public ResultSet selectSellerEmailAndListingName(int listingID) {
        // Select statement
        String sql = "SELECT User.Email, Listing.ListingName " +
                "FROM Listing " +
                "INNER JOIN User ON Listing.Username = User.Username " +
                "WHERE Listing.ListingID = " + listingID;


        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }


}

