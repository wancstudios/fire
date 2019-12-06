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
    String Id;

    SoldContainer(String name, String amount, String quantity, String othername, String date, String balance, String profit,String Id)
    {
        this.item_name = name;
        this.item_amount = amount;
        this.Id = Id;
        this.item_quantity = quantity;
        this.date = date;
        this.othername = othername;
        this.balance = balance;
        this.profit = profit;
    }
}
