package db;

import models.Section;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SectionsDB extends OracleDB {

    private static Connection conn = null;

    public static List getAllSectionsDB() {
        conn = connectDB(conn);
        List<Section> sections = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getSections(?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                Section section = new Section(resultSet.getInt("id"), resultSet.getString("section"));
                sections.add(section);
                System.out.println(section.getId() + " " + section.getSection());
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return sections;
    }

    public static String getSectionNameDB(int id) {
        conn = connectDB(conn);
        String name = null;
        try {
            CallableStatement myCall = conn.prepareCall("{call getSectionName(?,?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.setInt(2, id);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                name = resultSet.getString("section");
                System.out.println(name);
            }
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return name;
    }

    public static String addSectionsDB(List<Section> sections) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addSections(?,?)}");
            Iterator<Section> it = sections.iterator();
            while(it.hasNext()){
                Section section = it.next();
                myCall.setInt(1, section.getId());
                myCall.setString(2, section.getSection());
                myCall.execute();
            }
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String addSectionDB(Section section) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addSections(?,?)}");
            myCall.setInt(1, section.getId());
            myCall.setString(2, section.getSection());
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String updateSectionDB(Section section) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call updateSections(?,?)}");
            myCall.setInt(1, section.getId());
            myCall.setString(2, section.getSection());
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteSectionDB(int sectionId) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call deleteSections(?)}");
            myCall.setInt(1, sectionId);
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteAllSectionsDB() {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call deleteSectionsData()}");
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }
}