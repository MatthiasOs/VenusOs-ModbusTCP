package de.ossi.modbustcp.data.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum AccessMode {
    READ_WRITE("yes"),
    READ_ONLY("no");

    @Getter
    private final String name;

    public static AccessMode from(String name) {
        return Arrays.stream(AccessMode.values()).filter(a -> a.getName().equals(name)).findFirst().orElse(null);
    }
}