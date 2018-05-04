package de.ossi.wolfsbau.modbus.data.unit;

/** 0=AC Input 1;1=AC Input 2;240=Disconnected */
public class ActiveInput extends DBusUnit {

	public ActiveInput(String name) {
		super(name);
		// FIXME: Laut Excel Liste ist beides AC Input, macht das Sinn? #23
		values.put(0, "AC Input");
		values.put(1, "AC Input");
		values.put(240, "Not Connected");
	}
}