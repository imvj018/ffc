package com.example.ffc;

import java.io.Serializable;

public class Fruits implements Serializable {
    public String fruitName;
    public String fruitNum;
    public String quantity;

    public Fruits() {

    }

    public Fruits(String fruitName, String fruitNum, String quantity) {
        this.fruitName = fruitName;
        this.fruitNum = fruitNum;
        this.quantity = quantity;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getFruitNum() {
        return fruitNum;
    }

    public void setFruitNum(String fruitNum) {
        this.fruitNum = fruitNum;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
