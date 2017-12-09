package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class XMLDocument extends XMLElement {

	String version = "1.0";
	String encoding = "UTF-8";
	
	public XMLDocument(XMLBuilder xb, String root) {
		super(xb, root);
	}
	
	public XMLDocument(XMLBuilder xb, String root, XMLNamespace namespace) {
		super(xb, root, namespace);
	}
	
	public XMLDocument(XMLBuilder xb, String version, String encoding, String root, XMLNamespace namespace) {
		super(xb, root, namespace);
		this.version = version;
		this.encoding = encoding;
	}
	
	public XMLDocument setVersion(String version) {
		this.version = version;
		return this;
	}
	
	public XMLDocument setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}
	
	@Override
	XMLDocument setParent(XMLNode parent) { return this; }

	@Override
	public XMLDocument add(XMLNode node) {		
		return (XMLDocument)super.add(node);
	}
	
	@Override
	public XMLDocument remove(XMLNode node) {
		return (XMLDocument)super.remove(node);
	}
	
	@Override
	public XMLDocument set(XMLNamespace namespace) {		
		return (XMLDocument)super.set(namespace);
	}
	
	@Override
	public XMLDocument add(XMLNamespace namespace) {		
		return (XMLDocument)super.add(namespace);
	}
	
	@Override
	public XMLDocument remove(XMLNamespace namespace) {
		return (XMLDocument)super.remove(namespace);
	}
	

	
	@Override
	public PrintWriter toXml(PrintWriter pw) {
		if (xb.includeProlog()) pw.println("<?xml version=\""+version+"\" encoding=\""+encoding+"\"?>");
		return super.toXml(pw);
	}
	
}
