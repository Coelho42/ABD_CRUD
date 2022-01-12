package com.escoteiros.abd.controllers;

import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JTable;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.escoteiros.abd.db.*;
import com.escoteiros.abd.models.*;

public class ExcelController extends JTable {

    private static String result;

    public static String ExcelController() {

        try {
            // Reset Data
            Section.resetData();
            SubSection.resetData();
            Item.resetData();
            ItemCategories.resetData();
            ItemInspections.resetData();

            // Apaga da DB
            result = ItemInspectionsDB.deleteAllItemInspectionsDB();
            System.out.println("Inspeções: Eliminadas " + result);
            result = ItemInspectionsDB.resetIdDB();
            System.out.println("Inspeções ids resetados" + result);

            result = ItemDB.deleteAllItemsDB();
            System.out.println("Items: Eliminados" + result);
            result = ItemDB.resetIdDB();
            System.out.println("Item ids resetados" + result);

            result = ItemCategoriesDB.deleteAllItemCategoriesDB();
            System.out.println("Categorias: Eliminadas " + result);
            result = ItemCategoriesDB.resetIdDB();
            System.out.println("Categorias ids resetados" + result);

            result = SubSectionsDB.deleteAllSubSectionsDB();
            System.out.println("Subsections: Eliminadas " + result);
            result = SubSectionsDB.resetInternalCodeDB();
            System.out.println("Subsecção ids resetados" +result);

            result = SectionsDB.deleteAllSectionsDB();
            System.out.println("Sections: Eliminadas " + result);

            // Insere na DB
            result = SectionsDB.addSectionsDB(Section.defaultSections);
            System.out.println("Sections: Adicionadas " + result);
            result = SubSectionsDB.addSubSectionsDB(SubSection.defaultSubSections);
            System.out.println("Subsections: Adicionadas " + result);
            result = ItemCategoriesDB.addItemCategoriesDB(ItemCategories.defaultItemCategories);
            System.out.println("Categorias: Adicionadas " + result);

            // Reset ids
            SubSection.dbLastInternalCode = 0;
            Item.dbLastId = 0;
            ItemCategories.dbLastId = 0;
            ItemInspections.dbLastId = 0;

            // Recebe da DB
            SubSection.allSubSections = SubSectionsDB.getAllSubSectionsDB();
            System.out.println("SubSections: Recebidas " + result);
            ItemCategories.allItemCategories = ItemCategoriesDB.getAllItemCategoriesDB();
            System.out.println("Categorias: Recebidas " + result);

            String excelFilePath = "src/main/resources/Inventario_Material.xlsx";
            FileInputStream inputStream = new FileInputStream(excelFilePath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            int sheets = workbook.getNumberOfSheets();

            Item newItem = null;
            int i = 0;

            for (int s = 0; s < sheets-1; s++) {
                XSSFSheet sheet = workbook.getSheetAt(s);
                // For loop para ler os dados da sheet
                int rows = sheet.getLastRowNum();   // Número de linhas
                int cols = sheet.getRow(1).getLastCellNum();    // Número de colunas

                for (int r = 1; r <= rows; r++) {
                    XSSFRow row = sheet.getRow(r);
                    if(row.getCell(5) != null) {
                        newItem = new Item();
                        if (row.getCell(5).getCellType() != CellType.BLANK) {
                            for (int c = 0; c < cols; c++) {
                                XSSFCell cell = row.getCell(c);
                                if(c == 0) {
                                    newItem.setIdCode(cell.getStringCellValue());
                                }
                                else if(c == 2) {
                                    int subSeccaoInternalCode = SubSection.containsSubsection(newItem.getIdCode(), cell.getStringCellValue());
                                    if(subSeccaoInternalCode != -1) {
                                        newItem.setSubSectionInternalCode(subSeccaoInternalCode);
                                    }
                                }
                                else if(c == 3) {
                                    int categoryId = ItemCategories.containsCategory(cell.getStringCellValue());
                                    if(categoryId != -1) {
                                        newItem.setItemCategoriesId(categoryId);
                                    }
                                }
                                else if(c == 4) {
                                    newItem.setName(cell.getStringCellValue());

                                } else if (c == 5) {
                                    newItem.setDescription(cell.getStringCellValue());
                                }
                            }
                            i++;
                            if(i <= 99) {
                                System.out.println(newItem.getIdCode() + " | " + newItem.getName() + " | " + newItem.getDescription() + " | " + newItem.getSubSectionInternalCode() + " | " + newItem.getItemCategoriesId());
                                Item.allItems.add(newItem);
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
            }
            inputStream.close();
            result = ItemDB.addItemsDB(Item.allItems);
            if (result.equals("Successful")) {
                return result;
            }
        } catch (IOException e) {
            return e.toString();
        }
        return null;
    }
}
