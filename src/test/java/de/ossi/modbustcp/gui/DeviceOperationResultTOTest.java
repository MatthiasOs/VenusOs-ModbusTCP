package de.ossi.modbustcp.gui;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.data.unit.AccessMode;
import de.ossi.modbustcp.data.unit.Category;
import de.ossi.modbustcp.data.unit.DBusUnit;
import de.ossi.modbustcp.data.unit.Type;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;


class DeviceOperationResultTOTest {

    private static final DBusUnit CELSIUS = new DBusUnit("Degrees celsius");
    private static final ModbusOperation BAT_BATTERY_TEMPERATURE = new ModbusOperation(Category.BATTERY, "Battery temperature", 262, Type.INT16, 10D, "", "/Dc/0/Temperature",
            AccessMode.READ_ONLY, CELSIUS, "");
    private static final ModbusDevice CAN_BUS_BMS = new ModbusDevice(225, 512, "CAN-bus BMS");

    @SuppressWarnings("unchecked")
    @Test
    void shouldBeSerializable() throws Exception {
        //given
        File tmp = Files.newTemporaryFile();
        DeviceOperationResultTO to = new DeviceOperationResultTO(BAT_BATTERY_TEMPERATURE, CAN_BUS_BMS, null);
        to.setMeasurement("asd");
        to.setTimeOfMeasurement(LocalDateTime.now());
        to.setRemoveButton(new JButton("asd"));
        //when
        try (FileOutputStream fos = new FileOutputStream(tmp); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(singletonList(to));
            oos.flush();
        }

        List<DeviceOperationResultTO> tos;
        try (FileInputStream fis = new FileInputStream(tmp); ObjectInputStream ois = new ObjectInputStream(fis)) {
            tos = (List<DeviceOperationResultTO>) ois.readObject();
        }
        //then
        assertThat(tos)
                .extracting(DeviceOperationResultTO::getOperation, DeviceOperationResultTO::getModbusDevice, DeviceOperationResultTO::getMeasurement, DeviceOperationResultTO::getTimeOfMeasurement, DeviceOperationResultTO::getRemoveButton)
                .containsExactly(tuple(BAT_BATTERY_TEMPERATURE, CAN_BUS_BMS, null, null, null));
    }

}