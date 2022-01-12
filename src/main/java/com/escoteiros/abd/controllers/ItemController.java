package com.escoteiros.abd.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.table.DefaultTableModel;

import com.escoteiros.abd.db.ItemDB;
import com.escoteiros.abd.db.OracleDB;
import com.escoteiros.abd.models.*;

public class ItemController extends OracleDB {

    private static String result;

    public static String addItem(String itemName, String itemDescription, String itemPurchasedAt, String itemEndOfLife, SubSection subSection, ItemCategories category, DefaultTableModel tableModel) {
        if (!itemName.isEmpty() && itemName.length() <= 50) {
            if (itemDescription.length() <= 255) {
                if (category != null) {
                    if (subSection != null) {
                        LocalDate purchasedAtDate = null;
                        LocalDate endOfLifeDate = null;
                        if (!itemPurchasedAt.isEmpty()) {
                            purchasedAtDate = LocalDate.parse(itemPurchasedAt);
                        }
                        if (!itemEndOfLife.isEmpty()) {
                            endOfLifeDate = LocalDate.parse(itemEndOfLife);
                        }
                        String idCode;
                        if(Item.dbLastId < 9) {
                            idCode = "1240." + subSection.getSectionId() + subSection.getId() + "." + category.getId() + "0";
                        }
                        else {
                            idCode = "1240." + subSection.getSectionId() + subSection.getId() + "." + category.getId();
                        }
                        Item newItem = new Item(itemName, itemDescription, purchasedAtDate, endOfLifeDate, idCode, subSection.getInternalCode(), category.getId());
                        result = ItemDB.addItemDB(newItem);
                        if (result.equals("Successful")) {
                            Item.allItems.add(newItem);
                            tableModel.addRow(new Object[]{newItem.getId(), newItem.getIdCode(), newItem.getName(), newItem.getDescription(), newItem.getPurchasedAt(), newItem.getEndOfLife(), category.getCategory(), subSection.getSubSection(), category.getId(), subSection.getInternalCode()});
                        }
                        return result;
                    }
                    return "SubSectionName";
                }
                return "CategoryName";
            }
            return "Description";
        }
        return "ItemName";
    }

    public static String updateItem(String itemName, String itemDescription, String itemPurchasedAt, String itemEndOfLife, SubSection subSection, ItemCategories category, Item itemValue, int index, DefaultTableModel tableModel) {
        if (!itemName.isEmpty() && itemName.length() <= 50) {
            if(itemDescription.length() <= 255) {
                if(category.getCategory() != null) {
                    if(subSection.getSubSection() != null) {
                        itemValue.setName(itemName);
                        itemValue.setDescription(itemDescription);
                        LocalDate purchasedAtDate = null;
                        LocalDate endOfLifeDate = null;
                        if(!itemPurchasedAt.isEmpty()) {
                            purchasedAtDate = LocalDate.parse(itemPurchasedAt);
                        }
                        if(!itemEndOfLife.isEmpty()) {
                            endOfLifeDate = LocalDate.parse(itemEndOfLife);
                        }
                        itemValue.setPurchasedAt(purchasedAtDate);
                        itemValue.setEndOfLife(endOfLifeDate);
                        itemValue.setItemCategoriesId(category.getId());
                        itemValue.setSubSectionInternalCode(subSection.getInternalCode());
                        String idCode = itemValue.getIdCode();
                        String[] parts = idCode.split("\\.", 3);
                        String part2 = parts[2];
                        String idCode1 = String.valueOf(Character.digit(part2.charAt(1), 10));
                        String idCode2 = String.valueOf(Character.digit(part2.charAt(2), 10));
                        String idItem = idCode1+idCode2;
                        System.out.println(idCode1+idCode2);
                        idCode = "1240." + subSection.getSectionId() + subSection.getId() + "." + category.getId() + idItem;
                        itemValue.setIdCode(idCode);
                        result = ItemDB.updateItemsDB(itemValue);
                        if (result.equals("Successful")) {
                            Item.allItems.set(index, itemValue);
                            tableModel.setValueAt(itemValue.getId(), index, 0);
                            tableModel.setValueAt(itemValue.getIdCode(), index, 1);
                            tableModel.setValueAt(itemValue.getName(), index, 2);
                            tableModel.setValueAt(itemValue.getDescription(), index, 3);
                            tableModel.setValueAt(itemValue.getPurchasedAt(), index, 4);
                            tableModel.setValueAt(itemValue.getEndOfLife(), index, 5);
                            tableModel.setValueAt(category.getCategory(), index, 6);
                            tableModel.setValueAt(subSection.getSubSection(), index, 7);
                            tableModel.setValueAt(category.getId(), index, 8);
                            tableModel.setValueAt(subSection.getId(), index, 9);
                        }
                        return result;
                    }
                    return "SubSectionName";
                }
                return "CategoryName";
            }
            return "Description";
        }
        return "ItemName";
    }

