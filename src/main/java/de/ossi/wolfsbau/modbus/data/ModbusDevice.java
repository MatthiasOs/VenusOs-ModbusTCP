package de.ossi.wolfsbau.modbus.data;

/**
 * 
 * Unterstützte Modbus Geräte nach dem Excel Sheet:
 * CCGX-Modbus-TCP-register-list-2.12.xlsx 
 * @author ossi
 *
 */
public class ModbusDevice {

	public static final ModbusDevice CCGX_VE_BUS = new ModbusDevice("CCGX VE.Bus port (ttyO1)", 246, 257);
	public static final ModbusDevice CCGX_VE_DIRECT_1 = new ModbusDevice("CCGX VE.Direct 1 port (ttyO0)", 247, 256);
	public static final ModbusDevice CCGX_VE_DIRECT_2 = new ModbusDevice("CCGX VE.Direct 2 port, Venus GX VE.Direct 1 port (ttyO2)", 245, 258);
	public static final ModbusDevice VENUS_GX_VE_DIRECT_2 = new ModbusDevice("Venus GX VE.Direct 2 port (ttyO4)", 243,			260);
	public static final ModbusDevice VENUS_GX_VE_PORT = new ModbusDevice("Venus GX VE.Bus port (ttyO5)", 242, 261);
	public static final ModbusDevice VE_CAN_AND_SYSTEM_DEVICE_0 = new ModbusDevice("VE.Can device instance 0 and system device", 100, 0);
	public static final ModbusDevice VE_CAN_DEVICE_1 = new ModbusDevice("VE.Can device instance 1", 1, 1);
	public static final ModbusDevice VE_CAN_DEVICE_2 = new ModbusDevice("VE.Can device instance 2", 2, 2);
	public static final ModbusDevice VE_CAN_DEVICE_3 = new ModbusDevice("VE.Can device instance 3", 3, 3);
	public static final ModbusDevice VE_CAN_DEVICE_4 = new ModbusDevice("VE.Can device instance 4", 4, 4);
	public static final ModbusDevice VE_CAN_DEVICE_5 = new ModbusDevice("VE.Can device instance 5", 5, 5);
	public static final ModbusDevice VE_CAN_DEVICE_6 = new ModbusDevice("VE.Can device instance 6", 6, 6);
	public static final ModbusDevice VE_CAN_DEVICE_7 = new ModbusDevice("VE.Can device instance 7", 7, 7);
	public static final ModbusDevice VE_CAN_DEVICE_8 = new ModbusDevice("VE.Can device instance 8", 8, 8);
	public static final ModbusDevice VE_CAN_DEVICE_9 = new ModbusDevice("VE.Can device instance 9", 9, 9);

	public static final ModbusDevice VE_BUS_AC_SENSOR_INPUT_1 = new ModbusDevice("VE.Bus AC-Sensor on input 1", 10, 10);
	public static final ModbusDevice VE_BUS_AC_SENSOR_OUTPUT = new ModbusDevice("VE.Bus AC-Sensor on output", 11, 11);
	public static final ModbusDevice VE_BUS_AC_SENSOR_INPUT_2 = new ModbusDevice("VE.Bus AC-Sensor on input 2", 12, 12);

	public static final ModbusDevice PV_INVERTER_0 = new ModbusDevice("PV Inverter instance 0", 20, 20);
	public static final ModbusDevice PV_INVERTER_1 = new ModbusDevice("PV Inverter instance 1", 21, 21);
	public static final ModbusDevice PV_INVERTER_2 = new ModbusDevice("PV Inverter instance 2", 22, 22);
	public static final ModbusDevice PV_INVERTER_3 = new ModbusDevice("PV Inverter instance 3", 23, 23);
	public static final ModbusDevice PV_INVERTER_4 = new ModbusDevice("PV Inverter instance 4", 24, 24);
	public static final ModbusDevice PV_INVERTER_5 = new ModbusDevice("PV Inverter instance 5", 25, 25);
	public static final ModbusDevice PV_INVERTER_6 = new ModbusDevice("PV Inverter instance 6", 26, 26);
	public static final ModbusDevice PV_INVERTER_7 = new ModbusDevice("PV Inverter instance 7", 27, 27);
	public static final ModbusDevice PV_INVERTER_8 = new ModbusDevice("PV Inverter instance 8", 28, 28);
	public static final ModbusDevice PV_INVERTER_9 = new ModbusDevice("PV Inverter instance 9", 29, 29);

	public static final ModbusDevice GRID_METER_0 = new ModbusDevice("Carlo Gavazi grid meters instance 0", 30, 30);
	public static final ModbusDevice GRID_METER_1 = new ModbusDevice("Carlo Gavazi grid meters instance 1", 31, 31);
	public static final ModbusDevice GRID_METER_2 = new ModbusDevice("Carlo Gavazi grid meters instance 2", 32, 35);
	public static final ModbusDevice GRID_METER_3 = new ModbusDevice("Carlo Gavazi grid meters instance 3", 33, 33);
	public static final ModbusDevice GRID_METER_4 = new ModbusDevice("Carlo Gavazi grid meters instance 4", 34, 34);

	public static final ModbusDevice REDFLOW_SYSTEM_TOOLS = new ModbusDevice("Redflow system totals", 40, 40);

	public static final ModbusDevice REDFLOW_BATTERY_1 = new ModbusDevice("Redflow battery (Modbus-RTU ID 1)", 41, 41);
	public static final ModbusDevice REDFLOW_BATTERY_2 = new ModbusDevice("Redflow battery (Modbus-RTU ID 2)", 42, 42);
	public static final ModbusDevice REDFLOW_BATTERY_3 = new ModbusDevice("Redflow battery (Modbus-RTU ID 3)", 43, 43);
	public static final ModbusDevice REDFLOW_BATTERY_4 = new ModbusDevice("Redflow battery (Modbus-RTU ID 4)", 44, 44);
	public static final ModbusDevice REDFLOW_BATTERY_5 = new ModbusDevice("Redflow battery (Modbus-RTU ID 5)", 45, 45);
	public static final ModbusDevice REDFLOW_BATTERY_6 = new ModbusDevice("Redflow battery (Modbus-RTU ID 6)", 46, 46);

	public static final ModbusDevice VE_DIRECT_USB0 = new ModbusDevice("VE.Direct via USB (ttyUSB0)", 239, 288);
	public static final ModbusDevice VE_DIRECT_USB1 = new ModbusDevice("VE.Direct via USB (ttyUSB1)", 238, 289);
	public static final ModbusDevice VE_DIRECT_USB2 = new ModbusDevice("VE.Direct via USB (ttyUSB2)", 237, 290);
	public static final ModbusDevice VE_DIRECT_USB3 = new ModbusDevice("VE.Direct via USB (ttyUSB3)", 236, 291);
	public static final ModbusDevice VE_DIRECT_USB4 = new ModbusDevice("VE.Direct via USB (ttyUSB4)", 235, 292);
	public static final ModbusDevice VE_DIRECT_USB5 = new ModbusDevice("VE.Direct via USB (ttyUSB5)", 233, 293);
	public static final ModbusDevice CAN_BUS_BMS = new ModbusDevice("CAN-bus BMS", 225, 512);

	private final String name;
	private final int unitId;
	private final int deviceInstance;

	private ModbusDevice(String name, int unitId, int deviceInstance) {
		this.name = name;
		this.unitId = unitId;
		this.deviceInstance = deviceInstance;
	}

	public String getName() {
		return name;
	}

	public int getUnitId() {
		return unitId;
	}

	public int getDeviceInstance() {
		return deviceInstance;
	}

}
