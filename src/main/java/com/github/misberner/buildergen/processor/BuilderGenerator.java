package com.github.misberner.buildergen.processor;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Locale;

import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STWriter;

public class BuilderGenerator {
	
	private static final class TypeElementRenderer implements AttributeRenderer {

		@Override
		public String toString(Object o, String formatString, Locale locale) {
			TypeElement te = (TypeElement)o;
			if("TypeMirror".equals(formatString)) {
				return te.asType().toString();
			}
			return te.toString();
		}
		
	}

	private final STGroup templateGroup;
	
	public BuilderGenerator() {
		URL url = BuilderGenerator.class.getResource("/stringtemplates/builder-source.st");
		this.templateGroup = new STGroupFile(url, "UTF-8", '<', '>');
		this.templateGroup.load();
		templateGroup.registerRenderer(TypeElement.class, new TypeElementRenderer());
	}

	
	public void generateBuilder(SpecModel spec, Filer filer) throws IOException {
		ST st = templateGroup.getInstanceOf("builder_source");
		st.add("spec", spec);
		JavaFileObject jfo = filer.createSourceFile(spec.getFullName(), spec.getInstantiator());

		try(Writer w = jfo.openWriter()) {
			STWriter stWriter = new AutoIndentWriter(w);
			st.write(stWriter);
		}
	}
	
}
