package com.wancstudios.fireextinguisher;

import java.io.Serializable;

public class ItemContainer implements Serializable
{
    String item_name;
    String item_amount;
    String item_quantity;

    ItemContainer(String name, String amount, String quantity)
    {
        this.item_name = name;
        this.item_amount = amount;
        this.item_quantity = quantity;
    }
}
