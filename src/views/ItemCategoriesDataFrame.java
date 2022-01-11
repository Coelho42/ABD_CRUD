package views;
import controllers.ItemCategoriesController;
import models.ItemCategories;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ItemCategoriesDataFrame extends JFrame {
    private JButton acaoButton;
    private JButton cancelarButton;
    private JTextField textFieldCategory;
    private JLabel JLabelCategory;
    private JPanel ItemCategoriesPanel;
    private String result;

    public ItemCategoriesDataFrame(ListDataFrame listData, DefaultTableModel tableModel, ItemCategories itemCategoriesData, String acao, int selectedRow) {
        setContentPane(ItemCategoriesPanel);
        setTitle("ItemCategories");
        setSize(512, 384);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        if(acao.equals("Adicionar")) {
            acaoButton.setText("Adicionar");
            acaoButton.addActionListener(e -> {
                String nome = textFieldCategory.getText();
                result = ItemCategoriesController.addItemCategories(nome, tableModel);
                if(result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "Categoria Adicionada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                }
                else if(result.equals("Exists")) {
                    textFieldCategory.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(null, "Categoria existente.", "Insucesso", JOptionPane.WARNING_MESSAGE);
                    textFieldCategory.setBackground(UIManager.getColor("TextField.background"));
                }
                else if(result.equals("Name")) {
                    textFieldCategory.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo nome tem de ser preenchido.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textFieldCategory.setBackground(UIManager.getColor("TextField.background"));
                }
                else {
                    JOptionPane.showMessageDialog(null, result, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        else if(acao.equals("Editar")) {
            acaoButton.setText("Editar");
            textFieldCategory.setText(itemCategoriesData.getCategory());
            acaoButton.addActionListener(e -> {
                String nome = textFieldCategory.getText();
                result = ItemCategoriesController.updateItemCategories(nome, itemCategoriesData, selectedRow, tableModel);
                if(result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "Categoria Atualizada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                }
                else if(result.equals("Exists")) {
                    textFieldCategory.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(null, "Categoria com nome existente.", "Insucesso", JOptionPane.WARNING_MESSAGE);
                    textFieldCategory.setBackground(UIManager.getColor("TextField.background"));
                }
                else if(result.equals("Name")) {
                    textFieldCategory.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Nome não possui os requisitos", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textFieldCategory.setBackground(UIManager.getColor("TextField.background"));
                }
                else {
                    JOptionPane.showMessageDialog(null, result, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        cancelarButton.addActionListener(e -> {
            listData.setEnabled(true);
            dispose();
        });
    }
}
