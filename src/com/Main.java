package com;

import db.*;
import models.*;
import views.MainFrame;

import java.util.Locale;

public class Main {
    public static void main(String args[]) {
        Locale.setDefault(new Locale("pt", "PT"));
        Section.setDefaultSections();
        SubSection.setDefaultSubSections();
        ItemCategories.setDefaultItemCategories();
        String result;
        // Apaga da DB
        result = ItemInspectionsDB.deleteAllItemInspectionsDB();
        System.out.println("Inspeções: Eliminadas " + result);
        result = ItemInspectionsDB.resetIdDB();
        ItemInspections.dbLastId = 0;
        System.out.println("Inspeções ids resetados" + result);

        result = ItemDB.deleteAllItemsDB();
        System.out.println("Items: Eliminados" + result);
        result = ItemDB.resetIdDB();
        Item.dbLastId = 0;
        System.out.println("Item ids resetados" + result);

        result = ItemCategoriesDB.deleteAllItemCategoriesDB();
        System.out.println("Categorias: Eliminadas " + result);
        result = ItemCategoriesDB.resetIdDB();
        ItemCategories.dbLastId = 0;
        System.out.println("Categorias ids resetados" + result);

        result = SubSectionsDB.deleteAllSubSectionsDB();
        System.out.println("Subsections: Eliminadas " + result);
        result = SubSectionsDB.resetInternalCodeDB();
        SubSection.dbLastInternalCode = 0;
        System.out.println("Subsecção ids resetados" +result);

        result = SectionsDB.deleteAllSectionsDB();
        System.out.println("Sections: Eliminadas " + result);

        MainFrame mainFrame = new MainFrame();
    }
}
