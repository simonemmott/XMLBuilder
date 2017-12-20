package com.k2.XMLBuilder;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.k2.CssSelectorParser.CssElementFilter;
import com.k2.CssSelectorParser.CssElementFilterRule;
import com.k2.CssSelectorParser.CssSelectorParser;
import com.k2.XMLBuilder.XMLBuilder.NullAttributeHandling;

/**
 * This class represents an xml element within an xml document
 * 
 * Instances of xml elements can only be created through an instance of XMLBuilder
 * @author simon
 *
 */
public class XMLElement extends XMLNode {
	
	private String tag;
	private Map<String, String> attributes;
	private NullAttributeHandling nullAttributeHandling = null;
	private boolean emptyTag = false;
	private XMLNamespace namespace;
	private Set<XMLNamespace> namespaces;

	
	/**
	 * Create an instance of an xml element for the given xml builder and tag
	 * @param xb The xml builder that created the xml element
	 * @param tag The tag of the xml element
	 */
	protected XMLElement(XMLBuilder xb, String tag) {
		super(xb);
		this.tag = tag;
	}
	/**
	 * Create an instance of an xml element for the given xml builder, tag and namespace
	 * @param xb The xml builder that created the xml element
	 * @param tag The tag of the xml element
	 * @param namespace The namespace of the xml element
	 */
	protected XMLElement(XMLBuilder xb, String tag, XMLNamespace namespace) {
		super(xb);
		this.tag = tag;
		this.namespace = namespace;
	}
	
	/**
	 * This method casts this nodes parent as an XMLElement
	 * @return	This nodes parent cast as XMLElement
	 */
	public XMLElement parent() { return (XMLElement)parent; }
	
	/**
	 * A utility method to check whether the element defines the given attribute
	 * @param attribute	The attribute to check
	 * @return	True if this element has defined the attribute
	 */
	public boolean has(String attribute) {
		return attributes.containsKey(attribute);
	}
	
	/**
	 * A utilty method to get the previous sibling of this element
	 * @return	The previous sibling or null if there is no parent or this element is the first child
	 */
	public XMLElement getPreviousSibling() {
		if (parent == null) return null;
		int indexOf = parent.contents.indexOf(this);
		if (indexOf == 0) return null;
		return (XMLElement)parent.contents.get(indexOf - 1);
	}
	
	/**
	 * A utility method to get the next sibling of this element
	 * @return	The next sibling or null if there is not parent or this is the last child
	 */
	public XMLElement getNextSibling() {
		if (parent == null) return null;
		int indexOf = parent.contents.indexOf(this);
		if (indexOf == parent.contents.size()-1) return null;
		return (XMLElement)parent.contents.get(indexOf + 1);
	}
	
	/**
	 * Check whether this element matches the given CSS selector
	 * @param cssSelector The CSS selector to compare to this element
	 * @return	True if the element matches the CSS selector
	 */
	public boolean matches(String cssSelector) {
		return matches(CssSelectorParser.parse(cssSelector));
	}
	
	/**
	 * Test whether this element passes the given CSS selector rule
	 * @param rule The CSS selector rule to compare to this element
	 * @return True if the element passes the CSS selector rule
	 */
	private boolean matches(CssElementFilterRule rule) {
		
		String value;
		
		switch(rule.type) {
		case ANY_TAG:
			return true;
		case ATTRIBUTE_CONTAINS_STRING:
			if (attributes == null) return false;
			value = attributes.get(rule.attribute);
			if (value == null || "".equals(value)) return false;
			return value.contains(rule.check);
		case ATTRIBUTE_CONTAINS_WORD:
			if (attributes == null) return false;
			value = attributes.get(rule.attribute);
			if (value == null || "".equals(value)) return false;
			for (String word : value.split("\\s+")) if (word.equals(rule.check)) return true;
			return false;
		case ATTRIBUTE_ENDS_WITH:
			if (attributes == null) return false;
			value = attributes.get(rule.attribute);
			if (value == null || "".equals(value)) return false;
			return value.endsWith(rule.check);
		case ATTRIBUTE_STARTS_WITH:
			if (attributes == null) return false;
			value = attributes.get(rule.attribute);
			if (value == null || "".equals(value)) return false;
			return value.startsWith(rule.check);
		case ATTRIBUTE_STARTS_WITH_TILL_HYPHEN:
			if (attributes == null) return false;
			value = attributes.get(rule.attribute);
			if (value == null || "".equals(value)) return false;
			int indexOf = value.indexOf("-");
			if (indexOf == -1) return value.equals(rule.check);
			return value.substring(0, indexOf).equals(rule.check);
		case ATTRUBUTE_EQUALS:
			if (attributes == null) return false;
			value = attributes.get(rule.attribute);
			if (value == null || "".equals(value)) return false;
			return value.equals(rule.check);
		case HAS_ATTRIBUTE:
			if (attributes == null) return false;
			return attributes.containsKey(rule.attribute);
		case HAS_CLASS:
			if (attributes == null) return false;
			value = attributes.get("class");
			if (value == null || "".equals(value)) return false;
			for (String word : value.split("\\s+")) if (word.equals(rule.check)) return true;
			return false;
		case ID_EQUALS:
			if (attributes == null) return false;
			value = attributes.get("id");
			if (value == null || "".equals(value)) return false;
			return value.equals(rule.check);
		case TAG_EQUALS:
			return tag.equals(rule.check);
		default:
			break;
		
		}
		return false;
	}
	
