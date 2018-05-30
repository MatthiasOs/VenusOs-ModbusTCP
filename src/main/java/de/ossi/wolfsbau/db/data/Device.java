package de.ossi.wolfsbau.db.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import de.ossi.wolfsbau.db.data.persister.LocalDateTimePersister;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@DatabaseTable(tableName = "device")
public class Device {
	// all persisted classes must define a no-arg constructor
	// with at least package visibility

	@DatabaseField(generatedId = true)
	private long id;

	@Setter
	@ForeignCollectionField(eager = true)
	private Collection<Measurement> measurements;
	@Setter
	@DatabaseField
	private String name;
	@Setter
	@DatabaseField
	private Integer nominalPower;
	@Setter
	@DatabaseField
	private String type;
	@Setter
	@DatabaseField
	private String serial;
	@Setter
	@DatabaseField
	private Integer busAddress;
	@Setter
	@DatabaseField
	private String netBiosName;
	@Setter
	@DatabaseField
	private String ipAddress;
	@Setter
	@DatabaseField(persisterClass = LocalDateTimePersister.class, canBeNull = false)
	private LocalDateTime requestTime;

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

}
