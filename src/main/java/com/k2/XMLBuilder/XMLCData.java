package com.k2.XMLBuilder;

import java.io.PrintWriter;

/**
 * This class represents a CData node within an xml document
 * 
 * CData sections contain data that is not xml encoded
 * @author simon
 *
 */
public class XMLCData extends XMLNode {

	StringBuilder cData;
	
	/**
	 * Create an instance of a CData node for the given xml builder and string builder
	 * @param xb The xml builder that created the instance
	 * @param cData The string builder that defines the cData
	 */
	XMLCData(XMLBuilder xb, StringBuilder cData) {
		super(xb);
		this.cData = cData;
	}
	/**
	 * Create an instance of a CData node for the given xml builder and string
	 * @param xb The xml builder that created the instance
	 * @param cData	The string repesenting the data
	 */
	XMLCData(XMLBuilder xb, String cData) {
		super(xb);
		this.cData = new StringBuilder(cData);
	}
	/**
	 * Create an instance of a CData node for the given xml builder
	 * @param xb The xml builder that created the instance
	 */
	XMLCData(XMLBuilder xb) {
		super(xb);
		this.cData = new StringBuilder();
	}
	
	/**
	 * Append the given string to the data 
	 * @param cData	The string representing the data to append
	 * @return	This instance of the CData section
	 */
	public XMLCData append(String cData) {
		this.cData.append(cData);
		return this;
	}
	
	/**
	 * Cdata sections cannot have child nodes
	 */
	@Override
	public XMLNode add(XMLNode node) {		
		return this;
	}
	/**
	 * Cdata sections cannot have child nodes
	 */
	@Override
	public XMLNode remove(XMLNode node) {
		return this;
	}
	
	/**
	 * Return the data of this cData section
	 */
	@Override
	public String toString() { return cData.toString(); }

	/**
	 * Output this cData section to the given print writer wrapped in xml cData tags.
	 */
	@Override
	public PrintWriter toXml(PrintWriter pw) {
		pw.println(xb.getIndentStr()+"<![CDATA[");
		pw.println(cData);
		pw.println(xb.getIndentStr()+"]]>");
		return pw;
	}
	


}
