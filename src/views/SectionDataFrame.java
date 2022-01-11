package views;

import controllers.SectionController;
import models.Section;
import models.SubSection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SectionDataFrame extends JFrame{
    private JButton cancelarButton;
    private JButton acaoButton;
    private JLabel JLabelSection;
    private JTextField textFieldSection;
    private JPanel SectionPanel;
    private String result;

    public SectionDataFrame(ListDataFrame listData, DefaultTableModel tableModel, Section sectionData, String acao, int selectedRow) {
        setContentPane(SectionPanel);
        setTitle("Section");
        setSize(512, 384);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        if(acao.equals("Adicionar")) {
            acaoButton.setText("Adicionar");
            acaoButton.addActionListener(e -> {
                String nome = textFieldSection.getText();
                result = SectionController.addSections(nome, tableModel);
                if(result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "Secção Adicionada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                }
                else if(result.equals("Exists")) {
                    textFieldSection.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(null, "Secção existente.", "Insucesso", JOptionPane.WARNING_MESSAGE);
                    textFieldSection.setBackground(UIManager.getColor("TextField.background"));
                }
                else if(result.equals("Name")) {
                    textFieldSection.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Nome tem de ser preenchido.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textFieldSection.setBackground(UIManager.getColor("TextField.background"));
                }
                else {
                    JOptionPane.showMessageDialog(null, result, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        else if(acao.equals("Editar")) {
            acaoButton.setText("Editar");
            textFieldSection.setText(sectionData.getSection());
            acaoButton.addActionListener(e -> {
                String nome = textFieldSection.getText();
                result = SectionController.updateSection(nome, sectionData, selectedRow, tableModel);
                if(result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "Secção Atualizada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                }
                else if(result.equals("Exists")) {
                    textFieldSection.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(null, "Secção com nome existente.", "Insucesso", JOptionPane.WARNING_MESSAGE);
                    textFieldSection.setBackground(UIManager.getColor("TextField.background"));
                }
                else if(result.equals("Name")) {
                    textFieldSection.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo Nome não possui os requisitos", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textFieldSection.setBackground(UIManager.getColor("TextField.background"));
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


