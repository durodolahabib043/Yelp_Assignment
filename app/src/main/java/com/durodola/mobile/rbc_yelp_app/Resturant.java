package com.durodola.mobile.rbc_yelp_app;

/**
 * Created by mobile on 2016-05-24.
 */
public class Resturant {
    String name = "name";
    String phone = "phone";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return (name);
    }


}
