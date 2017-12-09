package com.k2.XMLBuilder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents an xml element within an xml document
 * 
 * Instances of xml elements can only be created through an instance of XMLBuilder
 * @author simon
 *
 */
public class XMLElement extends XMLNode {
	
	private String tag;
	private Map<String, String> attributes;
	private XMLNamespace namespace;
	private Set<XMLNamespace> namespaces;

	
	/**
	 * Create an instance of an xml element for the given xml builder and tag
	 * @param xb The xml builder that created the xml element
	 * @param tag The tag of the xml element
	 */
	XMLElement(XMLBuilder xb, String tag) {
		super(xb);
		this.tag = tag;
	}
	/**
	 * Create an instance of an xml element for the given xml builder, tag and namespace
	 * @param xb The xml builder that created the xml element
	 * @param tag The tag of the xml element
	 * @param namespace The namespace of the xml element
	 */
	XMLElement(XMLBuilder xb, String tag, XMLNamespace namespace) {
		super(xb);
		this.tag = tag;
		this.namespace = namespace;
	}
	
	/**
	 * Set an attribute of the xml element
	 * @param attr The attribute to set
	 * @param value The attribute value to set
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement set(String attr, String value) {
		if (attributes == null) attributes = new HashMap<String, String>();
		attributes.put(attr, value);
		return this;
	}
	/**
	 * An alternative name for the set method to set an attribute of this element
	 * @param attr The attribute to set
	 * @param value The attribute value to set
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement attribute(String attr, String value) { return set(attr, value); }
	/**
	 * An alternative name for the set method to set an attribute of this element
	 * @param attr The attribute to set
	 * @param value The attribute value to set
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement attr(String attr, String value) { return set(attr, value); }
	/**
	 * An alternative name for the set method to set an attribute of this element
	 * @param attr The attribute to set
	 * @param value The attribute value to set
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement a(String attr, String value) { return set(attr, value); }
	/**
	 * Remove the named attribute from the element
	 * @param attr The name of the attribute to remove
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement remove(String attr) {
		if (attributes == null) return this;
		attributes.remove(attr);
		return this;
	}
	
	/**
	 * A utility method to create and add an xml element as a child of this element
	 * @param tag The tag of the element to create and add
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement element(String tag) {
		XMLElement el = xb.element(tag);
		super.add(el);
		return el;
	}
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element
	 * @param tag The tag of the element to create and add
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement elem(String tag) { return element(tag); }
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element
	 * @param tag The tag of the element to create and add
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement el(String tag) { return element(tag); }
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element
	 * @param tag The tag of the element to create and add
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement e(String tag) { return element(tag); }
	/**
	 * A utility method to create and add an xml element as a child of this element and set the child elements namespace
	 * @param tag The tag of the element to create and add
	 * @param ns The namespace of the new element
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement element(String tag, XMLNamespace ns) {
		XMLElement el = xb.element(tag, ns);
		super.add(el);
		return el;
	}
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element and set
	 * the child elements namespace
	 * @param tag tag The tag of the element to create and add
	 * @param ns The namespace of the new element
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement elem(String tag, XMLNamespace ns) { return element(tag, ns); }
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element and set
	 * the child elements namespace
	 * @param tag tag The tag of the element to create and add
	 * @param ns The namespace of the new element
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement el(String tag, XMLNamespace ns) { return element(tag, ns); }
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element and set
	 * the child elements namespace
	 * @param tag tag The tag of the element to create and add
	 * @param ns The namespace of the new element
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement e(String tag, XMLNamespace ns) { return element(tag, ns); }
	
	/**
	 * A utilty method to create and add a data section as a child of this element
	 * @param data The string representing the data for the section
	 * @return This xml element for method chaining
	 */
	public XMLElement data(String data) {
		return (XMLElement)super.add(xb.data(data));
	}
	/**
	 * A utility method to create and add a data section as a child of this element and define whether or not
	 * to encode the given data
	 * @param data A string representing the data to add
	 * @param encoded Whether or not to encode the given data string. Default true
	 * @return This xml element for method chaining
	 */
	public XMLElement data(String data, boolean encoded) {
		return (XMLElement)super.add(xb.data(data, encoded));
	}
	/**
	 * An alternative name for the utility method to create and add a data section as a child of this element
	 * @param data A string representing the data to add
	 * @return This xml element for method chaining
	 */
	public XMLElement d(String data) { return data(data); }
	/**
	 * An alternative name for the utility method to create and add a data section as a child of this element and define whether or not
	 * to encode the given data
	 * @param data A string representing the data to add
	 * @param encoded Whether or not to encode the given data string. Default true
	 * @return This xml element for method chaining
	 */
	public XMLElement d(String data, boolean encoded) { return data(data, encoded); }
	/**
	 * A utility method to create and add a cData section as a child of this element
	 * @param cData A string representing the data for the cData section
	 * @return This xml element for method chaining
	 */
	public XMLElement cData(String cData) {
		return (XMLElement)super.add(xb.cData(cData));
	}
	/**
	 * An alternativ name for the utility method to create and add a cData section as a child of this element
	 * @param cData A string representing the data for the cData section
	 * @return This xml element for method chaining
	 */
	public XMLElement c(String cData) { return cData(cData); }
	/**
	 * A utility method to create and add a cData section as a child of this element
	 * @param cData A string builder representing the data for this cData section
	 * @return This xml element for method chaining
	 */
	public XMLElement cData(StringBuilder cData) {
		return (XMLElement)super.add(xb.cData(cData));
	}
	/**
	 * An alternativ name for the utility method to create and add a cData section as a child of this element
	 * @param cData A string builder representing the data for this cData section
	 * @return This xml element for method chaining
	 */
	public XMLElement c(StringBuilder cData) { return cData(cData); }
	
