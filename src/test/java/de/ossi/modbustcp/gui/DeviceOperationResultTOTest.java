package de.ossi.modbustcp.gui;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.data.unit.AccessMode;
import de.ossi.modbustcp.data.unit.Category;
import de.ossi.modbustcp.data.unit.DBusUnit;
import de.ossi.modbustcp.data.unit.Type;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class DeviceOperationResultTOTest {

    private static final DBusUnit CELSIUS = new DBusUnit("Degrees celsius");
    private static final DBusUnit KWH = new DBusUnit("kWh");
    protected static final ModbusOperation BAT_BATTERY_TEMPERATURE = new ModbusOperation(Category.BATTERY, "Battery temperature", 262, Type.INT16, 10D, "", "/Dc/0/Temperature",
            AccessMode.READ_ONLY, CELSIUS, "");
    private static final ModbusOperation GRI_GRID_L1_ENERGY_TO_NET = new ModbusOperation(Category.GRID, "Grid L1 - Energy to net", 2606, Type.UINT16, 100D, "",
            "/Ac/L1/Energy/Reverse", AccessMode.READ_ONLY, KWH, "");
    private static final ModbusDevice CAN_BUS_BMS = new ModbusDevice(225, 512, "CAN-bus BMS");

    @SuppressWarnings("unchecked")
    @Test
    void shouldBeSerializable() throws Exception {
        //given
        File tmp = Files.newTemporaryFile();
        DeviceOperationResultTO to1 = new DeviceOperationResultTO(BAT_BATTERY_TEMPERATURE, CAN_BUS_BMS, null);
        DeviceOperationResultTO to2 = new DeviceOperationResultTO(GRI_GRID_L1_ENERGY_TO_NET, CAN_BUS_BMS, null);
        //when
        try (FileOutputStream fos = new FileOutputStream(tmp); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(Arrays.asList(to1, to2));
            oos.flush();
        }

        List<DeviceOperationResultTO> tos;
        try (FileInputStream fis = new FileInputStream(tmp); ObjectInputStream ois = new ObjectInputStream(fis)) {
            tos = (List<DeviceOperationResultTO>) ois.readObject();
        }
        //then
        assertThat(tos).containsExactlyInAnyOrder(to1, to2);
    }
}