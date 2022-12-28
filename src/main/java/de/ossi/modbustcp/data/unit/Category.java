package de.ossi.modbustcp.data.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum Category {
    SYSTEM("com.victronenergy.system"),
    VEBUS("com.victronenergy.vebus"),
    BATTERY("com.victronenergy.battery"),
    SOLARCHARGER("com.victronenergy.solarcharger"),
    PVINVERTER("com.victronenergy.pvinverter"),
    MOTODRIVE("com.victronenergy.motordrive"),
    CHARGER("com.victronenergy.charger"),
    GRID("com.victronenergy.grid"),
    SETTINGS("com.victronenergy.settings"),
    GPS("com.victronenergy.gps"),
    TANK("com.victronenergy.tank"),
    INVERTER("com.victronenergy.inverter"),
    GENSET("com.victronenergy.genset"),
    TEMPERATURE("com.victronenergy.temperature"),
    PULSEMETER("com.victronenergy.pulsemeter"),
    DIGITALINPUT("com.victronenergy.digitalinput"),
    GENERATOR("com.victronenergy.generator"),
    METEO("com.victronenergy.meteo"),
    ;

    @Getter
    private final String name;

    public static Category from(String name) {
        return Arrays.stream(Category.values()).filter(a -> a.getName().equals(name)).findFirst().orElse(null);
    }
}