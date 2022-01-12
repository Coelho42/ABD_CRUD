package com.escoteiros.abd.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemInspections {

    private int id;
    private LocalDate date;
    private String description;
    private int itemId;
    public static List<ItemInspections> allItemInspections = new ArrayList<>();
    public static ArrayList<Integer> idList = new ArrayList<>();
    public static ArrayList<String> itemNameList = new ArrayList<>();
    public static ArrayList<Integer> itemNameIdList = new ArrayList<>();
    public static int dbLastId;

    public ItemInspections(LocalDate date, String description, int itemId) {
        this.date = date;
        this.description = description;
        this.itemId = itemId;
    }
    public ItemInspections(int id, LocalDate date, String description, int itemId) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public static void resetData() {
        ItemInspections.allItemInspections.clear();
        ItemInspections.itemNameIdList.clear();
        ItemInspections.idList.clear();
    }
}

