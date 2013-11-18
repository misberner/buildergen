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

## Features
* Builder generation from constructors or static methods
* Handles generic type parameters of both instance classes and constructor methods
* Respects `throws` specification of constructor or static method
* Highly configurable

## License
BuilderGen is distributed under the terms of the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

## Maintainer
Created and developed by [Malte Isberner](https://github.com/misberner).
