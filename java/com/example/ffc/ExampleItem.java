package com.example.ffc;

public class ExampleItem {
    private String mName, mKUNNR, mCity, mTelephone, mAddress, mPostal;

    public ExampleItem(String name, String KUNNR, String city, String telephone, String address, String postal) {

        mName = name;
        mKUNNR = KUNNR;
        mCity = city;
        mTelephone = telephone;
        mAddress = address;
        mPostal = postal;

    }


    public String getName() {
        return mName;
    }

    public String getKUNNR() {
        return mKUNNR;
    }

    public String getCity() {
        return mCity;
    }

    public String getTelephone() {
        return mTelephone;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getPostal() {
        return mPostal;
    }


}
