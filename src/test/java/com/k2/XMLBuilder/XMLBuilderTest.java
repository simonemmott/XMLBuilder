package com.k2.XMLBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit tests for XMLBuilder.
 */
public class XMLBuilderTest 
{
	@Test
    public void basicTest()
    {
    	
    		String expectedResult = 	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
    								"<root>\n" + 
    								"	<elm attr=\"value\">AAAA</elm>\n" + 
    								"	<elm attr=\"value2\">BBBB</elm>\n" + 
    								"</root>\n";
    	
    		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
    		
		XMLBuilder xb = new XMLBuilder();
		xb.document("root")
		.elem("elm")
			.attr("attr", "value")
			.data("AAAA")
			.up()
		.elem("elm")
			.attr("attr", "value2")
			.data("BBBB")
			.up()
		.toXml(pw).flush();

		pw.close();
		
        assertEquals( expectedResult, sw.toString());
    }

	@Test
    public void indentAndPrologTest()
    {
    	
    		String expectedResult = 	"<root>\n" + 
    								"  <elm attr=\"value\">\n" +
    								"    AAAA\n" + 
    								"    <elm attr=\"value2\">BBBB</elm>\n" + 
    								"  </elm>\n" +
    								"</root>\n";
    	
    		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
    		
		XMLBuilder xb = new XMLBuilder().setIndent("  ").includeProlog(false);
		xb.document("root")
		.elem("elm")
			.attr("attr", "value")
			.data("AAAA")
			.elem("elm")
				.attr("attr", "value2")
				.data("BBBB")
				.up()
			.up()
		.toXml(pw).flush();

		pw.close();
		
        assertEquals( expectedResult, sw.toString());
    }
	
	@Test
    public void versionAndCharacterEncodingTest()
    {
    	
    		String expectedResult = 	"<?xml version=\"0.9\" encoding=\"ISO 8859-1\"?>\n" + 
    								"<root>\n" + 
    								"	<elm attr=\"value\">AAAA</elm>\n" + 
    								"	<elm attr=\"value2\">BBBB</elm>\n" + 
    								"</root>\n";
    	
    		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
    		
    		XMLBuilder xb = new XMLBuilder();
    		xb.document("root")
    		.setVersion("0.9")
    		.setEncoding("ISO 8859-1")
    		.elem("elm")
    			.attr("attr", "value")
    			.data("AAAA")
    			.up()
    		.elem("elm")
    			.attr("attr", "value2")
    			.data("BBBB")
    			.up()
    		.toXml(pw).flush();

    		pw.close();
		
        assertEquals( expectedResult, sw.toString());
    }

	@Test
    public void attributesAndEncodingTest()
    {
    	
    		String expectedResult = 	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
    								"<root root-attr=\"root-value\">\n" + 
    								"	<elm duplicated=\"true\" attr=\"value\" encoded=\": &lt; &gt; &apos; &quot; &amp; :\">AAAA</elm>\n" + 
    								"	<elm>\n" + 
    								"		Encoded: &lt; &gt; &apos; &quot; &amp; :\n" + 
    								"		Unencoded: < > ' \" & :\n" + 
    								"	</elm>\n" + 
    								"</root>\n";
    	
    		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
    		
		XMLBuilder xb = new XMLBuilder();
		xb.document("root")
		.attr("root-attr", "root-value")
		.elem("elm")
			.attr("attr", "value")
			.attr("duplicated", "false")
			.attr("duplicated", "true")
			.attr("encoded", ": < > ' \" & :")
			.data("AAAA")
			.up()
		.elem("elm")
			.data("Encoded: < > ' \" & :", true)
			.data("Unencoded: < > ' \" & :", false)
			.up()
		.toXml(pw).flush();

		pw.close();
		
        assertEquals( expectedResult, sw.toString());
    }

	@Test
    public void cDataTest()
    {
    	
    		String expectedResult = 	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
    								"<root>\n" + 
    								"	<elm>\n" + 
    								"		<![CDATA[\n" + 
    								"AAAA\n" + 
    								"		]]>\n" + 
    								"	</elm>\n" + 
    								"	<elm>\n" + 
    								"		<![CDATA[\n" + 
    								"!@#$%^&*()_+\n" + 
    								"QWERTYUIOP{}\n" + 
    								"ASDFGHJKL:\"|\n" + 
    								"~ZXCVBNM<>?\n" + 
    								"1234567890-=\n" + 
    								"qwertyuiop[]\n" + 
    								"asdfghjkl;'\\\n" + 
    								"`zxcvbnm,./\n" + 
    								"		]]>\n" + 
    								"	</elm>\n" + 
    								"</root>\n";
    	
    		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
    		
    		StringBuilder sb = new StringBuilder();
    		sb.append("!@#$%^&*()_+\n")
    			.append("QWERTYUIOP{}\n")
    			.append("ASDFGHJKL:\"|\n")
    			.append("~ZXCVBNM<>?\n");
    		
		XMLBuilder xb = new XMLBuilder();
		XMLDocument doc = xb.document("root");
		doc.elem("elm")
			.cData("AAAA")
			.up()
		.elem("elm")
			.cData(sb)
			.up();
		
		sb.append("1234567890-=\n")
			.append("qwertyuiop[]\n")
			.append("asdfghjkl;'\\\n")
			.append("`zxcvbnm,./");
		
		doc.toXml(pw).flush();

		pw.close();
		
        assertEquals( expectedResult, sw.toString());
    }

	@Test
    public void namespacesTest()
    {
    	
    		String expectedResult = 	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
    								"<root xmlns:a=\"http://a.com\" xmlns:b=\"http://b.com\">\n" + 
    								"	<a:elm>AAAA</a:elm>\n" + 
    								"	<b:elm>BBBB</b:elm>\n" + 
    								"	<c:elm xmlns:c=\"http://c.com\">\n" + 
    								"		CCCC\n" + 
    								"		<c:child-elm>cccc</c:child-elm>\n" + 
    								"	</c:elm>\n" + 
    								"</root>\n";
    	
    		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
    		
    		XMLNamespace nsA = new XMLNamespace("a", "http://a.com");
    		XMLNamespace nsB = new XMLNamespace("b", "http://b.com");
    		XMLNamespace nsC = new XMLNamespace("c", "http://c.com");
    		
		XMLBuilder xb = new XMLBuilder();
		xb.document("root")
		.add(nsA)
		.add(nsB)
		.elem("elm", nsA)
			.data("AAAA")
			.up()
		.elem("elm", nsB)
			.data("BBBB")
			.up()
		.elem("elm", nsC)
			.data("CCCC")
			.elem("child-elm", nsC)
				.data("cccc")
				.up()
			.up()
		.toXml(pw).flush();

		pw.close();
		
        assertEquals( expectedResult, sw.toString());
    }



}
