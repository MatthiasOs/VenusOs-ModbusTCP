package de.ossi.modbustcp.data.operation;

import com.creditdatamw.zerocell.annotation.Column;
import de.ossi.modbustcp.data.unit.AccessMode;
import de.ossi.modbustcp.data.unit.Category;
import de.ossi.modbustcp.data.unit.DBusUnit;
import de.ossi.modbustcp.data.unit.Type;
import de.ossi.modbustcp.data.unit.converter.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Supports Operations from the Excel Sheet: CCGX-Modbus-TCP-register-list.xlsx
 *
 * @author ossi
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ModbusOperation implements Serializable {

    private static final long serialVersionUID = -300082385619843600L;

    @Column(index = 0, name = "dbus-service-name", converterClass = CategoryConverter.class)
    private Category category;

    @Column(index = 1, name = "description")
    private String description;

    @Column(index = 2, name = "Address")
    private Integer address;

    @Column(index = 3, name = "Type", converterClass = TypeConverter.class)
    private Type type;

    @Column(index = 4, name = "Scalefactor", converterClass = DoubleConverter.class)
    private Double scaleFactor;

    @Column(index = 5, name = "Range")
    private String range;

    @Column(index = 6, name = "dbus-obj-path")
    private String dbusObjPath;

    @Column(index = 7, name = "writable", converterClass = AccessModeConverter.class)
    private AccessMode mode;

    @Column(index = 8, name = "dbus-unit", converterClass = DBusUnitConverter.class)
    private DBusUnit dbusUnit;

    @Column(index = 9, name = "Remarks")
    private String remarks;

    @Override
    public String toString() {
        return category + ": " + description + " on register: " + address;
    }

    public String getName() {
        return category + ": " + description;
    }
}
