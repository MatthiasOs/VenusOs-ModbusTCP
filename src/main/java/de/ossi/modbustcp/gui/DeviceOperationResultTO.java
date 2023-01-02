package de.ossi.modbustcp.gui;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Transport Object for Data of the GUI Table.
 *
 * @author ossi
 */
@EqualsAndHashCode(exclude = "removeButton")
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
    @Setter
    private transient JButton removeButton;

    public DeviceOperationResultTO(ModbusOperation operation, ModbusDevice modbusDevice, JButton removeButton) {
        this.operation = operation;
        this.modbusDevice = modbusDevice;
        this.removeButton = removeButton;
    }
}
