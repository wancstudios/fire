package com.wancstudios.fireextinguisher;

import java.io.Serializable;

public class SoldContainer implements Serializable
{
    String item_name;
    String othername;
    String item_amount;
    String item_quantity;
    String profit;
    String date;
    String balance;

    SoldContainer(String name, String amount, String quantity, String othername, String date, String balance, String profit)
    {
        this.item_name = name;
        this.item_amount = amount;
        this.item_quantity = quantity;
        this.date = date;
        this.othername = othername;
        this.balance = balance;
        this.profit = profit;
    }
}
