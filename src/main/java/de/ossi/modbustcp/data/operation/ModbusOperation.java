package de.ossi.modbustcp.data.operation;

import com.creditdatamw.zerocell.annotation.Column;

import de.ossi.modbustcp.data.unit.AccessMode;
import de.ossi.modbustcp.data.unit.Category;
import de.ossi.modbustcp.data.unit.DBusUnit;
import de.ossi.modbustcp.data.unit.Type;
import de.ossi.modbustcp.data.unit.converter.AccessModeConverter;
import de.ossi.modbustcp.data.unit.converter.CategoryConverter;
import de.ossi.modbustcp.data.unit.converter.DBusUnitConverter;
import de.ossi.modbustcp.data.unit.converter.DoubleConverter;
import de.ossi.modbustcp.data.unit.converter.TypeConverter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * Supports Operations from the Excel Sheet: CCGX-Modbus-TCP-register-list.xlsx
 * 
 * @author ossi
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ModbusOperation {

	@Column(index = 0, name = "dbus-service-name", convertorClass = CategoryConverter.class)
	private Category category;

	@Column(index = 1, name = "description")
	private String description;

	@Column(index = 2, name = "Address")
	private int address;

	@Column(index = 3, name = "Type", convertorClass = TypeConverter.class)
	private Type type;

	@Column(index = 4, name = "Scalefactor", convertorClass = DoubleConverter.class)
	private double scaleFactor;

	@Column(index = 5, name = "Range")
	private String range;

	@Column(index = 6, name = "dbus-obj-path")
	private String dbusObjPath;

	@Column(index = 7, name = "writable", convertorClass = AccessModeConverter.class)
	private AccessMode mode;

	@Column(index = 8, name = "dbus-unit", convertorClass = DBusUnitConverter.class)
	private DBusUnit dbusUnit;

	@Column(index = 9, name = "Remarks")
	private String remarks;

	// TODO entfernen
	private double scaleValue(Double registerValue) {
		return registerValue / this.scaleFactor;
	}

	private static final double MAX_SIGNED = 32767D;
	private static final double MAX_REGISTER = 65535D;

	public Double getValueInRange(Integer registerValue) {
		if (type.isUnsigned()) {
			return scaleValue(Double.valueOf(registerValue));
		} else {
			double valueInRange = registerValue > MAX_SIGNED ? registerValue - MAX_REGISTER - 1 : registerValue;
			return scaleValue(valueInRange);
		}
	}

	@Override
	public String toString() {
		return new StringBuilder().append(category).append(": ").append(description).append(" on register: ").append(address).toString();
	}

	public String getName() {
		return new StringBuilder().append(category).append(": ").append(description).toString();
	}
}
