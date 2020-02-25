package com.accenture.flowershop.fe.formdata;

import javax.validation.constraints.*;

public class RegistrationFD {

    @NotBlank(message = "Enter name")
    @Size(max = 255, message = "Name is too long")
    private String name;

    @NotBlank(message = "Enter second name")
    @Size(max = 255, message = "Second name is too long")
    private String secondName;

    @Size(max = 255, message = "Father name is to long")
    private String fatherName;

    @NotBlank(message = "Enter city's name")
    @Size(max = 255, message = "City's name is to long")
    private String city;

    @NotBlank(message = "Enter street's name")
    @Size(max = 255, message = "Street's name is to long")
    private String street;

    @NotNull(message = "Enter code of the your address")
    @Max(value = 100000000, message = "Enter incorrect code")
    @Min(value = 0, message = "Enter incorrect code")
    private Integer code;

    @Max(value = 100000000, message = "Enter incorrect code")
    @Min(value = 0, message = "Enter incorrect code")
    private Integer building;

    @NotBlank(message = "Enter phone")
    @Pattern(regexp = "^[0-9]{10,10}$", message = "Number is incorrect")
    private String phone;

    @NotBlank(message = "Enter email")
    @Email(message = "Email is incorrect")
    @Size(max = 255, message = "Email is to long")
    private String email;

    @NotBlank(message = "Enter password")
    @Size(max = 255, message = "Password is to long")
    private String password;

    @NotBlank(message = "Confirm password")
    @Size(max = 255, message = "Password is to long")
    private String confirmPassword;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public RegistrationFD() {
    }
}
