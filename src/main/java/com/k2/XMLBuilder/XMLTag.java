package com.k2.XMLBuilder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XMLTag extends XMLNode {
	
	String tag;
	Map<String, String> attributes = new HashMap<String, String>();
	
	public XMLTag(XMLBuilder xb, String tag) {
		super(xb);
		this.tag = tag;
	}
	
	public XMLTag(XMLBuilder xb, String tag, XMLNamespace namespace) {
		super(xb, namespace);
		this.tag = tag;
	}
	
	public XMLTag set(String attr, String value) {
		attributes.put(attr, value);
		return this;
	}
	
	public XMLTag remove(String attr) {
		attributes.remove(attr);
		return this;
	}
	
	@Override
	public XMLTag add(XMLNode node) {		
		return (XMLTag)super.add(node);
	}
	
	@Override
	public XMLTag remove(XMLNode node) {
		return (XMLTag)super.remove(node);
	}
	
	@Override
	public XMLTag set(XMLNamespace namespace) {		
		return (XMLTag)super.set(namespace);
	}
	
	@Override
	public XMLTag add(XMLNamespace namespace) {		
		return (XMLTag)super.add(namespace);
	}
	
	@Override
	public XMLTag remove(XMLNamespace namespace) {
		return (XMLTag)super.remove(namespace);
	}
	

	
	@Override
	public PrintWriter toXml(PrintWriter pw) {
		String nsPrefix = "";
		if (getNamespace()!=null) nsPrefix = getNamespace().ns+":";
		
		pw.print(xb.getIndentStr()+"<"+nsPrefix+tag);
		
		if (!xb.defined(getNamespace())) {
			pw.print(" xmlns:"+getNamespace().ns+"=\""+getNamespace().url+"\"");
			xb.define(getNamespace());
		}

		if (namespaces != null) {
			for (XMLNamespace namespace : namespaces) {
				if (!xb.defined(namespace)) {
					pw.print(" xmlns:"+namespace.ns+"=\""+namespace.url+"\"");
					xb.define(namespace);
				}
			}
		}
		
		for (String attr : attributes.keySet()) pw.print(" "+attr+"=\""+XMLBuilder.encodeXmlAttribute(attributes.get(attr))+"\"");
		
		if (contents==null||contents.size()==0) pw.println("/>");
		else {
			if (contents.size()==1&&contents.get(0).getClass()==XMLData.class) {
				pw.println(">"+contents.get(0).toString()+"</"+nsPrefix+tag+">");
			}
			else {
				pw.println(">");
				xb.indent();
				for (XMLNode node : contents) node.toXml(pw);
				xb.outdent();
				pw.println(xb.getIndentStr()+"</"+nsPrefix+tag+">");
			}
		}
		
		if (parent != null) {
			if (namespaces != null) for (XMLNamespace namespace : namespaces) if (!parent.defined(namespace)) xb.undefine(namespace);
			if (!parent.defined(namespace)) xb.undefine(namespace);
		} else {
			if (namespaces != null) for (XMLNamespace namespace : namespaces) xb.undefine(namespace);
			xb.undefine(namespace);
		}
		return pw;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<"+tag);
		for (String attr : attributes.keySet()) sb.append(" "+attr+"=\""+XMLBuilder.encodeXmlAttribute(attributes.get(attr))+"\"");
		if (contents==null||contents.size()==0) sb.append("/>");
		else {
			sb.append(">");
			for (XMLNode node : contents) sb.append(node.toString());
			sb.append("</"+tag+">");
		}
		return sb.toString();
	}
	
	

}
