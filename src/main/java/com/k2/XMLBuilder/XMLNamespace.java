package com.k2.XMLBuilder;

/**
 * A simple utility class to define xml namespaces
 * 
 * @author simon
 *
 */
public class XMLNamespace {
	
	String ns;
	String url;
	
	/**
	 * Create an instance of an xml namespace defining the namespace and namespace url
	 * @param ns The namespace prefix to be prepended to elements in this namespace
	 * @param url The url documenting this namespace
	 */
	public XMLNamespace(String ns, String url) {
		this.ns = ns;
		this.url = url;
	}
	
	/**
	 * The equality check for xml namespaces is based on the namespace prefix
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ns == null) ? 0 : ns.hashCode());
		return result;
	}
	/**
	 * The equality check for xml namespaces is based on the namespace prefix
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XMLNamespace other = (XMLNamespace) obj;
		if (ns == null) {
			if (other.ns != null)
				return false;
		} else if (!ns.equals(other.ns))
			return false;
		return true;
	}

}
