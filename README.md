# XMLBuilder
XMLBuilder is a light weight utility for building well formed xml documents with with minimum of java code.
It supports namespaces for the distinction of similarly named elements and the API is designd to mimic the
sructure of the reulstant xml document.

### License

[Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

## Basic Example

XMLBuilder makes it easy to quickly produce xml documents.

This code:

```java
XMLBuilder xb = new XMLBuilder();
xb.document("root")
.elem("elm")
    .attr("attr", "value")
    .data("AAAA")
    .up()
.elem("elm")
    .attr("attr", "value2")
    .data("BBBB")
    .up()
.toXml(new PrintWriter(System.out).flush();
```
Produces this xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <elm attr="value">AAAA</elm>
    <elm attr="value2">BBBB</elm>
</root>
```

## Getting Started

Download a jar file containing the latest version or fork this project and install in your IDE

Maven users can add this project using the following additions to the pom.xml file.
```maven
<dependencies>
    ...
    <dependency>
        <groupId>com.k2</groupId>
        <artifactId>XMLBuilder</artifactId>
        <version>1.0.0-FINAL</version>
    </dependency>
    ...
</dependencies>
```

## Examples

The following are examples of using XMLBuilder to generate various xml documents. Full working examples can be found
in `/src/example/java/`.

### Creating An Xml Document

These example shows how to create xml documents using XMLBuilder

#### Really Simple Example

The following code produces a default xml document.
```java
// Create the XMLBuilder instance
XMLBuilder xb = new XMLBuilder();

// Extract the document from the builder instance
XMLDocument doc = xb.document("root");
```









