package com.github.jxen.tables.test;

import com.github.jxen.tables.annotations.ColumnIndex;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * {@code SampleEntity} class is sample entity for unit tests.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class SampleEntity {

	@ColumnIndex(index = 0, name = "text", defaultValue = "unknown")
	private String text;

	@ColumnIndex(index = 1, name = "number", defaultValue = "1")
	private int number;

	@ColumnIndex(index = 2, name = "value", defaultValue = "1.0")
	private double value;

	@ColumnIndex(index = 4, name = "date", defaultValue = "2020-01-01")
	private LocalDate date;

	@ColumnIndex(index = 5, name = "flag", defaultValue = "false")
	private boolean flag;

	@ColumnIndex(index = 7, name = "dateTime", defaultValue = "2020-01-01T00:00:00")
	private LocalDateTime dateTime;

	/**
	 * Default constructor.
	 */
	public SampleEntity() {
	}

	/**
	 * @param text     text
	 * @param number   number
	 * @param value    value
	 * @param date     date
	 * @param flag     flag
	 * @param dateTime date time
	 */
	public SampleEntity(String text, int number, double value, LocalDate date, boolean flag, LocalDateTime dateTime) {
		this.text = text;
		this.number = number;
		this.value = value;
		this.date = date;
		this.flag = flag;
		this.dateTime = dateTime;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * @return the dateTime
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		SampleEntity entity = (SampleEntity) o;
		return number == entity.number
				&& Double.compare(entity.value, value) == 0
				&& flag == entity.flag
				&& Objects.equals(text, entity.text)
				&& Objects.equals(date, entity.date)
				&& Objects.equals(dateTime, entity.dateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(text, number, value, date, flag, dateTime);
	}
}
