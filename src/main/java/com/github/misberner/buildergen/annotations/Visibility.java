package com.github.misberner.buildergen.annotations;

/**
 * The visibility of a builder option attribute.
 * 
 * @author Malte Isberner <malte.isberner@gmail.com>
 *
 */
public enum Visibility {
	/**
	 * Public visibility.
	 */
	PUBLIC("public "),
	/**
	 * Default (package-level) visibility.
	 */
	DEFAULT(""),
	/**
	 * Protected visibility.
	 */
	PROTECTED("protected "),
	/**
	 * Private visibility.
	 */
	PRIVATE("private "),
	/**
	 * This is not a regular Java visibility. It is used to specify that the visibility of an option
	 * should be the same as the value declared in the {@link GenerateBuilder} annotation.
	 */
	INHERIT("!");
	
	private final String declPrefix;
	private Visibility(String declPrefix) {
		this.declPrefix = declPrefix;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.declPrefix;
	}

}
