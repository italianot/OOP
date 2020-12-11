package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class PopUp  extends JPopupMenu {
    JMenuItem menuItem;

    public PopUp(MouseEvent e, JTable table, int activeTable){

        menuItem = new JMenuItem("Редактировать");

        menuItem.addActionListener(event -> {

            int column = table.columnAtPoint(e.getPoint());
            int row = table.rowAtPoint(e.getPoint());
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            Vector element = (Vector) model.getDataVector().elementAt(row);

            String name = element.get(0).toString();

            Place place = AppGUI.findPlace(name, activeTable == 0 ? "OBL" : "CITY");

            AddPanel.setCellPos(row,column);

            AddPanel.fillFields(place);

            AddPanel.setEditMode();

            CardLayout cardLayout = (CardLayout) AppGUI.getCardPane().getLayout();
            cardLayout.show(AppGUI.getCardPane(), "Add");
        });

        add(menuItem);

        menuItem = new JMenuItem("Удалить");

        try {
            int column = table.columnAtPoint(e.getPoint());
            int row = table.rowAtPoint(e.getPoint());
            table.setColumnSelectionInterval(column, column);
            table.setRowSelectionInterval(row, row);

            menuItem.addActionListener(event -> {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Удалить эту строку?", "Warning", dialogButton);

                if(dialogResult == JOptionPane.YES_OPTION){
                    DefaultTableModel model = (DefaultTableModel) table.getModel();

                    Vector element = (Vector) model.getDataVector().elementAt(row);

                    String name = element.get(0).toString();

                    AppGUI.deletePlace(name, activeTable == 0 ? "OBL" : "CITY");

                    model.removeRow(row);
                }
            });
        } catch (IllegalAccessError error) {
            System.out.println("Строка не выбрана");
        }

        add(menuItem);
    }
}
