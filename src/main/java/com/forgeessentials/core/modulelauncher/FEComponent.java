package com.forgeessentials.core.modulelauncher;

import java.io.File;
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
	public Class configClass() default DummyConfig.class;
	/**
	 * Version of this module
	 */
	public String version() default "";
	
	
	public class DummyConfig extends BaseConfig{

		public DummyConfig(File file) {
			super(file);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void load() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void save() {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE})
	public @interface Instance {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE})
	public @interface ModuleDir {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE})
	public @interface Container {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface FEModuleEventHandler {}
	

}
