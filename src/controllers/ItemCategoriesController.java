package controllers;

import db.ItemCategoriesDB;
import db.OracleDB;
import javax.swing.table.DefaultTableModel;
import models.ItemCategories;

import java.util.Objects;

public class ItemCategoriesController extends OracleDB {
    private static String result;

    public ItemCategoriesController() {
    }

    public static String addItemCategories(String itemCategoryName, DefaultTableModel tableModel) {
        if (!itemCategoryName.isEmpty() && itemCategoryName.length() <= 30) {
            for (ItemCategories itemCategory: ItemCategories.allItemCategories) {
                if (Objects.equals(itemCategory.getCategory(), itemCategoryName)) {
                    return "Exists";
                }
            }
            ItemCategories newItemCategory = new ItemCategories(itemCategoryName);
            result = ItemCategoriesDB.addItemCategoryDB(newItemCategory);
            if (result.equals("Successful")) {
                ItemCategories.allItemCategories.add(newItemCategory);
                tableModel.addRow(new Object[]{newItemCategory.getId(), newItemCategory.getCategory()});
            }
            return result;
        }
        return "SectionName";
    }

    public static String updateItemCategories(String itemCategoryName, ItemCategories itemCategoryValue, int index, DefaultTableModel tableModel) {
        if (!itemCategoryName.isEmpty() && itemCategoryName.length() <= 30) {
            for (ItemCategories itemCategory: ItemCategories.allItemCategories) {
                if (itemCategory.getCategory() == itemCategoryName) {
                    return "Exists";
                }
            }
            itemCategoryValue.setCategory(itemCategoryName);
            result = ItemCategoriesDB.updateItemCategoryDB(itemCategoryValue);
            if (result.equals("Successful")) {
                ItemCategories.allItemCategories.set(index, itemCategoryValue);
                tableModel.setValueAt(itemCategoryValue.getId(), index, 0);
                tableModel.setValueAt(itemCategoryValue.getCategory(), index, 1);
            }
            return result;
        }
        return "SectionName";
    }

    public static void deleteItemCategories(int sectionId) {
        result = ItemCategoriesDB.deleteItemCategoryDB(sectionId);
    }
}