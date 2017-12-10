package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class DataExample {

	public static void main(String[] args) {
		
		XMLBuilder xb = new XMLBuilder();
		xb.document("root")
		.elem("child-elm")
			.data("AAAA")
			.up()
		.elem("child-elm")
			.data("AAAA")
			.data("BBBB")
			.up()
		.elem("child-elm")
			.data("AAAA")
			.elem("child-child-elm")
				.data("aaaa")
				.up()
			.data("BBBB")
			.up()
		.elem("encoded")
			.data("This is encoded: < > ' \" & :")
			.up()
		.elem("unencoded")
			.data("This is unencoded: < > ' \" & : and unsafe", false)
			.up()
		.toXml(new PrintWriter(System.out)).flush();

	}

}
