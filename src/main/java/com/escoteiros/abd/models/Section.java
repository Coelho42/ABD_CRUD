package com.escoteiros.abd.models;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private int id;
    private String section;
    public static List<Section> defaultSections = new ArrayList<>();
    public static List<Section> allSections = new ArrayList<>();
    public static ArrayList<Integer> idList = new ArrayList<>();

    public Section(int id, String section) {
        this.id = id;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public static List<Section> getDefaultSections() {
        return defaultSections;
    }

    public static void setDefaultSections() {
        Section.defaultSections.add(new Section(0,"Agrupamento"));
        Section.defaultSections.add(new Section(1, "Alcateia"));
        Section.defaultSections.add(new Section(2, "Expedição"));
        Section.defaultSections.add(new Section(3, "Comunidade"));
        Section.defaultSections.add(new Section(4, "Clã"));
    }

    public static void resetData() {
        Section.allSections.clear();
        Section.idList.clear();
    }
}
