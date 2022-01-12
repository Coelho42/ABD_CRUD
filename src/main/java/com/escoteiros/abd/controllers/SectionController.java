package com.escoteiros.abd.controllers;

import javax.swing.table.DefaultTableModel;

import com.escoteiros.abd.db.OracleDB;
import com.escoteiros.abd.db.SectionsDB;
import com.escoteiros.abd.models.Section;

public class SectionController extends OracleDB {

    private static String result;

    public static String addSections(String sectionName, DefaultTableModel tableModel) {
        if (!sectionName.isEmpty() && sectionName.length() <= 12) {
            if (Section.allSections.stream().noneMatch(o -> o.getSection().equalsIgnoreCase(sectionName))) {
                for (int id = 0; id <= 4; id++) {
                    if (!Section.idList.contains(id)) {
                        Section newSection = new Section(id, sectionName);
                        result = SectionsDB.addSectionDB(newSection);
                        if (result.equals("Successful")) {
                            Section.allSections.add(newSection);
                            Section.idList.add(newSection.getId());
                            tableModel.addRow(new Object[]{newSection.getId(), newSection.getSection()});
                        }
                        return result;
                    }
                }
            }
            return "Exists";
        }
        return "Name";
    }

    public static String updateSection(String sectionName, Section sectionValue, int index, DefaultTableModel tableModel) {
        if (!sectionName.isEmpty() && sectionName.length() <= 12) {
            if (Section.allSections.stream().noneMatch(o -> o.getSection().equalsIgnoreCase(sectionName))) {
                sectionValue.setSection(sectionName);
                result = SectionsDB.updateSectionDB(sectionValue);
                if (result.equals("Successful")) {
                    Section.allSections.set(index, sectionValue);
                    Section.idList.set(index, sectionValue.getId());
                    tableModel.setValueAt(sectionValue.getId(), index, 0);
                    tableModel.setValueAt(sectionValue.getSection(), index, 1);
                }
                return result;
            }
            return "Exists";
        }
        return "Name";
    }

    public static void deleteSection(int sectionId) {
        result = SectionsDB.deleteSectionDB(sectionId);
    }
}
