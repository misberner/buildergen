package io.github.misberner.buildergen.processor;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;

public class IndentedPrinter implements AutoCloseable {
	
	private final PrintStream out;
	private final String indentString;
	private int indent = 0;
	private final Deque<Integer> indentSteps = new ArrayDeque<>();
	private boolean lineStart = true;

	public IndentedPrinter(OutputStream out, String indentString) {
		this.out = new PrintStream(out);
		this.indentString = indentString;
	}

	@Override
	public void close() throws Exception {
		out.close();
	}

	public void print(Object ...objects) {
		printIndent();
		if(objects.length == 0)
			return;
		
		for(Object obj : objects) {
			out.print(obj);
		}
		lineStart = false;
	}
	
	public void println(Object ...objects) {
		printIndent();
		for(Object obj : objects) {
			out.print(obj);
		}
		out.println();
		lineStart = true;
	}
	
	public void println() {
		printIndent();
		out.println();
		lineStart = true;
	}
	
	public void printfln(String format, Object ...args) {
		printIndent();
		out.printf(format, args);
		out.print('\n');
		lineStart = true;
	}
	
	public void printf(String format, Object ...args) {
		printIndent();
		out.printf(format, args);
		if(!format.isEmpty()) {
			lineStart = (format.charAt(format.length() - 1) == '\n');
		}
	}

	
	public void pushIndent(int indentStep) {
		this.indent += indentStep;
		indentSteps.push(indentStep);
	}
	
	public void popIndent() {
		int lastStep = indentSteps.pop();
		this.indent -= lastStep;
	}
	
	
	private void printIndent() {
		if(!lineStart)
			return;
		for(int i = 0; i < indent; i++) {
			out.print(indentString);
		}
		lineStart = false;
	}
	
	
	

}
