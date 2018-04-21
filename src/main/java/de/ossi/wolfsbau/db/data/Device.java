package de.ossi.wolfsbau.db.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import de.ossi.wolfsbau.db.data.persister.LocalDateTimePersister;

@DatabaseTable(tableName = "device")
public class Device {

	@DatabaseField(generatedId = true)
	private long id;

	@ForeignCollectionField(eager = true)
	private Collection<Measurement> measurements;
	@DatabaseField
	private String name;
	@DatabaseField
	private Integer nominalPower;
	@DatabaseField
	private String type;
	@DatabaseField
	private String serial;
	@DatabaseField
	private Integer busAddress;
	@DatabaseField
	private String netBiosName;
	@DatabaseField
	private String ipAddress;
	@DatabaseField(persisterClass = LocalDateTimePersister.class, canBeNull = false)
	private LocalDateTime requestTime;

	public Device() {
		// all persisted classes must define a no-arg constructor
		// with at least package visibility
	}

	public void addAll(Collection<Measurement> measurements) {
		measurements.forEach(this::add);
	}

	public void add(Measurement measurement) {
		if (this.measurements == null) {
			this.measurements = new ArrayList<>();
		}
		measurement.setDevice(this);
		this.measurements.add(measurement);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(LocalDateTime requestTime) {
		this.requestTime = requestTime;
	}

	public Collection<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(Collection<Measurement> measurements) {
		this.measurements = measurements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNominalPower() {
		return nominalPower;
	}

	public void setNominalPower(Integer nominalPower) {
		this.nominalPower = nominalPower;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getBusAddress() {
		return busAddress;
	}

	public void setBusAddress(Integer busAddress) {
		this.busAddress = busAddress;
	}

	public String getNetBiosName() {
		return netBiosName;
	}

	public void setNetBiosName(String netBiosName) {
		this.netBiosName = netBiosName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
