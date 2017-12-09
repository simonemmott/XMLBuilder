package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class XMLData extends XMLNode {

	String data;
	boolean encode = true;
	
	public XMLData(XMLBuilder xb, String data) {
		super(xb);
		this.data = data;
	}
	
	public XMLData(XMLBuilder xb, String data, boolean encode) {
		super(xb);
		this.data = data;
		this.encode = encode;
	}
	
	public XMLData encode(boolean encode) { this.encode = encode; return this; }
	
	@Override
	public XMLNode add(XMLNode node) {		
		return this;
	}
	
	@Override
	public XMLNode remove(XMLNode node) {
		return this;
	}
	
	@Override
	public XMLNode add(XMLNamespace namespace) {		
		return this;
	}
	
	@Override
	public XMLNode remove(XMLNamespace namespace) {
		return this;
	}
	
	@Override
	public String toString() { if (encode) return XMLBuilder.encodeXmlAttribute(data); else return data; }

	@Override
	public PrintWriter toXml(PrintWriter pw) {
		if (encode) {
			pw.println(xb.getIndentStr()+XMLBuilder.encodeXmlAttribute(data));
		} else {
			pw.println(xb.getIndentStr()+data);
		}
		return pw;
	}
	


}
