package com.wancstudios.fireextinguisher;

import java.io.Serializable;

public class DailyRecordsContainer implements Serializable
{
    String item_name;
    String item_amount;
    String item_quantity;
    String otherName;
    String date;
    String type;



    DailyRecordsContainer(String name, String amount, String quantity ,String otherName, String date, String type)
    {
        this.item_name = name;
        this.item_amount = amount;
        this.item_quantity = quantity;
        this.otherName = otherName;
        this.date = date;
        this.type = type;
    }
}
