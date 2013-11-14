package io.github.misberner.buildergen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for providing information about an option used in a builder.
 * 
 * @author Malte Isberner <malte.isberner@gmail.com>
 *
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface Option {
	public static final String SUPPRESS_SETTER = "-";
	public static final String SUPPRESS_GETTER = "-";
	public static final String SUPPRESS_WITH = "-";
	/**
	 * The name of this option. This value is only taken into account when the
	 * {@link #name()} attribute is set to the empty string. If this value
	 * is also empty, the default option name (see above) will be used.
	 */
	public String value() default "";
	/**
	 * The name of the option. If this field is set to the empty string,
	 * the value of the {@link #value()} attribute is used, if this is specified and
	 * non-empty. Otherwise, the default option name (see above) will be used.
	 */
	public String name() default "";
	/**
	 * An expression (specified as a string) to use as a default value for this option. Note that
	 * all references to Java elements (including the instantiated type) that are not in the same package as the generated builder must
	 * be fully qualified. Generic type parameter names may be used as declared. If the empty string is
	 * specified, the default value will be taken from the defaults class (see {@link GenerateBuilder#defaults()}),
	 * if available.
	 */
	public String defaultExpr() default "";
	
	/**
	 * The name of the setter for this option. If set to the empty string, the default policy (see above)
	 * applies. If set to {@link #SUPPRESS_SETTER}, no setter will be generated for this option.
	 */
	public String setterName() default "";
	/**
	 * The name of the getter for this option. If set to the empty string, the default policy (see above)
	 * applies. If set to {@link #SUPPRESS_GETTER}, no getter will be generated for this option.
	 */
	public String getterName() default "";
	/**
	 * The name of the with method for this option. If set to the empty string, the default policy (see above)
	 * applies. If set to {@link #SUPPRESS_WITH}, no with method will be generated for this option.
	 */
	public String withName() default "";
	
	/**
	 * The visibility of the attribiute for this option. If {@link Visibility#INHERIT} is specified, the
	 * visibility declared in the {@link GenerateBuilder} annotation will be used.
	 */
	public Visibility visibility() default Visibility.INHERIT;
}
