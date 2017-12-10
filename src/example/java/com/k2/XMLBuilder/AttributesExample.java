package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class AttributesExample {

	public static void main(String[] args) {
		
		XMLBuilder xb = new XMLBuilder();
		xb.document("root")
		.attribute("root-attr", "root-value")
		.element("child-elm")
			.attribute("attr1", "Added by attribute(...)")
			.attr("attr2", "Added by attr(...)")
			.a("attr3", "Added by a(...)")
			.up()
		.element("child-elm")
			.attr("duplicate", "false")
			.attr("duplicate", "true")
			.up()
		.toXml(new PrintWriter(System.out)).flush();

	}

}
