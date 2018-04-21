package de.ossi.wolfsbau.db;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.ossi.wolfsbau.db.data.Device;
import de.ossi.wolfsbau.db.data.Measurement;

/**
 * Führt DB Operationen durch
 */
public class DBModel {

	private static final String COL_REQUEST_TIME = "requestTime";
	/**
	 * Default sqlite Pfad
	 */
	private String dbDriver = "jdbc:sqlite:wr.sqlite";

	public DBModel(String dbDriver) {
		this.dbDriver = dbDriver;
		createAllTables();
	}

	public DBModel() {
		createAllTables();
	}

	public List<Device> getAll() {
		ConnectionSource cs = null;
		List<Device> devices = new ArrayList<>();
		try {
			cs = openConnection();
			Dao<Device, Long> deviceDao = DaoManager.createDao(cs, Device.class);
			deviceDao.iterator().forEachRemaining(devices::add);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(cs);
		}
		return devices;
	}

	public List<Device> getDevicesByInterval(LocalDateTime von, LocalDateTime bis) {
		ConnectionSource cs = null;
		List<Device> devices = new ArrayList<>();
		try {
			cs = openConnection();
			Dao<Device, Long> deviceDao = DaoManager.createDao(cs, Device.class);
			QueryBuilder<Device, Long> qb = deviceDao.queryBuilder();
			qb.where().between(COL_REQUEST_TIME, von, bis);
			CloseableIterator<Device> iterator = deviceDao.iterator(qb.prepare());
			try {
				iterator.forEachRemaining(devices::add);
			} finally {
				iterator.close();
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} finally {
			closeConnection(cs);
		}
		return devices;
	}

	public void saveDevice(Device device) {
		if (device == null) {
			return;
		}
		ConnectionSource cs = null;
		try {
			cs = openConnection();
			Dao<Device, Long> deviceDao = DaoManager.createDao(cs, Device.class);
			deviceDao.create(device);
			saveMeasurements(device.getMeasurements(), cs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(cs);
		}
	}

	private void saveMeasurements(Collection<Measurement> measurements, ConnectionSource cs) throws SQLException {
		if (measurements == null) {
			return;
		}
		Dao<Measurement, Long> measurementDao = DaoManager.createDao(cs, Measurement.class);
		measurementDao.create(measurements);
	}

	public String getSource() {
		return dbDriver;
	}

	private ConnectionSource openConnection() {
		if (dbDriver == null) {
			throw new NullPointerException("Source der ConnectionSource ist null!");
		}
		try {
			return new JdbcConnectionSource(dbDriver);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void closeConnection(ConnectionSource connection) {
		if (connection == null) {
			throw new NullPointerException("ConnectionSource war null beim Schließen!");
		}
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createAllTables() {
		ConnectionSource cs = null;
		try {
			cs = openConnection();
			TableUtils.createTableIfNotExists(cs, Device.class);
			TableUtils.createTableIfNotExists(cs, Measurement.class);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(cs);
		}
	}
}
