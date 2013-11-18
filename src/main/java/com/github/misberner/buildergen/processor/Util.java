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

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

final class Util {

	private final Elements elements;
	
	public Util(Elements elements) {
		this.elements = elements;
	}
	
	public static AnnotationMirror findAnnotationMirror(Element elem, String annClassName) {
		for(AnnotationMirror am : elem.getAnnotationMirrors()) {
			TypeElement te = (TypeElement)am.getAnnotationType().asElement();
			if(te.getQualifiedName().contentEquals(annClassName)) {
				return am;
			}
		}
		return null;
	}
	
	public Map<String,Object> getAnnotationValues(Element elem, String annClassName) {
		AnnotationMirror mirror = findAnnotationMirror(elem, annClassName);
		if(mirror == null)
			return null;
		return getAnnotationValues(mirror);
	}
	
	public Map<String,Object> getAnnotationValues(AnnotationMirror mirror) {
		Map<? extends ExecutableElement,? extends AnnotationValue> values
			= elements.getElementValuesWithDefaults(mirror);
		Map<String,Object> result = new HashMap<>();
		for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
				: values.entrySet()) {
			ExecutableElement key = entry.getKey();
			AnnotationValue val = entry.getValue();
			result.put(key.getSimpleName().toString(), val.getValue());
		}
		
		return result;
	}
	
	
	public <T> T getAnnotationValue(AnnotationMirror mirror, String name, Class<T> expectedType) {
		Map<? extends ExecutableElement,? extends AnnotationValue> values = elements.getElementValuesWithDefaults(mirror);
		for(Map.Entry<? extends ExecutableElement,? extends AnnotationValue> entry : values.entrySet()) {
			ExecutableElement elem = entry.getKey();
			if(elem.getSimpleName().contentEquals(name)) {
				Object value = entry.getValue().getValue();
				if(expectedType.isAssignableFrom(value.getClass())) {
					return expectedType.cast(value);
				}
				return null;
			}
		}
		return null;
	}
	
	public static <T extends Element> Map<String,? extends T> getEnclosedElements(Element elem, Class<T> filter) {
		Map<String,T> result = new HashMap<>();
		for(Element encElem : elem.getEnclosedElements()) {
			if(filter.isAssignableFrom(encElem.getClass())) {
				result.put(encElem.getSimpleName().toString(), filter.cast(encElem));
			}
		}
		return result;
	}


	public static PackageElement getPackage(Element elem) {
		while(elem.getKind() != ElementKind.PACKAGE) {
			elem = elem.getEnclosingElement();
		}
		return (PackageElement)elem;
	}
	
	public static String getPackageName(Element elem) {
		PackageElement pkg = getPackage(elem);
		if(pkg == null) {
			return "";
		}
		return pkg.getQualifiedName().toString();
	}

}
