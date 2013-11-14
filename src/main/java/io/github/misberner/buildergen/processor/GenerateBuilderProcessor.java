package io.github.misberner.buildergen.processor;

import io.github.misberner.buildergen.annotations.GenerateBuilder;
import io.github.misberner.buildergen.annotations.Option;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

public class GenerateBuilderProcessor extends AbstractProcessor {
	
	public static final String GENERATE_BUILDER_NAME = GenerateBuilder.class.getName();
	public static final String OPTION_NAME = Option.class.getName();
	
	private static final Set<String> SUPPORTED_ANNOTATIONS = new HashSet<>(
			Arrays.asList(GENERATE_BUILDER_NAME, OPTION_NAME));
	
	private Util util;
	private final BuilderGenerator builderGen;
	

	public GenerateBuilderProcessor() {
		this.builderGen = new BuilderGenerator();
	}
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		this.util = new Util(processingEnv.getElementUtils());
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return SUPPORTED_ANNOTATIONS;
	}
	
	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_7;
	}

	@Override
	public Iterable<? extends Completion> getCompletions(Element element,
			AnnotationMirror annotation, ExecutableElement member,
			String userText) {
		return Collections.emptyList();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		
		Set<? extends Element> optionElements = roundEnv.getElementsAnnotatedWith(Option.class);
		Set<? extends Element> builderElements = roundEnv.getElementsAnnotatedWith(GenerateBuilder.class);
		
		for(Element optElem : optionElements) {
			Element enclosing = optElem.getEnclosingElement();
			if(enclosing.getAnnotation(GenerateBuilder.class) == null) {
				processingEnv.getMessager().printMessage(Kind.ERROR, "Only parameters of methods or constructors "
					+ "annotated with a @GenerateBuilder annotation may be annotated with @Option!", optElem);
			}
		}
		
		
		for(Element elem : builderElements) {
			AnnotationMirror annMirror = util.findAnnotationMirror(elem, GENERATE_BUILDER_NAME);
			if(annMirror == null) {
				processingEnv.getMessager().printMessage(Kind.WARNING, "Unable to find annotation " + GENERATE_BUILDER_NAME, elem);
				continue;
			}
			
			if(elem.getKind() == ElementKind.METHOD) {
				if(!elem.getModifiers().contains(Modifier.STATIC)) {
					processingEnv.getMessager().printMessage(Kind.ERROR, "@GenerateBuilder can only be applied to constructors or static methods", elem, annMirror);
					continue;
				}
			}
			else if(!(elem instanceof ExecutableElement)) {
				processingEnv.getMessager().printMessage(Kind.ERROR, "Only executable elements can be annotated with @GenerateBuilder", elem, annMirror);
				continue;
			}
			
			SpecModel specModel;
			
			try {
				specModel = new SpecModel((ExecutableElement)elem, processingEnv, util);
			}
			catch(IllegalArgumentException ex) {
				specModel = null;
			}
			
			if(specModel == null) {
				continue;
			}
			
			try {
				builderGen.generateBuilder(specModel, processingEnv.getFiler());
			}
			catch(IOException ex) {
				processingEnv.getMessager().printMessage(Kind.ERROR, "Could not writer builder source: " + ex.getMessage());
			}
			
			
		}
		return true;
	}

}