	/**
	 * Check whether the element matches the given parsed CSS selector element filter
	 * @param filter The CSS element filter to compare to this element
	 * @return	True if this element matches the filter element
	 */
	boolean matches(CssElementFilter filter) {
		if (filter.elementFilterRules != null) {
			for (CssElementFilterRule rule : filter.elementFilterRules) {
				if (!matches(rule)) return false;
			}
		}
		
		if (filter.previousFilter == null) {
			return true;
		} else {
			switch(filter.rule) {
			case IS_ANCESTOR:
				if (parent == null) return false;
				XMLElement ancestor = ((XMLElement)parent);
				while (ancestor != null) {
					if (ancestor.matches(filter.previousFilter)) return true;
					if (ancestor.parent == null) return false;
					ancestor = ((XMLElement)ancestor.parent);
				}
				return false;
			case IS_PARENT:
				if (parent == null) return false;
				return ((XMLElement)parent).matches(filter.previousFilter);
			case NEXT_SIBLING:
				if (parent == null) return false;
				int i = parent.contents.indexOf(this);
				while (++i < parent.contents.size()) {
					if (XMLElement.class.isAssignableFrom(parent.contents.get(i).getClass())) {
						if (((XMLElement)parent.contents.get(i)).matches(filter.previousFilter)) return true;
					}
				}
				return false;
			case PREVIOUS_SIBLING:
				if (parent == null) return false;
				int j = parent.contents.indexOf(this);
				if (j == 0) return false;
				if (XMLElement.class.isAssignableFrom(parent.contents.get(j -1).getClass())) {
					return ((XMLElement)parent.contents.get(j -1)).matches(filter.previousFilter);
				} return false;
			default:
				return true;
			}
		}
	}
	
	/**
	 * Check whether this element matches at least one of the element filters in the list
	 * @param filters	A list of CSS elemnt filters
	 * @return	True if this element matches at least one of the element filters in the list
	 */
	private boolean matches(List<CssElementFilter> filters) {
		if (filters == null) return false;
		for (CssElementFilter filter : filters) if (matches(filter)) return true;
		return false;
	}
	
	/**
	 * Find all the ancestor element that match the given filters building up the results
	 * @param filters The filters to compare to this elements ancestors
	 * @param results The results identified do far in this recursion
	 * @return The results identified so far + the results for this check
	 */
	List<XMLElement> findUp(List<CssElementFilter> filters, List<XMLElement> results) {
		if (matches(filters)) results.add(this);
		if (parent == null) {
			return results; 
		} else {
			return ((XMLElement)parent).findUp(filters, results);
		}
	}
	
	/**
	 * Find the nearest ancestor that matches the given CSS selector
	 * 
	 * Note this will not check this element
	 * 
	 * @param cssSelector	The CSS selector to compare to this elements ancestors
	 * @return	The nearest ancestor that matches the given CSS selector
	 */
	public XMLElement nearest(String cssSelector) {
		if (parent == null) return null;
		XMLElement nearest = (XMLElement)parent;
		while (nearest != null) {
			if (nearest.matches(cssSelector)) return nearest;
			nearest = nearest.parent();
		}
		return null;
	}
	
	/**
	 * Find all the elements that match the given CSS selector from this elements ancestors.
	 * 
	 * Note this will not check or include this element
	 * 
	 * @param cssSelector The CSS selector to compare to this elements ancestors
	 * @return	A list of elements from this elements ancestors that match the given CSS selector
	 */
	public List<XMLElement> findUp(String cssSelector) {
		
		List<XMLElement> results = new ArrayList<XMLElement>();
		
		if (parent == null) {
			return results; 
		} else {
			return ((XMLElement)parent).findUp(CssSelectorParser.parse(cssSelector), results);
		}
	}
	
