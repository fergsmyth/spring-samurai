package com.genericgames.samurai.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {

    private List<Item> items = new ArrayList<Item>();

    public Inventory(){

    }

    public List<Item> getItems(){
        return Collections.unmodifiableList(items);
    }

    public void addAllItems(List<Item> items) {
        this.items.addAll(items);
    }

    public void addItem(Item item){
        this.items.add(item);
    }
}
