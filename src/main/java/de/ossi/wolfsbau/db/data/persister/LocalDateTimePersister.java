package de.ossi.wolfsbau.db.data.persister;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.DateType;

public class LocalDateTimePersister extends DateType {

	private static LocalDateTimePersister singleTon = new LocalDateTimePersister();

	protected LocalDateTimePersister(SqlType sqlType, Class<?>[] classes) {
		super(sqlType, classes);
	}

	public LocalDateTimePersister() {
		super(SqlType.DATE, new Class<?>[] { LocalDateTime.class });
	}

	public static LocalDateTimePersister getSingleton() {
		return singleTon;
	}

	@Override
	public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return ((Timestamp) sqlArg).toLocalDateTime();
	}

	@Override
	public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
		return Timestamp.valueOf(((LocalDateTime) javaObject));
	}

}
