package de.ossi.modbustcp.gui;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Transport Object for Data of the GUI Table.
 *
 * @author ossi
 */
@Getter
public class DeviceOperationResultTO implements Serializable {
    private static final long serialVersionUID = 6004098018019229429L;
    @NonNull
    private final ModbusOperation operation;
    @NonNull
    private final ModbusDevice modbusDevice;
    @Setter
    private transient LocalDateTime timeOfMeasurement;
    @Setter
    private transient String measurement;

    public DeviceOperationResultTO(ModbusOperation operation, ModbusDevice modbusDevice) {
        this.operation = operation;
        this.modbusDevice = modbusDevice;
    }
}
