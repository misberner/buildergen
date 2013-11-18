BuilderGen
==========
A free, open-source Java Annotation Processor for generating Builder classes.

## In a Nutshell
From code like *this*:
```java
public class Foo {
  // ...
  public Foo(int id, float ratio, String name, boolean enable) {
    // ...
  }
  // ...
}
```
... generate a builder that can be used like *this*:
```java
new FooBuilder().withId(5).withName("foo").withRatio(0.5f).create();
```
... by adding a single line:
```java
public class Foo {
  // ...
  @GenerateBuilder // magic!
  public Foo(int id, float ratio, String name, boolean enable) {
    // ...
  }
  // ...
}
```

## Using it
Annotation Processing has become standard since Java 1.6. Adding BuilderGen to your build hence is very simple:

### Maven
BuilderGen is deployed to the Maven Central Repository. Add the following to the `dependencies` section of your `pom.xml`:
```xml
<dependency>
  <groupId>com.github.misberner.buildergen</groupId>
  <artifactId>buildergen</artifactId>
  <!-- Make this dependency non-transitive -->
  <scope>provided</scope>
  <version>0.1</version>
</dependency>
```
If you are using `maven-compiler-plugin` of at least version 2.2, your builders will be automatically generated (into `src/target/generated-sources/annotations`) and compiled.

### Non-Maven
* Download the [latest stable version](http://repo1.maven.org/maven2/com/github/misberner/buildergen/buildergen/0.1/buildergen-0.1.jar).
* Make sure the JAR file is in your classpath during compilation (e.g., using an appropriate `classpathref` specification for the `javac` target in Ant).
* Enjoy!

## Documentation
* Usage guide in the Wiki (TBD)
* [Javadoc](https://misberner.github.io/buildergen/maven-site/apidocs)
* [Maven Project Site](https://misberner.github.io/buildergen/maven-site)

## Features
* Builder generation from constructors or static methods
* Handles generic type parameters of both instance classes and constructor methods
* Respects `throws` specification of constructor or static method
* Highly configurable
* Very lightweight: only depends on [StringTemplate](http://www.stringtemplate.org/)


## License
BuilderGen is distributed under the terms of the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

## Maintainer
Created and developed by [Malte Isberner](https://github.com/misberner).
