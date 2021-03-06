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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.AbstractElementVisitor7;
import javax.tools.Diagnostic.Kind;

final class DefaultsModel {
	
	private final Map<String,Element> defaultElements;
	
	public static DefaultsModel create(final TypeElement defaultsType, final ProcessingEnvironment env) {
		if(defaultsType == null) {
			Map<String,Element> empty = Collections.emptyMap();
			return new DefaultsModel(empty);
		}
		
		final Messager m = env.getMessager();
		final Map<String,Element> defaultElements = new HashMap<>();
		ElementVisitor<Void, Void> vis = new AbstractElementVisitor7<Void, Void>() {
			@Override
			public Void visitPackage(PackageElement e, Void p) {
				return null;
			}
			@Override
			public Void visitType(TypeElement e, Void p) {
				return null;
			}
			@Override
			public Void visitVariable(VariableElement e, Void p) {
				if(!e.getModifiers().contains(Modifier.STATIC)) {
					m.printMessage(Kind.WARNING, "Ignoring non-static field in default class", e);
					return null;
				}
				String name = e.getSimpleName().toString();
				if(defaultElements.containsKey(name)) {
					m.printMessage(Kind.WARNING, "Ignoring field in favor of nullary method for same option", e);
					return null;
				}
				defaultElements.put(name, e);
				return null;
			}
			@Override
			public Void visitExecutable(ExecutableElement e, Void p) {
				if(e.getKind() != ElementKind.METHOD) {
					return null;
				}
				if(!e.getModifiers().contains(Modifier.STATIC)) {
					m.printMessage(Kind.WARNING, "Ignoring non-static method in defaults class", e);
					return null;
				}
				if(!e.getParameters().isEmpty()) {
					m.printMessage(Kind.WARNING, "Ignoring non-nullary method in defaults class", e);
					return null;
				}
				
				String name = e.getSimpleName().toString();
				Element old = defaultElements.put(name, e);
				if(old != null) {
					m.printMessage(Kind.WARNING, "Ignoring field in favor of nullary method for same option", old);
				}
				return null;
			}
			@Override
			public Void visitTypeParameter(TypeParameterElement e, Void p) {
				return null;
			}
		};
		for(Element enclosed : defaultsType.getEnclosedElements()) {
			vis.visit(enclosed);
		}
		
		return new DefaultsModel(defaultElements);
	}
	
	public DefaultsModel(Map<String,Element> elemMap) {
		this.defaultElements = elemMap;
	}
	
	public String getDefaultsExpression(String name) {
		Element defaultElem = defaultElements.get(name);
		if(defaultElem == null)
			return null;
		TypeElement defaultsType = (TypeElement)defaultElem.getEnclosingElement();
		StringBuilder sb = new StringBuilder();
		sb.append(defaultsType.getQualifiedName());
		sb.append('.');
		sb.append(defaultElem.getSimpleName());
		if(defaultElem.getKind() == ElementKind.METHOD)
			sb.append("()");
		return sb.toString();
	}


}
