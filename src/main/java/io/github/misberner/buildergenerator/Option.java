package io.github.misberner.buildergenerator;

public @interface Option {
	public boolean ignore() default false;
	public String value() default "";
	public String name() default "";
	
	public String setterName() default "";
	public String getterName() default "";
	public String withName() default "";
}
