package views;

import com.github.lgooddatepicker.zinternaltools.Convert;
import controllers.SubSectionController;
import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SubSectionDataFrame extends JFrame{
    private JButton acaoButton;
    private JButton cancelarButton;
    private JComboBox comboBoxSeccao;
    private JLabel JLabelSubSection;
    private JLabel JLabelSection;
    private JTextField textFieldSubSection;
    private JPanel SubSectionPanel;
    private JLabel JLabelSubSectionId;
    private JComboBox comboBoxSubSectionId;
    private String result;

    public SubSectionDataFrame(ListDataFrame listData, DefaultTableModel tableModel, SubSection subSectionData, String acao, int selectedRow, int subSectionIdSelectedCombo, String sectionSelectedCombo) {
        setContentPane(SubSectionPanel);
        setTitle("SubSection");
        setSize(512, 384);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        comboBoxSubSectionId.setModel(new DefaultComboBoxModel<>(SubSection.defaultSubSectionIds.toArray()));
        comboBoxSeccao.setModel(new DefaultComboBoxModel<>(SubSection.sectionNameList.toArray()));
        setVisible(true);
        if (acao.equals("Adicionar")) {
            acaoButton.setText("Adicionar");
            acaoButton.addActionListener(e -> {
                String subSectionId = comboBoxSubSectionId.getSelectedItem().toString();
                String name = textFieldSubSection.getText();
                String section = null;
                if(comboBoxSeccao.getSelectedItem() != null) {
                    section = comboBoxSeccao.getSelectedItem().toString();
                }
                result = SubSectionController.addSubSections(subSectionId, name, section, tableModel);
                if (result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "SubSecção Adicionada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                } else if (result.equals("SubSectionName")) {
                    textFieldSubSection.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo nome não possui os requisitos.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textFieldSubSection.setBackground(UIManager.getColor("TextField.background"));
                } else if (result.equals("SectionName")) {
                    comboBoxSeccao.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo secção tem de ser preenchido.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    comboBoxSeccao.setBackground(UIManager.getColor("ComboBox.background"));
                } else if (result.equals("Exists")) {
                    textFieldSubSection.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(null, "SubSecção com nome existente.", "Insucesso", JOptionPane.WARNING_MESSAGE);
                    textFieldSubSection.setBackground(UIManager.getColor("TextField.background"));
                } else {
                    JOptionPane.showMessageDialog(null, result, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
        } else if (acao.equals("Editar")) {
            acaoButton.setText("Editar");
            comboBoxSubSectionId.setSelectedItem(subSectionIdSelectedCombo);
            comboBoxSubSectionId.setEnabled(false);
            textFieldSubSection.setText(subSectionData.getSubSection());
            comboBoxSeccao.setSelectedItem(sectionSelectedCombo);
            acaoButton.addActionListener(e -> {
                String subSectionId = comboBoxSubSectionId.getSelectedItem().toString();
                String name = textFieldSubSection.getText();
                String sectionName = comboBoxSeccao.getSelectedItem().toString();
                result = SubSectionController.updateSubSections(subSectionId, name, sectionName, subSectionData, selectedRow, tableModel);
                if (result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "SubSecção Atualizada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listData.setEnabled(true);
                    dispose();
                } else if (result.equals("SubSectionName")) {
                    textFieldSubSection.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo nome não possui os requisitos.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    textFieldSubSection.setBackground(UIManager.getColor("TextField.background"));
                } else if (result.equals("SectionName")) {
                    comboBoxSeccao.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "O campo secção tem de ser preenchido.", "Erro nos dados", JOptionPane.ERROR_MESSAGE);
                    comboBoxSeccao.setBackground(UIManager.getColor("ComboBox.background"));
                } else if (result.equals("Exists")) {
                    textFieldSubSection.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(null, "SubSecção com nome existente.", "Insucesso", JOptionPane.WARNING_MESSAGE);
                    textFieldSubSection.setBackground(UIManager.getColor("TextField.background"));
                } else {
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