    public static void deleteItem(int itemId) {
        result = ItemDB.deleteItemsDB(itemId);
    }

    public static void getSubSectionItems(String subSectionName, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        SubSection subSection = SubSection.allSubSections.stream().filter(a -> Objects.equals(a.getSubSection(), subSectionName)).collect(Collectors.toList()).get(0);
        List<Item> itemList = ItemDB.getAllSubSectionSpecificItemsDB(subSection.getInternalCode());
        for (Item newItem : itemList) {
            ItemCategories itemCategories = ItemCategories.allItemCategories.stream().filter(a -> a.getId() == newItem.getItemCategoriesId()).collect(Collectors.toList()).get(0);
            SubSection itemSubSection = SubSection.allSubSections.stream().filter(a -> a.getInternalCode() == newItem.getSubSectionInternalCode()).collect(Collectors.toList()).get(0);
            tableModel.addRow(new Object[]{newItem.getId(), newItem.getIdCode(), newItem.getName(), newItem.getDescription(), newItem.getPurchasedAt(), newItem.getEndOfLife(), itemCategories.getCategory(), itemSubSection.getSubSection(), itemCategories.getId(), itemSubSection.getInternalCode()});
        }
    }

    public static void getSectionItems(String sectionName, DefaultTableModel tableModel) {
        Section section = Section.allSections.stream().filter(a -> Objects.equals(a.getSection(), sectionName)).collect(Collectors.toList()).get(0);
        tableModel.setRowCount(0);
        List<Item> itemList = null;
        for (SubSection subSection : SubSection.allSubSections) {
            if (subSection.getSectionId() == section.getId()) {
                itemList = ItemDB.getAllSectionSpecificItemsDB(subSection.getInternalCode(), section.getId());
            }
        }
        for (Item newItem : itemList) {
            ItemCategories itemCategories = ItemCategories.allItemCategories.stream().filter(a -> a.getId() == newItem.getItemCategoriesId()).collect(Collectors.toList()).get(0);
            SubSection itemSubSection = SubSection.allSubSections.stream().filter(a -> a.getInternalCode() == newItem.getSubSectionInternalCode()).collect(Collectors.toList()).get(0);
            tableModel.addRow(new Object[]{newItem.getId(), newItem.getIdCode(), newItem.getName(), newItem.getDescription(), newItem.getPurchasedAt(), newItem.getEndOfLife(), itemCategories.getCategory(), itemSubSection.getSubSection(), itemCategories.getId(), itemSubSection.getInternalCode()});
        }
    }

    // Extra

    // Inspections Dates

    public static void getInspectedItemsFromDate(String dateBefore, String dateAfter, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Item> inspectedItemList = ItemDB.getAllInspectedItemsBetweenDatesDB(dateBefore, dateAfter);
        for (Item newItem : inspectedItemList) {
            ItemCategories itemCategories = ItemCategories.allItemCategories.stream().filter(a -> a.getId() == newItem.getItemCategoriesId()).collect(Collectors.toList()).get(0);
            SubSection itemSubSection = SubSection.allSubSections.stream().filter(a -> a.getInternalCode() == newItem.getSubSectionInternalCode()).collect(Collectors.toList()).get(0);
            tableModel.addRow(new Object[]{newItem.getId(), newItem.getIdCode(), newItem.getName(), newItem.getDescription(), newItem.getPurchasedAt(), newItem.getEndOfLife(), itemCategories.getCategory(), itemSubSection.getSubSection(), itemCategories.getId(), itemSubSection.getInternalCode()});
        }
    }

    public static void getCategoryItemsInspectedFromDate(String categoryName, String dateBefore, String dateAfter, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        ItemCategories itemCategories = ItemCategories.allItemCategories.stream().filter(a -> Objects.equals(a.getCategory(), categoryName)).collect(Collectors.toList()).get(0);
        List<Item> inspectedItemList = ItemDB.getAllCategoryInspectionsBetweenDatesDB(itemCategories.getId(), dateBefore, dateAfter);
        for (Item newItem : inspectedItemList) {
            SubSection itemSubSection = SubSection.allSubSections.stream().filter(a -> a.getInternalCode() == newItem.getSubSectionInternalCode()).collect(Collectors.toList()).get(0);
            tableModel.addRow(new Object[]{newItem.getId(), newItem.getIdCode(), newItem.getName(), newItem.getDescription(), newItem.getPurchasedAt(), newItem.getEndOfLife(), itemCategories.getCategory(), itemSubSection.getSubSection(), itemCategories.getId(), itemSubSection.getInternalCode()});
        }
    }

    public static int getNumInspections(Item item, DefaultTableModel tableModel) {
        int valor = ItemDB.getItemInspectionNum(item);
        return valor;
    }
}
