package com.tikiticket.core.api;

/**
 * Created by veinhorn on 1.4.17.
 */
public interface Profile {
    String getLogin();
    String getFirstName();
    String getSecondName();
    String getPatronymic();
    String getPhoneNumber();
    String getCountry();
    String getAddress();
    String getEmail();
    String getGender();
    String getAge();
}
