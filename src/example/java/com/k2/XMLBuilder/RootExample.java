package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class RootExample {

	public static void main(String[] args) {
		
		XMLBuilder xb = new XMLBuilder();
		xb.document("root")
		.elem("This")
			.elem("is")
				.elem("a")
					.elem("really")
						.elem("really")
							.elem("deep")
								.elem("tree")
								.root()
		.elem("This")
			.elem("is")
				.elem("another")
					.elem("really")
						.elem("really")
							.elem("deep")
								.elem("tree")
								.root()
		.toXml(new PrintWriter(System.out)).flush();

	}

}
