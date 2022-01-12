package com.escoteiros.abd.models;

import java.util.ArrayList;
import java.util.List;

public class ItemCategories {

    private int id;
    private String category;
    public static List<ItemCategories> defaultItemCategories = new ArrayList<ItemCategories>();
    public static List<ItemCategories> allItemCategories  = new ArrayList<>();
    public static int dbLastId;

    public ItemCategories(String category) {
        this.id = id;
        this.category = category;
    }

    public ItemCategories(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static List<ItemCategories> getDefaultItemCategories() {
        return defaultItemCategories;
    }

    public static void setDefaultItemCategories() {
        ItemCategories.defaultItemCategories.add(new ItemCategories("Sede"));
        ItemCategories.defaultItemCategories.add(new ItemCategories("Abrigo"));
        ItemCategories.defaultItemCategories.add(new ItemCategories("Cozinha"));
        ItemCategories.defaultItemCategories.add(new ItemCategories("Ferramentas"));
        ItemCategories.defaultItemCategories.add(new ItemCategories("Energizados"));
        ItemCategories.defaultItemCategories.add(new ItemCategories("Socorrismo"));
        ItemCategories.defaultItemCategories.add(new ItemCategories("Desportivos"));
        ItemCategories.defaultItemCategories.add(new ItemCategories("Diversos"));
        ItemCategories.defaultItemCategories.add(new ItemCategories("Jogos"));
    }

    public static int containsCategory(String itemCategoryName) {
        for(ItemCategories listItemCategory: ItemCategories.allItemCategories) {
            if(listItemCategory.getCategory().equals(itemCategoryName)) {
                return listItemCategory.getId();
            }
        }
        return -1;
    }

    public static void resetData() {
        ItemCategories.allItemCategories.clear();
    }
}
