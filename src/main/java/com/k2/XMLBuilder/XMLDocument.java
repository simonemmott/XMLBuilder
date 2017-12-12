package com.k2.XMLBuilder;

import java.io.PrintWriter;

/**
 * This class represents an xml document
 * 
 * The document by default includes the xml prolog though this can be omitted if the xml builder that
 * created the document is so configured.  By default the xml version is 1.0 and the character encoding
 * is UTF-8 though these can be overriden when the document instance in created or after through the use 
 * of the relevant methods
 * 
 * Instances of xml documents can only be created through an instance of XMLBuilder
 * @author simon
 *
 */
public class XMLDocument extends XMLElement {

	private String version = "1.0";
	private String encoding = "UTF-8";
	
	/**
	 * Create an instance of an xml document for the given xml builder and root element tag
	 * @param xb The xml builder that created the instance
	 * @param root The tag of the root document element
	 */
	protected XMLDocument(XMLBuilder xb, String root) {
		super(xb, root);
	}
	/**
	 * Create an instance of an xml document for the given xml builder, root element tag and namespace
	 * @param xb The xml builder that created the instance
	 * @param root The tag of the root element
	 * @param namespace The namespace of the root element
	 */
	protected XMLDocument(XMLBuilder xb, String root, XMLNamespace namespace) {
		super(xb, root, namespace);
	}
	/**
	 * Create an instance of an xml document for the given xml builder, xml version, character encoding, 
	 * root element tag and namespace
	 * @param xb The xml builder that created the instance
	 * @param version The xml version of the xml document
	 * @param encoding The character encoding of the xml document
	 * @param root The tag of the root element
	 * @param namespace The namespace of the root element
	 */
	protected XMLDocument(XMLBuilder xb, String version, String encoding, String root, XMLNamespace namespace) {
		super(xb, root, namespace);
		this.version = version;
		this.encoding = encoding;
	}
	
	/**
	 * Set the xml version of the document
	 * @param version The xml version of the document
	 * @return This instance of the document for method chaining
	 */
	public XMLDocument setVersion(String version) {
		this.version = version;
		return this;
	}
	
	/**
	 * Set the character encoding of the xml document
	 * @param encoding The string representing the character encoding
	 * @return This instance of the document for method chaining
	 */
	public XMLDocument setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}
	
	/**
	 * Xml documents cannot have a parent
	 */
	@Override
	XMLDocument setParent(XMLNode parent) { return this; }

	/**
	 * Ensure that the method to add a child node returns this instance as an XMLDocument
	 */
	@Override
	public XMLDocument add(XMLNode node) {		
		return (XMLDocument)super.add(node);
	}
	/**
	 * Ensure that the method to remove a child node returns this instance as an XMLDocument
	 */
	@Override
	public XMLDocument remove(XMLNode node) {
		return (XMLDocument)super.remove(node);
	}
	/**
	 * Ensure that the method to set the namespce of the root element returns this instance as an XMLDocument
	 */
	@Override
	public XMLDocument set(XMLNamespace namespace) {		
		return (XMLDocument)super.set(namespace);
	}
	/**
	 * Ensure that the method to add a namespce definition to the root element returns this instance as an XMLDocument
	 */
	@Override
	public XMLDocument add(XMLNamespace namespace) {		
		return (XMLDocument)super.add(namespace);
	}
	/**
	 * Ensure that the method to remove a namespace definition from the root element returns this instance as an XMLDocument
	 */
	@Override
	public XMLDocument remove(XMLNamespace namespace) {
		return (XMLDocument)super.remove(namespace);
	}
	
	/**
	 * The method returns the prolog as a string. It is used to add the prolog to the document and should be overriden if an
	 * Alternative prolog is required e.g. \<!DOCTYPE html\> for an html document
	 * @return	The prolog string
	 */
	protected String prolog() { return "<?xml version=\""+version+"\" encoding=\""+encoding+"\"?>"; }

	/**
	 * Output this document on the given print writer including the xml prolog if the xml builder is so configured
	 */
	@Override
	public PrintWriter toXml(PrintWriter pw) {
		if (xb.includeProlog()) pw.println(prolog());
		return super.toXml(pw);
	}
	
}
