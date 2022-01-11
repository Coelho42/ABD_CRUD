package controllers;

import db.OracleDB;
import db.SectionsDB;
import models.Section;
import javax.swing.table.DefaultTableModel;

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
