package com.company;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class PopClickListener extends MouseAdapter {
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        JTable table = null;
        int activeTable = ListPanel.getActiveTable();

        if (activeTable == 0) {
            table = ListPanel.getCarsTable();
        } else if (activeTable == 1) {
            table = ListPanel.getExpressTable();
        }

        assert table != null;
        PopUp menu = new PopUp(e, table, activeTable);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
