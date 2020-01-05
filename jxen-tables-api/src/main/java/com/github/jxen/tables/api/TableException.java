package com.github.jxen.tables.api;

/**
 * {@code TableException} exception is standard exception for Tables project.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class TableException extends RuntimeException {

	private static final long serialVersionUID = 1247060906661610336L;

	/**
	 * @param message message
	 */
	public TableException(String message) {
		super(message);
	}

	/**
	 * @param message message
	 * @param cause   cause
	 */
	public TableException(String message, Throwable cause) {
		super(message, cause);
	}
}
