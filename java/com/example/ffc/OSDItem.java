package com.example.ffc;

public class OSDItem {

    private String xItem, xQuantity, xAmount, xStatus, xMnum, xInum, xWeight, xCurrency;

    public OSDItem (String item, String quantity, String amount, String status, String matnum, String Itnum, String Weight, String Currency)
    {
        xItem = item;
        xQuantity = quantity;
        xAmount = amount;
        xStatus = status;
        xMnum = matnum;
        xInum = Itnum;
        xWeight = Weight;
        xCurrency = Currency;
    }
    public  String getItem()
    {
        return xItem;
    }

    public  String getQuantity()
    {
        return xQuantity;
    }

    public  String getAmount()
    {
        return xAmount;
    }

    public  String getStatus()
    {
        return xStatus;
    }

    public  String getMnum()
    {
        return xMnum;
    }

    public  String getInum()
    {
        return xInum;
    }
    public  String getWeight()
    {
        return xWeight;
    }
    public  String getCurrency()
    {
        return xCurrency;
    }

}
