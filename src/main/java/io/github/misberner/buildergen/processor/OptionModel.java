package io.github.misberner.buildergen.processor;


import io.github.misberner.buildergen.annotations.Visibility;

import javax.lang.model.type.TypeMirror;

public class OptionModel {
	
	private final TypeMirror type;
	private final String name;
	private final String getterName;
	private final String setterName;
	private final String withName;
	private final String defaultExpr;
	private final Visibility visiblity;

	

	public OptionModel(TypeMirror type, String name, String getterName, String setterName,
			String withName, String defaultExpr, Visibility visiblity) {
		this.type = type;
		this.name = name;
		this.getterName = getterName;
		this.setterName = setterName;
		this.withName = withName;
		this.defaultExpr = defaultExpr;
		this.visiblity = visiblity;
	}
	
	public String getName() {
		return name;
	}


	public String foobar() {
		return "foobar";
	}

	public TypeMirror getType() {
		return type;
	}
	
	public String getTypeName() {
		return type.toString();
	}



	public String getGetterName() {
		return getterName;
	}



	public String getSetterName() {
		return setterName;
	}



	public String getWithName() {
		return withName;
	}



	public String getDefaultExpr() {
		return defaultExpr;
	}



	public Visibility getVisibility() {
		return visiblity;
	}


}
