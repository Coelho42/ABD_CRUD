package com.escoteiros.abd.db;

import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.escoteiros.abd.models.ItemCategories;

public class ItemCategoriesDB extends OracleDB{

    private static Connection conn = null;

    public static List getAllItemCategoriesDB() {
        conn = connectDB(conn);
        List<ItemCategories> itemCategories = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getItemCategories(?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                ItemCategories itemCategory = new ItemCategories(resultSet.getInt("id"), resultSet.getString("category"));
                itemCategories.add(itemCategory);
                System.out.println(itemCategory.getId() + " " + itemCategory.getCategory());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return itemCategories;
    }
    public static String addItemCategoriesDB(List<ItemCategories> itemCategories) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addItemCategories(?,?)}");
            Iterator<ItemCategories> it = itemCategories.iterator();
            while(it.hasNext()){
                ItemCategories itemCategory = it.next();
                myCall.setString(1, itemCategory.getCategory());
                myCall.registerOutParameter(2, Types.INTEGER);
                myCall.execute();
                ItemCategories.dbLastId = myCall.getInt(2);
            }
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String addItemCategoryDB(ItemCategories itemCategory) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addItemCategory(?,?)}");
            myCall.setString(1, itemCategory.getCategory());
            myCall.registerOutParameter(2, Types.INTEGER);
            myCall.execute();
            itemCategory.setId(myCall.getInt(2));
            ItemCategories.dbLastId = myCall.getInt(2);
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String updateItemCategoryDB(ItemCategories itemCategory) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call updateItemCategories(?,?)}");
            myCall.setInt(1, itemCategory.getId());
            myCall.setString(2, itemCategory.getCategory());
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteItemCategoryDB(int itemCategoryId) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call deleteItemCategories(?)}");
            myCall.setInt(1, itemCategoryId);
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteAllItemCategoriesDB() {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call deleteItemCategoriesData()}");
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
            sql.execute("ALTER TABLE item_categories MODIFY(id GENERATED AS IDENTITY (START WITH 1))");
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }
}
