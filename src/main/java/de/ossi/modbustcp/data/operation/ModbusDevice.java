package de.ossi.modbustcp.data.operation;

import java.io.Serializable;

import com.creditdatamw.zerocell.annotation.Column;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * Supports Devices from the Excel Sheet: CCGX-Modbus-TCP-register-list.xlsx
 * 
 * @author ossi
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ModbusDevice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(index = 0, name = "Unit ID")
	private Integer unitId;
	@Column(index = 1, name = " /DeviceInstance")
	private Integer deviceInstance;
	@Column(index = 2, name = "Remark")
	private String name;

	@Override
	public String toString() {
		return name + " with UnitId: " + unitId;
	}
}
