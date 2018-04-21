package de.ossi.wolfsbau.modbus.data;

/**
 * // FIXME byte in Java geht nur bis 127!
 * 
 * Werte von UnitId Tabelle aus: CCGX-Modbus-TCP-register-list-2.12.xlsx
 * 
 * @author ossi
 *
 */
public class ModbusDevice {

	public static final ModbusDevice CCGX_VE_BUS = new ModbusDevice("CCGX VE.Bus port (ttyO1)", (byte) 246, 257);
	public static final ModbusDevice CCGX_VE_DIRECT_1 = new ModbusDevice("CCGX VE.Direct 1 port (ttyO0)", (byte) 247, 256);
	public static final ModbusDevice CCGX_VE_DIRECT_2 = new ModbusDevice("CCGX VE.Direct 2 port, Venus GX VE.Direct 1 port (ttyO2)", (byte) 245, 258);
	public static final ModbusDevice VENUS_GX_VE_DIRECT_2 = new ModbusDevice("Venus GX VE.Direct 2 port (ttyO4)", (byte) 243, 260);
	public static final ModbusDevice VENUS_GX_VE_PORT = new ModbusDevice("Venus GX VE.Bus port (ttyO5)", (byte) 242, 261);

	public static final ModbusDevice VE_CAN_AND_SYSTEM_DEVICE_0 = new ModbusDevice("VE.Can device instance 0 and system device", (byte) 100, 0);
	public static final ModbusDevice VE_CAN_DEVICE_1 = new ModbusDevice("VE.Can device instance 1", (byte) 1, 1);
	public static final ModbusDevice VE_CAN_DEVICE_2 = new ModbusDevice("VE.Can device instance 2", (byte) 2, 2);
	public static final ModbusDevice VE_CAN_DEVICE_3 = new ModbusDevice("VE.Can device instance 3", (byte) 3, 3);
	public static final ModbusDevice VE_CAN_DEVICE_4 = new ModbusDevice("VE.Can device instance 4", (byte) 4, 4);
	public static final ModbusDevice VE_CAN_DEVICE_5 = new ModbusDevice("VE.Can device instance 5", (byte) 5, 5);
	public static final ModbusDevice VE_CAN_DEVICE_6 = new ModbusDevice("VE.Can device instance 6", (byte) 6, 6);
	public static final ModbusDevice VE_CAN_DEVICE_7 = new ModbusDevice("VE.Can device instance 7", (byte) 7, 7);
	public static final ModbusDevice VE_CAN_DEVICE_8 = new ModbusDevice("VE.Can device instance 8", (byte) 8, 8);
	public static final ModbusDevice VE_CAN_DEVICE_9 = new ModbusDevice("VE.Can device instance 9", (byte) 9, 9);

	public static final ModbusDevice VE_BUS_AC_SENSOR_INPUT_1 = new ModbusDevice("VE.Bus AC-Sensor on input 1", (byte) 10, 10);
	public static final ModbusDevice VE_BUS_AC_SENSOR_OUTPUT = new ModbusDevice("VE.Bus AC-Sensor on output", (byte) 11, 11);
	public static final ModbusDevice VE_BUS_AC_SENSOR_INPUT_2 = new ModbusDevice("VE.Bus AC-Sensor on input 2", (byte) 12, 12);

	public static final ModbusDevice PV_INVERTER_0 = new ModbusDevice("PV Inverter instance 0", (byte) 20, 20);
	public static final ModbusDevice PV_INVERTER_1 = new ModbusDevice("PV Inverter instance 1", (byte) 21, 21);
	public static final ModbusDevice PV_INVERTER_2 = new ModbusDevice("PV Inverter instance 2", (byte) 22, 22);
	public static final ModbusDevice PV_INVERTER_3 = new ModbusDevice("PV Inverter instance 3", (byte) 23, 23);
	public static final ModbusDevice PV_INVERTER_4 = new ModbusDevice("PV Inverter instance 4", (byte) 24, 24);
	public static final ModbusDevice PV_INVERTER_5 = new ModbusDevice("PV Inverter instance 5", (byte) 25, 25);
	public static final ModbusDevice PV_INVERTER_6 = new ModbusDevice("PV Inverter instance 6", (byte) 26, 26);
	public static final ModbusDevice PV_INVERTER_7 = new ModbusDevice("PV Inverter instance 7", (byte) 27, 27);
	public static final ModbusDevice PV_INVERTER_8 = new ModbusDevice("PV Inverter instance 8", (byte) 28, 28);
	public static final ModbusDevice PV_INVERTER_9 = new ModbusDevice("PV Inverter instance 9", (byte) 29, 29);

