package com.escoteiros.abd;

import java.util.Locale;

import com.escoteiros.abd.db.ItemCategoriesDB;
import com.escoteiros.abd.db.ItemInspectionsDB;
import com.escoteiros.abd.db.SectionsDB;
import com.escoteiros.abd.db.SubSectionsDB;
import com.escoteiros.abd.models.*;
import com.escoteiros.abd.views.MainFrame;

public class Main {
	public static void main(String args[]) {
		Locale.setDefault(new Locale("pt", "PT"));
		Section.setDefaultSections();
		SubSection.setDefaultSubSections();
		ItemCategories.setDefaultItemCategories();
		String result;
		try {
			// Apaga da DB
			result = ItemInspectionsDB.deleteAllItemInspectionsDB();
			System.out.println("Inspeções: Eliminadas " + result);
			//        result = ItemInspectionsDB.resetIdDB();
			ItemInspections.dbLastId = 0;
			System.out.println("Inspeções ids resetados" + result);

			//        result = ItemDB.deleteAllItemsDB();
			System.out.println("Items: Eliminados" + result);
			//        result = ItemDB.resetIdDB();
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
			System.out.println("Subsecção ids resetados" + result);

			result = SectionsDB.deleteAllSectionsDB();
			System.out.println("Sections: Eliminadas " + result);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		MainFrame mainFrame = new MainFrame();
	}
}
