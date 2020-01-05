package com.github.jxen.tables.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code ColumnIndex} annotation is used for marking Java Bean fields as columns in table.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnIndex {

	/**
	 * @return index of column.
	 */
	int index();

	/**
	 * @return name of column.
	 */
	String name();

	/**
	 * @return default value of a column.
	 */
	String defaultValue() default "";
}
