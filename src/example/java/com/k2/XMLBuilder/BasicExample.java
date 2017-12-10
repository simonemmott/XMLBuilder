package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class BasicExample {

	public static void main(String[] args) {
		
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
		.toXml(new PrintWriter(System.out)).flush();

	}

}
