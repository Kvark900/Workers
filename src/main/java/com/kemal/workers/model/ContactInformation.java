package com.kemal.workers.model;

import javax.persistence.*;

/**
 * Created by Keno&Kemo on 11.11.2017..
 */

@Embeddable
public class ContactInformation {

    private String address;
    private String city;
    private String telephoneNum;
    private String email;

    public ContactInformation() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
