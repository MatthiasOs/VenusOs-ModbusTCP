package de.ossi.modbustcp.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.BatchPoints.Builder;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;

import de.ossi.modbustcp.data.ModbusResultInt;
import lombok.Getter;

@SuppressWarnings("deprecation")
public class InfluxDBModel {

	private static final String MEASUREMENT = "ModbusTCP";
	private static final String RP_ONE_YEAR = "one_year";
	private static final String F_OPERATION = "Operation";
	private static final String F_DEVICE = "Device";
	private static final String F_VALUE = "Value";
	private static final String F_UNIT = "Unit";
	private final String databaseName;
	private final List<Point> pointsToWrite = new ArrayList<>();

	@Getter
	private final InfluxDB influxDB;

	public InfluxDBModel(String url, String databaseName, String userName, String password) {
		this.databaseName = databaseName;
		influxDB = InfluxDBFactory.connect(url, userName, password);
		if (!existsDatabase(databaseName)) {
			createDatabase(databaseName);
			influxDB.enableBatch(BatchOptions.DEFAULTS);
			influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
		}
	}

	private boolean existsDatabase(String databaseName) {
		return influxDB.databaseExists(databaseName);
	}

	private void createDatabase(String databaseName) {
		influxDB.createDatabase(databaseName);
		createRetentionPolicy(databaseName);
	}

	private void createRetentionPolicy(String databaseName) {
		influxDB.createRetentionPolicy(RP_ONE_YEAR, databaseName, "52w", 2, true);
		influxDB.setRetentionPolicy(RP_ONE_YEAR);
	}

	public void deleteDatabase(String dbName) {
		influxDB.deleteDatabase(dbName);
	}

	public Point addPoint(ModbusResultInt result) {
		Point point = createPoint(result);
		pointsToWrite.add(point);
		return point;
	}

	public Point createPoint(ModbusResultInt result) {
		return Point.measurement(MEASUREMENT).time(Timestamp.valueOf(result.getTimestamp()).getTime(), TimeUnit.MILLISECONDS)
				// .tag("tagName", "value")
				.addField(F_DEVICE, result.getDevice().getName()).addField(F_OPERATION, result.getOperation().getName()).addField(F_VALUE, result.getValueOfOperation())
				.addField(F_UNIT, result.getOperation().getDbusUnit().getName()).build();
	}

	public void writeAllPoints() {
		if (pointsToWrite.isEmpty()) {
			return;
		}
		Builder batchPoints = BatchPoints.database(databaseName).retentionPolicy(RP_ONE_YEAR).points(pointsToWrite);
		influxDB.write(batchPoints.build());
	}

	public void writePoint(Point point) {
		influxDB.write(point);
	}

	public List<Result> getAllMeasurements() {
		Query query = new Query("SELECT * FROM " + MEASUREMENT, databaseName);
		QueryResult queryResult = influxDB.query(query);
		return queryResult.getResults();
	}
}
