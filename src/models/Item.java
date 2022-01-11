package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Item {

    private int id;
    private String name;
    private String description;
    private LocalDate purchasedAt;
    private LocalDate endOfLife;
    private String idCode;
    private int subSectionInternalCode;
    private int itemCategoriesId;
    public static List<Item> allItems = new ArrayList<>();
    public static ArrayList<String> itemNameList = new ArrayList<>();
    public static ArrayList<String> sectionNameList = new ArrayList<>();
    public static ArrayList<Integer> sectionNameIdList = new ArrayList<>();
    public static ArrayList<String> subSectionNameList = new ArrayList<>();
    public static ArrayList<Integer> subSectionNameIdList = new ArrayList<>();
    public static ArrayList<String> categoryNameList = new ArrayList<>();
    public static ArrayList<Integer> categoryNameIdList = new ArrayList<>();
    public static int dbLastId;

    public Item(){}

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item(int id, String name, String description, LocalDate purchasedAt, LocalDate endOfLife, String idCode, int subSectionInternalCode, int itemCategoriesId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.purchasedAt = purchasedAt;
        this.endOfLife = endOfLife;
        this.idCode = idCode;
        this.subSectionInternalCode = subSectionInternalCode;
        this.itemCategoriesId = itemCategoriesId;
    }

    public Item(String name, String description, LocalDate purchasedAt, LocalDate endOfLife, String idCode, int subSectionInternalCode, int itemCategoriesId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.purchasedAt = purchasedAt;
        this.endOfLife = endOfLife;
        this.idCode = idCode;
        this.subSectionInternalCode = subSectionInternalCode;
        this.itemCategoriesId = itemCategoriesId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDate purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public LocalDate getEndOfLife() {
        return endOfLife;
    }

    public void setEndOfLife(LocalDate endOfLife) {
        this.endOfLife = endOfLife;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public int getSubSectionInternalCode() {
        return subSectionInternalCode;
    }

    public void setSubSectionInternalCode(int subSectionInternalNumber) {
        this.subSectionInternalCode = subSectionInternalNumber;
    }

    public int getItemCategoriesId() {
        return itemCategoriesId;
    }

    public void setItemCategoriesId(int itemCategoriesId) {
        this.itemCategoriesId = itemCategoriesId;
    }

    public static void resetData() {
        Item.allItems.clear();
        Item.sectionNameList.clear();
        Item.sectionNameIdList.clear();
        Item.subSectionNameList.clear();
        Item.subSectionNameIdList.clear();
        Item.categoryNameList.clear();
        Item.categoryNameIdList.clear();
        Item.itemNameList.clear();
    }
}
