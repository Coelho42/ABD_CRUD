package com.escoteiros.abd.controllers;

import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.table.DefaultTableModel;

import com.escoteiros.abd.db.OracleDB;
import com.escoteiros.abd.db.SubSectionsDB;
import com.escoteiros.abd.models.Section;
import com.escoteiros.abd.models.SubSection;

public class SubSectionController extends OracleDB {

    private static String result;

    public static String addSubSections(String subSectionId, String subSectionName, String sectionName, DefaultTableModel tableModel) {
        if(subSectionId != null) {
            if (!subSectionName.isEmpty() && subSectionName.length() <= 60) {
                if(sectionName != null) {
                    Section section = Section.allSections.stream().filter(a -> Objects.equals(a.getSection(), sectionName)).collect(Collectors.toList()).get(0);
                    for (SubSection subSection : SubSection.allSubSections) {
                        if (subSection.getId() == Integer.parseInt(subSectionId) && subSection.getSubSection().equalsIgnoreCase(subSectionName) && subSection.getSectionId() == Integer.parseInt(subSectionId)) {
                            return "Exists";
                        }
                    }
                    SubSection newSubSection = new SubSection(Integer.parseInt(subSectionId), subSectionName, section.getId());
                    result = SubSectionsDB.addSubSectionDB(newSubSection);
                    if (result.equals("Successful")) {
                        SubSection.allSubSections.add(newSubSection);
                        tableModel.addRow(new Object[]{newSubSection.getInternalCode(), newSubSection.getId(), newSubSection.getSubSection(), section.getSection(), section.getId()});
                    }
                    return result;
                }
                return "SectionName";
            }
            return "SubSectionName";
        }
        return "SubSectionId";
    }

    public static String updateSubSections(String subSectionId, String subSectionName, String sectionName, SubSection subSectionValue, int index, DefaultTableModel tableModel) {
        if(subSectionId != null) {
            if (!subSectionName.isEmpty() && subSectionName.length() <= 60) {
                if(sectionName != null) {
                    for (SubSection subSection : SubSection.allSubSections) {
                        if (subSection.getId() == Integer.parseInt(subSectionId) && subSection.getSubSection().equalsIgnoreCase(subSectionName) && subSection.getSectionId() == subSectionValue.getSectionId()) {
                            return "Exists";
                        }
                    }
                    subSectionValue.setId(Integer.parseInt(subSectionId));
                    subSectionValue.setSubSection(subSectionName);
                    Section section = Section.allSections.stream().filter(a -> Objects.equals(a.getSection(), sectionName)).collect(Collectors.toList()).get(0);
                    subSectionValue.setSectionId(section.getId());
                    result = SubSectionsDB.updateSubSectionDB(subSectionValue);
                    if (result.equals("Successful")) {
                        SubSection.allSubSections.set(index, subSectionValue);
                        tableModel.setValueAt(subSectionValue.getId(), index, 1);
                        tableModel.setValueAt(subSectionValue.getSubSection(), index, 2);
                        tableModel.setValueAt(sectionName, index, 3);
                    }
                    return result;
                }
                return "SectionName";
            }
            return "SubSectionName";
        }
        return "SubSectionId";
    }

    public  static void deleteSubSection(int subSectionId) {
        result = SubSectionsDB.deleteSubSectionDB(subSectionId);
    }

}
