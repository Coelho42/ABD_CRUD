package views;

import controllers.*;
import db.*;
import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class ListDataFrame extends JFrame {
    private JPanel panelListData;
    private JTable tableData;
    private JButton adicionarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton voltarButton;
    private JButton searchButton;
    private String tableName;
    private DefaultTableModel tableModel;

    public ListDataFrame(String table) {
        tableName = table;
        setContentPane(panelListData);
        setTitle(tableName);
        setSize(1024, 768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        dataTable();
        switch (tableName) {
            case "Section" -> {
                adicionarButton.addActionListener(e -> {
                    if(Section.allSections.size() < 5) {
                        SectionDataFrame sectionData = new SectionDataFrame(this, tableModel, null, "Adicionar", -1);
                        setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Máximo de Secções atingido.", "Máximo Excedido", JOptionPane.WARNING_MESSAGE);
                    }
                });
                editarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if(index != -1) {
                        int sectionId = (int) tableModel.getValueAt(index, 0);
                        String sectionName = (String) tableModel.getValueAt(index, 1);
                        Section editSection = new Section(sectionId, sectionName);
                        SectionDataFrame sectionData = new SectionDataFrame(this, tableModel, editSection, "Editar", index);
                        setEnabled(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar uma Secção.", "Selecionar Secção", JOptionPane.WARNING_MESSAGE);
                    }
                });
                eliminarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if(index != -1) {
                        int dialogResult = JOptionPane.showConfirmDialog (null, "Tem a certeza que quer eliminar o registo? Todos os dados ligados a este registo serão eliminados.","Eliminar", JOptionPane.OK_CANCEL_OPTION);
                        if(dialogResult == JOptionPane.YES_OPTION){
                            SectionController.deleteSection((int) tableModel.getValueAt(index, 0));
                            Section.allSections.remove(index);
                            Section.idList.remove(tableData.getValueAt(index, 0));
                            tableModel.removeRow(index);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar uma Secção.", "Selecionar Secção", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
            case "SubSection" -> {
                adicionarButton.addActionListener(e -> {
                    if(SubSection.dbLastInternalCode < 99) {
                        SubSectionDataFrame subSectionData = new SubSectionDataFrame(this, tableModel, null, "Adicionar", -1, -1, null);
                        setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Máximo de SubSecções atingido.", "Máximo Excedido", JOptionPane.WARNING_MESSAGE);
                    }
                });
                editarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if(index != -1) {
                        int subSectionInternalCode = (int) tableModel.getValueAt(index, 0);
                        int subSectionId = (int) tableModel.getValueAt(index, 1);
                        String subSectionName = (String) tableModel.getValueAt(index, 2);
                        String sectionName = (String) tableModel.getValueAt(index, 3);
                        int sectionId = (int) tableData.getValueAt(index, 4);
                        SubSection editSubSection = new SubSection(subSectionInternalCode, subSectionId, subSectionName, sectionId);
                        SubSectionDataFrame subSectionData = new SubSectionDataFrame(this, tableModel, editSubSection, "Editar", index, subSectionId, sectionName);
                        setEnabled(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar uma SubSecção.", "Selecionar SubSecção", JOptionPane.WARNING_MESSAGE);
                    }
                });
                eliminarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if(index != -1) {
                        int dialogResult = JOptionPane.showConfirmDialog (null, "Tem a certeza que quer eliminar o registo? Todos os dados ligados a este registo serão eliminados.","Eliminar", JOptionPane.OK_CANCEL_OPTION);
                        if(dialogResult == JOptionPane.YES_OPTION){
                            SubSectionController.deleteSubSection((int) tableModel.getValueAt(index, 0));
                            SubSection.allSubSections.remove(index);
                            tableModel.removeRow(index);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar uma Secção.", "Selecionar Secção", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
            case "ItemCategories" -> {
                adicionarButton.addActionListener(e -> {
                    if(ItemCategories.dbLastId < 9) {
                        ItemCategoriesDataFrame itemCategoriesData = new ItemCategoriesDataFrame(this, tableModel, null, "Adicionar", -1);
                        setEnabled(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Máximo de Categorias atingido.", "Máximo Excedido", JOptionPane.WARNING_MESSAGE);
                    }
                });
                editarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if(index != -1) {
                        int itemCategoriesId = (int) tableModel.getValueAt(index, 0);
                        String itemCategoriesName = (String) tableModel.getValueAt(index, 1);
                        ItemCategories editItemCategories = new ItemCategories(itemCategoriesId, itemCategoriesName);
                        ItemCategoriesDataFrame itemCategoriesData = new ItemCategoriesDataFrame(this, tableModel, editItemCategories, "Editar", index);
                        setEnabled(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar uma Categoria.", "Selecionar Categoria", JOptionPane.WARNING_MESSAGE);
                    }
                });
                eliminarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if(index != -1) {
                        int dialogResult = JOptionPane.showConfirmDialog (null, "Tem a certeza que quer eliminar o registo? Todos os dados ligados a este registo serão eliminados.","Eliminar", JOptionPane.OK_CANCEL_OPTION);
                        if(dialogResult == JOptionPane.YES_OPTION){
                            ItemCategoriesController.deleteItemCategories((int) tableModel.getValueAt(index, 0));
                            ItemCategories.allItemCategories.remove(index);
                            tableModel.removeRow(index);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar uma Categoria.", "Selecionar Categoria", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
            case "Items" -> {
                adicionarButton.addActionListener(e -> {
                    if (Item.dbLastId < 99) {
                        ItemDataFrame itemData = new ItemDataFrame(this, tableModel, null, "Adicionar", -1, null, null);
                        setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Máximo de Items atingido.", "Máximo Excedido", JOptionPane.WARNING_MESSAGE);
                    }
                });
                editarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if (index != -1) {
                        int itemIdInterno = (int) tableModel.getValueAt(index, 0);
                        String itemId = (String) tableModel.getValueAt(index, 1);
                        String itemName = (String) tableModel.getValueAt(index, 2);
                        String itemDescription = (String) tableModel.getValueAt(index, 3);
                        LocalDate itemPurchasedAt = (LocalDate) tableModel.getValueAt(index, 4);
                        LocalDate itemEndOfLife = (LocalDate) tableModel.getValueAt(index, 5);
                        String itemCategoryName = (String) tableModel.getValueAt(index, 6);
                        String itemSubsectionName = (String) tableModel.getValueAt(index, 7);
                        int itemCategoryId = (int) tableModel.getValueAt(index, 8);
                        int itemSubsectionId = (int) tableModel.getValueAt(index, 9);
                        Item editItem = new Item(itemIdInterno, itemName, itemDescription, itemPurchasedAt, itemEndOfLife, itemId, itemSubsectionId, itemCategoryId);
                        ItemDataFrame itemData = new ItemDataFrame(this, tableModel, editItem, "Editar", index, itemCategoryName, itemSubsectionName);
                        setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar um Item.", "Selecionar Item", JOptionPane.WARNING_MESSAGE);
                    }
                });
                eliminarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if (index != -1) {
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Tem a certeza que quer eliminar o registo? Todos os dados ligados a este registo serão eliminados.", "Eliminar", JOptionPane.OK_CANCEL_OPTION);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            ItemController.deleteItem((int) tableModel.getValueAt(index, 0));
                            Item.allItems.remove(index);
                            tableModel.removeRow(index);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar um Item.", "Selecionar Item", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
            case "ItemInspections" -> {
                adicionarButton.addActionListener(e -> {
                    if (ItemInspections.dbLastId < 99999) {
                        ItemInspectionsDataFrame itemInspectionsData = new ItemInspectionsDataFrame(this, tableModel, null, "Adicionar", -1, null);
                        setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Máximo de Inspeções atingido.", "Máximo Excedido", JOptionPane.WARNING_MESSAGE);
                    }
                });
                editarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if (index != -1) {
                        int itemInspectionId = (int) tableModel.getValueAt(index, 0);
                        String itemInspectionItemName = (String) tableModel.getValueAt(index, 1);
                        String itemInspectionDescription = (String) tableModel.getValueAt(index, 2);
                        LocalDate itemInspectionsData = (LocalDate) tableModel.getValueAt(index, 3);
                        int inspectionsItemId = (int) tableModel.getValueAt(index, 4);
                        ItemInspections editItemInspection = new ItemInspections(itemInspectionId, itemInspectionsData, itemInspectionDescription, inspectionsItemId);
                        ItemInspectionsDataFrame itemInspectionData = new ItemInspectionsDataFrame(this, tableModel, editItemInspection, "Editar", index, itemInspectionItemName);
                        setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar um Item.", "Selecionar Item", JOptionPane.WARNING_MESSAGE);
                    }
                });
                eliminarButton.addActionListener(e -> {
                    int index = tableData.getSelectedRow();
                    if (index != -1) {
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Tem a certeza que quer eliminar o registo? Todos os dados ligados a este registo serão eliminados.", "Eliminar", JOptionPane.OK_CANCEL_OPTION);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            ItemInspectionsController.deleteItemInspection((int) tableModel.getValueAt(index, 0));
                            ItemInspections.allItemInspections.remove(index);
                            tableModel.removeRow(index);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar uma Inspeção.", "Selecionar Item", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
            case "Materiais-SubSecção" -> {
                adicionarButton.setVisible(false);
                editarButton.setVisible(false);
                eliminarButton.setVisible(false);
                searchButton.setVisible(true);
                searchButton.addActionListener(e -> {
                    setEnabled(false);
                    SearchDataFrame searchData = new SearchDataFrame("Materiais-SubSecção", tableModel, this);
                });
            }
            case "Materiais-Secção" -> {
                adicionarButton.setVisible(false);
                editarButton.setVisible(false);
                eliminarButton.setVisible(false);
                searchButton.setVisible(true);
                searchButton.addActionListener(e -> {
                    setEnabled(false);
                    SearchDataFrame searchData = new SearchDataFrame("Materiais-Secção", tableModel, this);
                });
            }
            case "Materiais-InspecionadosPeríodo" -> {
                adicionarButton.setVisible(false);
                editarButton.setVisible(false);
                eliminarButton.setVisible(false);
                searchButton.setVisible(true);
                searchButton.addActionListener(e -> {
                    setEnabled(false);
                    SearchDataFrame searchData = new SearchDataFrame("Materiais-InspecionadosPeríodo", tableModel, this);
                });
            }
            case "Materiais-CategoriaInspecionadosPeríodo" -> {
                adicionarButton.setVisible(false);
                editarButton.setVisible(false);
                eliminarButton.setVisible(false);
                searchButton.setVisible(true);
                searchButton.addActionListener(e -> {
                    setEnabled(false);
                    SearchDataFrame searchData = new SearchDataFrame("Materiais-CategoriaInspecionadosPeríodo", tableModel, this);
                });
            }
            case "Materiais-NuncaInspecionados" -> {
                adicionarButton.setVisible(false);
                editarButton.setVisible(false);
                eliminarButton.setVisible(false);
                searchButton.setVisible(false);
                // Abre logo com os dados
            }
            case "Materiais-NumInspeções" -> {
                adicionarButton.setVisible(false);
                editarButton.setVisible(false);
                eliminarButton.setVisible(false);
                searchButton.setVisible(true);
                searchButton.addActionListener(e -> {
                    // Abre lista de materiais
                    // Calcular numero de inspeções de cada material
                    setEnabled(false);
                    SearchDataFrame searchData = new SearchDataFrame("Materiais-NumInspeções", tableModel, this);
                });
            }
        }
        voltarButton.addActionListener(e -> {
            MenuFrame menuFrame = new MenuFrame();
            dispose();
        });
        setVisible(true);
    }

    public void dataTable() {
        Section.resetData();
        SubSection.resetData();
        Item.resetData();
        ItemCategories.resetData();
        ItemInspections.resetData();
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableData.setModel(tableModel);
        tableData.setCellSelectionEnabled(false);
        tableData.setRowSelectionAllowed(true);
        tableData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        switch (tableName) {
            case "Section" -> {
                tableModel.addColumn("ID");
                tableModel.addColumn("Nome");
                tableData.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                Section.allSections = SectionsDB.getAllSectionsDB();
                for (int i = 0; i <= Section.allSections.size() - 1; i++) {
                    Section section = Section.allSections.get(i);
                    Section.idList.add(section.getId());
                    tableModel.addRow(new Object[]{section.getId(), section.getSection()});
                }
            }
            case "SubSection" -> {
                tableModel.addColumn("Código interno");
                tableModel.addColumn("ID");
                tableModel.addColumn("Nome");
                tableModel.addColumn("Secção");
                tableModel.addColumn("Id secção");
                tableData.getColumnModel().getColumn(0).setMinWidth(0);
                tableData.getColumnModel().getColumn(0).setMaxWidth(0);
                tableData.getColumnModel().getColumn(0).setWidth(0);
                tableData.getColumnModel().getColumn(4).setMinWidth(0);
                tableData.getColumnModel().getColumn(4).setMaxWidth(0);
                tableData.getColumnModel().getColumn(4).setWidth(0);
                tableData.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
                SubSection.allSubSections = SubSectionsDB.getAllSubSectionsDB();
                SubSection.setDefaultSubSectionIds();
                Section.allSections = SectionsDB.getAllSectionsDB();
                for (Section section : Section.allSections) {
                    SubSection.sectionNameList.add(section.getSection());
                }
                for (int i = 0; i <= SubSection.allSubSections.size() - 1; i++) {
                    SubSection subSection = SubSection.allSubSections.get(i);
                    tableModel.addRow(new Object[]{subSection.getInternalCode(), subSection.getId(), subSection.getSubSection(), SectionsDB.getSectionNameDB(subSection.getSectionId()), subSection.getSectionId()});
                }
            }
            case "ItemCategories" -> {
                tableModel.addColumn("ID");
                tableModel.addColumn("Nome");
                tableData.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                ItemCategories.allItemCategories = ItemCategoriesDB.getAllItemCategoriesDB();
                for (int i = 0; i <= ItemCategories.allItemCategories.size() - 1; i++) {
                    ItemCategories itemCategories = ItemCategories.allItemCategories.get(i);
                    tableModel.addRow(new Object[]{itemCategories.getId(), itemCategories.getCategory()});
                }
            }
            case "Items",  "Materiais-InspecionadosPeríodo", "Materiais-CategoriaInspecionadosPeríodo", "Materiais-NumInspeções" -> {
                tableModel.addColumn("ID interno");
                tableModel.addColumn("ID");
                tableModel.addColumn("Nome");
                tableModel.addColumn("Descrição");
                tableModel.addColumn("Data de compra");
                tableModel.addColumn("Fim de prazo");
                tableModel.addColumn("Categoria");
                tableModel.addColumn("SubSecção");
                tableModel.addColumn("Id categoria");
                tableModel.addColumn("Id subsecção");
                tableData.getColumnModel().getColumn(0).setMinWidth(0);
                tableData.getColumnModel().getColumn(0).setMaxWidth(0);
                tableData.getColumnModel().getColumn(0).setWidth(0);
                tableData.getColumnModel().getColumn(8).setMinWidth(0);
                tableData.getColumnModel().getColumn(8).setMaxWidth(0);
                tableData.getColumnModel().getColumn(8).setWidth(0);
                tableData.getColumnModel().getColumn(9).setMinWidth(0);
                tableData.getColumnModel().getColumn(9).setMaxWidth(0);
                tableData.getColumnModel().getColumn(9).setWidth(0);
                tableData.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
                Item.allItems = ItemDB.getAllItemsDB();
                for (Item item : Item.allItems) {
                    Item.itemNameList.add(item.getName());
                }
                SubSection.allSubSections = SubSectionsDB.getAllSubSectionsDB();
                ItemCategories.allItemCategories = ItemCategoriesDB.getAllItemCategoriesDB();
                for (SubSection subSection : SubSection.allSubSections) {
                    Item.subSectionNameList.add(subSection.getSubSection());
                    Item.subSectionNameIdList.add(subSection.getInternalCode());
                }
                for (ItemCategories itemCategory : ItemCategories.allItemCategories) {
                    Item.categoryNameList.add(itemCategory.getCategory());
                    Item.categoryNameIdList.add(itemCategory.getId());
                }
                for (int i = 0; i <= Item.allItems.size() - 1; i++) {
                    Item item = Item.allItems.get(i);
                    ItemCategories itemCategories = ItemCategories.allItemCategories.stream().filter(a -> a.getId() == item.getItemCategoriesId()).collect(Collectors.toList()).get(0);
                    SubSection subSection = SubSection.allSubSections.stream().filter(a -> a.getInternalCode() == item.getSubSectionInternalCode()).collect(Collectors.toList()).get(0);
                    tableModel.addRow(new Object[]{item.getId(), item.getIdCode(), item.getName(), item.getDescription(), item.getPurchasedAt(), item.getEndOfLife(), itemCategories.getCategory(), subSection.getSubSection(), itemCategories.getId(), subSection.getInternalCode()});
                }
            }
            case "ItemInspections" -> {
                tableModel.addColumn("ID interno");
                tableModel.addColumn("Item");
                tableModel.addColumn("Descrição");
                tableModel.addColumn("Data");
                tableModel.addColumn("Id item");
                tableData.getColumnModel().getColumn(0).setMinWidth(0);
                tableData.getColumnModel().getColumn(0).setMaxWidth(0);
                tableData.getColumnModel().getColumn(0).setWidth(0);
                tableData.getColumnModel().getColumn(4).setMinWidth(0);
                tableData.getColumnModel().getColumn(4).setMaxWidth(0);
                tableData.getColumnModel().getColumn(4).setWidth(0);
                tableData.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
                ItemInspections.allItemInspections = ItemInspectionsDB.getAllItemInspectionsDB();
                Item.allItems = ItemDB.getAllItemsDB();
                for (Item item : Item.allItems) {
                    ItemInspections.itemNameList.add(item.getName());
                    ItemInspections.itemNameIdList.add(item.getId());
                }
                for (int i = 0; i <= ItemInspections.allItemInspections.size() - 1; i++) {
                    ItemInspections itemInspections = ItemInspections.allItemInspections.get(i);
                    Item item = Item.allItems.stream().filter(a -> a.getId() == itemInspections.getItemId()).collect(Collectors.toList()).get(0);
                    tableModel.addRow(new Object[]{itemInspections.getId(), item.getName(), itemInspections.getDescription(), itemInspections.getDate(), itemInspections.getItemId()});
                }
            }

            // Extra
            case "Materiais-SubSecção" -> {
                tableModel.addColumn("ID interno");
                tableModel.addColumn("ID");
                tableModel.addColumn("Nome");
                tableModel.addColumn("Descrição");
                tableModel.addColumn("Data de compra");
                tableModel.addColumn("Fim de prazo");
                tableModel.addColumn("Categoria");
                tableModel.addColumn("SubSecção");
                tableModel.addColumn("Id categoria");
                tableModel.addColumn("Id subsecção");
                tableData.getColumnModel().getColumn(0).setMinWidth(0);
                tableData.getColumnModel().getColumn(0).setMaxWidth(0);
                tableData.getColumnModel().getColumn(0).setWidth(0);
                tableData.getColumnModel().getColumn(8).setMinWidth(0);
                tableData.getColumnModel().getColumn(8).setMaxWidth(0);
                tableData.getColumnModel().getColumn(8).setWidth(0);
                tableData.getColumnModel().getColumn(9).setMinWidth(0);
                tableData.getColumnModel().getColumn(9).setMaxWidth(0);
                tableData.getColumnModel().getColumn(9).setWidth(0);
                tableData.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
                Item.allItems = ItemDB.getAllSubSectionItemsDB();
                SubSection.allSubSections = SubSectionsDB.getAllSubSectionNamesDB();
                ItemCategories.allItemCategories = ItemCategoriesDB.getAllItemCategoriesDB();
                for (SubSection subSection : SubSection.allSubSections) {
                    Item.subSectionNameList.add(subSection.getSubSection());
                    Item.subSectionNameIdList.add(subSection.getInternalCode());
                }
                for (int i = 0; i <= Item.allItems.size() - 1; i++) {
                    Item item = Item.allItems.get(i);
                    ItemCategories itemCategories = ItemCategories.allItemCategories.stream().filter(a -> a.getId() == item.getItemCategoriesId()).collect(Collectors.toList()).get(0);
                    SubSection subSection = SubSection.allSubSections.stream().filter(a -> a.getInternalCode() == item.getSubSectionInternalCode()).collect(Collectors.toList()).get(0);
                    tableModel.addRow(new Object[]{item.getId(), item.getIdCode(), item.getName(), item.getDescription(), item.getPurchasedAt(), item.getEndOfLife(), itemCategories.getCategory(), subSection.getSubSection(), itemCategories.getId(), subSection.getInternalCode()});
                }
            }
            case "Materiais-Secção" -> {
                tableModel.addColumn("ID interno");
                tableModel.addColumn("ID");
                tableModel.addColumn("Nome");
                tableModel.addColumn("Descrição");
                tableModel.addColumn("Data de compra");
                tableModel.addColumn("Fim de prazo");
                tableModel.addColumn("Categoria");
                tableModel.addColumn("SubSecção");
                tableModel.addColumn("Id categoria");
                tableModel.addColumn("Id subsecção");
                tableData.getColumnModel().getColumn(0).setMinWidth(0);
                tableData.getColumnModel().getColumn(0).setMaxWidth(0);
                tableData.getColumnModel().getColumn(0).setWidth(0);
                tableData.getColumnModel().getColumn(8).setMinWidth(0);
                tableData.getColumnModel().getColumn(8).setMaxWidth(0);
                tableData.getColumnModel().getColumn(8).setWidth(0);
                tableData.getColumnModel().getColumn(9).setMinWidth(0);
                tableData.getColumnModel().getColumn(9).setMaxWidth(0);
                tableData.getColumnModel().getColumn(9).setWidth(0);
                tableData.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
                Item.allItems = ItemDB.getAllSectionItemsDB();
                SubSection.allSubSections = SubSectionsDB.getAllSubSectionsDB();
                ItemCategories.allItemCategories = ItemCategoriesDB.getAllItemCategoriesDB();
                Section.allSections = SectionsDB.getAllSectionsDB();
                for (Section section : Section.allSections) {
                    Item.sectionNameList.add(section.getSection());
                    Item.sectionNameIdList.add(section.getId());
                }
                for (int i = 0; i <= Item.allItems.size() - 1; i++) {
                    Item item = Item.allItems.get(i);
                    ItemCategories itemCategories = ItemCategories.allItemCategories.stream().filter(a -> a.getId() == item.getItemCategoriesId()).collect(Collectors.toList()).get(0);
                    SubSection subSection = SubSection.allSubSections.stream().filter(a -> a.getInternalCode() == item.getSubSectionInternalCode()).collect(Collectors.toList()).get(0);
                    tableModel.addRow(new Object[]{item.getId(), item.getIdCode(), item.getName(), item.getDescription(), item.getPurchasedAt(), item.getEndOfLife(), itemCategories.getCategory(), subSection.getSubSection(), itemCategories.getId(), subSection.getInternalCode()});
                }
            }
            case "Materiais-NuncaInspecionados" -> {
                tableModel.addColumn("ID interno");
                tableModel.addColumn("ID");
                tableModel.addColumn("Nome");
                tableModel.addColumn("Descrição");
                tableModel.addColumn("Data de compra");
                tableModel.addColumn("Fim de prazo");
                tableModel.addColumn("Categoria");
                tableModel.addColumn("SubSecção");
                tableModel.addColumn("Id categoria");
                tableModel.addColumn("Id subsecção");
                tableData.getColumnModel().getColumn(0).setMinWidth(0);
                tableData.getColumnModel().getColumn(0).setMaxWidth(0);
                tableData.getColumnModel().getColumn(0).setWidth(0);
                tableData.getColumnModel().getColumn(8).setMinWidth(0);
                tableData.getColumnModel().getColumn(8).setMaxWidth(0);
                tableData.getColumnModel().getColumn(8).setWidth(0);
                tableData.getColumnModel().getColumn(9).setMinWidth(0);
                tableData.getColumnModel().getColumn(9).setMaxWidth(0);
                tableData.getColumnModel().getColumn(9).setWidth(0);
                tableData.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
                tableData.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
                Item.allItems = ItemDB.getAllNotInspectedItemsDB();
                SubSection.allSubSections = SubSectionsDB.getAllSubSectionsDB();
                ItemCategories.allItemCategories = ItemCategoriesDB.getAllItemCategoriesDB();
                for (SubSection subSection : SubSection.allSubSections) {
                    Item.subSectionNameList.add(subSection.getSubSection());
                    Item.subSectionNameIdList.add(subSection.getInternalCode());
                }
                for (ItemCategories itemCategory : ItemCategories.allItemCategories) {
                    Item.categoryNameList.add(itemCategory.getCategory());
                    Item.categoryNameIdList.add(itemCategory.getId());
                }
                for (int i = 0; i <= Item.allItems.size() - 1; i++) {
                    Item item = Item.allItems.get(i);
                    ItemCategories itemCategories = ItemCategories.allItemCategories.stream().filter(a -> a.getId() == item.getItemCategoriesId()).collect(Collectors.toList()).get(0);
                    SubSection subSection = SubSection.allSubSections.stream().filter(a -> a.getInternalCode() == item.getSubSectionInternalCode()).collect(Collectors.toList()).get(0);
                    tableModel.addRow(new Object[]{item.getId(), item.getIdCode(), item.getName(), item.getDescription(), item.getPurchasedAt(), item.getEndOfLife(), itemCategories.getCategory(), subSection.getSubSection(), itemCategories.getId(), subSection.getInternalCode()});
                }
            }
        }
    }
}
