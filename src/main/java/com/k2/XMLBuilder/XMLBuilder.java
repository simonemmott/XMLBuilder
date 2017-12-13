package com.k2.XMLBuilder;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * XMLBuilder is the heart of the XMLBuilder API
 * 
 * This class creates the XML document through the document methods. It also links all the elements of the document together and provides 
 * methods to create new XML elements and data in the document thorough the element, data and cData methods.
 * 
 * Documents and elements created with the XMLBuilder can optionally define the namespace of the element by creating the element and
 * passing in the appropriate XMLNamespace object.
 * @author simon
 * 
 * @see XMLNamespace
 * @see XMLElement
 * @see XMLData
 * @see XMLCData
 *
 */
public class XMLBuilder {
	
	public enum NullAttributeHandling {
		SKIP,
		BLANK,
		USE_NULL;
	}
	
	private int indent = 0;
	private String indentStr = "\t";
	private String currentIndentStr = "";
	private boolean includeProlog = true;
	protected XMLDocument builderRoot;
	private NullAttributeHandling nullAttributeHandling = NullAttributeHandling.USE_NULL;
	private boolean allowOptionalEndTags = false;
	
	public XMLBuilder() {}
	
	/**
	 * This method is called internally to increase the indent of the document for output
	 */
	void indent() { 
		indent++; 
		currentIndentStr = currentIndentStr+indentStr;
	}
	
	/**
	 * The method is called internally to decrease the indent of the document for output
	 */
	void outdent() { 
		if (indent == 0) { return; }
		indent--;
		currentIndentStr = currentIndentStr.substring(0, currentIndentStr.length()-indentStr.length());
	}
	
	/**
	 * This method returns the current indent level
	 * @return	The current indent as an integer
	 */
	int getIndent() { return indent; }
	
	/**
	 * This method returns the current indent as a string
	 * @return The current indent as a string.
	 */
	String getIndentStr() {
		return currentIndentStr;
	}
	
	/**
	 * This method identifies the xml builder as allowing option end tags
	 * This is not allowed for pure xml but is allowed for html
	 * @return	This xml builder
	 */
	protected XMLBuilder allowOptionalEndTags() {
		allowOptionalEndTags = true;
		return this;
	}
	
	/**
	 * This method identifies whether this builder allows optional end tags
	 * @return	true if this xml builder allows optional end tags
	 */
	boolean optionalEndTagsAllowed() { return allowOptionalEndTags; }
	
	/**
	 * This method controls how documents with null attributes are output in the resultant xml
	 * @param nullAttributeHandling	The null attribute handling method
	 * @return	The current xml bulder for method chaining
	 */
	public XMLBuilder setNullAttributeHandling(NullAttributeHandling nullAttributeHandling) {
		this.nullAttributeHandling = nullAttributeHandling;
		return this;
	}
	
	NullAttributeHandling getNullAttributeHandling() { return this.nullAttributeHandling; }
	
	/**
	 * The method tells the builder whether to include the XML prolog in the output
	 * @param include	include or not the XML prolog
	 * @return	The current xml builder to allow method chaining.
	 */
	public XMLBuilder includeProlog(boolean include) { includeProlog = include; return this; }
	
	/**
	 * This method sets the string value to repeat as the indent
	 * @param indent	The sting that is repeated when calculating the indent. Default: \\t
	 * @return The current xml builder to allow method chaining
	 */
	public XMLBuilder setIndent(String indent) { this.indentStr = indent; return this;}
	
	/**
	 * This method is called internally when outputting the document to identify whether or not to include the 
	 * xml prolog
	 * @return	true if the xml prolog should be included in the output.
	 */
	boolean includeProlog() {
		return includeProlog;
	}
	
	private Set<XMLNamespace> definedNamespaces;
	
	/**
	 * The method is called internally when a namespace is added to the document
	 * @param namespace The namespace being defined
	 */
	public void define(XMLNamespace namespace) {
		if (definedNamespaces == null) definedNamespaces=new HashSet<XMLNamespace>();
		definedNamespaces.add(namespace);
	}
	
	/**
	 * This method is called internally when the last tag defining the namespace is removed
	 * @param namespace	The namespace that is no longer defined at the documents current path
	 */
	public void undefine(XMLNamespace namespace) {
		if (definedNamespaces == null) return;
		definedNamespaces.remove(namespace);
	}
	