	/**
	 * This method returns the tag for this element
	 * @return the tag for this element
	 */
	public String getTag() { return tag; }
	
	/**
	 * This method returns the path to the element in the document
	 * @return The path to the element
	 */
	public String getPath() {
		if (parent!=null) return ((XMLElement)parent).getPath()+"/"+tag;
		return "/"+tag;
	}
	/**
	 * This method identifies that this element must be empty
	 * @return this element
	 */
	protected XMLElement emptyTag() {
		emptyTag = true;
		return this;
	}
	/**
	 * This method changes the tag of the element
	 * @param tag the new value for the tag
	 * @return this element
	 */
	protected XMLElement overrideTag(String tag) {
		this.tag = tag;
		return this;
	}
		
	/**
	 * This method defines how this element should handle null attributes. 
	 * If it is not set then the value from the xml builder is used which default to use the string 'null'
	 * @param nullAttributeHandling	the null attribute handling method
	 * @return	This instance of the xml element for method chaining
	 */
	public XMLElement setNullAttributeHandling(NullAttributeHandling nullAttributeHandling) {
		this.nullAttributeHandling = nullAttributeHandling;
		return this;
	}
	
	/**
	 * Set an attribute of the xml element
	 * @param attr The attribute to set
	 * @param value The attribute value to set
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement set(String attr, String value) {
		if (attributes == null) attributes = new HashMap<String, String>();
		attributes.put(attr, value);
		return this;
	}
	
	/**
	 * Get the value of an attribute
	 * @param attr	The name of the attribute to get
	 * @return	The value of the attribute
	 */
	public String get(String attr) {
		if (attributes == null) return null;
		return attributes.get(attr);
	}
	/**
	 * An alternative name for the set method to set an attribute of this element
	 * @param attr The attribute to set
	 * @param value The attribute value to set
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement attribute(String attr, String value) { return set(attr, value); }
	/**
	 * An alternative name for the set method to set an attribute of this element
	 * @param attr The attribute to set
	 * @param value The attribute value to set
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement attr(String attr, String value) { return set(attr, value); }
	/**
	 * An alternative name for the set method to set an attribute of this element
	 * @param attr The attribute to set
	 * @param value The attribute value to set
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement a(String attr, String value) { return set(attr, value); }
	/**
	 * Remove the named attribute from the element
	 * @param attr The name of the attribute to remove
	 * @return This instance of the xml element for method chaining
	 */
	public XMLElement remove(String attr) {
		if (attributes == null) return this;
		attributes.remove(attr);
		return this;
	}
	
	/**
	 * A utility method to create and add an xml element as a child of this element
	 * @param tag The tag of the element to create and add
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement element(String tag) {
		if (emptyTag) throw new XMLBuilderError("Unable to add element with tag: "+tag+" to this element with tag: "+this.tag+" that is defined to be empty");
		XMLElement el = xb.element(tag);
		super.add(el);
		return el;
	}
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element
	 * @param tag The tag of the element to create and add
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement elem(String tag) { return element(tag); }
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element
	 * @param tag The tag of the element to create and add
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement el(String tag) { return element(tag); }
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element
	 * @param tag The tag of the element to create and add
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement e(String tag) { return element(tag); }
	/**
	 * A utility method to create and add an xml element as a child of this element and set the child elements namespace
	 * @param tag The tag of the element to create and add
	 * @param ns The namespace of the new element
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement element(String tag, XMLNamespace ns) {
		if (emptyTag) throw new XMLBuilderError("Unable to add element with tag: "+tag+" to this element with tag: "+this.tag+" that is defined to be empty");
		XMLElement el = xb.element(tag, ns);
		super.add(el);
		return el;
	}
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element and set
	 * the child elements namespace
	 * @param tag tag The tag of the element to create and add
	 * @param ns The namespace of the new element
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement elem(String tag, XMLNamespace ns) { return element(tag, ns); }
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element and set
	 * the child elements namespace
	 * @param tag tag The tag of the element to create and add
	 * @param ns The namespace of the new element
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement el(String tag, XMLNamespace ns) { return element(tag, ns); }
	/**
	 * An alternative name for the utility method to create and add a xml element as a child of this element and set
	 * the child elements namespace
	 * @param tag tag The tag of the element to create and add
	 * @param ns The namespace of the new element
	 * @return The xml element that was added as a child for method chaining
	 */
	public XMLElement e(String tag, XMLNamespace ns) { return element(tag, ns); }
	
