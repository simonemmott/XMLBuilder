package com.k2.XMLBuilder;

import java.io.PrintWriter;

/**
 * Hello world!
 *
 */
public class XMLBuilderAPITest 
{
    public static void main( String[] args )
    {
    	
		XMLNamespace nsA = new XMLNamespace("a", "http://a.com");
		XMLNamespace nsB = new XMLNamespace("b", "http://b.com");
		XMLNamespace nsC = new XMLNamespace("c", "http://c.com");
		XMLNamespace nsD = new XMLNamespace("d", "http://d.com");
		
    		XMLBuilder xb = new XMLBuilder().setIndent("  ").includeProlog(false);
    		    		
    		xb.document("root", nsA)
    		.add(nsA)
    		.add(nsB)
    		.add(
    			xb.tag("tag1", nsA)
    			.set("attr1.1", "attr1.1-value")
    			.set("attr1.2", "attr1.2-value")
    			.add(xb.tag("tag1-data", nsA)
    				.add(xb.data("This is some data"))
    			)
    			.add(xb.tag("empty", nsA)
    				.set("attr", "value")
    			)
    		)
    		.add(
    			xb.tag("tag2", nsC)
    			.set("attr2.1", "attr2.1-value")
    			.set("attr2.2", "attr2.2-value<>'\"&")
    			.add(xb.tag("encoded", nsC)
    	    			.add(xb.data("This is encoded: < > ' \" &"))
    			)
    			.add(xb.tag("unencoded", nsD)
    	    			.add(xb.data("This is not encoded: < > ' \" &", false))
    			)
    			.add(xb.tag("cdata", nsB)
    				.add(xb.cData("THIS IS CDATA !@#$%^&*()"))
    			)
    			.add(xb.tag("encoded", nsD)
    	    			.add(xb.data("This should define the name space d"))
    			)
    		)
    		.set("root-attr", "root-value")
    		.toXml(new PrintWriter(System.out)).flush();
    		
    }
}
