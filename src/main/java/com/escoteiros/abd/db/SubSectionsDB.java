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

import com.escoteiros.abd.models.SubSection;

public class SubSectionsDB extends OracleDB {

    private static Connection conn = null;

    public static List getAllSubSectionsDB() {
        conn = connectDB(conn);
        List<SubSection> subSections = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getSubSections(?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                SubSection subSection = new SubSection(resultSet.getInt("internal_code"), resultSet.getInt("id"), resultSet.getString("subsection"), resultSet.getInt("section_id"));
                subSections.add(subSection);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return subSections;
    }
    public static String addSubSectionsDB(List<SubSection> subSections) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addSubSections(?,?,?,?)}");
            Iterator<SubSection> it = subSections.iterator();
            while(it.hasNext()){
                SubSection subSection = it.next();
                myCall.setInt(1, subSection.getId());
                myCall.setString(2, subSection.getSubSection());
                myCall.setInt(3, subSection.getSectionId());
                myCall.registerOutParameter(4, Types.INTEGER);
                myCall.execute();
                SubSection.dbLastInternalCode = myCall.getInt(4);
            }
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String addSubSectionDB(SubSection subSection) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call addSubSections(?,?,?,?)}");
            myCall.setInt(1, subSection.getId());
            myCall.setString(2, subSection.getSubSection());
            myCall.setInt(3, subSection.getSectionId());
            myCall.registerOutParameter(4, Types.INTEGER);
            myCall.execute();
            subSection.setInternalCode(myCall.getInt(4));
            SubSection.dbLastInternalCode = subSection.getInternalCode();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String updateSubSectionDB(SubSection subSection) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call updateSubSections(?,?,?,?)}");
            myCall.setInt(1, subSection.getInternalCode());
            myCall.setInt(2, subSection.getId());
            myCall.setString(3, subSection.getSubSection());
            myCall.setInt(4, subSection.getSectionId());
            myCall.execute();
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteSubSectionDB(int subSectionId) {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call deleteSubSections(?)}");
            myCall.setInt(1, subSectionId);
            myCall.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String deleteAllSubSectionsDB() {
        conn = connectDB(conn);
        try {
            CallableStatement myCall = conn.prepareCall("{call deleteSubSectionsData()}");
            myCall.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return "Successful";
    }

    public static String resetInternalCodeDB() {
        conn = connectDB(conn);
        try {
            Statement sql = conn.createStatement();
            sql.execute("ALTER TABLE sub_section MODIFY(internal_code GENERATED AS IDENTITY (START WITH 1))");
        } catch (
                SQLException e) {
            return e.toString();
        }
        closeConnDB(conn);
        return "Successful";
    }

    // Extra
    public static List getAllSubSectionNamesDB() {
        conn = connectDB(conn);
        List<SubSection> subSections = new ArrayList<>();
        try {
            CallableStatement myCall = conn.prepareCall("{call getAllSubSectionNames(?)}");
            myCall.registerOutParameter(1, OracleTypes.CURSOR);
            myCall.execute();
            ResultSet resultSet = (ResultSet) myCall.getObject(1);
            while(resultSet.next()){
                SubSection subSection = new SubSection(resultSet.getInt("internal_code"), resultSet.getInt("id"), resultSet.getString("subsection"), resultSet.getInt("section_id"));
                subSections.add(subSection);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        closeConnDB(conn);
        return subSections;
    }
}
