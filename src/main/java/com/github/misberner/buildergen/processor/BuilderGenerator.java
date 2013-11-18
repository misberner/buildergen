/*
 * Copyright (c) 2013 Malte Isberner, https://github.com/misberner.
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

final class BuilderGenerator {
	
	private static final String SOURCE_TEMPLATE_NAME = "builder_source";

	private final STGroup templateGroup;


	public BuilderGenerator() {
		URL url = BuilderGenerator.class.getResource("/stringtemplates/builder-source.stg");
		this.templateGroup = new STGroupFile(url, "UTF-8", '<', '>');
		this.templateGroup.load();
		if(!this.templateGroup.isDefined(SOURCE_TEMPLATE_NAME)) {
			throw new IllegalStateException("StringTemplate resource does not define 'builder_source' template");
		}
	}

	
	public void generateBuilder(SpecModel spec, Filer filer) throws IOException {
		ST st = templateGroup.getInstanceOf(SOURCE_TEMPLATE_NAME);
		st.add("spec", spec);
		JavaFileObject jfo = filer.createSourceFile(spec.getFullName(), spec.getInstantiator());

		try(Writer w = jfo.openWriter()) {
			STWriter stWriter = new AutoIndentWriter(w);
			st.write(stWriter);
		}
	}
	
}
