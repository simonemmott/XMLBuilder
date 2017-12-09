package com.k2.XMLBuilder;

import java.io.PrintWriter;

/**
 * This class represents a data node within an xml document
 * 
 * By default the data within a date node is xml encoded
 * @author simon
 *
 */
public class XMLData extends XMLNode {

	String data;
	boolean encode = true;
	
	/**
	 * Create an instance of a data node for the given xml builder and string data
	 * @param xb The xml builder that created the instance
	 * @param data The data as a string for this data node
	 */
	XMLData(XMLBuilder xb, String data) {
		super(xb);
		this.data = data;
	}
	/**
	 * Create an instance of a data node for the given xml builder, string data and identifying 
	 * whether to xml encode the data
	 * @param xb The xml builder that created the instance
	 * @param data The data as a string for this data node
	 * @param encode Whether or not the encode the data. The default is true
	 */
	XMLData(XMLBuilder xb, String data, boolean encode) {
		super(xb);
		this.data = data;
		this.encode = encode;
	}
	
	/**
	 * data nodes cannot contain child nodes
	 */
	@Override
	public XMLNode add(XMLNode node) {		
		return this;
	}
	/**
	 * data nodes cannot contain child nodes
	 */
	@Override
	public XMLNode remove(XMLNode node) {
		return this;
	}
		
	/**
	 * Return the data for this section xml encoded or not
	 */
	@Override
	public String toString() { if (encode) return XMLBuilder.encodeXmlAttribute(data); else return data; }

	/**
	 * Output this data section within an xml document xml encoded or not
	 */
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
