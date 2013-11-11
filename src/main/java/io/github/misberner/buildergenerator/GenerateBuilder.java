package io.github.misberner.buildergenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.CONSTRUCTOR)
public @interface GenerateBuilder {
	public String value() default "";
	public String name() default "";
	public String packageName() default "";
	
	public boolean enableGetters() default true;
	public boolean enableSetters() default true;
	public boolean enableWiths() default true;
	public boolean enableStaticWiths() default true;
	
	public Class<?> defaults() default GenerateBuilder.class;
	public Class<?>[] builderInterfaces() default {};
	
	public boolean visiblePublic() default true;
}
