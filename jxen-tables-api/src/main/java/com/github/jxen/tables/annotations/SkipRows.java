package com.github.jxen.tables.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code SkipRows} annotation sets count of table rows to be skipped.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SkipRows {

	/**
	 * @return rows to be skipped.
	 */
	int value();
}
