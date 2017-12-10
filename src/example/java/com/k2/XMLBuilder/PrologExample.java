package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class PrologExample {

	public static void main(String[] args) {
		
		XMLBuilder xb = new XMLBuilder();
		xb.document("root")
		.setVersion("0.9")
		.setEncoding("ISO 8859-1")
		.toXml(new PrintWriter(System.out)).flush();

	}

}
