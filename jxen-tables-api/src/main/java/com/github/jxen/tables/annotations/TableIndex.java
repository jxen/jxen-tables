package com.github.jxen.tables.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * {@code TableIndex} annotation sets index of table in multiple tables source to be used. Indexes start with 0.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableIndex {

	/**
	 * @return index of table to be used.
	 */
	int value() default 0;
}
