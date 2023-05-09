package de.ossi.modbustcp.gui;

import ca.odell.glazedlists.gui.WritableTableFormat;

import java.time.format.DateTimeFormatter;

/**
 * Tableformat welches angibt, wie die Tabelle aussieht, also Spalten Anzahl und
 * Bezeichnung.
 *
 * @author ossi
 */
class DeviceOperationResultTableFormat implements WritableTableFormat<DeviceOperationResultTO> {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Operation";
            case 1:
                return "Device";
            case 2:
                return "Time";
            case 3:
                return "Result";
            default:
                return null;
        }
    }

    @Override
    public Object getColumnValue(DeviceOperationResultTO baseObject, int column) {
        switch (column) {
            case 0:
                return baseObject.getOperation()
                                 .getName();
            case 1:
                return baseObject.getModbusDevice()
                                 .getName();
            case 2:
                return baseObject.getTimeOfMeasurement() != null ? ISO_DATE.format(baseObject.getTimeOfMeasurement()) : null;
            case 3:
                return baseObject.getMeasurement();
            default:
                return null;
        }
    }

    @Override
    public boolean isEditable(DeviceOperationResultTO baseObject, int column) {
        return false;
    }

    @Override
    public DeviceOperationResultTO setColumnValue(DeviceOperationResultTO baseObject, Object editedValue, int column) {
        return null;
    }
}