	/**
	 * A utilty method to create and add a data section as a child of this element
	 * @param data The string representing the data for the section
	 * @return This xml element for method chaining
	 */
	public XMLElement data(String data) {
		return (XMLElement)super.add(xb.data(data));
	}
	/**
	 * A utility method to create and add a data section as a child of this element and define whether or not
	 * to encode the given data
	 * @param data A string representing the data to add
	 * @param encoded Whether or not to encode the given data string. Default true
	 * @return This xml element for method chaining
	 */
	public XMLElement data(String data, boolean encoded) {
		return (XMLElement)super.add(xb.data(data, encoded));
	}
	/**
	 * An alternative name for the utility method to create and add a data section as a child of this element
	 * @param data A string representing the data to add
	 * @return This xml element for method chaining
	 */
	public XMLElement d(String data) { return data(data); }
	/**
	 * An alternative name for the utility method to create and add a data section as a child of this element and define whether or not
	 * to encode the given data
	 * @param data A string representing the data to add
	 * @param encoded Whether or not to encode the given data string. Default true
	 * @return This xml element for method chaining
	 */
	public XMLElement d(String data, boolean encoded) { return data(data, encoded); }
	/**
	 * A utility method to create and add a cData section as a child of this element
	 * @param cData A string representing the data for the cData section
	 * @return This xml element for method chaining
	 */
	public XMLElement cData(String cData) {
		return (XMLElement)super.add(xb.cData(cData));
	}
	/**
	 * An alternativ name for the utility method to create and add a cData section as a child of this element
	 * @param cData A string representing the data for the cData section
	 * @return This xml element for method chaining
	 */
	public XMLElement c(String cData) { return cData(cData); }
	/**
	 * A utility method to create and add a cData section as a child of this element
	 * @param cData A string builder representing the data for this cData section
	 * @return This xml element for method chaining
	 */
	public XMLElement cData(StringBuilder cData) {
		return (XMLElement)super.add(xb.cData(cData));
	}
	/**
	 * An alternativ name for the utility method to create and add a cData section as a child of this element
	 * @param cData A string builder representing the data for this cData section
	 * @return This xml element for method chaining
	 */
	public XMLElement c(StringBuilder cData) { return cData(cData); }
	
	/**
	 * Ensure that the method to add a child node returns this instance as an XMLElement
	 */
	@Override
	public XMLElement add(XMLNode node) {		
		if (emptyTag) throw new XMLBuilderError("Unable to add element with tag: "+tag+" to this element with tag: "+this.tag+" that is defined to be empty");
		return (XMLElement)super.add(node);
	}
	/**
	 * Ensure that the method to remove a child node returns this instance as an XMLElement
	 */
	@Override
	public XMLElement remove(XMLNode node) {
		return (XMLElement)super.remove(node);
	}
	/**
	 * Ensure that the method to set the namespace of this element returns this instance as an XMLElement
	 */
	
