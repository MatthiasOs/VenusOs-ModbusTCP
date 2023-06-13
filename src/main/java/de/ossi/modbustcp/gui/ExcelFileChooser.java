package de.ossi.modbustcp.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExcelFileChooser extends JFileChooser {

    public ExcelFileChooser() {
        super(System.getProperty("user.dir"));
        setDialogTitle("Choose 'CCGX-Modbus-TCP-register' Excel");
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setMultiSelectionEnabled(false);
        setFileFilter(new FileNameExtensionFilter(".xlsx", "xlsx"));
    }
}
