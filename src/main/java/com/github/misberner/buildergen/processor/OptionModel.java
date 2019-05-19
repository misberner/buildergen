/*
 * Copyright (c) 2013 by Malte Isberner (https://github.com/misberner).
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.misberner.buildergen.processor;



import javax.lang.model.type.TypeMirror;

import com.github.misberner.buildergen.annotations.Visibility;

final class OptionModel {
	
	private final TypeMirror type;
	private final String name;
	private final String getterName;
	private final String setterName;
	private final String withName;
	private final String defaultExpr;
	private final Visibility visiblity;
	private final boolean requiredOnInstantiation;
	private final boolean requiredOnCreation;

	

	public OptionModel(TypeMirror type, String name, String getterName, String setterName,
			String withName, String defaultExpr, Visibility visiblity, boolean requiredOnInstantiation,
			boolean requiredOnCreation) {
		this.type = type;
		this.name = name;
		this.getterName = getterName;
		this.setterName = setterName;
		this.withName = withName;
		this.defaultExpr = defaultExpr;
		this.visiblity = visiblity;
		this.requiredOnInstantiation = requiredOnInstantiation;
		this.requiredOnCreation = requiredOnCreation;
	}
	
	public String getName() {
		return name;
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



	public boolean isRequiredOnInstantiation() {
		return requiredOnInstantiation;
	}



	public boolean isRequiredOnCreation() {
		return requiredOnCreation;
	}


}
