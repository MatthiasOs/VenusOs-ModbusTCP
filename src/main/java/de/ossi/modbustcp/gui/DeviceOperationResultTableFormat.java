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
        return switch (column) {
            case 0 -> "Operation";
            case 1 -> "Device";
            case 2 -> "Time";
            case 3 -> "Result";
            default -> null;
        };
    }

    @Override
    public Object getColumnValue(DeviceOperationResultTO baseObject, int column) {
        return switch (column) {
            case 0 -> baseObject.getOperation().toString();
            case 1 -> baseObject.getModbusDevice().toString();
            case 2 ->
                    baseObject.getTimeOfMeasurement() != null ? ISO_DATE.format(baseObject.getTimeOfMeasurement()) : null;
            case 3 -> baseObject.getMeasurement();
            default -> null;
        };
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