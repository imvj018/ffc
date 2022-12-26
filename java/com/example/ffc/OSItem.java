package com.example.ffc;

public class OSItem {
    private String mNumber, mAmount, mCurrency, mStatus, mDate;

    public OSItem(String number, String amount, String currency, String status, String date) {

        mNumber = number;
        mAmount = amount;
        mCurrency = currency;
        mStatus = status;
        mDate = date;


    }



    public String getNumber() {
        return mNumber;
    }
    public String getAmount() {
        return mAmount;
    }
    public String getCurrency() {
        return mCurrency;
    }
    public String getStatus() {
        return mStatus;
    }
    public String getDate() {
        return mDate;
    }


}
