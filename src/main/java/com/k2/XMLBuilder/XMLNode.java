package com.k2.XMLBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An abstract class defining nodes within an xml document.
 * @author simon
 *
 */
public abstract class XMLNode {
	
	protected XMLBuilder xb;
	protected XMLNode parent;
	List<XMLNode> contents;

	/**
	 * Create an xml node for the given xml builder
	 * @param xb The xml builder that created the xml node
	 */
	XMLNode(XMLBuilder xb) {
		this.xb = xb;
	}
	/**
	 * Set the parent of this node
	 * @param parent The xml node that is a parent of this node
	 * @return This node for method chaining
	 */
	XMLNode setParent(XMLNode parent) {
		this.parent = parent;
		return this;
	}
	
	/**
	 * Get the parent node of this node
	 * @return The parent node of this node
	 */
	XMLNode getParent() {
		return parent;
	}
	/**
	 * A utility method to move up the document path once the node has been fully defined.
	 * @return The parent node of this node as an XMLElement
	 */
	public XMLElement up() {
		return (XMLElement)parent;
	}
	/**
	 * A utility method to return to the docuement root once the node has been fully defined
	 * @return The root of the xml document
	 */
	public XMLDocument root() { return xb.root(); }
	/**
	 * Add a node as a child of this node setting this node as the added noded parent
	 * @param node The node to add
	 * @return This node for method chaining
	 */
	public XMLNode add(XMLNode node) {
		if (contents == null) { contents = new ArrayList<XMLNode>(); }
		contents.add(node);
		node.setParent(this);
		return this;
	}
	/**
	 * Remove a node from the child nodes of this node
	 * @param node The node to remove
	 * @return This node for method chaining
	 */
	public XMLNode remove(XMLNode node) {
		if (contents == null) { return this; }
		contents.remove(node);
		return this;
	}
	/**
	 * This abstract method is a place holder for implementation of the method to output the node in an xml document.
	 * @param pw The print writer used to write the xml document
	 * @return The print writer used to write the xml document for method chaining
	 */
	public abstract PrintWriter toXml(PrintWriter pw);

	/**
	 * This method is a convenience method to allow any writer to be used to generate the xml document
	 * @param w	The writer to output the document
	 * @return	The writer that was passed in for mthod chaining.
	 */
	public Writer toXml(Writer w) {
		toXml(new PrintWriter(w));
		return w;
	}

	/**
	 * This method is a convenience method to simplify the generation of xml files.
	 * @param f	The file to write the xml to
	 * @return The file that was passed in
	 * @throws FileNotFoundException
	 */
	public File toXml(File f) throws FileNotFoundException {
		toXml(new PrintWriter(f)).flush();
		return f;
	}

}
