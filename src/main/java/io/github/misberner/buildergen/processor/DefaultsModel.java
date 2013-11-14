package io.github.misberner.buildergen.processor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.AbstractElementVisitor7;
import javax.tools.Diagnostic.Kind;

public class DefaultsModel {
	
	private final Map<String,Element> defaultElements;
	
	public static DefaultsModel create(final TypeElement defaultsType, final ProcessingEnvironment env) {
		if(defaultsType == null) {
			Map<String,Element> empty = Collections.emptyMap();
			return new DefaultsModel(empty);
		}
		
		final Messager m = env.getMessager();
		final Map<String,Element> defaultElements = new HashMap<>();
		new AbstractElementVisitor7<Void, Void>() {
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
		}.visit(defaultsType);
		
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
