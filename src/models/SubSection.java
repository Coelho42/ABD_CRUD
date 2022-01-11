package models;

import java.util.ArrayList;
import java.util.List;

public class SubSection {

    private int internalCode;
    private int id;
    private String subSection;
    private int sectionId;
    public static List<SubSection> defaultSubSections = new ArrayList<>();
    public static List<SubSection> allSubSections = new ArrayList<>();
    public static ArrayList<String> sectionNameList = new ArrayList<>();
    public static ArrayList<Integer> defaultSubSectionIds = new ArrayList<>();
    public static int dbLastInternalCode;

    public SubSection(int internalCode, int id, String subSection, int sectionId) {
        this.internalCode = internalCode;
        this.id = id;
        this.subSection = subSection;
        this.sectionId = sectionId;
    }

    public SubSection(int id, String subSection, int sectionId) {
        this.id = id;
        this.subSection = subSection;
        this.sectionId = sectionId;
    }

    public int getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(int internalCode) {
        this.internalCode = internalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubSection() {
        return subSection;
    }

    public void setSubSection(String subSection) {
        this.subSection = subSection;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public static List<SubSection> getDefaultSubSections() {
        return defaultSubSections;
    }

    public static void setDefaultSubSections() {
        SubSection.defaultSubSections.add(new SubSection(0, "Secção",0));
        SubSection.defaultSubSections.add(new SubSection(0, "Secção",1));
        SubSection.defaultSubSections.add(new SubSection(0, "Secção",2));
        SubSection.defaultSubSections.add(new SubSection(0, "Secção",3));
        SubSection.defaultSubSections.add(new SubSection(0, "Secção",4));
        //Bandos
        SubSection.defaultSubSections.add(new SubSection(1,"Bando Branco",1));
        SubSection.defaultSubSections.add(new SubSection(2,"Bando Cinzento",1));
        SubSection.defaultSubSections.add(new SubSection(3,"Bando Castanho",1));
        SubSection.defaultSubSections.add(new SubSection(4,"Bando Preto",1));
        SubSection.defaultSubSections.add(new SubSection(5,"Bando Ruivo",1));
        // Patrulhas
        SubSection.defaultSubSections.add(new SubSection(1,"Patrulha Pantera",2));
        SubSection.defaultSubSections.add(new SubSection(2,"Patrulha Mocho",2));
        SubSection.defaultSubSections.add(new SubSection(3,"Patrulha Falcão",2));
        SubSection.defaultSubSections.add(new SubSection(4,"Patrulha Leão",2));
        SubSection.defaultSubSections.add(new SubSection(5,"Patrulha Touro",2));
        // Equipas
        SubSection.defaultSubSections.add(new SubSection(1,"Eq. B.P",3));
        SubSection.defaultSubSections.add(new SubSection(2,"Eq. Camões",3));
        SubSection.defaultSubSections.add(new SubSection(3,"Eq. Padeira",3));
        // Tribos
        SubSection.defaultSubSections.add(new SubSection(1,"Tr. Vasco Gama",4));
    }

    public static int containsSubsection(String idCode, String subSectionName) {
        for(SubSection listSubSection: SubSection.allSubSections) {
            System.out.println(idCode);
            String[] parts = idCode.split("\\.", 3);
            String part2 = parts[1];
            System.out.println(part2);
            int sectionId = Character.digit(part2.charAt(0), 10);
            System.out.println(sectionId);
            if(listSubSection.getSubSection().equals(subSectionName)) {
                if(listSubSection.getSectionId() == sectionId) {
                    return listSubSection.getInternalCode();
                }
            }
        }
        return -1;
    }

    public static void setDefaultSubSectionIds() {
        SubSection.defaultSubSectionIds.add(0);
        SubSection.defaultSubSectionIds.add(1);
        SubSection.defaultSubSectionIds.add(2);
        SubSection.defaultSubSectionIds.add(3);
        SubSection.defaultSubSectionIds.add(4);
        SubSection.defaultSubSectionIds.add(5);
    }

    public static void resetData() {
        SubSection.allSubSections.clear();
        SubSection.sectionNameList.clear();
        SubSection.defaultSubSectionIds.clear();
    }
}
