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

### Creating A Document

These example shows how to create xml documents using XMLBuilder

#### Really Simple Example

The following code produces a default xml document.
```java
// Create the XMLBuilder instance
XMLBuilder xb = new XMLBuilder();

// Extract the document from the builder instance setting the tag of the root element to 'root'
XMLDocument doc = xb.document("root");
```
This will produce the following xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root/>
```
**Note** the automatic inclusion of the xml prolog

#### Controlling The Xml Prolog
The xml prolog is included by default in all documents generated by XMLBuilder. This can optionally be ommitted
as follows:
```java
XMLBuilder xb = new XMLBuilder().includeProlog(false);
```
**Note** the includeProlog(...) method returns the adjusted XMLBuilder instance. This allows method chaining
and is typically the case with all methods in the XMLBuilder API.

XMLBuilder allows control over the parameters in the xml prolog as follows:
```java
XMLBuilder xb = new XMLBuilder();
xb.document("root")
.setVersion("0.9")
.setEncoding("ISO 8859-1");
```
Which produces the following xml
```xml
<?xml version="0.9" encoding="ISO 8859-1"?>
<root/>
```

#### Formatting The Indent
By default XMLBuilder uses a tab `'\t'` character as the indent. This can be overridden as follows:
```java
XMLBuilder xb = new XMLBuilder().setIndent("  ");
```
Which uses two space characters `'  '` as the indent.

### Adding Contents To A Document

Xml documents are a hierarchy of nodes. Documents produced by XMLBuilder mirror this structure and provide simple
methods to add and remove nodes from the hierarchy. Nodes that define the structure of the document are elements
while nodes that define the data within that structure are data or cData nodes.

Elements have attributes and may contain other nodes while data and cData nodes do not.  The document root is
a special element with methods to control the xml prolog as above. Element nodes produced by XMLBuilder have methods 
to set attributes and add child elements.

#### Setting Attributes On The Document Root
The following example shows how to set attributes on the document root using the `attribute(...)` method:
```java
XMLBuilder xb = new XMLBuilder();
xb.document("root")
.attribute("root-attr", "root-value");
```
Which produces the following xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root root-attr="root-value"/>
```

#### Setting Atributes On Elements
The following example shows adding multiple attributes to a single element:
```java
XMLBuilder xb = new XMLBuilder();
xb.document("root")
.element("child-elm")
	.attribute("attr1", "Added by attribute(...)")
	.attr("attr2", "Added by attr(...)")
	.a("attr3", "Added by a(...)")
	.up()
.element("child-elm")
	.attr("duplicate", "false")
	.attr("duplicate", "true");
	.up()
```
Which produces the follwing xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
	<child-elm attr2="Added by attr(...)" attr1="Added by attribute(...)" attr3="Added by a(...)"/>
	<child-elm duplicate="true"/>
</root>
```
**Note** For brevity there are multiple names for the `attribute(...)` method

**Note** If multiple attibutes with the same name are added the last one takes precedence.

**Note** The multiple names for the attribute method are also available on the root element.

**Note** All attribute values are automatically xml encoded.

**Note** The order of attributes is not preserved.

#### Adding Elements To A Document

Elements are added to an xml document using the `element(...)` method which returns the newly created element
as a child of the current element.

This code:
```java
XMLBuilder xb = new XMLBuilder();
xb.document("root")
.element("child-elm")
	.attr("added-by", "element(...)")
	.up()
.elem("child-elm")
	.attr("added-by", "elem(...)")
	.up()
.el("child-elm")
	.attr("added-by", "el(...)")
	.up()
.e("child-elm")
	.attr("added-by", "e(...)")
	.elem("child-child-elm")
		.elem("child-child-child-elm");
```
Produces this xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
	<child-elm added-by="element(...)"/>
	<child-elm added-by="elem(...)"/>
	<child-elm added-by="el(...)"/>
	<child-elm added-by="e(...)">
		<child-child-elm>
			<child-child-child-elm/>
		</child-child-elm>
	</child-elm>
</root>
```
**Note** For brevity there are multiple names for the `element(...)` method.

**Note** The `up()` method moves one level back up the document path.

**Note** All the `attribute(...)` methods are available on all the elements in the document

#### Adding Data To A Document

