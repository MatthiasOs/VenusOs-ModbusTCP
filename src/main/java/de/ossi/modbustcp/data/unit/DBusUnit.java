package de.ossi.modbustcp.data.unit;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "value")
@EqualsAndHashCode(of = "value")
public class DBusUnit implements Serializable {

	private static final long serialVersionUID = 1496887712045628303L;

	@Getter
	private String value;

	public boolean isSpecialUnit() {
		// i.e. 0=No alarm;2=Alarm
		return Character.isDigit(value.charAt(0));
	}
}
