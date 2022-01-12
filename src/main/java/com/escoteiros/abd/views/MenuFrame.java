package com.escoteiros.abd.views;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.escoteiros.abd.controllers.ExcelController;
import com.escoteiros.abd.db.*;
import com.escoteiros.abd.models.Item;
import com.escoteiros.abd.models.ItemCategories;
import com.escoteiros.abd.models.ItemInspections;
import com.escoteiros.abd.models.SubSection;

public class MenuFrame extends JFrame {
    private JPanel menuPanel;
    private JLabel labelIcon;
    private String connectionResult;

    public MenuFrame() {
        setContentPane(menuPanel);
        setTitle("Menu");
        setSize(1024, 768);
        setResizable(false);
        menuBar();
        labelIcon.setIcon(new ImageIcon(new ImageIcon("src/main/resources/menuLogo.png").getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT)));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void menuBar() {
        var menuBar = new JMenuBar();

        var fileMenu = new JMenu("DB");
        var secMenu = new JMenu("Secções");
        var subSecMenu = new JMenu("Sub-secções");
        var itemsMenu = new JMenu("Items");
        var categoriesMenu = new JMenu("Categorias de Items");
        var inspectionsMenu = new JMenu("Inspeções de Items");
        var materiaisMenu = new JMenu("Operações com Materiais");

        var fileItem1 = new JMenuItem("Test Connection");
        var fileItem2 = new JMenuItem("Upload Excel");
        var fileItem3 = new JMenuItem("Reset dados");
        var secItem1 = new JMenuItem("Ver / Manipular dados");
        var subSecItem1 = new JMenuItem("Ver / Manipular dados");
        var itemsItem1 = new JMenuItem("Ver / Manipular dados");
        var categoriesItem1 = new JMenuItem("Ver / Manipular dados");
        var inspectionsItem1 = new JMenuItem("Ver / Manipular dados");
        var materiaisMenuItem1 = new JMenuItem("Materiais - SubSecções");
        var materiaisMenuItem2 = new JMenuItem("Materiais - Secções");
        var materiaisMenuItem3 = new JMenuItem("Materiais - Inspecionados em dado período Tempo");
        var materiaisMenuItem4 = new JMenuItem("Materiais - Certa Categoria, Inspecionados em dado período Tempo");
        var materiaisMenuItem5 = new JMenuItem("Materiais - Nunca Inspecionados");
        var materiaisMenuItem6 = new JMenuItem("Materiais - Número de Inspeções");

        fileItem1.setToolTipText("Testa a ligação à DB");
        fileItem2.setToolTipText("Upload dos dados para a DB");
        fileItem2.setToolTipText("Reset dos dados da DB");
        secItem1.setToolTipText("Ver / Adicionar / Editar / Remover");
        subSecItem1.setToolTipText("Ver / Adicionar / Editar / Remover");
        itemsItem1.setToolTipText("Ver / Adicionar / Editar / Remover");
        categoriesItem1.setToolTipText("Ver / Adicionar / Editar / Remover");
        inspectionsItem1.setToolTipText("Ver / Adicionar / Editar / Remover");
        materiaisMenuItem1.setToolTipText("Materiais das SubSecções");
        materiaisMenuItem2.setToolTipText("Materiais das Secções");
        materiaisMenuItem3.setToolTipText("Materiais Inspecionados em dado período");
        materiaisMenuItem4.setToolTipText("Materiais das Categorias, Inspecionados em dado período");
        materiaisMenuItem5.setToolTipText("Materiais nunca Inspecionados");
        materiaisMenuItem6.setToolTipText("Materiais número de Inspeções");

        fileItem1.addActionListener(e -> {
            setEnabled(false);
            connectionResult = OracleDB.testConnection();
            if(connectionResult.equals("Conexão bem sucedida!")) {
                JOptionPane.showMessageDialog(null, connectionResult, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, connectionResult, "Erro", JOptionPane.ERROR_MESSAGE);
            }
            setEnabled(true);
        });
        fileItem2.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog (null, "A seguinte operação irá substituir os registos de todas as tabelas. Deseja Continuar?","Inserir dados DB", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                setEnabled(false);
                String result = ExcelController.ExcelController();
                if(result.equals("Successful")) {
                    JOptionPane.showMessageDialog(null, "Dados do Excel inseridos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, result, "Erro na inserção dos dados.", JOptionPane.ERROR_MESSAGE);
                }
                setEnabled(true);
            }
        });
        fileItem3.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog (null, "A seguinte operação irá eliminar os registos de todas as tabelas. Deseja Continuar?","Eliminar dados DB", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                setEnabled(false);
                String result;
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

                SubSection.dbLastInternalCode = 0;
                Item.dbLastId = 0;
                ItemCategories.dbLastId = 0;
                ItemInspections.dbLastId = 0;
            }
            JOptionPane.showMessageDialog(null, "Dados removidos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            setEnabled(true);
        });
        secItem1.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("Section");
            dispose();
        });
        subSecItem1.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("SubSection");
            dispose();
        });
        itemsItem1.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("Items");
            dispose();
        });
        categoriesItem1.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("ItemCategories");
            dispose();
        });
        inspectionsItem1.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("ItemInspections");
            dispose();
        });

        // EXTRA

        materiaisMenuItem1.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("Materiais-SubSecção");
            dispose();
        });

        materiaisMenuItem2.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("Materiais-Secção");
            dispose();
        });

        materiaisMenuItem3.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("Materiais-InspecionadosPeríodo");
            dispose();
        });

        materiaisMenuItem4.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("Materiais-CategoriaInspecionadosPeríodo");
            dispose();
        });

        materiaisMenuItem5.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("Materiais-NuncaInspecionados");
            dispose();
        });

        materiaisMenuItem6.addActionListener(e -> {
            ListDataFrame listData = new ListDataFrame("Materiais-NumInspeções");
            dispose();
        });

        fileMenu.add(fileItem1);
        fileMenu.add(fileItem2);
        fileMenu.add(fileItem3);
        secMenu.add(secItem1);
        subSecMenu.add(subSecItem1);
        itemsMenu.add(itemsItem1);
        categoriesMenu.add(categoriesItem1);
        inspectionsMenu.add(inspectionsItem1);
        // Extra
        materiaisMenu.add(materiaisMenuItem1);
        materiaisMenu.add(materiaisMenuItem2);
        materiaisMenu.add(materiaisMenuItem3);
        materiaisMenu.add(materiaisMenuItem4);
        materiaisMenu.add(materiaisMenuItem5);
        materiaisMenu.add(materiaisMenuItem6);

        menuBar.add(fileMenu);
        menuBar.add(secMenu);
        menuBar.add(subSecMenu);
        menuBar.add(itemsMenu);
        menuBar.add(categoriesMenu);
        menuBar.add(inspectionsMenu);
        //Extra
        menuBar.add(materiaisMenu);

        setJMenuBar(menuBar);
    }
}