	/**
	 * Ensure that the method to add a child node returns this instance as an XMLElement
	 */
	@Override
	public XMLElement add(XMLNode node) {		
		return (XMLElement)super.add(node);
	}
	/**
	 * Ensure that the method to remove a child node returns this instance as an XMLElement
	 */
	@Override
	public XMLElement remove(XMLNode node) {
		return (XMLElement)super.remove(node);
	}
	/**
	 * Ensure that the method to set the namespace of this element returns this instance as an XMLElement
	 */
//	@Override
//	public XMLElement set(XMLNamespace namespace) {		
//		return (XMLElement)super.set(namespace);
//	}
	/**
	 * Ensure that the method to add a namespace definition to this element returns this instance as an XMLElement
	 */
//	@Override
//	public XMLElement add(XMLNamespace namespace) {		
//		return (XMLElement)super.add(namespace);
//	}
	/**
	 * Ensure that the method to remove a namespace definition from this element returns this instance as an XMLElement
	 */
//	@Override
//	public XMLElement remove(XMLNamespace namespace) {
//		return (XMLElement)super.remove(namespace);
//	}

	/**
	 * Output this element as well formed xml including and defining namespaces as necessary
	 */
	@Override
	public PrintWriter toXml(PrintWriter pw) {
		
		// If the element has a namespace prepare the namespace prefix
		String nsPrefix = "";
		if (getNamespace()!=null) nsPrefix = getNamespace().ns+":";
		
		// Write the beginning for the elements start tag after the current indent
		pw.print(xb.getIndentStr()+"<"+nsPrefix+tag);
		
		// If this element has a namespace that has not already been defined in the current document path
		// Write the namespace definition as an attribute of the element
		if (!xb.defined(getNamespace())) {
			pw.print(" xmlns:"+getNamespace().ns+"=\""+getNamespace().url+"\"");
			xb.define(getNamespace());
		}

		// If this element includes namespace defintions that have not already been defined in the current document path
		// Write the namespace definitions as attributes of the element
		if (namespaces != null) {
			for (XMLNamespace namespace : namespaces) {
				if (!xb.defined(namespace)) {
					pw.print(" xmlns:"+namespace.ns+"=\""+namespace.url+"\"");
					xb.define(namespace);
				}
			}
		}
		
		// Add any attributes that have been defined for the element xml encoding the attribute values
		if (attributes != null) for (String attr : attributes.keySet()) pw.print(" "+attr+"=\""+XMLBuilder.encodeXmlAttribute(attributes.get(attr))+"\"");
		
		// If this element has no contents close the element
		if (contents==null||contents.size()==0) pw.println("/>");
		// Otherwise write the child contents of this element
		else {
			// If there is only one child element and that elemnt is a data element
			// Write the data element on the same line as the open element tag and close the element
			if (contents.size()==1&&contents.get(0).getClass()==XMLData.class) {
				pw.println(">"+contents.get(0).toString()+"</"+nsPrefix+tag+">");
			}
			// Otherwise close the elements start tag and writer all the child elements indented
			// And close the element after reducing the indent
			else {
				pw.println(">");
				xb.indent();
				for (XMLNode node : contents) node.toXml(pw);
				xb.outdent();
				pw.println(xb.getIndentStr()+"</"+nsPrefix+tag+">");
			}
		}
		
		// If this element has a parent node and defines namespaces undefine the namespaces in the xml builder that are not also defined 
		// higher up the document path
		if (parent != null) {
			if (namespaces != null) for (XMLNamespace namespace : namespaces) if (!((XMLElement)parent).defined(namespace)) xb.undefine(namespace);
			if (!((XMLElement)parent).defined(namespace)) xb.undefine(namespace);
		} else {
			if (namespaces != null) for (XMLNamespace namespace : namespaces) xb.undefine(namespace);
			xb.undefine(namespace);
		}
		
		// Return the print writer used to output the xml element for method chaining
		return pw;
	}
	
