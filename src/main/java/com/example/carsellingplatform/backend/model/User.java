package com.example.carsellingplatform.backend.model;

public class User {
    private String FirstName;
    private String LastName;
    private String Username;
    private String Email;
    private String Address;
    private String City;
    public User(String FirstName,String LastName,String Username,String Email,String Address,String City){
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Username = Username;
        this.Email = Email;
        this.City = City;
        this.Address = Address;
    }

    public String getFirstName(){return this.FirstName;}
    public String getLastName(){return this.LastName;}
    public String getUsername(){return this.Username;}
    public String getEmail(){return this.Email;}
    public String getAddress(){return this.Address;}
    public String getCity(){return this.City;}

    public void setUsername(String username) {
        this.Username = username;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public void setEmail(String email){this.Email = email;};

    @Override
    public String toString() {
        return "User{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Username='" + Username + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                '}';
    }
}
