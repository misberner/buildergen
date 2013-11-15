package com.github.misberner.buildergen.processor;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STWriter;

public class BuilderGenerator {

	private final STGroup templateGroup;
	
	public BuilderGenerator() {
		URL url = BuilderGenerator.class.getResource("/stringtemplates/builder-source.stg");
		this.templateGroup = new STGroupFile(url, "UTF-8", '<', '>');
		this.templateGroup.load();
		if(!this.templateGroup.isDefined("builder_source")) {
			throw new IllegalStateException("StringTemplate resource does not define 'builder_source' template");
		}
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