	/**
	 * Get the namespace of this element
	 * @return This elements namespace
	 */
	public XMLNamespace getNamespace() { return namespace; }	
	/**
	 * Set the namespace for this element
	 * @param namespace The namespace to set for this element
	 * @return This element for method chaining
	 */
	public XMLElement set(XMLNamespace namespace) {
		this.namespace = namespace;
		return this;
	}
	/**
	 * Add a namepsace definition to this element
	 * @param namespace The namespace definition to add
	 * @return This element for method chaining
	 */
	public XMLElement add(XMLNamespace namespace) {
		if (namespaces == null) { namespaces = new HashSet<XMLNamespace>(); }
		namespaces.add(namespace);
		return this;
	}
	/**
	 * Remove a namespace definition from this element
	 * @param namespace The namespace definition to remove
	 * @return This element for method chaining
	 */
	public XMLElement remove(XMLNamespace namespace) {
		if (namespaces == null) { return this; }
		namespaces.remove(namespace);
		return this;
	}
	/**
	 * Check whether the given namespace is defined in this element or any of this elements parent nodes
	 * 
	 * This method will return true if the namespace to check is null
	 * This method will return true if this element is in the namespace to check of if the namespace to check is in
	 * the list of namespaces defined on this element
	 * Otherwise if this element has a parent then this method returns the value of this method called on this elements parent
	 * Otherwise it returns false
	 * 
	 * @param namespace The namespace to check
	 * @return True if this element or any of its parent elements define the namespace to check
	 */
	public boolean defined(XMLNamespace namespace) {
		if (namespace == null) return true;
		if (namespace.equals(getNamespace())) return true;
		if (namespaces!=null && namespaces.contains(namespace)) return true;
		if (parent!=null) return ((XMLElement)parent).defined(namespace);
		return false;
	}
	
	/**
	 * Output this element as string without carriage returns or namespaces but including xml encoding.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<"+tag);
		if (attributes != null) for (String attr : attributes.keySet()) sb.append(" "+attr+"=\""+XMLBuilder.encodeXmlAttribute(attributes.get(attr))+"\"");
		if (contents==null||contents.size()==0) sb.append("/>");
		else {
			sb.append(">");
			for (XMLNode node : contents) sb.append(node.toString());
			sb.append("</"+tag+">");
		}
		return sb.toString();
	}
	
	

}
