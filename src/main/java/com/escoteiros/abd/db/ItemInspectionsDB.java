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

import com.escoteiros.abd.models.ItemInspections;

public class ItemInspectionsDB extends OracleDB {

    private static Connection conn = null;

    public static List getAllItemInspectionsDB() {
        conn = connectDB(conn);
        List<ItemInspections> itemInspections = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getItemInspections(?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                LocalDate date = null;
                if(resultSet.getDate("date") != null) {
                    date = resultSet.getDate("date").toLocalDate();
                }
                ItemInspections itemInspection = new ItemInspections(resultSet.getInt("id"), date, resultSet.getString("description"), resultSet.getInt("item_id"));
                itemInspections.add(itemInspection);
                System.out.println(itemInspection.getId() + " " + itemInspection.getDate() + " " + itemInspection.getDescription() + " " + itemInspection.getItemId());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return itemInspections;
    }
    public static String addItemInspectionsDB(List<ItemInspections> itemInspections) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addItemInspections(?,?,?,?)}");
            Iterator<ItemInspections> it = itemInspections.iterator();
            while(it.hasNext()){
                ItemInspections itemInspection = it.next();
                Date date = null;
                if(itemInspection.getDate() != null) {
                    date = Date.valueOf(itemInspection.getDate());
                }
                myCall.setDate(1, date);
                myCall.setString(2, itemInspection.getDescription());
                myCall.setInt(3, itemInspection.getItemId());
                myCall.registerOutParameter(4, Types.INTEGER);
                myCall.execute();
                ItemInspections.dbLastId = myCall.getInt(4);
            }
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String addItemInspectionDB(ItemInspections itemInspection) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addItemInspections(?,?,?,?)}");
            Date date = null;
            if(itemInspection.getDate() != null) {
                date = Date.valueOf(itemInspection.getDate());
            }
            myCall.setDate(1, date);
            myCall.setString(2, itemInspection.getDescription());
            myCall.setInt(3, itemInspection.getItemId());
            myCall.registerOutParameter(4, Types.INTEGER);
            myCall.execute();
            itemInspection.setId(myCall.getInt(4));
            ItemInspections.dbLastId = myCall.getInt(4);
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String updateItemInspectionDB(ItemInspections itemInspection) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call updateItemInspections(?,?,?,?)}");
            myCall.setInt(1, itemInspection.getId());
            myCall.setDate(2, Date.valueOf(itemInspection.getDate()));
            myCall.setString(3, itemInspection.getDescription());
            myCall.setInt(4, itemInspection.getItemId());
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteItemInspectionDB(int itemInspectionId) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call deleteItemInspections(?)}");
            myCall.setInt(1, itemInspectionId);
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteAllItemInspectionsDB() {
//        conn = connectDB(conn);
//        try {
//            CallableStatement myCall = conn.prepareCall("{call deleteItemInspectionsData()}");
//            myCall.execute();
//        } catch (
//                SQLException e) {
//            return e.toString();
//        }
//        closeConnDB(conn);
        return "Successful";
    }

    public static String resetIdDB() {
        conn = connectDB(conn);
        try {
            Statement sql = conn.createStatement();
            sql.execute("ALTER TABLE item_inspections MODIFY(id GENERATED AS IDENTITY (START WITH 1))");
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }
}