	/**
	 * Output this element as well formed xml including and defining namespaces as necessary
	 */
	@Override
	public PrintWriter toXml(PrintWriter pw) {
		
		// If the element has a namespace prepare the namespace prefix
		String nsPrefix = "";
		if (getNamespace()!=null) nsPrefix = getNamespace().ns+":";
		
		// Write the beginning for the elements start tag after the current indent
		pw.print(xb.getIndentStr()+"<"+nsPrefix+tag);
		
		// If this element has a namespace that has not already been defined in the current document path
		// Write the namespace definition as an attribute of the element
		if (!xb.defined(getNamespace())) {
			pw.print(" xmlns:"+getNamespace().ns+"=\""+getNamespace().url+"\"");
			xb.define(getNamespace());
		}

		// If this element includes namespace defintions that have not already been defined in the current document path
		// Write the namespace definitions as attributes of the element
		if (namespaces != null) {
			for (XMLNamespace namespace : namespaces) {
				if (!xb.defined(namespace)) {
					pw.print(" xmlns:"+namespace.ns+"=\""+namespace.url+"\"");
					xb.define(namespace);
				}
			}
		}
		
		// Add any attributes that have been defined for the element xml encoding the attribute values
		if (attributes != null) {
			for (String attr : attributes.keySet()) {
				NullAttributeHandling nullHandling = this.nullAttributeHandling;
				if (nullHandling == null) nullHandling = xb.getNullAttributeHandling();
				switch (nullHandling) {
				case BLANK:
					if (attributes.get(attr) == null) {
						pw.print(" "+attr);
					} else {
						pw.print(" "+attr+"=\""+XMLBuilder.encodeXmlAttribute(attributes.get(attr))+"\"");
					}
					break;
				case SKIP:
					if (attributes.get(attr) != null) {
						pw.print(" "+attr+"=\""+XMLBuilder.encodeXmlAttribute(attributes.get(attr))+"\"");
					}
					break;
				case USE_NULL:
					pw.print(" "+attr+"=\""+XMLBuilder.encodeXmlAttribute(attributes.get(attr))+"\"");
					break;
				default:
					pw.print(" "+attr+"=\""+XMLBuilder.encodeXmlAttribute(attributes.get(attr))+"\"");
					break;
				
				}
			}
		}
		
		// If this element has no contents close the element
		if (contents==null||contents.size()==0) {
			if (xb.optionalEndTagsAllowed() && emptyTag) {
				pw.println(">");
			} else {
				pw.println("/>");
			}
		}
		// Otherwise write the child contents of this element
		else {
			// If there is only one child element and that elemnt is a data element
			// Write the data element on the same line as the open element tag and close the element
			if (contents.size()==1&&contents.get(0).getClass()==XMLData.class) {
				pw.println(">"+contents.get(0).toString()+"</"+nsPrefix+tag+">");
			}
			// Otherwise close the elements start tag and writer all the child elements indented
			// And close the element after reducing the indent
			else {
				pw.println(">");
				xb.indent();
				for (XMLNode node : contents) node.toXml(pw);
				xb.outdent();
				pw.println(xb.getIndentStr()+"</"+nsPrefix+tag+">");
			}
		}
		
		// If this element has a parent node and defines namespaces undefine the namespaces in the xml builder that are not also defined 
		// higher up the document path
		if (parent != null) {
			if (namespaces != null) for (XMLNamespace namespace : namespaces) if (!((XMLElement)parent).defined(namespace)) xb.undefine(namespace);
			if (!((XMLElement)parent).defined(namespace)) xb.undefine(namespace);
		} else {
			if (namespaces != null) for (XMLNamespace namespace : namespaces) xb.undefine(namespace);
			xb.undefine(namespace);
		}
		
		// Return the print writer used to output the xml element for method chaining
		return pw;
	}
	
	/**
	 * Get the namespace of this element
	 * @return This elements namespace
	 */
	public XMLNamespace getNamespace() { return namespace; }	
	/**
	 * Set the namespace for this element
	 * @param namespace The namespace to set for this element
	 * @return This element for method chaining
	 */
	public XMLElement set(XMLNamespace namespace) {
		this.namespace = namespace;
		return this;
	}
	/**
	 * Add a namepsace definition to this element
	 * @param namespace The namespace definition to add
	 * @return This element for method chaining
	 */
	public XMLElement add(XMLNamespace namespace) {
		if (namespaces == null) { namespaces = new HashSet<XMLNamespace>(); }
		namespaces.add(namespace);
		return this;
	}
	/**
	 * Remove a namespace definition from this element
	 * @param namespace The namespace definition to remove
	 * @return This element for method chaining
	 */
	public XMLElement remove(XMLNamespace namespace) {
		if (namespaces == null) { return this; }
		namespaces.remove(namespace);
		return this;
	}
	/**
	 * Check whether the given namespace is defined in this element or any of this elements parent nodes
	 * 
	 * This method will return true if the namespace to check is null
	 * This method will return true if this element is in the namespace to check of if the namespace to check is in
	 * the list of namespaces defined on this element
	 * Otherwise if this element has a parent then this method returns the value of this method called on this elements parent
	 * Otherwise it returns false
	 * 
	 * @param namespace The namespace to check
	 * @return True if this element or any of its parent elements define the namespace to check
	 */
	public boolean defined(XMLNamespace namespace) {
		if (namespace == null) return true;
		if (namespace.equals(getNamespace())) return true;
		if (namespaces!=null && namespaces.contains(namespace)) return true;
		if (parent!=null) return ((XMLElement)parent).defined(namespace);
		return false;
	}
	
	/**
	 * Output this element as string without carriage returns or namespaces but including xml encoding.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<"+tag);
		if (attributes != null) for (String attr : attributes.keySet()) sb.append(" "+attr+"=\""+XMLBuilder.encodeXmlAttribute(attributes.get(attr))+"\"");
		if (contents==null||contents.size()==0) sb.append("/>");
		else {
			sb.append(">");
			for (XMLNode node : contents) sb.append(node.toString());
			sb.append("</"+tag+">");
		}
		return sb.toString();
	}
	
	

}
