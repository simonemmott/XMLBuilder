package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class ElementsExample {

	public static void main(String[] args) {
		
		XMLBuilder xb = new XMLBuilder();
		xb.document("root")
		.element("child-elm")
			.attr("added-by", "element(...)")
			.up()
		.elem("child-elm")
			.attr("added-by", "elem(...)")
			.up()
		.el("child-elm")
			.attr("added-by", "el(...)")
			.up()
		.e("child-elm")
			.attr("added-by", "e(...)")
			.elem("child-child-elm")
				.elem("child-child-child-elm")
					.up()
				.up()
			.up()
		.toXml(new PrintWriter(System.out)).flush();

	}

}
