package com.escoteiros.abd.views;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.escoteiros.abd.controllers.ItemInspectionsController;
import com.escoteiros.abd.models.Item;
import com.escoteiros.abd.models.ItemInspections;
import com.github.lgooddatepicker.components.DatePicker;

public class ItemInspectionsDataFrame extends JFrame{
    private JButton acaoButton;
    private JButton cancelarButton;
    private JTextArea textAreaDescription;
    private JPanel ItemInspectionsPanel;
    private JComboBox comboBoxItem;
    private DatePicker datePickerDate;
    private JTextField textFieldItem;
    private String result;

    public ItemInspectionsDataFrame(ListDataFrame listData, DefaultTableModel tableModel, ItemInspections itemInspectionData, String acao, int selectedRow, String itemSelectedCombo) {
        setContentPane(ItemInspectionsPanel);
        setTitle("ItemInspections");
        setSize(512, 384);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        comboBoxItem.setModel(new DefaultComboBoxModel<>(ItemInspections.itemNameIdList.toArray()));
        setVisible(true);
        if (acao.equals("Adicionar")) {
            acaoButton.setText("Adicionar");
            acaoButton.addActionListener(e -> {
                String date = datePickerDate.getDateStringOrEmptyString();
                String description = textAreaDescription.getText();
                Item item = null;
                if (comboBoxItem.getSelectedItem() != null) {
                    item = Item.allItems.get(comboBoxItem.getSelectedIndex());
                }
                result = ItemInspectionsController.addItemInspection(date, description, item, tableModel);
                if (result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "Inspeção Adicionada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                } else if (result.equals("ItemName")) {
                    comboBoxItem.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Nome não contem os requisitos.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    comboBoxItem.setBackground(UIManager.getColor("ComboBox.background"));
                } else if (result.equals("Description")) {
                    textAreaDescription.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Descrição não contem os requisitos.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textAreaDescription.setBackground(UIManager.getColor("TextArea.background"));
                } else if (result.equals("Date")) {
                    datePickerDate.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Date tem de ser preenchido.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    datePickerDate.setBackground(UIManager.getColor("DatePicker.background"));
                } else {
                    JOptionPane.showMessageDialog(null, result, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        } else if (acao.equals("Editar")) {
            acaoButton.setText("Editar");
            comboBoxItem.setSelectedItem(itemSelectedCombo);
            datePickerDate.setDate(itemInspectionData.getDate());
            textAreaDescription.setText(itemInspectionData.getDescription());
            acaoButton.addActionListener(e -> {
                Item item = null;
                if (comboBoxItem.getSelectedItem() != null) {
                    item = Item.allItems.get(comboBoxItem.getSelectedIndex());
                }
                String date = datePickerDate.getDateStringOrEmptyString();
                String description = textAreaDescription.getText();
                result = ItemInspectionsController.updateItemInspection(date, description, item, itemInspectionData, selectedRow, tableModel);
                if (result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "SubSecção Atualizada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                } else if (result.equals("ItemName")) {
                    comboBoxItem.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Nome não contem os requisitos.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    comboBoxItem.setBackground(UIManager.getColor("ComboBox.background"));
                } else if (result.equals("Description")) {
                    textAreaDescription.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Descrição não contem os requisitos.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textAreaDescription.setBackground(UIManager.getColor("TextArea.background"));
                } else if (result.equals("Date")) {
                    datePickerDate.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Date tem de ser preenchido.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    datePickerDate.setBackground(UIManager.getColor("DatePicker.background"));
                } else {
                    JOptionPane.showMessageDialog(null, result, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        cancelarButton.addActionListener(e -> {
            listData.setEnabled(true);
            dispose();
        });

        comboBoxItem.addActionListener(e -> {
            textFieldItem.setText(Item.allItems.get(comboBoxItem.getSelectedIndex()).getName());
        });
    }
}
