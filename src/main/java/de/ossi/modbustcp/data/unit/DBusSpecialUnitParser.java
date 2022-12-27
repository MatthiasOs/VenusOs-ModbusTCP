package de.ossi.modbustcp.data.unit;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DBusSpecialUnitParser {

	public static String parse(DBusUnit dbusUnit, Integer number) {
		// i.e. 0=No alarm;2=Alarm
		String[] keyValues = dbusUnit.getValue().split(";");
		Map<Integer, String> keyValue;
		try {
			keyValue = Arrays.stream(keyValues).map(kv -> kv.split("=")).collect(Collectors.toMap(kv -> Integer.parseInt(kv[0]), kv -> kv[1]));
		} catch (Exception e) {
			return null;
		}
		return keyValue.get(number);
	}

}
