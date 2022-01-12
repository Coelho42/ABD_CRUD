package com.escoteiros.abd.controllers;

import java.time.LocalDate;

import javax.swing.table.DefaultTableModel;

import com.escoteiros.abd.db.ItemInspectionsDB;
import com.escoteiros.abd.db.OracleDB;
import com.escoteiros.abd.models.*;

public class ItemInspectionsController extends OracleDB {
    private static String result;

    public static String addItemInspection(String itemDate, String itemDescription, Item itemData, DefaultTableModel tableModel) {
        if (itemData.getName() != null) {
            if (!itemDate.isEmpty()) {
                System.out.println("Item id" + itemData.getId());
                if (!itemDescription.isEmpty() && itemDescription.length() <= 255) {
                    LocalDate date = LocalDate.parse(itemDate);
                    ItemInspections newItemInspection = new ItemInspections(date, itemDescription, itemData.getId());
                    result = ItemInspectionsDB.addItemInspectionDB(newItemInspection);
                    if (result.equals("Successful")) {
                        ItemInspections.allItemInspections.add(newItemInspection);
                        tableModel.addRow(new Object[]{newItemInspection.getId(), itemData.getName(), newItemInspection.getDescription(), newItemInspection.getDate(), itemData.getId()});
                    }
                    return result;
                }
                return "Description";
            }
            return "Date";
        }
        return "ItemName";
    }

    public static String updateItemInspection(String itemDate, String itemDescription, Item item, ItemInspections itemInspectionValue, int index, DefaultTableModel tableModel) {
        if (item.getName() != null) {
            if (!itemDate.isEmpty()) {
                if (!itemDescription.isEmpty() && itemDescription.length() <= 255) {
                    itemInspectionValue.setDescription(itemDescription);
                    LocalDate date = LocalDate.parse(itemDate);
                    itemInspectionValue.setDate(date);
                    itemInspectionValue.setItemId(item.getId());
                    result = ItemInspectionsDB.updateItemInspectionDB(itemInspectionValue);
                    if (result.equals("Successful")) {
                        ItemInspections.allItemInspections.set(index, itemInspectionValue);
                        tableModel.setValueAt(itemInspectionValue.getId(), index, 0);
                        tableModel.setValueAt(item.getName(), index, 1);
                        tableModel.setValueAt(itemInspectionValue.getDescription(), index, 2);
                        tableModel.setValueAt(itemInspectionValue.getDate(), index, 3);
                        tableModel.setValueAt(item.getId(), index, 4);
                    }
                    return result;
                }
                return "Description";
            }
            return "Date";
        }
        return "ItemName";
    }
    public static void deleteItemInspection ( int itemInspectionId){
        result = ItemInspectionsDB.deleteItemInspectionDB(itemInspectionId);
    }
}
