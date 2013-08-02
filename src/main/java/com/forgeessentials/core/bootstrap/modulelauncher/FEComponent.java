package com.forgeessentials.core.bootstrap.modulelauncher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will be used generically as a replacement for @FEModule in 1.x.
 * @author luacs1998
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface FEComponent {
	
	/**
	 * Name of the module
	 */
	public String name() default "";
	/**
	 * Load before other modules?
	 */
	public boolean isCore() default false;
	/**
	 * Will this not work on a client?
	 */
	public boolean serverOnly() default false;
	/**
	 * Does this component have an API class, and if so, what is it?
	 */
	public String apiClass() default "";
	/**
	 * Does this component have a Config class, and if so, what is it?
	 */
	public String configClass() default "";
	/**
	 * Version of this module
	 */
	public String version() default "";

}