Data in xml documents is recored as a string. By default data nodes are automatically xml encoded though this can 
be overridden if desired.

This code:
```java
XMLBuilder xb = new XMLBuilder();
xb.document("root")
.elem("child-elm")
	.data("AAAA")
	.up()
.elem("child-elm")
	.data("AAAA")
	.d("BBBB")
	.up()
.elem("child-elm")
	.data("AAAA")
	.elem("child-child-elm")
		.data("aaaa")
		.up()
	.data("BBBB")
	.up()
.elem("encoded")
	.data("This is encoded: < > ' \" & :")
	.up()
.elem("unencoded")
	.data("This is unencoded: < > ' \" & : and unsafe", false)
	.up();
```
Produces this xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
	<child-elm>AAAA</child-elm>
	<child-elm>
		AAAA
		BBBB
	</child-elm>
	<child-elm>
		AAAA
		<child-child-elm>aaaa</child-child-elm>
		BBBB
	</child-elm>
	<encoded>This is encoded: &lt; &gt; &apos; &quot; &amp; :</encoded>
	<unencoded>This is unencoded: < > ' " & : and unsafe</unencoded>
</root>
```
**Note** elements containing only a single node that is a data node are automatically output on a single line.

**Note** for brevity there multiple names for the `data(...)` method.

**Note** all the `data(...)` methods are available on all elements.

**Note** the order of nodes within an element is preserved

#### Adding cData To A Document

Sometimes it is necessary to include unsafe characters in xml documents but not xml encode them. Typically this
is used for embedding code fragments or other rich text data in a document. In these cases a cData node should be used.
Sometimes preserving white space and carriage returns is important, cData nodes preserve white space and carriage
returns.

Typically cData nodes contain multiple lines and to facilitate this cData nodes can be created from a string or
a string builder.

The following code shows using the `cData(...)` method to create cData nodes:
```java
StringBuilder sb = new StringBuilder();
sb.append("!@#$%^&*()_+\n")
	.append("QWERTYUIOP{}\n")
	.append("ASDFGHJKL:\"|\n")
	.append("~ZXCVBNM<>?\n");

XMLBuilder xb = new XMLBuilder();
XMLDocument doc = xb.document("root");
doc.elem("elm")
	.cData("AAAA")
	.up()
.elem("elm")
	.cData(sb)
	.up();

sb.append("1234567890-=\n")
	.append("qwertyuiop[]\n")
	.append("asdfghjkl;'\\\n")
	.append("`zxcvbnm,./");
```
Produces the follwing xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
	<elm>
		<![CDATA[
AAAA
		]]>
	</elm>
	<elm>
		<![CDATA[
!@#$%^&*()_+
QWERTYUIOP{}
ASDFGHJKL:"|
~ZXCVBNM<>?
1234567890-=
qwertyuiop[]
asdfghjkl;'\
`zxcvbnm,./
		]]>
	</elm>
</root>
```
**Note** cData nodes can be created from a string or a string builder

**Note** the contents of the cData node created from a string builder can be built at any time up to the generation of the xml

**Note** to preserve white space and carriage returns the contents of a cData node are always entered on thier own line wihtout applying the current indent.

### Outputting The Xml

The xml output is generated by calling the `toXml(...)` method. The toXml method generates the xml from the current state
of the document. The `toXml(...)` method can be called from any node in the document and the generated xml will contain
only those nodes that either the node on which the `toXml(...)` method was called and its descendant nodes.

The following code snippet shows outputting the xml to standard out.
```java
XMLBuilder xb = new XMLBuilder();
xb.document("root")
	...
.toXml(new PrintWriter(System.out)).flush();
```
The following code snippet shows outputting the xml to a string.
```java
StringWriter sw = new StringWriter();
XMLBuilder xb = new XMLBuilder();
xb.document("root")
	...
.toXml(sw).flush();

sw.toString();
```
The following code snippet shows outputting the xml to a file.
```java
File myFile = new File("myFile.xml");

XMLBuilder xb = new XMLBuilder();
xb.document("root")
	...
.toXml(myFile).close();
```
**Note** the generated xml is automatically flushed to the file, but the file is not closed.

**Note** this implementation of the `toXml(...)` throws the checked exception `FileNotFoundException`.

### Working With Deep Trees













































