package com.escoteiros.abd.db;

import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.escoteiros.abd.models.Item;

public class ItemDB extends OracleDB {

    private static Connection conn = null;

    public static List getAllItemsDB() {
        conn = connectDB(conn);
        List<Item> items = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getItems(?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                LocalDate purchaseAt = null;
                LocalDate endOfLife = null;
                if(resultSet.getDate("purchaseat") != null) {
                    purchaseAt = resultSet.getDate("purchaseat").toLocalDate();
                }
                if(resultSet.getDate("endoflife") != null) {
                    endOfLife = resultSet.getDate("endoflife").toLocalDate();
                }
                Item item = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), purchaseAt, endOfLife, resultSet.getString("id_code"), resultSet.getInt("sub_section_internal_code"), resultSet.getInt("item_categories_id"));
                items.add(item);
                System.out.println(item.getId() + " " + item.getName() + " " + item.getDescription() + " " + item.getPurchasedAt() + " " + item.getEndOfLife() + " " + item.getIdCode() + " " + item.getSubSectionInternalCode() + " " + item.getItemCategoriesId());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return items;
    }

    public static String addItemsDB(List<Item> items) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addItems(?,?,?,?,?,?,?,?)}");
            Iterator<Item> it = items.iterator();
            while(it.hasNext()){
                Item item = it.next();
                myCall.setString(1, item.getName());
                myCall.setString(2, item.getDescription());
                Date purchaseAt = null;
                Date endOfLife = null;
                if(item.getPurchasedAt() != null) {
                    purchaseAt = Date.valueOf(item.getPurchasedAt());
                }
                if(item.getEndOfLife() != null) {
                    endOfLife = Date.valueOf(item.getEndOfLife());
                }
                myCall.setDate(3, purchaseAt);
                myCall.setDate(4, endOfLife);
                myCall.setString(5, item.getIdCode());
                if(item.getSubSectionInternalCode() == 0) {
                    myCall.setInt(6, 1);
                }
                else {
                    myCall.setInt(6, item.getSubSectionInternalCode());
                }
                myCall.setInt(7, item.getItemCategoriesId());
                myCall.registerOutParameter(8, Types.INTEGER);
                myCall.execute();
                Item.dbLastId = myCall.getInt(8);
            }
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String addItemDB(Item item) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addItem(?,?,?,?,?,?,?,?)}");
            myCall.setString(1, item.getName());
            myCall.setString(2, item.getDescription());
            Date purchaseAt = null;
            Date endOfLife = null;
            if(item.getPurchasedAt() != null) {
                purchaseAt = Date.valueOf(item.getPurchasedAt());
            }
            if(item.getEndOfLife() != null) {
                endOfLife = Date.valueOf(item.getEndOfLife());
            }
            myCall.setDate(3, purchaseAt);
            myCall.setDate(4, endOfLife);
            myCall.setString(5, item.getIdCode());
            myCall.setInt(6, item.getSubSectionInternalCode());
            myCall.setInt(7, item.getItemCategoriesId());
            myCall.registerOutParameter(8, Types.INTEGER);
            myCall.execute();
            item.setId(myCall.getInt(8));
            item.setIdCode(item.getIdCode() + item.getId());
            Item.dbLastId = item.getId();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String updateItemsDB(Item item) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call updateItems(?,?,?,?,?,?,?,?)}");
            myCall.setInt(1, item.getId());
            myCall.setString(2, item.getName());
            myCall.setString(3, item.getDescription());
            if(item.getPurchasedAt() != null) {
                myCall.setDate(4, Date.valueOf(item.getPurchasedAt()));
            }
            else {
                myCall.setDate(4, null);
            }
            if(item.getEndOfLife() != null) {
                myCall.setDate(5, Date.valueOf(item.getEndOfLife()));
            }
            else {
                myCall.setDate(5, null);
            }
            myCall.setString(6, item.getIdCode());
            myCall.setInt(7, item.getSubSectionInternalCode());
            myCall.setInt(8, item.getItemCategoriesId());
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteItemsDB(int itemId) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call deleteItems(?)}");
            myCall.setInt(1, itemId);
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteAllItemsDB() {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call deleteItemsData()}");
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String resetIdDB() {
        conn = connectDB(conn);
        try {
            Statement sql = conn.createStatement();
            sql.execute("ALTER TABLE item MODIFY(id GENERATED AS IDENTITY (START WITH 1))");
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    // Extra SubSection
    public static List getAllSubSectionItemsDB() {
        conn = connectDB(conn);
        List<Item> items = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getAllSubSectionItems(?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                LocalDate purchaseAt = null;
                LocalDate endOfLife = null;
                if(resultSet.getDate("purchaseat") != null) {
                    purchaseAt = resultSet.getDate("purchaseat").toLocalDate();
                }
                if(resultSet.getDate("endoflife") != null) {
                    endOfLife = resultSet.getDate("endoflife").toLocalDate();
                }
                Item newItem = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), purchaseAt, endOfLife, resultSet.getString("id_code"), resultSet.getInt("sub_section_internal_code"), resultSet.getInt("item_categories_id"));
                items.add(newItem);
                System.out.println(newItem.getId() + " " + newItem.getName() + " " + newItem.getDescription() + " " + newItem.getPurchasedAt() + " " + newItem.getEndOfLife() + " " + newItem.getIdCode() + " " + newItem.getSubSectionInternalCode() + " " + newItem.getItemCategoriesId());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return items;
    }

    public static List getAllSubSectionSpecificItemsDB(int subSectionId) {
        conn = connectDB(conn);
        List<Item> items = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getAllSubSectionSpecificItems(?,?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.setInt(2, subSectionId);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                LocalDate purchaseAt = null;
                LocalDate endOfLife = null;
                if(resultSet.getDate("purchaseat") != null) {
                    purchaseAt = resultSet.getDate("purchaseat").toLocalDate();
                }
                if(resultSet.getDate("endoflife") != null) {
                    endOfLife = resultSet.getDate("endoflife").toLocalDate();
                }
                Item newItem = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), purchaseAt, endOfLife, resultSet.getString("id_code"), resultSet.getInt("sub_section_internal_code"), resultSet.getInt("item_categories_id"));
                items.add(newItem);
                System.out.println(newItem.getId() + " " + newItem.getName() + " " + newItem.getDescription() + " " + newItem.getPurchasedAt() + " " + newItem.getEndOfLife() + " " + newItem.getIdCode() + " " + newItem.getSubSectionInternalCode() + " " + newItem.getItemCategoriesId());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return items;
    }

    // Extra Section
    public static List getAllSectionItemsDB() {
        conn = connectDB(conn);
        List<Item> items = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getAllSectionItems(?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                LocalDate purchaseAt = null;
                LocalDate endOfLife = null;
                if(resultSet.getDate("purchaseat") != null) {
                    purchaseAt = resultSet.getDate("purchaseat").toLocalDate();
                }
                if(resultSet.getDate("endoflife") != null) {
                    endOfLife = resultSet.getDate("endoflife").toLocalDate();
                }
                Item newItem = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), purchaseAt, endOfLife, resultSet.getString("id_code"), resultSet.getInt("sub_section_internal_code"), resultSet.getInt("item_categories_id"));
                items.add(newItem);
                System.out.println(newItem.getId() + " " + newItem.getName() + " " + newItem.getDescription() + " " + newItem.getPurchasedAt() + " " + newItem.getEndOfLife() + " " + newItem.getIdCode() + " " + newItem.getSubSectionInternalCode() + " " + newItem.getItemCategoriesId());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return items;
    }

    public static List getAllSectionSpecificItemsDB(int subSectionId, int sectionId) {
        conn = connectDB(conn);
        List<Item> items = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getAllSectionSpecificItems(?,?,?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.setInt(2, subSectionId);
            myCall.setInt(3, sectionId);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                LocalDate purchaseAt = null;
                LocalDate endOfLife = null;
                if(resultSet.getDate("purchaseat") != null) {
                    purchaseAt = resultSet.getDate("purchaseat").toLocalDate();
                }
                if(resultSet.getDate("endoflife") != null) {
                    endOfLife = resultSet.getDate("endoflife").toLocalDate();
                }
                Item newItem = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), purchaseAt, endOfLife, resultSet.getString("id_code"), resultSet.getInt("sub_section_internal_code"), resultSet.getInt("item_categories_id"));
                items.add(newItem);
                System.out.println(newItem.getId() + " " + newItem.getName() + " " + newItem.getDescription() + " " + newItem.getPurchasedAt() + " " + newItem.getEndOfLife() + " " + newItem.getIdCode() + " " + newItem.getSubSectionInternalCode() + " " + newItem.getItemCategoriesId());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return items;
    }

    // Inspections
    // Inspection Dates

    public static List getAllInspectedItemsBetweenDatesDB(String dateBefore, String dateAfter) {
        conn = connectDB(conn);
        List<Item> items = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getAllInspectedItemsBetweenDatesItems(?,?,?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.setDate(2, Date.valueOf(dateBefore));
            myCall.setDate(3, Date.valueOf(dateAfter));
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                LocalDate purchaseAt = null;
                LocalDate endOfLife = null;
                if(resultSet.getDate("purchaseat") != null) {
                    purchaseAt = resultSet.getDate("purchaseat").toLocalDate();
                }
                if(resultSet.getDate("endoflife") != null) {
                    endOfLife = resultSet.getDate("endoflife").toLocalDate();
                }
                Item newItem = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), purchaseAt, endOfLife, resultSet.getString("id_code"), resultSet.getInt("sub_section_internal_code"), resultSet.getInt("item_categories_id"));
                items.add(newItem);
                //System.out.println(newItem.getId() + " " + newItem.getName() + " " + newItem.getDescription() + " " + newItem.getPurchasedAt() + " " + newItem.getEndOfLife() + " " + newItem.getIdCode() + " " + newItem.getSubSectionInternalCode() + " " + newItem.getItemCategoriesId());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return items;
    }

    public static List getAllCategoryInspectionsBetweenDatesDB(int itemCategoryId, String dateBefore, String dateAfter) {
        conn = connectDB(conn);
        List<Item> items = new ArrayList<>();
        System.out.println(dateBefore + " " + dateAfter + " " + itemCategoryId);
        try {
            CallableStatement myCall = conn.prepareCall("{call getAllInspectedCategoryItemsBetweenDatesItems(?,?,?,?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.setInt(2, itemCategoryId);
            myCall.setDate(3, Date.valueOf(dateBefore));
            myCall.setDate(4, Date.valueOf(dateAfter));
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                LocalDate purchaseAt = null;
                LocalDate endOfLife = null;
                if(resultSet.getDate("purchaseat") != null) {
                    purchaseAt = resultSet.getDate("purchaseat").toLocalDate();
                }
                if(resultSet.getDate("endoflife") != null) {
                    endOfLife = resultSet.getDate("endoflife").toLocalDate();
                }
                Item newItem = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), purchaseAt, endOfLife, resultSet.getString("id_code"), resultSet.getInt("sub_section_internal_code"), resultSet.getInt("item_categories_id"));
                items.add(newItem);
                //System.out.println(newItem.getId() + " " + newItem.getName() + " " + newItem.getDescription() + " " + newItem.getPurchasedAt() + " " + newItem.getEndOfLife() + " " + newItem.getIdCode() + " " + newItem.getSubSectionInternalCode() + " " + newItem.getItemCategoriesId());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return items;
    }

    public static List getAllNotInspectedItemsDB() {
        conn = connectDB(conn);
        List<Item> items = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getAllNotInspectedItems(?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                LocalDate purchaseAt = null;
                LocalDate endOfLife = null;
                if(resultSet.getDate("purchaseat") != null) {
                    purchaseAt = resultSet.getDate("purchaseat").toLocalDate();
                }
                if(resultSet.getDate("endoflife") != null) {
                    endOfLife = resultSet.getDate("endoflife").toLocalDate();
                }
                Item newItem = new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), purchaseAt, endOfLife, resultSet.getString("id_code"), resultSet.getInt("sub_section_internal_code"), resultSet.getInt("item_categories_id"));
                items.add(newItem);
                //System.out.println(newItem.getId() + " " + newItem.getName() + " " + newItem.getDescription() + " " + newItem.getPurchasedAt() + " " + newItem.getEndOfLife() + " " + newItem.getIdCode() + " " + newItem.getSubSectionInternalCode() + " " + newItem.getItemCategoriesId());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return items;
    }

    public static int getItemInspectionNum(Item item) {
        conn = connectDB(conn);
        int inspectionNum = -1;
        try {
            CallableStatement myCall = conn.prepareCall("{call getItemInspectionNum(?,?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.setInt(2, item.getId());
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                inspectionNum = resultSet.getInt("total");
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return inspectionNum;
    }



}
