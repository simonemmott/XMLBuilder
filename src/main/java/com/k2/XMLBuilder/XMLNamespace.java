package com.k2.XMLBuilder;

public class XMLNamespace {
	
	String ns;
	String url;
	
	public XMLNamespace(String ns, String url) {
		this.ns = ns;
		this.url = url;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ns == null) ? 0 : ns.hashCode());
		return result;
	}
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