	public static final ModbusDevice GRID_METER_0 = new ModbusDevice("Carlo Gavazi grid meters instance 0", (byte) 30, 30);
	public static final ModbusDevice GRID_METER_1 = new ModbusDevice("Carlo Gavazi grid meters instance 1", (byte) 31, 31);
	public static final ModbusDevice GRID_METER_2 = new ModbusDevice("Carlo Gavazi grid meters instance 2", (byte) 32, 32);
	public static final ModbusDevice GRID_METER_3 = new ModbusDevice("Carlo Gavazi grid meters instance 3", (byte) 33, 33);
	public static final ModbusDevice GRID_METER_4 = new ModbusDevice("Carlo Gavazi grid meters instance 4", (byte) 34, 34);

	public static final ModbusDevice REDFLOW_SYSTEM_TOOLS = new ModbusDevice("Redflow system totals", (byte) 40, 40);

	public static final ModbusDevice REDFLOW_BATTERY_1 = new ModbusDevice("Redflow battery (Modbus-RTU ID 1)", (byte) 41, 41);
	public static final ModbusDevice REDFLOW_BATTERY_2 = new ModbusDevice("Redflow battery (Modbus-RTU ID 2)", (byte) 42, 42);
	public static final ModbusDevice REDFLOW_BATTERY_3 = new ModbusDevice("Redflow battery (Modbus-RTU ID 3)", (byte) 43, 43);
	public static final ModbusDevice REDFLOW_BATTERY_4 = new ModbusDevice("Redflow battery (Modbus-RTU ID 4)", (byte) 44, 44);
	public static final ModbusDevice REDFLOW_BATTERY_5 = new ModbusDevice("Redflow battery (Modbus-RTU ID 5)", (byte) 45, 45);
	public static final ModbusDevice REDFLOW_BATTERY_6 = new ModbusDevice("Redflow battery (Modbus-RTU ID 6)", (byte) 46, 46);

	public static final ModbusDevice VE_DIRECT_USB0 = new ModbusDevice("VE.Direct via USB (ttyUSB0)", (byte) 239, 288);
	public static final ModbusDevice VE_DIRECT_USB1 = new ModbusDevice("VE.Direct via USB (ttyUSB1)", (byte) 238, 289);
	public static final ModbusDevice VE_DIRECT_USB2 = new ModbusDevice("VE.Direct via USB (ttyUSB2)", (byte) 237, 290);
	public static final ModbusDevice VE_DIRECT_USB3 = new ModbusDevice("VE.Direct via USB (ttyUSB3)", (byte) 236, 291);
	public static final ModbusDevice VE_DIRECT_USB4 = new ModbusDevice("VE.Direct via USB (ttyUSB4)", (byte) 235, 292);
	public static final ModbusDevice VE_DIRECT_USB5 = new ModbusDevice("VE.Direct via USB (ttyUSB5)", (byte) 233, 293);
	public static final ModbusDevice CAN_BUS_BMS = new ModbusDevice("CAN-bus BMS", (byte) 225, 512);

	private final String name;
	private final byte unitId;
	private final int deviceInstance;

	private ModbusDevice(String name, byte unitId, int deviceInstance) {
		this.name = name;
		this.unitId = unitId;
		this.deviceInstance = deviceInstance;
	}

	public String getName() {
		return name;
	}

	public byte getUnitId() {
		return unitId;
	}

	public int getDeviceInstance() {
		return deviceInstance;
	}

}
