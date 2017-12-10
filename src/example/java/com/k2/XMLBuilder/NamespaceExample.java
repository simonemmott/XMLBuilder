package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class NamespaceExample {

	public static void main(String[] args) {
		
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
		.toXml(new PrintWriter(System.out)).flush();

	}

}
