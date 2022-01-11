package views;

import com.github.lgooddatepicker.components.DatePicker;
import controllers.ItemController;
import controllers.ItemInspectionsController;
import models.Item;
import models.ItemInspections;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchDataFrame extends JFrame {
    private JPanel SearchPanel;
    private JButton buttonSearch;
    private JButton buttonVoltar;
    private JComboBox comboBoxSearchData;
    private DatePicker datePickerAfter;
    private DatePicker datePickerBefore;

    public SearchDataFrame(String tipoPesquisa, DefaultTableModel tableData, ListDataFrame listData) {
        setContentPane(SearchPanel);
        setTitle(tipoPesquisa);
        setSize(512, 384);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        datePickerBefore.setVisible(false);
        datePickerAfter.setVisible(false);
        setVisible(true);
        switch (tipoPesquisa) {
            case "Materiais-SubSecção" -> {
                comboBoxSearchData.setModel(new DefaultComboBoxModel<>(Item.subSectionNameList.toArray()));
                buttonSearch.addActionListener(e -> {
                    if(comboBoxSearchData.getSelectedItem() != null) {
                        setEnabled(false);
                        ItemController.getSubSectionItems(comboBoxSearchData.getSelectedItem().toString(), tableData);
                        setEnabled(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar um Item.", "Selecionar Item", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
            case "Materiais-Secção" -> {
                comboBoxSearchData.setModel(new DefaultComboBoxModel<>(Item.sectionNameList.toArray()));
                buttonSearch.addActionListener(e -> {
                    if(comboBoxSearchData.getSelectedItem() != null) {
                        setEnabled(false);
                        ItemController.getSectionItems(comboBoxSearchData.getSelectedItem().toString(), tableData);
                        setEnabled(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar um Item.", "Selecionar Item", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
            case "Materiais-InspecionadosPeríodo" -> {
                datePickerBefore.setVisible(true);
                datePickerAfter.setVisible(true);
                comboBoxSearchData.setVisible(false);
                buttonSearch.addActionListener(e -> {
                    if(datePickerBefore.getDate() != null && datePickerAfter.getDate() != null) {
                        if(datePickerBefore.getDate().isBefore(datePickerAfter.getDate())) {
                            setEnabled(false);
                            ItemController.getInspectedItemsFromDate(datePickerBefore.getDate().toString(), datePickerAfter.getDate().toString(), tableData);
                            setEnabled(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "A data inicial tem de ser menor do que a final.", "Valor das Datas", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar as Datas.", "Selecionar Datas", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
            case "Materiais-CategoriaInspecionadosPeríodo" -> {
                datePickerBefore.setVisible(true);
                datePickerAfter.setVisible(true);
                comboBoxSearchData.setModel(new DefaultComboBoxModel<>(Item.categoryNameList.toArray()));
                buttonSearch.addActionListener(e -> {
                    if(comboBoxSearchData.getSelectedItem() != null) {
                        if (datePickerBefore.getDate() != null && datePickerAfter.getDate() != null) {
                            if (datePickerBefore.getDate().isBefore(datePickerAfter.getDate())) {
                                setEnabled(false);
                                ItemController.getCategoryItemsInspectedFromDate(comboBoxSearchData.getSelectedItem().toString(), datePickerBefore.getDate().toString(), datePickerAfter.getDate().toString(), tableData);
                                setEnabled(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "A data inicial tem de ser menor do que a final.", "Valor das Datas", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Tem de selecionar as Datas.", "Selecionar Datas", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Tem de selecionar um Item.", "Selecionar Item", JOptionPane.WARNING_MESSAGE);
                    }
                });
            }
            case "Materiais-NumInspeções" -> {
                comboBoxSearchData.setModel(new DefaultComboBoxModel<>(Item.itemNameList.toArray()));
                buttonSearch.addActionListener(e -> {
                    if(comboBoxSearchData.getSelectedItem() != null) {
                        Item item = Item.allItems.get(comboBoxSearchData.getSelectedIndex());
                        int numInspecs = ItemController.getNumInspections(item, tableData);
                        if (numInspecs != -1) {
                            setEnabled(false);
                            JOptionPane.showMessageDialog(null, "Este Item tem: "+ numInspecs +" Inspeções.", "Número de Inspeções", JOptionPane.INFORMATION_MESSAGE);
                            setEnabled(true);
                        }
                    } else {
                    JOptionPane.showMessageDialog(null, "Tem de selecionar um Item.", "Selecionar Item", JOptionPane.WARNING_MESSAGE);
                }
                });
            }
        }
        buttonVoltar.addActionListener(e -> {
            listData.setEnabled(true);
            listData.dataTable();
            dispose();
        });
    }
}