	/**
	 * This method is used internally to identify whether the given namespace has already been defined 
	 * at the documents current path.
	 * @param namespace	The namespace to check
	 * @return	true if the given namespace is defined in the document or if the given namespace is null
	 */
	public boolean defined(XMLNamespace namespace) {
		if (namespace == null) return true;
		if (definedNamespaces == null) return false;
		return definedNamespaces.contains(namespace);
	} 
	
	/**
	 * Create and register as the root in this builder a new docuemnt
	 * @param root The tag of the xml documents root element
	 * @return	The instance of XMLDocument defining this document
	 */
	public XMLDocument document(String root) { builderRoot = new XMLDocument(this, root); return builderRoot; }
	/**
	 * Create and register as the root in this builder a new document and define the root elements namespace
	 * @param root The tag of the xml documents root element
	 * @param namespace	The namespace of the root element
	 * @return	The instance of XMLDocument defining this document
	 */
	public XMLDocument document(String root, XMLNamespace namespace) { builderRoot = new XMLDocument(this, root, namespace);  return builderRoot; }
	/**
	 * Create and register as the root in this builder a new document defining the xml version, character encoding and setting the root elements namespace
	 * @param version	The xml version of the document, defaults to 1.0
	 * @param encoding	The character encoding of the document. defaults to UTF-8
	 * @param root The tag of the root element
	 * @param namespace	The namespace of the root element
	 * @return	The instance of XMLDocument defining this document
	 */
	public XMLDocument document(String version, String encoding, String root, XMLNamespace namespace) { builderRoot = new XMLDocument(this, version, encoding, root, namespace);  return builderRoot; }
	
	/**
	 * This method returns the xml builders current root document.
	 * <stong>NOTE</strong> You should create a new xml builder each time you create a new document
	 * @return	The XMLDocument that it the current document
	 */
	public XMLDocument root() { return builderRoot; }
	
	/**
	 * Create an xml element tied to this builder for the given tag
	 * @param tag The tag of the element to create
	 * @return	An instance of XMLElement representing the created xml element
	 */
	public XMLElement element(String tag) { return new XMLElement(this, tag); }
	/**
	 * Create an xml element tied to this builder for the given tag and namespace
	 * @param tag	The tag of the element to create
	 * @param namespace	The namespace of the tag to create
	 * @return	An instance of XMLElement represening the created xml element
	 */
	public XMLElement element(String tag, XMLNamespace namespace) { return new XMLElement(this, tag, namespace); }
	
	/**
	 * Create a data node tied to this builder for the given data string
	 * @param data	A string representing the data to add to the document
	 * @return	An instance of XMLData representing the created data node
	 */
	public XMLData data(String data) { return new XMLData(this, data); }
	/**
	 * Create a data node tied to this builder for the given data string and defining whether to xml encode the given data.
	 * @param data A string representing the data to add to the document
	 * @param encode Whether or not to xml encode the given data. The default is true
	 * @return An instance of XMLData representing the created data node
	 */
	public XMLData data(String data, boolean encode) { return new XMLData(this, data, encode); }
	
	/**
	 * Create a cData node tied to this builder for the given data string
	 * @param cData	A string representing the data to add to the document
	 * @return	An instance of XMLCData representing the created cData node.
	 */
	public XMLCData cData(String cData) { return new XMLCData(this, cData); }
	/**
	 * Create a cData node tied to this builder for the given string builder.
	 * 
	 * The given Sting builder can be built at any time before the call to the documents toXml(...) method.
	 * @param cData A string builder representing the data to add to the docuement.
	 * @return An instance of XMLCData representing the created cData node. 
	 */
	public XMLCData cData(StringBuilder cData) { return new XMLCData(this, cData); }
	
	/**
	 * A basic utility method to apply xml encoding to xml attributes and data sections
	 * @param str The string to xml encode
	 * @return	The xml encoded string
	 */
	public static String encodeXmlAttribute(String str) {
	    if (str == null) return null;
	
	    	int len = str.length();
	    	if (len == 0) return str;
	
	    	StringBuffer encoded = new StringBuffer();
	    	for (int i = 0; i < len; i++) {
	    		char c = str.charAt(i);
	    		switch (c) {
	    			case '<':
	    				encoded.append("&lt;");
	    				break;
	    			case '"':
	    				encoded.append("&quot;");
	    				break;
	    			case '>':
	    				encoded.append("&gt;");
	    				break;
	    			case '\'':
	    				encoded.append("&apos;");
	    				break;
	    			case '&':
	    				encoded.append("&amp;");
	    				break;
	    			default:
	    				encoded.append(c);
	    		}
	    	}
	
	    	return encoded.toString();
	} 

}
