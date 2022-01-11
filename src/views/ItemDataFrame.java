package views;

import com.github.lgooddatepicker.components.DatePicker;
import controllers.ItemController;
import models.Item;
import models.ItemCategories;
import models.SubSection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemDataFrame extends JFrame {
    private JButton acaoButton;
    private JButton cancelarButton;
    private JTextField textFieldItemName;
    private JComboBox comboBoxSubSection;
    private JComboBox comboBoxItemCategories;
    private JTextArea textAreaDescription;
    private JPanel ItemPanel;
    private DatePicker datePickerPurchasedAt;
    private DatePicker datePickerEndOfLife;
    private JTextField textFieldSubSection;
    private String result;

    public ItemDataFrame(ListDataFrame listData, DefaultTableModel tableModel, Item itemData, String acao, int selectedRow, String CategorySelectedCombo, String subSectionSelectedCombo) {
        setContentPane(ItemPanel);
        setTitle("Item");
        setSize(512, 384);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        comboBoxSubSection.setModel(new DefaultComboBoxModel<>(Item.subSectionNameIdList.toArray()));
        comboBoxItemCategories.setModel(new DefaultComboBoxModel<>(Item.categoryNameList.toArray()));
        setVisible(true);
        if (acao.equals("Adicionar")) {
            acaoButton.setText("Adicionar");
            acaoButton.addActionListener(e -> {
                String name = textFieldItemName.getText();
                String description = textAreaDescription.getText();
                String purchasedAt = datePickerPurchasedAt.getDateStringOrEmptyString();
                String endOfLife = datePickerEndOfLife.getDateStringOrEmptyString();
                SubSection subSection = null;
                if (comboBoxSubSection.getSelectedItem() != null) {
                    subSection = SubSection.allSubSections.get(comboBoxSubSection.getSelectedIndex());
                }
                ItemCategories category = null;
                if(comboBoxItemCategories.getSelectedItem() != null) {
                    category = ItemCategories.allItemCategories.get(comboBoxItemCategories.getSelectedIndex());
                }
                result = ItemController.addItem(name, description, purchasedAt, endOfLife, subSection, category, tableModel);
                if (result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "Item Adicionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                }
                else if (result.equals("ItemName")) {
                    textFieldItemName.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Nome não contem os requisitos.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textFieldItemName.setBackground(UIManager.getColor("TextField.background"));
                } else if (result.equals("Description")) {
                    textAreaDescription.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Descrição excedeu o número máximo de caracteres.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textAreaDescription.setBackground(UIManager.getColor("TextArea.background"));
                } else if (result.equals("CategoryName")) {
                    comboBoxItemCategories.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Categoria tem de ser preenchido.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    comboBoxItemCategories.setBackground(UIManager.getColor("ComboBox.background"));
                } else if (result.equals("SubSectionName")) {
                    comboBoxSubSection.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(null, "O campo SubSecção tem de ser preenchido.", "Erro nos dados", JOptionPane.WARNING_MESSAGE);
                    comboBoxSubSection.setBackground(UIManager.getColor("ComboBox.background"));
                } else {
                    JOptionPane.showMessageDialog(null, result, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        } else if (acao.equals("Editar")) {
            acaoButton.setText("Editar");
            textFieldItemName.setText(itemData.getName());
            datePickerPurchasedAt.setDate(itemData.getPurchasedAt());
            datePickerEndOfLife.setDate(itemData.getEndOfLife());
            textAreaDescription.setText(itemData.getDescription());
            comboBoxItemCategories.setSelectedItem(CategorySelectedCombo);
            comboBoxSubSection.setSelectedItem(subSectionSelectedCombo);
            acaoButton.addActionListener(e -> {
                String name = textFieldItemName.getText();
                String description = textAreaDescription.getText();
                String purchasedAt = datePickerPurchasedAt.getDateStringOrEmptyString();
                String endOfLife = datePickerEndOfLife.getDateStringOrEmptyString();
                SubSection subSection = null;
                if (comboBoxSubSection.getSelectedItem() != null) {
                    subSection = SubSection.allSubSections.get(comboBoxSubSection.getSelectedIndex());
                }
                ItemCategories category = null;
                if(comboBoxItemCategories.getSelectedItem() != null) {
                    category = ItemCategories.allItemCategories.get(comboBoxItemCategories.getSelectedIndex());
                }
                result = ItemController.updateItem(name, description, purchasedAt, endOfLife, subSection, category, itemData, selectedRow, tableModel);
                if (result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "SubSecção Atualizada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                }  else if (result.equals("ItemName")) {
                    textFieldItemName.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Nome não contem os requisitos.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textFieldItemName.setBackground(UIManager.getColor("TextField.background"));
                } else if (result.equals("Description")) {
                        textAreaDescription.setBackground(Color.red);
                        JOptionPane.showMessageDialog(null, "O campo Descrição excedeu o número máximo de caracteres.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                        textAreaDescription.setBackground(UIManager.getColor("TextArea.background"));
                } else if (result.equals("CategoryName")) {
                    comboBoxItemCategories.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Categoria tem de ser preenchido.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    comboBoxItemCategories.setBackground(UIManager.getColor("ComboBox.background"));
                } else if (result.equals("SubSectionName")) {
                    comboBoxSubSection.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(null, "O campo SubSecção tem de ser preenchido.", "Erro nos dados", JOptionPane.WARNING_MESSAGE);
                    comboBoxSubSection.setBackground(UIManager.getColor("ComboBox.background"));
                } else {
                    JOptionPane.showMessageDialog(null, result, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        cancelarButton.addActionListener(e -> {
            listData.setEnabled(true);
            dispose();
        });
        comboBoxSubSection.addActionListener(e -> {
            textFieldSubSection.setText(SubSection.allSubSections.get(comboBoxSubSection.getSelectedIndex()).getSubSection());
        });
    }
}
