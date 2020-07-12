package de.ossi.modbustcp.data.unit;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AccessMode {
	READ_WRITE("yes"), 
	READ_ONLY("no");

	@Getter
	String name;

	public static AccessMode from(String name) {
		return Arrays.stream(AccessMode.values()).filter(a -> a.getName().equals(name)).findFirst().orElse(null);
	}
}