package com.github.misberner.buildergen.annotations;

import java.lang.annotation.Target;

@Target({})
public @interface IfaceImpl {
	public Class<?> value();
	public String[] typeArgs() default {};
}
