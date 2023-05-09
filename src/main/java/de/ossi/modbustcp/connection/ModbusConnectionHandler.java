package de.ossi.modbustcp.connection;

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;

public abstract class ModbusConnectionHandler {

    protected ModbusTCPMaster modbusMaster;

    protected ModbusConnectionHandler(String ip, int port) {
        modbusMaster = new ModbusTCPMaster(ip, port);
    }

    protected void connect() {
        try {
            modbusMaster.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void disconnect() {
        try {
            modbusMaster.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
