package com.k2.XMLBuilder;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class XMLBuilder {
	
	private int indent = 0;
	private String indentStr = "\t";
	private String currentIndentStr = "";
	private boolean includeProlog = true;
	
	public XMLBuilder() {}
	
	void indent() { 
		indent++; 
		currentIndentStr = currentIndentStr+indentStr;
	}
	void outdent() { 
		if (indent == 0) { return; }
		indent--;
		currentIndentStr = currentIndentStr.substring(0, currentIndentStr.length()-indentStr.length());
	}
	int getIndent() { return indent; }
	
	String getIndentStr() {
		return currentIndentStr;
	}
	
	public XMLBuilder includeProlog(boolean include) { includeProlog = include; return this; }
	
	public XMLBuilder setIndent(String indent) { this.indentStr = indent; return this;}
	
	boolean includeProlog() {
		return includeProlog;
	}
	
	private Set<XMLNamespace> definedNamespaces;
	
	public void define(XMLNamespace namespace) {
		if (definedNamespaces == null) definedNamespaces=new HashSet<XMLNamespace>();
		definedNamespaces.add(namespace);
	}
	
	public void undefine(XMLNamespace namespace) {
		if (definedNamespaces == null) return;
		definedNamespaces.remove(namespace);
	}
	
	public boolean defined(XMLNamespace namespace) {
		if (namespace == null) return true;
		if (definedNamespaces == null) return false;
		return definedNamespaces.contains(namespace);
	} 
	
	public XMLDocument document(String root) { return new XMLDocument(this, root); }
	public XMLDocument document(String root, XMLNamespace namespace) { return new XMLDocument(this, root, namespace); }
	public XMLDocument document(String version, String encoding, String root, XMLNamespace namespace) { return new XMLDocument(this, version, encoding, root, namespace); }
	
	public XMLElement element(String tag) { return new XMLElement(this, tag); }
	public XMLElement element(String tag, XMLNamespace namespace) { return new XMLElement(this, tag, namespace); }
	
	public XMLData data(String data) { return new XMLData(this, data); }
	public XMLData data(String data, boolean encode) { return new XMLData(this, data, encode); }
	
	public XMLCData cData(String cData) { return new XMLCData(this, cData); }
	public XMLCData cData(StringBuilder cData) { return new XMLCData(this, cData); }
	
	
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
