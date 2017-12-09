package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class XMLCData extends XMLNode {

	StringBuilder cData;
	
	public XMLCData(XMLBuilder xb, StringBuilder cData) {
		super(xb);
		this.cData = cData;
	}
	
	public XMLCData(XMLBuilder xb, String cData) {
		super(xb);
		this.cData = new StringBuilder(cData);
	}
	
	public XMLCData(XMLBuilder xb) {
		super(xb);
		this.cData = new StringBuilder();
	}
	
	public XMLCData append(String cData) {
		this.cData.append(cData);
		return this;
	}
	
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
	public String toString() { return cData.toString(); }

	@Override
	public PrintWriter toXml(PrintWriter pw) {
		pw.println(xb.getIndentStr()+"<![CDATA[");
		pw.println(cData);
		pw.println(xb.getIndentStr()+"]]>");
		return pw;
	}
	


}
