package com.k2.XMLBuilder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XMLElement extends XMLNode {
	
	String tag;
	Map<String, String> attributes = new HashMap<String, String>();
	
	public XMLElement(XMLBuilder xb, String tag) {
		super(xb);
		this.tag = tag;
	}
	
	public XMLElement(XMLBuilder xb, String tag, XMLNamespace namespace) {
		super(xb, namespace);
		this.tag = tag;
	}
	
	public XMLElement set(String attr, String value) {
		attributes.put(attr, value);
		return this;
	}
	public XMLElement attribute(String attr, String value) { return set(attr, value); }
	public XMLElement attr(String attr, String value) { return set(attr, value); }
	public XMLElement a(String attr, String value) { return set(attr, value); }
	
	public XMLElement remove(String attr) {
		attributes.remove(attr);
		return this;
	}
	
	public XMLElement element(String tag) {
		XMLElement el = xb.element(tag);
		super.add(el);
		return el;
	}
	public XMLElement elem(String tag) { return element(tag); }
	public XMLElement el(String tag) { return element(tag); }
	public XMLElement e(String tag) { return element(tag); }
	
	public XMLElement element(String tag, XMLNamespace ns) {
		XMLElement el = xb.element(tag, ns);
		super.add(el);
		return el;
	}
	public XMLElement elem(String tag, XMLNamespace ns) { return element(tag, ns); }
	public XMLElement el(String tag, XMLNamespace ns) { return element(tag, ns); }
	public XMLElement e(String tag, XMLNamespace ns) { return element(tag, ns); }
	
	public XMLElement data(String data) {
		return (XMLElement)super.add(xb.data(data));
	}
	public XMLElement data(String data, boolean encoded) {
		return (XMLElement)super.add(xb.data(data, encoded));
	}
	public XMLElement d(String data) { return data(data); }
	public XMLElement d(String data, boolean encoded) { return data(data, encoded); }
	
	public XMLElement cData(String cData) {
		return (XMLElement)super.add(xb.cData(cData));
	}
	public XMLElement c(String cData) { return cData(cData); }
	
	public XMLElement cData(StringBuilder cData) {
		return (XMLElement)super.add(xb.cData(cData));
	}
	public XMLElement c(StringBuilder cData) { return cData(cData); }
	
	@Override
	public XMLElement add(XMLNode node) {		
		return (XMLElement)super.add(node);
	}
	
	@Override
	public XMLElement remove(XMLNode node) {
		return (XMLElement)super.remove(node);
	}
	
	@Override
	public XMLElement set(XMLNamespace namespace) {		
		return (XMLElement)super.set(namespace);
	}
	
	@Override
	public XMLElement add(XMLNamespace namespace) {		
		return (XMLElement)super.add(namespace);
	}
	
	@Override
	public XMLElement remove(XMLNamespace namespace) {
		return (XMLElement)super.remove(namespace);
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
