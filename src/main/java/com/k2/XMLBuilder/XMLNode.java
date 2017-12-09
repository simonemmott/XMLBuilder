package com.k2.XMLBuilder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class XMLNode {
	
	XMLBuilder xb;
	XMLNode parent;
	List<XMLNode> contents;
	XMLNamespace namespace;
	Set<XMLNamespace> namespaces;


	public XMLNode(XMLBuilder xb) {
		this.xb = xb;
	}
	
	public XMLNode(XMLBuilder xb, XMLNode parent) {
		this.xb = xb;
		this.parent = parent;
	}
	
	public XMLNode(XMLBuilder xb, XMLNamespace namespace) {
		this.xb = xb;
		this.namespace = namespace;
	}
	
	public XMLNode(XMLBuilder xb, XMLNode parent, XMLNamespace namespace) {
		this.xb = xb;
		this.parent = parent;
		this.namespace = namespace;
	}
	
	XMLNode setParent(XMLNode parent) {
		this.parent = parent;
		return this;
	}
	
	XMLNode set(XMLNamespace namespace) {
		this.namespace = namespace;
		return this;
	}
	
	public XMLNode add(XMLNode node) {
		if (contents == null) { contents = new ArrayList<XMLNode>(); }
		contents.add(node);
		node.setParent(this);
		return this;
	}

	public XMLNode add(XMLNamespace namespace) {
		if (namespaces == null) { namespaces = new HashSet<XMLNamespace>(); }
		namespaces.add(namespace);
		return this;
	}
	
	public XMLNode remove(XMLNode node) {
		if (contents == null) { return this; }
		contents.remove(node);
		return this;
	}
	
	public XMLNode remove(XMLNamespace namespace) {
		if (namespaces == null) { return this; }
		namespaces.remove(namespace);
		return this;
	}
	
	public XMLNamespace getNamespace() { return namespace; }	
	
	public boolean defined(XMLNamespace namespace) {
		if (namespace == null) return true;
		if (namespace.equals(getNamespace())) return true;
		if (namespaces!=null && namespaces.contains(namespace)) return true;
		if (parent!=null) return parent.defined(namespace);
		return false;
	}
	
	public abstract PrintWriter toXml(PrintWriter pw);



}
