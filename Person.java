package com.example.stockfinal;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public abstract class Person {
    protected  String userName;
    protected String password;
    protected static  String firstName;
    protected  String secondName;

    public Person() {
    }

    public Person(String userName, String password) {
        this.userName = userName;
        this.password = password;

    }
    public Person(String userName, String password,String firstName,String secondName){
        this.userName = userName;
        this.password = password;
        Person.firstName = firstName;
        this.secondName= secondName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        Person.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public static boolean searchByUsername(String username) {
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/com/example/stockfinal/users.csv"))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String savedUsername = nextLine[0];

                if (savedUsername.equals(username)) {
                    // Match found
                    System.out.println("Username: " + savedUsername);
                    return true;
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return false;
    }
    }


